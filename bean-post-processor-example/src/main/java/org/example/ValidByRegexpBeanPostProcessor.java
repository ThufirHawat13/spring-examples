package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    Map<String, List<ParamNumberWithRegexp>> methodsWithMarkedParameters =
        createMethodWithMarkedParametersMap(bean.getClass().getMethods());

    if (!methodsWithMarkedParameters.isEmpty()) {
      return new ValidationProxyGenerator(
          createMethodWithMarkedParametersMap(bean.getClass().getMethods()))
          .generateProxy(bean);
    }

    return bean;
  }

  private Map<String, List<ParamNumberWithRegexp>> createMethodWithMarkedParametersMap(
      Method[] methods) {
    Map<String, List<ParamNumberWithRegexp>> result = new HashMap<>();

    for (Method method : methods) {
      List<ParamNumberWithRegexp> markedParameters = new ArrayList<>();

      checkParametersForAnnotationAndAddToMarkedParametersIfNeeds(method, markedParameters);

      if (!markedParameters.isEmpty()) {
        result.put(method.getName(), markedParameters);
      }
    }

    return result;
  }

  private void checkParametersForAnnotationAndAddToMarkedParametersIfNeeds(
      Method method, List<ParamNumberWithRegexp> markedParameters) {
    for (Parameter parameter : method.getParameters()) {
      if (parameter.isAnnotationPresent(ValidByRegexp.class)) {
        addToMarkedParameters(parameter, markedParameters);
      }
    }
  }

  private void addToMarkedParameters(Parameter parameter,
      List<ParamNumberWithRegexp> markedParameters) {
    markedParameters.add(
        ParamNumberWithRegexp
            .builder()
            .paramNumber(extractParameterNumber(parameter))
            .regexp(extractRegexp(parameter))
            .build());
  }

  private int extractParameterNumber(Parameter parameter) {
    return Integer.parseInt(
        parameter.getName()
            .replaceAll("arg(?<counter>\\d)", "${counter}"));
  }

  private String extractRegexp(Parameter parameter) {
    return parameter.getAnnotation(ValidByRegexp.class).regexp();
  }
}
