package org.example;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class ThreeTimesScope implements Scope {

  private final Map<String, ObjectWithCallTimes> map = new HashMap<>();


  @Override
  public Object get(String name, ObjectFactory<?> objectFactory) {
    Object result;
    ObjectWithCallTimes objectWithCallTimes;

    if (map.containsKey(name) && (objectWithCallTimes = map.get(name)).getCallTimes() <= 2) {
        result = map.get(name).getObject();
        objectWithCallTimes.incrementCallTimes();
    } else {
      result = objectFactory.getObject();
      map.put(name,
          ObjectWithCallTimes
              .builder()
              .callTimes(1)
              .object(result)
              .build());
    }

    return result;
  }

  @Override
  public Object remove(String name) {
    return null;
  }

  @Override
  public void registerDestructionCallback(String name, Runnable callback) {

  }

  @Override
  public Object resolveContextualObject(String key) {
    return null;
  }

  @Override
  public String getConversationId() {
    return null;
  }


  @Getter
  @AllArgsConstructor
  @Builder
  private static class ObjectWithCallTimes {
    private int callTimes;
    private Object object;


    void incrementCallTimes() {
      callTimes++;
    }
  }
}
