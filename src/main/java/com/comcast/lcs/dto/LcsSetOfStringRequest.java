package com.comcast.lcs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class LcsSetOfStringRequest {

  @NotNull private Set<LcsStringDto> setOfStrings;
}
