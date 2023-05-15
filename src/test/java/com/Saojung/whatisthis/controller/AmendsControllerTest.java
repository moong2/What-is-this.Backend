package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.service.AmendsService;
import com.Saojung.whatisthis.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AmendsControllerTest {

    MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;
    @Autowired
    private AmendsService amendsService;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, objectMapper);
        this.mvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        List<MemberDto> members = memberService.getMembers();
        for (MemberDto member : members) {
            memberService.withdraw(member.getIdx());
        }
    }

    @Test
    @DisplayName("보상 전달")
    void 보상_전달() throws Exception {
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        MemberDto resultDto = memberService.signUp(memberDto);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("member_idx", String.valueOf(resultDto.getIdx()));

        //when
        //then
        mvc.perform(get("/getAmends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idx", notNullValue()))
                .andDo(print());
    }
}