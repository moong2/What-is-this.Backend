package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.vo.LoginVo;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.exception.DuplicateMemberException;
import com.Saojung.whatisthis.exception.NoMemberException;
import com.Saojung.whatisthis.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.passwordEncoder = new BCryptPasswordEncoder();
        memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Create 테스트")
    void 회원_가입() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        List<Member> mockList = new ArrayList<>();
        mockList.add(member);

        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findAll()).willReturn(mockList);
        
        memberService.signUp(memberDto);

        //then
        assertEquals(memberService.getMembers().size(), 1);
    }

    @Test
    @DisplayName("Read 테스트")
    void 회원정보_가져오기() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        MemberDto save_member = memberService.signUp(memberDto);

        //then
        assertEquals(memberDto.getUserId(), save_member.getUserId());
        assertEquals(memberDto.getName(), save_member.getName());
        assertEquals(memberDto.getBirth(), save_member.getBirth());
        assertEquals(memberDto.getParentPassword(), save_member.getParentPassword());
    }

    @Test
    @DisplayName("Update 테스트")
    void 회원정보_업데이트() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        Member member2 = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("하성박")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        MemberDto save_member = memberService.signUp(memberDto);

        save_member.setName("하성박");

        BDDMockito.given(memberRepository.findById(String.valueOf(memberDto.getIdx()))).willReturn(Optional.of(member));
        BDDMockito.given(memberRepository.save(any())).willReturn(member2);
        MemberDto change_member = memberService.update(save_member);

        //then
        assertEquals(change_member.getName(), save_member.getName());
    }

    @Test
    @DisplayName("Delete 테스트")
    void 회원_탈퇴() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());
        memberService.signUp(memberDto);

        BDDMockito.given(memberRepository.findById(String.valueOf(memberDto.getIdx()))).willReturn(Optional.of(member));
        memberService.withdraw(memberDto.getIdx());

        //then
        assertEquals(memberService.getMembers().size(), 0);
    }

    @Test
    @DisplayName("패스워드 암호화")
    void 패스워드_암호화() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        MemberDto save_member = memberService.signUp(memberDto);

        //then
        assertNotEquals(save_member.getPassword(), memberDto.getPassword());
    }

    @Test
    @DisplayName("부모패스워드 암호화")
    void 부모패스워드_암호화() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        MemberDto save_member = memberService.signUp(memberDto);

        //then
        assertNotEquals(save_member.getParentPassword(), memberDto.getParentPassword());
    }

    @Test
    @DisplayName("회원가입 시 중복 아이디 방지")
    void 중복아이디_회원가입_방지() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        MemberDto memberDto2 = new MemberDto(
                2L, "castlehi", "password", "하성박", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        memberService.signUp(memberDto);

        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.of(member));

        //then
        assertThrows(DuplicateMemberException.class, () -> {
            memberService.signUp(memberDto2);
        });
    }

    @Test
    @DisplayName("로그인 성공")
    void 로그인_성공() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo givenDto = new LoginVo(
                "castlehi", "password", null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        memberService.signUp(memberDto);

        BDDMockito.given(memberRepository.findByUserId(givenDto.getUserId())).willReturn(Optional.of(member));

        MemberDto returnDto = memberService.login(givenDto);

        //then
        assertEquals(returnDto.getUserId(), memberDto.getUserId());
    }

    @Test
    @DisplayName("로그인 실패 - 다른 아이디")
    void 로그인_실패_아이디() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo givenDto = new LoginVo(
                "wrong", "password", null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        memberService.signUp(memberDto);

        BDDMockito.given(memberRepository.findByUserId(givenDto.getUserId())).willReturn(Optional.empty());

        //then

        assertThrows(NoMemberException.class, () -> {
            MemberDto returnDto = memberService.login(givenDto);
        });
    }

    @Test
    @DisplayName("로그인 실패 - 다른 패스워드")
    void 로그인_실패_패스워드() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo givenDto = new LoginVo(
                "castlehi", "wrong", null
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        memberService.signUp(memberDto);

        BDDMockito.given(memberRepository.findByUserId(givenDto.getUserId())).willReturn(Optional.of(member));

        //then
        assertThrows(NoMemberException.class, () -> {
            MemberDto returnDto = memberService.login(givenDto);
        });
    }

    @Test
    @DisplayName("부모로그인 성공")
    void 부모로그인_성공() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo givenDto = new LoginVo(
                "castlehi", "password", "p_password"
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        MemberDto save_member = memberService.signUp(memberDto);

        BDDMockito.given(memberRepository.findByUserId(givenDto.getUserId())).willReturn(Optional.of(member));
        MemberDto returnDto = memberService.parentLogin(givenDto);

        //then
        assertEquals(returnDto.getIdx(), save_member.getIdx());
    }

    @Test
    @DisplayName("부모로그인 실패")
    void 부모로그인_실패() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password(passwordEncoder.encode("password"))
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword(passwordEncoder.encode("p_password"))
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        LoginVo givenDto = new LoginVo(
                "castlehi", "password", "wrong"
        );

        //when
        BDDMockito.given(memberRepository.save(any())).willReturn(member);
        BDDMockito.given(memberRepository.findByUserId(member.getUserId())).willReturn(Optional.empty());

        memberService.signUp(memberDto);

        BDDMockito.given(memberRepository.findByUserId(givenDto.getUserId())).willReturn(Optional.empty());

        //then
        assertThrows(NoMemberException.class, () -> {
            MemberDto returnDto = memberService.parentLogin(givenDto);
        });
    }
}