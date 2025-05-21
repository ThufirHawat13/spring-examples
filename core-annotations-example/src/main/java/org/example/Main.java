package org.example;

import org.example.config.BeansConfiguration;
import org.example.model.TestBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    context.getEnvironment().setActiveProfiles("test");

    context.register(BeansConfiguration.class);

    context.refresh();

    context.getBeansOfType(TestBean.class)
        .forEach((k, v) -> System.out.println(v.getNum()));
  }
}