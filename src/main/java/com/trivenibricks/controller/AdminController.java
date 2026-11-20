package com.trivenibricks.controller;

import com.trivenibricks.config.AdminAuthInterceptor;
import com.trivenibricks.config.SiteProperties;
import com.trivenibricks.repository.EnquiryRepository;
import com.trivenibricks.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** The owner's private pages: see enquiries, change prices. */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EnquiryRepository enquiries;
    private final ProductRepository products;
    private final SiteProperties site;

    public AdminController(EnquiryRepository enquiries, ProductRepository products, SiteProperties site) {
        this.enquiries = enquiries;
        this.products = products;
        this.site = site;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("page", "admin");
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String password, HttpSession session, Model model) {
        if (password != null && password.equals(site.getAdminPassword())) {
            session.setAttribute(AdminAuthInterceptor.SESSION_KEY, Boolean.TRUE);
            return "redirect:/admin";
        }
        model.addAttribute("loginError", true);
        model.addAttribute("page", "admin");
        return "admin/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(AdminAuthInterceptor.SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("enquiries", enquiries.findAllByOrderByCreatedAtDesc());
        model.addAttribute("pendingCount", enquiries.countByHandledFalse());
        model.addAttribute("products", products.findAllByOrderBySortOrderAsc());
        model.addAttribute("page", "admin");
        return "admin/dashboard";
    }

    @PostMapping("/enquiry/{id}/handled")
    public String toggleHandled(@PathVariable Long id) {
        enquiries.findById(id).ifPresent(e -> {
            e.setHandled(!e.isHandled());
            enquiries.save(e);
        });
        return "redirect:/admin";
    }

    @PostMapping("/enquiry/{id}/delete")
    public String deleteEnquiry(@PathVariable Long id) {
        enquiries.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/product/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam Integer price,
                                @RequestParam(defaultValue = "false") boolean available) {
        products.findById(id).ifPresent(p -> {
            p.setPrice(price);
            p.setAvailable(available);
            products.save(p);
        });
        return "redirect:/admin#prices";
    }

    @PostMapping("/product/{id}/popular")
    public String markPopular(@PathVariable Long id) {
        products.findAll().forEach(p -> {
            boolean shouldBePopular = p.getId().equals(id) && !p.isPopular();
            if (p.isPopular() != shouldBePopular) {
                p.setPopular(shouldBePopular);
                products.save(p);
            }
        });
        return "redirect:/admin#prices";
    }
}
