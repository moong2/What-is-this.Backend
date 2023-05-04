package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AnalysisRepositoryTest {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("분석값 추가")
    void 분석값_추가() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        memberRepository.save(member);

        Analysis analysis = Analysis.builder()
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        //when
        analysisRepository.save(analysis);

        //then
        assertEquals(analysisRepository.findAll().size(), 1);
    }

    @Test
    @DisplayName("NotNull Attribute 테스트")
    void NotNull_확인() {
        assertThrows(NullPointerException.class, () -> {
            Analysis analysis = Analysis.builder()
                    .count(0)
                    .successRate1(0.0)
                    .successRate2(0.0)
                    .successRate3(0.0)
                    .build();
        });
    }

    @Test
    @DisplayName("분석 결과 조회 테스트")
    void 분석_결과_조회() {
        //given
        Analysis analysis = Analysis.builder()
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Analysis save_analysis = analysisRepository.save(analysis);

        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(save_analysis)
                .build();

        Member save_member = memberRepository.save(member);

        //when
        Optional<Member> byId = memberRepository.findById(save_member.getIdx());
        Analysis return_analysis = byId.get().getAnalysis();

        //then
        assertEquals(save_analysis.getIdx(), return_analysis.getIdx());
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
        Analysis analysis = byId.get().getAnalysis();

        //then
        assertEquals(analysis, null);
    }

    @Test
    @DisplayName("분석값 업데이트 테스트")
    void 분석값_업데이트() {
        //given
        Analysis analysis = Analysis.builder()
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Analysis save_analysis = analysisRepository.save(analysis);

        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(save_analysis)
                .build();

        Member save_member = memberRepository.save(member);

        //when
        analysisRepository.update(save_analysis.getCount() + 1, save_analysis.getLevel(), save_analysis.getSuccessRate1(), save_analysis.getSuccessRate2(), save_analysis.getSuccessRate3(), save_analysis.getIdx());

        Optional<Member> byId = memberRepository.findById(save_member.getIdx());
        Analysis update_analysis = byId.get().getAnalysis();

        //then
        assertEquals(update_analysis.getIdx(), save_analysis.getIdx());
        assertNotEquals(update_analysis.getCount(), save_analysis.getCount());
    }

    @Test
    @DisplayName("분석값 삭제 테스트")
    void 분석값_삭제() {
        //given
        Analysis analysis = Analysis.builder()
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Analysis save_analysis = analysisRepository.save(analysis);

        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(save_analysis)
                .build();

        Member save_member = memberRepository.save(member);

        //when
        Analysis return_analysis = save_member.getAnalysis();
        analysisRepository.delete(return_analysis);

        Optional<Analysis> byId = analysisRepository.findById(return_analysis.getIdx());

        //then
        assertEquals(byId, Optional.empty());
    }
}