package dev.priyanshuvishnoi.splitwiseclonebackend.configuration;

import dev.priyanshuvishnoi.splitwiseclonebackend.utils.ApiResponseInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public ApiResponseInterceptor apiResponseInterceptor() {
        return new ApiResponseInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiResponseInterceptor());
    }
}
