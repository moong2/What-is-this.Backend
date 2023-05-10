package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.dto.WordDto;
import com.Saojung.whatisthis.service.AnalysisService;
import com.Saojung.whatisthis.service.MemberService;
import com.Saojung.whatisthis.service.WordService;
import com.Saojung.whatisthis.vo.LoginVo;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AnalysisControllerTest {

    MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WordService wordService;

    @BeforeEach
    void beforeEach() {
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
    @DisplayName("분석값 Get")
    void 분석값_받아오기() throws Exception {
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        MemberDto resultDto = memberService.signUp(memberDto);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("member_idx", String.valueOf(resultDto.getIdx()));

        //when
        //then
        mvc.perform(get("/getAnalysis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idx", notNullValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("일정 시간 이후 분석값 Get")
    void 일정_시간_이후_분석값_받아오기() throws Exception {
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        MemberDto resultDto = memberService.signUp(memberDto);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("member_idx", String.valueOf(resultDto.getIdx()));
        map.add("date", String.valueOf(LocalDateTime.now().minusMonths(1)));

        //when
        //then
        mvc.perform(get("/getAnalysis/date/after")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idx", notNullValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("난이도 계산 테스트")
    void 난이도_받아오기() throws Exception {
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        MemberDto resultDto = memberService.signUp(memberDto);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("member_idx", String.valueOf(resultDto.getIdx()));

        //when
        //then
        mvc.perform(get("/getLevel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("기간동안 분석값 받아오기")
    void 기간동안_분석값_받아오기() throws Exception {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(null)
                .amends(null)
                .build();

        MemberDto member_dto = MemberDto.from(member);

        MemberDto result_dto = memberService.signUp(member_dto);

        Member result_member = Member.builder()
                .idx(result_dto.getIdx())
                .userId(result_dto.getUserId())
                .password(result_dto.getPassword())
                .name(result_dto.getName())
                .birth(result_dto.getBirth())
                .parentPassword(result_dto.getParentPassword())
                .analysis(result_dto.getAnalysis())
                .amends(result_dto.getAmends())
                .build();

        Word word = Word.builder()
                .word("apple")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.now().minusWeeks(2))
                .member(result_member)
                .build();

        Word old_word = Word.builder()
                .word("apple")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.now())
                .member(result_member)
                .build();

        WordDto word_dto = WordDto.from(word);
        WordDto old_word_dto = WordDto.from(old_word);

        for (int i = 0; i < 10; i++) {
            wordService.create(word_dto);
            wordService.create(old_word_dto);
        }

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("member_idx", String.valueOf(result_dto.getIdx()));
        map.add("older_date", String.valueOf(word.getDate()));
        map.add("newer_date", String.valueOf(word.getDate().plusWeeks(1)));

        //when
        //then
        mvc.perform(get("/getAnalysis/date/between")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andDo(print());
    }
}