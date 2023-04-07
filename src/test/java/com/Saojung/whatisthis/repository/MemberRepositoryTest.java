package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DuplicateKeyException;

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
                .age(24)
                .parent_password("p_password")
                .build();

        //when
        Member save_member = memberRepository.save(member);

        //then
        assertEquals(member.getId(), save_member.getId());
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
                    .name("박성하")
                    .age(24)
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
                .age(24)
                .build();

        //when
        Member save_member = memberRepository.save(member);
        Member find_member = memberRepository.getReferenceById(String.valueOf(save_member.getIdx()));

        //then
        assertEquals(find_member.getId(), save_member.getId());
    }
}