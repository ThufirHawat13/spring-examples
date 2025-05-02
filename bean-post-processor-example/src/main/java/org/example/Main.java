package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext();

    context.register(PostProcessorConfiguration.class);
    context.register(BeanConfiguration.class);

    context.refresh();

    context.getBean(Printer.class).print("KURWA!");
  }
}