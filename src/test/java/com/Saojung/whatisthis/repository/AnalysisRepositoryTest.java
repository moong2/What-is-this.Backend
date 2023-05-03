package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

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
                .success_rate_1(0.0)
                .success_rate_2(0.0)
                .success_rate_3(0.0)
                .build();

        //when
        analysisRepository.save(analysis);

        //then
        assertEquals(analysisRepository.findAll().size(), 1);
    }
}