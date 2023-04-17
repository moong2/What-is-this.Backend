//package com.Saojung.whatisthis.service;
//
//import com.Saojung.whatisthis.dto.MemberDto;
//import com.Saojung.whatisthis.repository.MemberRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class MemberServiceTest {
//
//    private MemberService memberService;
//
//    @Mock
//    private final MemberRepository memberRepository;
//
//    MemberServiceTest(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        memberService = new MemberService(memberRepository);
//    }
//
//    @Test
//    @DisplayName("Create 테스트")
//    void 회원_가입() {
//        //given
//        MemberDto memberDto = new MemberDto(
//                "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password"
//        );
//
//        //when
//        memberService.signUp(memberDto);
//
//        //then
//        assertEquals(memberService.getMembers().size(), 1);
//    }
//
//    @Test
//    @DisplayName("Read 테스트")
//    void 회원정보_가져오기() {
//        //given
//        MemberDto memberDto = new MemberDto(
//                "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password"
//        );
//
//        //when
//        memberService.signUp(memberDto);
//        save_member = memberService.getMemberById(memberDto.getId());
//
//        //then
//        assertEquals(memberDto.getId(), save_member.getId());
//        assertEquals(memberDto.getPassword(), save_member.getPassword());
//        assertEquals(memberDto.getName(), save_member.getName());
//        assertEquals(memberDto.getBirth(), save_member.getBirth());
//        assertEquals(memberDto.getParent_password(), save_member.getParent_password());
//    }
//
//    @Test
//    @DisplayName("Update 테스트")
//    void 회원정보_업데이트() {
//        //given
//        MemberDto memberDto = new MemberDto(
//                "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password"
//        );
//    }
//}