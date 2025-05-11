package org.example;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ValidByRegexpBeanPostProcessorTest {

  private static AnnotationConfigApplicationContext context;


  @BeforeAll
  static void init() {
    context = new AnnotationConfigApplicationContext();

    context.register(PostProcessorConfiguration.class);
    context.register(BeanConfiguration.class);

    context.refresh();
  }

  @Test
  void positiveCaseTest() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(byteArrayOutputStream));

    context.getBean(Printer.class).print("Test");

    Assertions.assertEquals(
        "Test",
        byteArrayOutputStream.toString().trim());
  }

  @Test
  void negativeCaseTest() {
    Printer testBean = context.getBean(Printer.class);

    String errMessage =
        "param №0 with value=test in method print don't matches regexp '^[A-Z].+[a-z]$'";

    Assertions.assertThrows(
        Exception.class,
        () -> testBean.print("test"),
        errMessage);
    Assertions.assertThrows(
        Exception.class,
        () -> testBean.print("TesT"),
        errMessage);
    Assertions.assertThrows(
        Exception.class,
        () -> testBean.print("Test1"),
        errMessage);
    Assertions.assertThrows(
        Exception.class,
        () -> testBean.print("1Test"),
        errMessage);
  }
}