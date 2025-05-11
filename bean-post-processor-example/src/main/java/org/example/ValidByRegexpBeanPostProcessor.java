package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
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

    fillMethodWithMarkedParametersMap(clazz.getMethods(), methodsWithMarkedParameters);

    if (!methodsWithMarkedParameters.isEmpty()) {
      return generateProxyWithValidation(bean, methodsWithMarkedParameters, clazz);
    }

    return bean;
  }

  private void fillMethodWithMarkedParametersMap(Method[] methods,
      Map<String, List<ParamNumberWithRegexp>> methodsWithMarkedParameters) {
    for (Method method : methods) {
      List<ParamNumberWithRegexp> markedParameters = new ArrayList<>();

      checkParametersForAnnotationAndAddToMarkedParametersIfNeeds(method, markedParameters);

      if (!markedParameters.isEmpty()) {
        methodsWithMarkedParameters.put(method.getName(), markedParameters);
      }
    }
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

  private Object generateProxyWithValidation(Object bean,
      Map<String, List<ParamNumberWithRegexp>> methodsWithMarkedParameters, Class clazz) {
    return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
        (proxy, method, args) -> {
          String methodName = method.getName();

          if (methodsWithMarkedParameters.containsKey(methodName)) {
            validArguments(methodsWithMarkedParameters, method, args, methodName);
          }

          return method.invoke(bean, args);
        });
  }

  private void validArguments(
      Map<String, List<ParamNumberWithRegexp>> methodsWithMarkedParameters, Method method,
      Object[] args, String methodName) {
    for (ParamNumberWithRegexp paramNumWithRegexp : methodsWithMarkedParameters.get(methodName)) {
      validArgByRegexp(method, paramNumWithRegexp, args[paramNumWithRegexp.paramNumber]);
    }
  }

  private void validArgByRegexp(Method method,
      ParamNumberWithRegexp paramNumberWithRegexp, Object arg) {
    if (!arg.toString().matches(paramNumberWithRegexp.getRegexp())) {
      throw new RuntimeException(
          "param №%s with value=%s in method %s don't matches regexp '%s'"
              .formatted(paramNumberWithRegexp.paramNumber, arg, method.getName(),
                  paramNumberWithRegexp.getRegexp()));
    }
  }


  @Builder
  @AllArgsConstructor
  @Getter
  private static class ParamNumberWithRegexp {

    private int paramNumber;
    private String regexp;
  }
}
