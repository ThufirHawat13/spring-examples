package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ParamNumberWithRegexp {

  private int paramNumber;
  private String regexp;
}