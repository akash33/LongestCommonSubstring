package com.comcast.lcs.service;

import com.comcast.lcs.dto.LcsSetOfStringRequest;
import com.comcast.lcs.dto.LcsSetOfStringResponse;
import com.comcast.lcs.dto.LcsStringDto;
import com.comcast.lcs.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LcsService {

  /**
   * Finds Longest common substrings
   *
   * @param lcsSetOfStringRequest set of strings request
   * @return longest common sub strings
   */
  public LcsSetOfStringResponse findLongestCommonSubstrings(LcsSetOfStringRequest lcsSetOfStringRequest) {

    List<String> setOfStrings = lcsSetOfStringRequest.getSetOfStrings().stream()
            .map(LcsStringDto::getValue)
            .collect(Collectors.toList());

    String firstString = setOfStrings.get(0);
    String longestCommonSubstring = "";
    List<String> possibleLongestSubstrings = new ArrayList<>();

    for (int i = 0; i < firstString.length(); i++) {
      for (int j = i + 1; j <= firstString.length(); j++) {
        String possibleSubstring = firstString.substring(i, j);

        int k;
        for (k = 1; k < setOfStrings.size(); k++) {
          if (!setOfStrings.get(k).contains(possibleSubstring)) {
            break;
          }
        }

        if (k == setOfStrings.size()
            && longestCommonSubstring.length() <= possibleSubstring.length()) {
          longestCommonSubstring = possibleSubstring;
          possibleLongestSubstrings.add(possibleSubstring);
        }
      }
    }

    if (longestCommonSubstring.equals("")) {
      throw new BadRequestException("Ensure set of strings is a Set");
    }

    List<String> lcsStrings = findLongestSubstrings(longestCommonSubstring, possibleLongestSubstrings);
    return buildLcsSetOfStringResponse(lcsStrings);
  }

  private List<String> findLongestSubstrings(String longestCommonSubString, List<String> possibleSubStrings) {
    int longestSubStringLength = longestCommonSubString.length();

    return possibleSubStrings.stream()
        .filter(s -> longestSubStringLength == s.length())
        .distinct()
        .sorted(String::compareTo)
        .collect(Collectors.toList());
  }

  private LcsSetOfStringResponse buildLcsSetOfStringResponse(List<String> longestCommonSubStrings) {
    LcsSetOfStringResponse response = new LcsSetOfStringResponse();

    List<LcsStringDto> lcsStringDtoList = longestCommonSubStrings.stream()
                  .map(s -> {
                        LcsStringDto lcsStringDto = new LcsStringDto();
                        lcsStringDto.setValue(s);
                        return lcsStringDto;
                      })
                  .collect(Collectors.toList());

    response.setLcs(lcsStringDtoList);
    return response;
  }
}
