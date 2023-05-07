package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.dto.WordDto;
import com.Saojung.whatisthis.service.MemberService;
import com.Saojung.whatisthis.service.WordService;
import com.Saojung.whatisthis.vo.WordVo;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class WordControllerTest {

    MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired ObjectMapper objectMapper;

    @Autowired
    WordService wordService;
    @Autowired
    MemberService memberService;

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
    @DisplayName("단어 추가 테스트")
    void 단어_추가() throws Exception {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        MemberDto memberDto = MemberDto.from(member);

        MemberDto returnDto = memberService.signUp(memberDto);

        WordVo wordVo = new WordVo(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52, 00)
        );

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("member_idx", String.valueOf(returnDto.getIdx()));

        //when
        //then
        mvc.perform(post("/studyWord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map)
                        .content(objectMapper.writeValueAsString(wordVo)))
                .andExpect(status().isOk())
                .andExpect(content().string("단어 학습이 완료되었습니다."));
    }

    @Test
    @DisplayName("모든 단어 전달 테스트")
    void 모든_단어_전달() throws Exception {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        MemberDto memberDto = MemberDto.from(member);

        MemberDto returnDto = memberService.signUp(memberDto);

        Member returnMember = Member.builder()
                .idx(returnDto.getIdx())
                .userId(returnDto.getUserId())
                .password(returnDto.getPassword())
                .name(returnDto.getName())
                .birth(returnDto.getBirth())
                .parentPassword(returnDto.getParentPassword())
                .amends(returnDto.getAmends())
                .analysis(returnDto.getAnalysis())
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), returnMember
        );

        WordDto wordDto2 = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), returnMember
        );

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("member_idx", String.valueOf(returnDto.getIdx()));

        wordService.create(wordDto);
        wordService.create(wordDto2);

        //when
        //then
        mvc.perform(get("/getWords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andDo(print());
    }
}