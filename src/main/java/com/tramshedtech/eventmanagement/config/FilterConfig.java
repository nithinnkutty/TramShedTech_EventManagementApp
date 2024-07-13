package com.tramshedtech.eventmanagement.config;

import com.tramshedtech.eventmanagement.filter.AuthFliter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter Configuration Class: Used to specify which requests are filtered by the filter
 * @Configuration Indicates that the current class is a configuration class with the same functionality as the previous spring-context.xml file.
 */

@Configuration
public class FilterConfig {
    /**
     * FilterRegistrationBean:Used to register the specified filter with the server, as the filter cannot take effect without registration
     * <AuthFilter>: Which filter to register
     * Methods in the configuration class must be publicly modified
     * You need to add an annotation to the method: @Bean <bean><bean/>, spring framework will automatically call the method to get the object and put it into the IOC container when it scans for a bean annotation on the method
     * @return
     */

    @Bean
    public FilterRegistrationBean<AuthFliter> filterRegistrationBean(){

        FilterRegistrationBean<AuthFliter> bean = new FilterRegistrationBean<>();

        // Set which requests are filtered by the current filter
        List<String> patterns = new ArrayList<>();
        patterns.add("/*");  // Filter all requests
        bean.setUrlPatterns(patterns);

        // Setting up filters
        bean.setFilter(new AuthFliter());
        return bean;
    }
}
