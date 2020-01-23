package com.awcsoftware.spring;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "com.awcsoftware")
public class SpringConfig extends WebMvcConfigurationSupport {
	
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter() {
    	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //do your customizations here...
        return converter;
    }
    
  
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/"); 
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
