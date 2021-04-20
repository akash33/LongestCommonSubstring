package com.comcast.lcs.service

import com.comcast.lcs.dto.LcsSetOfStringRequest
import com.comcast.lcs.dto.LcsStringDto
import com.comcast.lcs.exception.BadRequestException
import spock.lang.Specification

class LcsServiceSpec extends Specification {

    def lcsService = new LcsService()

    def "find the longest common string"() {
        given: "set of strings"
        def string1 = new LcsStringDto(value: string1Value)
        def string2 = new LcsStringDto(value: string2Value)
        def string3 = new LcsStringDto(value: string3Value)

        def setOfStrings = [string1, string2, string3] as Set
        def setOfStringsRequest = new LcsSetOfStringRequest(setOfStrings: setOfStrings)

        when: "findLongestCommonString method called"
        def response = lcsService.findLongestCommonSubstrings(setOfStringsRequest)

        then: "asserted longest common string response"
        response.lcs == result

        where: "provided different inputs"
        string1Value | string2Value | string3Value  | result
        "comcast"    | "comcastic"  | "broadcaster" | [new LcsStringDto(value: "cast")]
        "comcast"    | "aaaaaaaaa"  | "abcdn"       | [new LcsStringDto(value: "a")]
        "romcom"     | "com&rom"    | "romncom"     | [new LcsStringDto(value: "com"), new LcsStringDto(value: "rom")]
    }

    def "find the longest common string when only one string provided"() {
        given: "set of strings"
        def string1 = new LcsStringDto(value: "comcast")

        def setOfStrings = [string1] as Set
        def setOfStringsRequest = new LcsSetOfStringRequest(setOfStrings: setOfStrings)

        when: "findLongestCommonString method called"
        def response = lcsService.findLongestCommonSubstrings(setOfStringsRequest)

        then: "asserted longest common string response"
        response.lcs[0].value == "comcast"
    }

    def "find the longest common string when no common set of strings provided"() {
        given: "unique strings"
        def string1 = new LcsStringDto(value: "aaaaa")
        def string2 = new LcsStringDto(value: "bbbbb")
        def string3 = new LcsStringDto(value: "ccccc")

        def setOfStrings = [string1, string2, string3] as Set
        def setOfStringsRequest = new LcsSetOfStringRequest(setOfStrings: setOfStrings)

        when: "findLongestCommonString method called"
        lcsService.findLongestCommonSubstrings(setOfStringsRequest)

        then: "asserted thrown exception"
        def error = thrown(BadRequestException)
        error.message == "Ensure set of strings is a Set"
    }
}
