package com.comcast.lcs.integration

import com.comcast.lcs.dto.LcsSetOfStringRequest
import com.comcast.lcs.dto.LcsStringDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class LcsITSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    def "Find longest common string"() {
        given: "list of strings"
        def comcastString = new LcsStringDto(value: "comcast")
        def comcastic = new LcsStringDto(value: "comcastic")
        def broadcater = new LcsStringDto(value: "broadcaster")

        def setOfStrings = [comcastString, comcastic, broadcater] as Set
        def setOfStringsRequest = new LcsSetOfStringRequest(setOfStrings: setOfStrings)

        when: "/lcs endpoint called"
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(setOfStringsRequest)))

        then: "asserted longest common string with 200 http status"
        result.andExpect(status().isOk())
                .andExpect(jsonPath('$.lcs', Matchers.hasSize(1)))
                .andExpect(jsonPath('$.lcs[*].value').value('cast'))
    }

    def "Find longest common string throws exception when request body is null"() {
        given: "empty request body"

        when: "/lcs endpoint called"
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                .contentType("application/json"))

        then: "returned 400 bad request and asserted error response"
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.error').value('error.invalid.request'))
                .andExpect(jsonPath('$.message').value('Invalid request'))
                .andExpect(jsonPath('$.details').value('Ensure request body has correct format or not empty'))
    }

    def "Find longest common string throws exception when setOfString is empty"() {
        given: "empty request body"
        def setOfStringsRequest = new LcsSetOfStringRequest()

        when: "/lcs endpoint called"
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(setOfStringsRequest)))

        then: "returned 400 bad request and asserted error response"
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.error').value('error.invalid.request'))
                .andExpect(jsonPath('$.message').value('Invalid request'))
                .andExpect(jsonPath('$.details').value('Ensure set of strings is not empty'))
    }

    def "Find longest common string throws exception when setOfString is not Set"() {
        given: "unique strings"
        def string1 = new LcsStringDto(value: "ababab")
        def string2 = new LcsStringDto(value: "cdcdcd")
        def string3 = new LcsStringDto(value: "ererer")

        def setOfStrings = [string1, string2, string3] as Set
        def setOfStringsRequest = new LcsSetOfStringRequest(setOfStrings: setOfStrings)

        when: "/lcs endpoint called"
        def result = mockMvc.perform(MockMvcRequestBuilders.post("/lcs")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(setOfStringsRequest)))

        then: "returned 400 bad request and asserted error response"
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.error').value('error.invalid.request'))
                .andExpect(jsonPath('$.message').value('Invalid request'))
                .andExpect(jsonPath('$.details').value('Ensure set of strings is a Set'))
    }
}
