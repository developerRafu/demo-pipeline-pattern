package com.rafael.demopipelinepattern.helper;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SleepHelper {

  private SleepHelper() {}

  public static <T> T sleep(final String service, final T data) {
    try {
      final var millis = new Random().nextInt(500) + 100;
      Thread.sleep(millis);
      log.info("It took {} to return {}", millis, service);

      return data;
    } catch (InterruptedException e) {
      log.error("Error sleeping {}", e.getMessage(), e);
      throw new RuntimeException("Interrupted while sleeping", e);
    }
  }
}
