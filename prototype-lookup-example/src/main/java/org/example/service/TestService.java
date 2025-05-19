package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.TestClass;

@RequiredArgsConstructor
public abstract class TestService {

  public String test() {
    var testClass = testClass();

    return "num1:%s | num2:%s".formatted(
        testClass.getNum1(), testClass.getNum2());
  }

  protected abstract TestClass testClass();
}
