package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:test-application.properties")
public class PropertiesConfiguration {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    System.out.println("PROP");
    return new PropertySourcesPlaceholderConfigurer();
  }
}
