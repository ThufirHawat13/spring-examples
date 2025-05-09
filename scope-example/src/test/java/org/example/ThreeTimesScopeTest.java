package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ThreeTimesScopeTest {

  @Test
  public void positiveCase() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    context.register(BeanFactoryPostProcessorConfiguration.class);
    context.register(BeanConfiguration.class);
    context.refresh();

    int num = context.getBean(TestBean.class).getNum();

    Assertions.assertEquals(context.getBean(TestBean.class).getNum(), num);
    Assertions.assertEquals(context.getBean(TestBean.class).getNum(), num);
    Assertions.assertNotEquals(context.getBean(TestBean.class).getNum(), num);
  }
}