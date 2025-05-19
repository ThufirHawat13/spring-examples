package org.example;

import org.example.config.BeanPostProcessorsConfiguration;
import org.example.config.BeansConfiguration;
import org.example.service.TestService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    context.register(BeanPostProcessorsConfiguration.class);
    context.register(BeansConfiguration.class);

    context.refresh();

    var bean = context.getBean(TestService.class);

    for (int i = 0; i < 10; i++) {
      System.out.println(bean.test());
    }
  }
}