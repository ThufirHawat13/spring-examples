package org.example.config;

import org.example.model.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Configuration
@Profile("test")
public class TestProfileBeansConfiguration {

  private final Environment environment;

  @Autowired
  public TestProfileBeansConfiguration(Environment environment) {
    this.environment = environment;
  }


  @Bean
  @Order(3)
  public TestBean testBean3() {
    System.out.println("bean3");
    return new TestBean(
        Integer.parseInt(
            environment.getProperty("bean3.num")));
  }
}
