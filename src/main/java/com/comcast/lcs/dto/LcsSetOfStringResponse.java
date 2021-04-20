package com.comcast.lcs.dto;

import lombok.Data;

import java.util.List;

@Data
public class LcsSetOfStringResponse {

  private List<LcsStringDto> lcs;
}
