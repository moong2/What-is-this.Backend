package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.dto.AnalysisDto;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.repository.AnalysisRepository;
import com.Saojung.whatisthis.repository.MemberRepository;
import com.Saojung.whatisthis.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
class AnalysisServiceTest {
    @InjectMocks
    private AnalysisService analysisService;

    @Mock
    private WordRepository wordRepository;
    @Mock
    private AnalysisRepository analysisRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        analysisService = new AnalysisService(wordRepository, analysisRepository);
    }

    @Test
    @DisplayName("Update 테스트")
    void 업데이트_테스트() {
        //given
        Analysis analysis = Analysis.builder()
                .idx(1L)
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        AnalysisDto analysisDto = new AnalysisDto(
                1L, 1, 1, 0.0, 0.0, 0.0
        );

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(analysis)
                .build();

        MemberDto memberDto = new MemberDto(
                1L, "castlehi", "password", "박성하", LocalDate.of(2000, 06, 17), "p_password", null, null
        );

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(analysis);

        //when
        AnalysisDto returnDto = analysisService.update(analysisDto);

        //then
        assertEquals(returnDto.getIdx(), analysisDto.getIdx());
        assertNotEquals(returnDto.getCount(), analysisDto.getCount());
        assertEquals(member.getAnalysis().getCount(), returnDto.getCount());
    }
}