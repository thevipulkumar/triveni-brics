package com.trivenibricks.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * All the business details in one place.
 * Edit these in src/main/resources/application.properties -- no Java changes needed.
 */
@Component
@ConfigurationProperties(prefix = "site")
public class SiteProperties {

    private String nameEn = "Triveni Bricks";
    private String nameHi = "त्रिवेणी ब्रिक्स";
    private String ownerEn = "";
    private String ownerHi = "";
    private String phone = "";
    private String phoneAlt = "";
    private String whatsapp = "";
    private String email = "";
    private String addressEn = "";
    private String addressHi = "";
    private String cityEn = "";
    private String cityHi = "";
    private String gstin = "";
    private String establishedYear = "";
    private String openHoursEn = "";
    private String openHoursHi = "";
    private String mapUrl = "";
    private String adminPassword = "changeme";

    /** How many years the kiln has been running, worked out from the established year. */
    public int getYearsInBusiness() {
        try {
            return java.time.Year.now().getValue() - Integer.parseInt(establishedYear.trim());
        } catch (RuntimeException e) {
            return 0;
        }
    }

    /** Phone with country code and no spaces, for tel: links. */
    public String getPhoneLink() {
        return toLink(phone);
    }

    public String getPhoneAltLink() {
        return toLink(phoneAlt);
    }

    /** Digits only with 91 prefix, for wa.me links. */
    public String getWhatsappLink() {
        String source = (whatsapp == null || whatsapp.isBlank()) ? phone : whatsapp;
        if (source == null) {
            return "";
        }
        String digits = source.replaceAll("[^0-9]", "");
        if (digits.length() == 10) {
            digits = "91" + digits;
        }
        return digits;
    }

    private String toLink(String value) {
        if (value == null) {
            return "";
        }
        String digits = value.replaceAll("[^0-9+]", "");
        if (digits.length() == 10) {
            digits = "+91" + digits;
        }
        return digits;
    }

    public String getNameEn() { return nameEn; }
    public void setNameEn(String nameEn) { this.nameEn = nameEn; }

    public String getNameHi() { return nameHi; }
    public void setNameHi(String nameHi) { this.nameHi = nameHi; }

    public String getOwnerEn() { return ownerEn; }
    public void setOwnerEn(String ownerEn) { this.ownerEn = ownerEn; }

    public String getOwnerHi() { return ownerHi; }
    public void setOwnerHi(String ownerHi) { this.ownerHi = ownerHi; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPhoneAlt() { return phoneAlt; }
    public void setPhoneAlt(String phoneAlt) { this.phoneAlt = phoneAlt; }

    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddressEn() { return addressEn; }
    public void setAddressEn(String addressEn) { this.addressEn = addressEn; }

    public String getAddressHi() { return addressHi; }
    public void setAddressHi(String addressHi) { this.addressHi = addressHi; }

    public String getCityEn() { return cityEn; }
    public void setCityEn(String cityEn) { this.cityEn = cityEn; }

    public String getCityHi() { return cityHi; }
    public void setCityHi(String cityHi) { this.cityHi = cityHi; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getEstablishedYear() { return establishedYear; }
    public void setEstablishedYear(String establishedYear) { this.establishedYear = establishedYear; }

    public String getOpenHoursEn() { return openHoursEn; }
    public void setOpenHoursEn(String openHoursEn) { this.openHoursEn = openHoursEn; }

    public String getOpenHoursHi() { return openHoursHi; }
    public void setOpenHoursHi(String openHoursHi) { this.openHoursHi = openHoursHi; }

    public String getMapUrl() { return mapUrl; }
    public void setMapUrl(String mapUrl) { this.mapUrl = mapUrl; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
}
