package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    public void 회원가입() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);

        //then
        assertEquals(memberRepository.findAll().size(), 1);
    }

    @Test
    @DisplayName("Null Attribute 테스트")
    public void Null_확인() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .parent_password("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);

        //then
        assertEquals(member.getId(), save_member.getId());
    }

    @Test
    @DisplayName("NotNull Attribute 테스트")
    public void NotNull_확인() {
        assertThrows(NullPointerException.class, () -> {
            Member member = Member.builder()
                    .id("castlehi")
                    .password("password")
                    .birth(LocalDate.of(2000, 06, 17))
                    .parent_password("p_password")
                    .build();
        });
    }

    @Test
    @DisplayName("아이디 조회")
    public void 아이디_조회() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);
        Member find_member = memberRepository.getReferenceById(String.valueOf(save_member.getIdx()));

        //then
        assertEquals(find_member.getId(), save_member.getId());
    }

    @Test
    @DisplayName("<id, pw> 동일 체크 성공")
    public void 로그인_성공() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        String id = "castlehi";
        String pw = "password";
        Member save_member = memberRepository.save(member);

        //then
        assertEquals(save_member.getId(), id);
        assertEquals(save_member.getPassword(), pw);
    }

    @Test
    @DisplayName("<id, pw> 중 id 동일 체크 실패")
    public void 로그인_실패_id() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        String id = "wrong";
        String pw = "password";
        Member save_member = memberRepository.save(member);

        //then
        assertNotEquals(save_member.getId(), id);
        assertEquals(save_member.getPassword(), pw);
    }

    @Test
    @DisplayName("<id, pw> 중 pw 동일 체크 실패")
    public void 로그인_실패_pw() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        String id = "castlehi";
        String pw = "wrong";
        Member save_member = memberRepository.save(member);

        //then
        assertEquals(save_member.getId(), id);
        assertNotEquals(save_member.getPassword(), pw);
    }

    @Test
    @DisplayName("부모 pw 동일 체크 성공")
    public void 부모페이지_로그인_성공_pw() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        String p_pw = "p_password";
        Member save_member = memberRepository.save(member);

        //then
        assertEquals(save_member.getParent_password(), p_pw);
    }

    @Test
    @DisplayName("부모 pw 동일 체크 실패")
    public void 부모페이지_로그인_실패_pw() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        String p_pw = "wrong";
        Member save_member = memberRepository.save(member);

        //then
        assertNotEquals(save_member.getParent_password(), p_pw);
    }

    @Test
    @DisplayName("회원 정보 조회")
    public void 회원_정보_조회() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);
        Member find_member = memberRepository.getReferenceById(String.valueOf(save_member.getIdx()));

        //then
        assertEquals(find_member.getId(), member.getId());
        assertEquals(find_member.getPassword(), member.getPassword());
        assertEquals(find_member.getName(), member.getName());
        assertEquals(find_member.getBirth(), member.getBirth());
        assertEquals(find_member.getParent_password(), member.getParent_password());
    }

    @Test
    @DisplayName("회원 정보 변경")
    public void 회원_정보_변경() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);

        save_member = Member.builder()
                .id(member.getId())
                .password("change_password")
                .name(member.getName())
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password(member.getParent_password())
                .build();
        Member change_member = memberRepository.save(save_member);

        //then
        assertEquals(change_member.getId(), member.getId());
        assertNotEquals(change_member.getPassword(), member.getPassword());
    }

    @Test
    @DisplayName("회원 정보 삭제")
    public void 회원_정보_삭제() {
        //given
        Member member = Member.builder()
                .id("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parent_password("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);
        memberRepository.deleteById(String.valueOf(save_member.getIdx()));

        //then
        assertEquals(memberRepository.findAll().size(), 0);
    }
}