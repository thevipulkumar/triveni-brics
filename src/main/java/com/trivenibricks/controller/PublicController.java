package com.trivenibricks.controller;

import com.trivenibricks.model.Enquiry;
import com.trivenibricks.repository.EnquiryRepository;
import com.trivenibricks.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Every page a customer sees. */
@Controller
public class PublicController {

    private final ProductRepository products;
    private final EnquiryRepository enquiries;

    public PublicController(ProductRepository products, EnquiryRepository enquiries) {
        this.products = products;
        this.enquiries = enquiries;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", products.findByAvailableTrueOrderBySortOrderAsc());
        model.addAttribute("page", "home");
        return "index";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", products.findByAvailableTrueOrderBySortOrderAsc());
        model.addAttribute("page", "products");
        return "products";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("page", "about");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model, @RequestParam(required = false) String type) {
        if (!model.containsAttribute("enquiry")) {
            Enquiry enquiry = new Enquiry();
            enquiry.setBrickType(type);
            model.addAttribute("enquiry", enquiry);
        }
        model.addAttribute("products", products.findByAvailableTrueOrderBySortOrderAsc());
        model.addAttribute("page", "contact");
        return "contact";
    }

    @PostMapping("/contact")
    public String submitEnquiry(@Valid @ModelAttribute("enquiry") Enquiry enquiry,
                                BindingResult binding,
                                Model model,
                                RedirectAttributes redirect) {
        if (binding.hasErrors()) {
            model.addAttribute("products", products.findByAvailableTrueOrderBySortOrderAsc());
            model.addAttribute("page", "contact");
            return "contact";
        }
        enquiries.save(enquiry);
        redirect.addFlashAttribute("savedName", enquiry.getCustomerName());
        return "redirect:/thank-you";
    }

    @GetMapping("/thank-you")
    public String thankYou(Model model) {
        model.addAttribute("page", "contact");
        return "thank-you";
    }
}
