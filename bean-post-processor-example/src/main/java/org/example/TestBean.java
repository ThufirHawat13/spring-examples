package org.example;

public class TestBean implements Printer {

  @Override
  public void print(@ValidByRegexp(regexp = "^[A-Z].+[a-z]$") String string) {
    System.out.println(string);
  }
}
