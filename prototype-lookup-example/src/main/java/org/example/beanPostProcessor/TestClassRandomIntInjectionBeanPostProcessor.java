package org.example.beanPostProcessor;

import java.lang.reflect.Field;
import lombok.SneakyThrows;
import org.example.model.TestClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class TestClassRandomIntInjectionBeanPostProcessor implements BeanPostProcessor {

  @Override
  @SneakyThrows
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass() == TestClass.class) {
      Field field = bean.getClass().getDeclaredField("num2");

      field.setAccessible(true);

      field.setInt(bean, ((TestClass) bean).getNum1());
    }

    return bean;
  }
}
