package br.com.eicon.api.consultacredito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ExceptionMessageBundleConfig {

    @Bean
    public ReloadableResourceBundleMessageSource exceptionResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource r = new ReloadableResourceBundleMessageSource();
        r.setDefaultEncoding("ISO-8859-1");
        r.setBasename("classpath:exception");
        return r;
    }

}