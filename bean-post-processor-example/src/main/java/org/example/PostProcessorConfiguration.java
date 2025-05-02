package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorConfiguration {

  @Bean
  public static InjectRandomIntBeanPostProcessor injectRandomIntBeanPostProcessor() {
    return new InjectRandomIntBeanPostProcessor();
  }

  @Bean
  public static ValidByRegexpBeanPostProcessor validByRegexpBeanPostProcessor() {
    return new ValidByRegexpBeanPostProcessor();
  }
}
