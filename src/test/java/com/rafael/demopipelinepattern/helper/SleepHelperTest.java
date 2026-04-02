package com.rafael.demopipelinepattern.helper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class SleepHelperTest {

  @Test
  void givenServiceName_whenSleep_thenShouldNotThrowException() {
    // Given
    String serviceName = "TestService";

    // When & Then
    assertDoesNotThrow(() -> SleepHelper.sleep(serviceName));
  }

  @Test
  void givenMultipleSleepCalls_whenSleep_thenShouldCompleteSuccessfully() {
    // Given
    String[] services = {"Service1", "Service2", "Service3"};

    // When & Then
    for (String service : services) {
      assertDoesNotThrow(() -> SleepHelper.sleep(service));
    }
  }

  @Test
  void givenEmptyServiceName_whenSleep_thenShouldNotThrowException() {
    // Given
    String serviceName = "";

    // When & Then
    assertDoesNotThrow(() -> SleepHelper.sleep(serviceName));
  }

  @Test
  void givenNullServiceName_whenSleep_thenShouldNotThrowException() {
    // Given
    String serviceName = null;

    // When & Then
    assertDoesNotThrow(() -> SleepHelper.sleep(serviceName));
  }
}
