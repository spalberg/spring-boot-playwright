package de.palberg.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class PlaywrightConfiguration {

    @Bean
    public Playwright playwright() {
        return Playwright.create();
    }

    @Bean
    public Browser chromium(Playwright playwright) {
        return playwright.chromium().launch();
    }
}
