package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ValidByRegexpBeanPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Map<String, List<ParamNumberWithRegexp>> methodsWithMarkedParameters = new HashMap<>();

    Class clazz = bean.getClass();

    for (Method method : clazz.getMethods()) {
      List<ParamNumberWithRegexp> markedParameters = new ArrayList<>();
      for (Parameter parameter : method.getParameters()) {
        if (parameter.isAnnotationPresent(ValidByRegexp.class)) {
          int parameterNumber = Integer.parseInt(
              parameter.getName().replaceAll("arg(?<counter>\\d)", "${counter}"));
          markedParameters.add(
              ParamNumberWithRegexp.builder()
                  .paramNumber(parameterNumber)
                  .regexp(parameter.getAnnotation(ValidByRegexp.class).regexp())
                  .build());
        }
      }
      if (!markedParameters.isEmpty()) {
        methodsWithMarkedParameters.put(method.getName(), markedParameters);
      }
    }

    if (!methodsWithMarkedParameters.isEmpty()) {
      return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
          (proxy, method, args) -> {
            if (methodsWithMarkedParameters.containsKey(method.getName())) {
              for (ParamNumberWithRegexp paramNumberWithRegexp : methodsWithMarkedParameters.get(method.getName())) {
                Object arg = args[paramNumberWithRegexp.paramNumber];
                if (!arg.toString().matches(paramNumberWithRegexp.getRegexp())) {
                  throw new RuntimeException("param №%s with value=%s in method %s don't matches regexp '%s'"
                      .formatted(paramNumberWithRegexp.paramNumber, arg, method.getName(), paramNumberWithRegexp.getRegexp()));
                }
              }
            }

            return method.invoke(bean, args);
          });
    }

    return bean;
  }


  @Builder
  @AllArgsConstructor
  @Getter
  private static class ParamNumberWithRegexp {
    private int paramNumber;
    private String regexp;
  }
}
