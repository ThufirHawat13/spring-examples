package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactoryPostProcessorConfiguration {

  @Bean
  public static ThreeTimesScopeBeanFactoryPostProcessor threeTimesScopeBeanFactoryPostProcessor() {
    return new ThreeTimesScopeBeanFactoryPostProcessor();
  }
}
