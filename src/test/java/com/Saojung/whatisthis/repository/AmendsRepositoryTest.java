package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Amends;
import com.Saojung.whatisthis.domain.Member;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AmendsRepositoryTest {

    @Autowired
    private AmendsRepository amendsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("보상 추가")
    void 보상_추가() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        memberRepository.save(member);

        Amends amends = Amends.builder()
                .amends("마이쮸")
                .goal(5)
                .remain(0)
                .build();

        //when
        amendsRepository.save(amends);

        //then
        assertEquals(amendsRepository.findAll().size(), 1);
    }

    @Test
    @DisplayName("NotNull Attribute 테스트")
    void NotNull_확인() {
        assertThrows(NullPointerException.class, () -> {
            Amends amends = Amends.builder()
                    .amends(null)
                    .goal(0)
                    .build();
        });
    }

    @Test
    @DisplayName("보상 내용 읽기")
    void 보상_내용_읽기() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        memberRepository.save(member);

        Amends amends = Amends.builder()
                .amends("마이쮸")
                .goal(5)
                .remain(0)
                .build();

        //when
        Amends save_amend = amendsRepository.save(amends);

        //then
        assertEquals(save_amend.getAmends(), amends.getAmends());
        assertEquals(save_amend.getGoal(), amends.getGoal());
        assertEquals(save_amend.getRemain(), amends.getRemain());
    }

    @Test
    @DisplayName("빈 DB 조회")
    void 빈db_조회() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Member save_member = memberRepository.save(member);

        //when
        Optional<Member> byId = memberRepository.findById(save_member.getIdx());

        Amends amends = byId.get().getAmends();

        //then
        assertEquals(amends, null);
    }

    @Test
    @DisplayName("보상 업데이트")
    void 보상_새로_업데이트() {
        //given
        Amends amends = Amends.builder()
                .amends(null)
                .goal(0)
                .remain(0)
                .build();

        Amends save_amends = amendsRepository.save(amends);

        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .amends(save_amends)
                .build();

        Member save_member = memberRepository.save(member);

        //when
        amendsRepository.update("마이쮸", 5, 5, save_amends.getIdx());

        Optional<Member> byId = memberRepository.findById(save_member.getIdx());
        Amends update_amends = byId.get().getAmends();
        
        //then
        assertEquals(save_amends.getIdx(), update_amends.getIdx());
        assertNotEquals(save_amends.getAmends(), update_amends.getAmends());
        assertNotEquals(save_amends.getGoal(), update_amends.getGoal());
        assertNotEquals(save_amends.getRemain(), update_amends.getRemain());
    }

    @Test
    @DisplayName("보상 삭제")
    void 보상_삭제() {
        //given
        Amends amends = Amends.builder()
                .amends(null)
                .goal(0)
                .remain(0)
                .build();

        Amends save_amends = amendsRepository.save(amends);

        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .amends(save_amends)
                .build();

        Member save_member = memberRepository.save(member);

        //when
        amendsRepository.delete(save_member.getAmends());

        Optional<Amends> byId = amendsRepository.findById(save_member.getAmends().getIdx());

        //then
        assertEquals(byId, Optional.empty());
    }
}