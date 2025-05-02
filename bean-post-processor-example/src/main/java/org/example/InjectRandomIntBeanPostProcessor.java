package org.example;

import java.lang.reflect.Field;
import java.util.Random;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class InjectRandomIntBeanPostProcessor implements BeanPostProcessor {

  @SneakyThrows
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    for (Field field : bean.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(InjectRandomInt.class)) {
        field.setAccessible(true);
        field.setInt(bean, new Random().nextInt());
      }
    }

    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
  }
}
