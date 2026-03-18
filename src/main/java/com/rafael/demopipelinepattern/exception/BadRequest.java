package com.rafael.demopipelinepattern.exception;

import com.rafael.demopipelinepattern.models.Message;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BadRequest extends RuntimeException {
  private final List<Message> errors;
  private final String message;
}
