package com.rafael.demopipelinepattern.exception;

import com.rafael.demopipelinepattern.models.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor
public class BadRequest extends RuntimeException {
    private final List<Message> errors;
    private final String message;
}
