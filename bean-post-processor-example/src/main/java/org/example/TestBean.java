package org.example;

public class TestBean implements Printer {

  @InjectRandomInt()
  private int num;


  public void test() {
    System.out.println("NUM = " + num);
  }

  @Override
  public void print(@ValidByRegexp(regexp = "^[A-Z].+[a-z]$") String alo) {
    System.out.println(alo);
  }
}
