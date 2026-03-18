package com.rafael.demopipelinepattern.validation.postload;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import org.springframework.stereotype.Component;

@Component
public class ItemValidation implements ResponseRule {
    @Override
    public boolean shouldRun(final Context context) {
        return context.getItem() == null;
    }

    @Override
    public void run(final Context context) {
        context.addError(Message
                .builder()
                .text("Item not found")
                .type(MessageType.ERROR)
                .module(MessageModule.POST_LOAD_MODULE)
                .build());
    }
}
