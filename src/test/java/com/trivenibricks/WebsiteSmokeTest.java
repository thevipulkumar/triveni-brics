package com.trivenibricks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Checks that every page opens, both languages work, the enquiry form saves,
 * and the owner's dashboard stays locked. Runs on a throwaway in-memory database.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "site.admin-password=test-password"
})
class WebsiteSmokeTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void everyPublicPageOpens() throws Exception {
        for (String path : new String[]{"/", "/products", "/about", "/contact", "/thank-you"}) {
            mvc.perform(get(path)).andExpect(status().isOk());
        }
    }

    @Test
    void homePageIsInEnglishByDefault() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("First Class Bricks")));
    }

    @Test
    void homePageSwitchesToHindi() throws Exception {
        mvc.perform(get("/").param("lang", "hi"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("प्रथम श्रेणी ईंट")));
    }

    @Test
    void aValidEnquiryIsSaved() throws Exception {
        mvc.perform(post("/contact")
                        .param("customerName", "Ramesh Kumar")
                        .param("phone", "9876543210")
                        .param("place", "Bilaspur"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/thank-you"));
    }

    @Test
    void anEnquiryWithoutAPhoneNumberIsRejected() throws Exception {
        mvc.perform(post("/contact")
                        .param("customerName", "Ramesh Kumar")
                        .param("phone", "12")
                        .param("place", "Bilaspur"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("enquiry", "phone"));
    }

    @Test
    void dashboardIsClosedWithoutLogin() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/login"));
    }

    @Test
    void dashboardOpensWithTheRightPassword() throws Exception {
        mvc.perform(post("/admin/login").param("password", "test-password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    void dashboardRefusesAWrongPassword() throws Exception {
        mvc.perform(post("/admin/login").param("password", "not-the-password"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loginError", true));
    }
}
