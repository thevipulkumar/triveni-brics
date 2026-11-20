package com.trivenibricks.config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Puts the business details and current language into every page. */
@ControllerAdvice
public class GlobalModelAdvice {

    private final SiteProperties site;

    public GlobalModelAdvice(SiteProperties site) {
        this.site = site;
    }

    @ModelAttribute("site")
    public SiteProperties site() {
        return site;
    }

    /** true when the visitor is reading the site in Hindi. */
    @ModelAttribute("hi")
    public boolean hindi() {
        return "hi".equals(LocaleContextHolder.getLocale().getLanguage());
    }

    @ModelAttribute("lang")
    public String lang() {
        return hindi() ? "hi" : "en";
    }
}
