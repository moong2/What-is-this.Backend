package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    public void 회원가입() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
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
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .parentPassword("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);

        //then
        assertEquals(member.getUserId(), save_member.getUserId());
    }

    @Test
    @DisplayName("NotNull Attribute 테스트")
    public void NotNull_확인() {
        assertThrows(NullPointerException.class, () -> {
            Member member = Member.builder()
                    .userId("castlehi")
                    .password("password")
                    .birth(LocalDate.of(2000, 06, 17))
                    .parentPassword("p_password")
                    .build();
        });
    }

    @Test
    @DisplayName("아이디 조회")
    public void 아이디_조회() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);
        Member find_member = memberRepository.getReferenceById(save_member.getIdx());

        //then
        assertEquals(find_member.getUserId(), save_member.getUserId());
    }

    @Test
    @DisplayName("<id, pw> 동일 체크 성공")
    public void 로그인_성공() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        String id = "castlehi";
        String pw = "password";
        Member save_member = memberRepository.save(member);
        Optional<Member> find_member = memberRepository.findByUserId(id);

        //then
        assertEquals(pw, find_member.get().getPassword());
    }

    @Test
    @DisplayName("<id, pw> 중 id 동일 체크 실패")
    public void 로그인_실패_id() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        String id = "wrong";
        String pw = "password";
        Member save_member = memberRepository.save(member);
        Optional<Member> find_member = memberRepository.findByUserId(id);

        //then
        assertEquals(find_member, Optional.empty());
    }

    @Test
    @DisplayName("<id, pw> 중 pw 동일 체크 실패")
    public void 로그인_실패_pw() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        String id = "castlehi";
        String pw = "wrong";
        Member save_member = memberRepository.save(member);
        Optional<Member> find_member = memberRepository.findByUserId(id);

        //then
        assertNotEquals(find_member.get().getPassword(), pw);
    }

    @Test
    @DisplayName("부모 pw 동일 체크 성공")
    public void 부모페이지_로그인_성공_pw() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        String id = "castlehi";
        String p_pw = "p_password";
        Member save_member = memberRepository.save(member);
        Optional<Member> find_member = memberRepository.findByUserId(id);

        //then
        assertEquals(find_member.get().getParentPassword(), p_pw);
    }

    @Test
    @DisplayName("부모 pw 동일 체크 실패")
    public void 부모페이지_로그인_실패_pw() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        String id = "castlehi";
        String p_pw = "wrong";
        Member save_member = memberRepository.save(member);
        Optional<Member> find_member = memberRepository.findByUserId(id);

        //then
        assertNotEquals(find_member.get().getParentPassword(), p_pw);
    }

    @Test
    @DisplayName("회원 정보 조회")
    public void 회원_정보_조회() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);
        Member find_member = memberRepository.getReferenceById(save_member.getIdx());

        //then
        assertEquals(find_member.getUserId(), member.getUserId());
        assertEquals(find_member.getPassword(), member.getPassword());
        assertEquals(find_member.getName(), member.getName());
        assertEquals(find_member.getBirth(), member.getBirth());
        assertEquals(find_member.getParentPassword(), member.getParentPassword());
    }

    @Test
    @DisplayName("회원 정보 변경")
    public void 회원_정보_변경() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);

        memberRepository.update(save_member.getUserId(), "change_password", save_member.getName(), save_member.getBirth(), save_member.getParentPassword(), save_member.getIdx());
        Member save_change_member = memberRepository.getReferenceById(save_member.getIdx());

        //then
        assertEquals(save_change_member.getUserId(), member.getUserId());
        assertNotEquals(save_change_member.getPassword(), member.getPassword());
    }

    @Test
    @DisplayName("회원 정보 삭제")
    public void 회원_정보_삭제() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);
        memberRepository.deleteById(save_member.getIdx());

        //then
        assertEquals(memberRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("등록되지 않은 회원 조회")
    public void 등록되지_않은_회원_조회() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);
        memberRepository.deleteById(save_member.getIdx());

        //then
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            memberRepository.getReferenceById(save_member.getIdx());
        });
    }
}