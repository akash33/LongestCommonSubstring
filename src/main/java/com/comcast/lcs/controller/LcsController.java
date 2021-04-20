package com.comcast.lcs.controller;

import com.comcast.lcs.dto.LcsSetOfStringRequest;
import com.comcast.lcs.dto.LcsSetOfStringResponse;
import com.comcast.lcs.service.LcsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
public class LcsController {

  private final LcsService lcsService;

  public LcsController(LcsService lcsService) {
    this.lcsService = lcsService;
  }

  /**
   * Finds longest common string
   *
   * @param request set of string request
   * @return finds longest common string response
   */
  @PostMapping(path = "/lcs", consumes = "application/json", produces = "application/json")
  public ResponseEntity<LcsSetOfStringResponse> findLongestCommonString(
      @Valid @RequestBody LcsSetOfStringRequest request) {
    log.info("Request for finding longest common string started");
    LcsSetOfStringResponse response = lcsService.findLongestCommonSubstrings(request);
    log.info("Request for finding longest common string completed");
    return ResponseEntity.ok(response);
  }
}
