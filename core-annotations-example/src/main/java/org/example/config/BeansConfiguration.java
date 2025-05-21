package org.example.config;

import org.example.model.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration
@Import({PropertiesConfiguration.class,
        TestProfileBeansConfiguration.class})
public class BeansConfiguration {

  private final Environment environment;

  @Autowired
  public BeansConfiguration(Environment environment) {
    this.environment = environment;
  }


  @Bean
  @Order(1)
  public TestBean testBean() {
    System.out.println("bean1");
    return new TestBean(
        Integer.parseInt(
            environment.getProperty("bean1.num")));
  }

  @Bean
  @Order(2)
  @Conditional(TestCondition.class)
  public TestBean testBeanOnConditionEnabled() {
    System.out.println("bean2");
    return new TestBean(
        Integer.parseInt(
            environment.getProperty("bean2.num")));
  }

  private static class TestCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      return Boolean.parseBoolean(
          context.getEnvironment().getProperty("bean2.enabled"));
    }
  }
}
