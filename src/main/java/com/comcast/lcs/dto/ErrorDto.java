package com.comcast.lcs.dto;

import lombok.Data;

@Data
public class ErrorDto {

  private String error;
  private String message;
  private String details;
}
