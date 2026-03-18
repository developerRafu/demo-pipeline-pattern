package com.rafael.demopipelinepattern.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
  private String text;
  private MessageType type;
  private MessageModule module;
  private List<String> details;
}
