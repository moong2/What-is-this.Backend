package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.dto.AmendsDto;
import com.Saojung.whatisthis.dto.AnalysisDto;
import com.Saojung.whatisthis.dto.LoginDto;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.exception.DuplicateMemberException;
import com.Saojung.whatisthis.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private final MemberRepository memberRepository;

    MemberServiceTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("Create 테스트")
    void 회원_가입() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        memberService.signUp(memberDto);

        //then
        assertEquals(memberService.getMembers().size(), 1);
    }

    @Test
    @DisplayName("Read 테스트")
    void 회원정보_가져오기() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        MemberDto save_member = memberService.signUp(memberDto);

        //then
        assertEquals(memberDto.getId(), save_member.getId());
        assertEquals(memberDto.getName(), save_member.getName());
        assertEquals(memberDto.getBirth(), save_member.getBirth());
        assertEquals(memberDto.getParent_password(), save_member.getParent_password());
    }

    @Test
    @DisplayName("Update 테스트")
    void 회원정보_업데이트() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        MemberDto save_member = memberService.signUp(memberDto);

        save_member.setName("하성박");
        MemberDto change_member = memberService.signUp(save_member);

        //then
        assertNotEquals(change_member.getName(), save_member.getName());
    }

    @Test
    @DisplayName("Delete 테스트")
    void 회원_탈퇴() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        memberService.signUp(memberDto);
        memberService.withdraw(memberDto);

        //then
        assertEquals(memberService.getMembers().size(), 0);
    }

    @Test
    @DisplayName("패스워드 암호화")
    void 패스워드_암호화() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        MemberDto save_member = memberService.signUp(memberDto);

        //then
        assertNotEquals(save_member.getPassword(), memberDto.getPassword());
    }

    @Test
    @DisplayName("부모패스워드 암호화")
    void 부모패스워드_암호화() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        MemberDto save_member = memberService.signUp(memberDto);

        //then
        assertNotEquals(save_member.getParent_password(), memberDto.getParent_password());
    }

    @Test
    @DisplayName("회원가입 시 중복 아이디 방지")
    void 중복아이디_회원가입_방지() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        MemberDto memberDto2 = new MemberDto(
                "castlehi", "password", "하성박", LocalDate.of(2000, 06, 17), "p_password"
        );

        //when
        memberService.signUp(memberDto);

        assertThrows(DuplicateMemberException.class, () -> {
            memberService.signUp(memberDto2);
        });
    }

    @Test
    @DisplayName("로그인 성공")
    void 로그인_성공() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginDto givenDto = new LoginDto(
                "castlehi", "wrong", null
        );

        //when
        memberService.signUp(memberDto);
        Optional<MemberDto> returnDto = memberService.login(givenDto);

        //then
        assertEquals(returnDto.getId(), memberDto.getId());
    }

    @Test
    @DisplayName("로그인 실패 - 다른 아이디")
    void 로그인_실패_아이디() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginDto givenDto = new LoginDto(
                "castlehi", "wrong", null
        );

        //when
        memberService.signUp(memberDto);

        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            Optional<MemberDto> returnDto = memberService.login(givenDto);
        });
    }

    @Test
    @DisplayName("로그인 실패 - 다른 패스워드")
    void 로그인_실패_패스워드() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginDto givenDto = new LoginDto(
                "castlehi", "wrong", null
        );

        //when
        memberService.signUp(memberDto);

        //then
        assertThrows(RuntimeException.class, () -> {
            Optional<MemberDto> returnDto = memberService.login(givenDto);
        });
    }

    @Test
    @DisplayName("부모로그인 성공")
    void 부모로그인_성공() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginDto givenDto = new LoginDto(
                "castlehi", "password", "p_password"
        );

        //when
        memberService.signUp(memberDto);
        Optional<AmendsDto> returnAmendsDto = memberService.parentLogin(givenDto);
        Optional<AnalysisDto> returnAnalysisDto = memberService.parentLogin(givenDto);

        //then
        assertNotEquals(returnAmendsDto, null);
        assertNotEquals(returnAnalysisDto, null);
    }

    @Test
    @DisplayName("부모로그인 실패")
    void 부모로그인_실패() {
        //given
        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginDto givenDto = new LoginDto(
                "castlehi", "password", "wrong"
        );

        //when
        memberService.signUp(memberDto);

        //then
        assertThrows(RuntimeException.class, () -> {
            Optional<MemberDto> returnDto = memberService.parentLogin(parentLoginDto);
        });
    }
}