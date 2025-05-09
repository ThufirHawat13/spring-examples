package org.example;

import java.util.Random;
import lombok.Getter;

@Getter
public class TestBean {

  private final int num;

  public TestBean() {
    num = new Random().nextInt();
  }
}
