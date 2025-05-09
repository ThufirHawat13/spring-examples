package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfiguration {

  @Bean()
  @Scope("threeTimes")
  public TestBean testBean() {
    return new TestBean();
  }
}
