package org.example;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationProxyGenerator {

  private final Map<String, List<ParamNumberWithRegexp>> methodsWithMarkedParameter;


  public Object generateProxy(Object obj) {
    Class clazz = obj.getClass();

    return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
        (proxy, method, args) -> {
          String methodName = method.getName();

          if (methodsWithMarkedParameter.containsKey(methodName)) {
            validArguments(method, args, methodName);
          }

          return method.invoke(obj, args);
        });
  }

  private void validArguments(Method method, Object[] args, String methodName) {
    for (ParamNumberWithRegexp paramNumWithRegexp : methodsWithMarkedParameter.get(methodName)) {
      validArgByRegexp(method, paramNumWithRegexp, args[paramNumWithRegexp.getParamNumber()]);
    }
  }

  private void validArgByRegexp(Method method,
      ParamNumberWithRegexp paramNumberWithRegexp, Object arg) {
    if (!arg.toString().matches(paramNumberWithRegexp.getRegexp())) {
      throw new RuntimeException(
          "param №%s with value=%s in method %s don't matches regexp '%s'"
              .formatted(paramNumberWithRegexp.getParamNumber(), arg, method.getName(),
                  paramNumberWithRegexp.getRegexp()));
    }
  }
}
