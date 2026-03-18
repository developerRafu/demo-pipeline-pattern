package com.rafael.demopipelinepattern.helper;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
@Slf4j
public final class SleepHelper {
    public static void sleep(final String service){
        try {
            final var millis = new Random().nextInt(500) + 100;
            Thread.sleep(millis);
            log.info("It took {} to return {}", millis, service);
        } catch (InterruptedException e) {
            log.error("Error sleeping {}", e.getMessage(), e);
        }
    }
}
