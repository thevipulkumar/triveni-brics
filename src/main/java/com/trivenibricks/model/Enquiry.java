package com.trivenibricks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** A quote request sent in by a customer from the website. */
@Entity
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{enquiry.error.name}")
    @Size(max = 80)
    private String customerName;

    @NotBlank(message = "{enquiry.error.phone}")
    @Pattern(regexp = "^[0-9+\\-\\s]{10,15}$", message = "{enquiry.error.phone}")
    private String phone;

    /** Village / town / city where the bricks must be delivered. */
    @NotBlank(message = "{enquiry.error.place}")
    @Size(max = 120)
    private String place;

    /** Which brick type they want. Free text so it also works if they type it. */
    @Size(max = 120)
    private String brickType;

    /** How many bricks, roughly. Free text: "10,000" or "2 trolley". */
    @Size(max = 60)
    private String quantity;

    @Size(max = 1000)
    @Column(length = 1000)
    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();

    /** Marked done once your father has called the customer back. */
    private boolean handled;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getBrickType() { return brickType; }
    public void setBrickType(String brickType) { this.brickType = brickType; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isHandled() { return handled; }
    public void setHandled(boolean handled) { this.handled = handled; }

    /** Date and time in a form the owner can read at a glance. */
    public String getCreatedAtDisplay() {
        if (createdAt == null) {
            return "";
        }
        return createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    /** Digits only, for building a wa.me WhatsApp link. */
    public String getWhatsappDigits() {
        if (phone == null) {
            return "";
        }
        String digits = phone.replaceAll("[^0-9]", "");
        if (digits.length() == 10) {
            digits = "91" + digits;
        }
        return digits;
    }
}
