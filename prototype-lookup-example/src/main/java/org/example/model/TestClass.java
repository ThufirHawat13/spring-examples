package org.example.model;

import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

public class TestClass {

  @Getter
  private int num1;
  @Getter
  private int num2;

  @PostConstruct
  private void init() {
    num1 = new Random().nextInt();
  }
}
