package org.example.config;

import org.example.model.TestClass;
import org.example.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeansConfiguration {

  @Bean
  public TestService testService() {
    return new TestService() {
      @Override
      protected TestClass testClass() {
        return BeansConfiguration.this.testClass();
      }
    };
  }

  @Bean
  @Scope("prototype")
  public TestClass testClass() {
    return new TestClass();
  }
}
