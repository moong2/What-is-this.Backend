package com.Saojung.whatisthis.controller;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.repository.MemberRepository;
import com.Saojung.whatisthis.service.MemberService;
import com.Saojung.whatisthis.vo.LoginVo;
import com.Saojung.whatisthis.vo.MemberVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.List;


import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;
    @Autowired ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, objectMapper);
        this.passwordEncoder = new BCryptPasswordEncoder();
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
    @DisplayName("회원가입 완료")
    void 회원가입_완료() throws Exception {
        //given
        MemberDto resultDto = new MemberDto(
                1L, "castlehi", passwordEncoder.encode("password"), "박성하", LocalDate.of(2000, 06, 17), passwordEncoder.encode("p_password"), null, null
        );

        MemberVo memberVo = new MemberVo(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password"
        );

        //when
        //then
        mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberVo)))
                .andExpect(status().isOk())
                .andExpect(content().string(resultDto.getName() + "님의 회원가입이 완료되었습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입_실패")
    void 회원가입_실패() throws Exception {
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", passwordEncoder.encode("password"), "박성하", LocalDate.of(2000, 06, 17), passwordEncoder.encode("p_password"), null, null
        );

        MemberDto resultDto = new MemberDto(
                1L, "castlehi", passwordEncoder.encode("password"), "박성하", LocalDate.of(2000, 06, 17), passwordEncoder.encode("p_password"), null, null
        );

        MemberVo memberVo = new MemberVo(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password"
        );

        memberService.signUp(memberDto);

        //when
        //then
        mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberVo)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void 로그인() throws Exception {
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo loginVo = new LoginVo(
                "castlehi", "password", null
        );

        MemberDto resultDto = memberService.signUp(memberDto);

        //when
        //then
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginVo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idx", notNullValue()))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패")
    void 로그인_실패() throws Exception{
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo loginVo = new LoginVo(
                "castlehi", "wrong", null
        );

        MemberDto resultDto = memberService.signUp(memberDto);

        //when
        //then
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginVo)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void 회원탈퇴() throws Exception {
        //given
        MemberDto memberDto = new MemberDto(
                null, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo loginVo = new LoginVo(
                "castlehi", "password", "p_password"
        );

        MemberDto resultDto = memberService.signUp(memberDto);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("idx", String.valueOf(resultDto.getIdx()));

        //when
        //then
        mvc.perform(post("/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isOk())
                .andExpect(content().string("회원탈퇴가 완료되었습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 탈퇴 실패")
    void 회원탈퇴_실패() throws Exception{
        //given

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("idx", String.valueOf(1L));

        //when
        //then
        mvc.perform(post("/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(map))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("존재하지 않는 회원입니다."))
                .andDo(print());
    }
}