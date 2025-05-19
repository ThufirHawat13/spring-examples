package org.example.config;

import org.example.beanPostProcessor.TestClassRandomIntInjectionBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPostProcessorsConfiguration {

  @Bean
  public static TestClassRandomIntInjectionBeanPostProcessor testClassRandomIntInjectionBeanPostProcessor() {
    return new TestClassRandomIntInjectionBeanPostProcessor();
  }
}
