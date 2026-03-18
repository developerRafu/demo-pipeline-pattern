package com.rafael.demopipelinepattern.validation.preload;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import org.springframework.stereotype.Component;

@Component
public class IdValidation implements PreLoadValidation {
  @Override
  public boolean shouldRun(final Context context) {
    return context.getId() == null;
  }

  @Override
  public void run(final Context context) {
    context.addError(
        Message.builder()
            .text("Invalid id")
            .type(MessageType.ERROR)
            .module(MessageModule.PRE_LOAD_MODULE)
            .build());
  }
}
