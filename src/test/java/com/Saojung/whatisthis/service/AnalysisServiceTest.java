package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.domain.Word;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
class AnalysisServiceTest {
    @InjectMocks
    private AnalysisService analysisService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private WordRepository wordRepository;
    @Mock
    private AnalysisRepository analysisRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        analysisService = new AnalysisService(memberRepository, wordRepository, analysisRepository);
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

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(analysis);

        //when
        AnalysisDto returnDto = analysisService.update(analysisDto);

        //then
        assertEquals(returnDto.getIdx(), analysisDto.getIdx());
        assertNotEquals(returnDto.getCount(), analysisDto.getCount());
        assertEquals(member.getAnalysis().getCount(), returnDto.getCount());
    }

    @Test
    @DisplayName("모든 단어 데이터 분석값 저장")
    void 모든값_분석() {
        //given
        Analysis analysis = Analysis.builder()
                .idx(1L)
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(analysis)
                .build();

        Word word = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Word word2 = Word.builder()
                .idx(2L)
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Analysis change_analysis = Analysis.builder()
                .idx(1L)
                .count(2)
                .level(2)
                .successRate1(100.0)
                .successRate2(50.0)
                .successRate3(0.0)
                .build();

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        List<Word> words = new ArrayList<>();
        words.add(word);
        words.add(word2);
        BDDMockito.given(wordRepository.findAllByMember_Idx(member.getIdx())).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 1)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 2)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 3)).willReturn(new ArrayList<>());

        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 1, 1)).willReturn(new ArrayList<>());
        ArrayList<Word> words1 = new ArrayList<>();
        words1.add(word);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 2, 1)).willReturn(words1);
        ArrayList<Word> words2 = new ArrayList<>();
        words2.add(word2);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 2, 2)).willReturn(words2);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 1)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 2)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 3)).willReturn(new ArrayList<>());

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(change_analysis);

        //when
        AnalysisDto calculateDto = analysisService.calculate(member.getIdx());

        //then
        assertEquals(calculateDto.getCount(), change_analysis.getCount());
        assertEquals(calculateDto.getLevel(), change_analysis.getLevel());
        assertEquals(calculateDto.getSuccessRate1(), change_analysis.getSuccessRate1());
        assertEquals(calculateDto.getSuccessRate2(), change_analysis.getSuccessRate2());
        assertEquals(calculateDto.getSuccessRate3(), change_analysis.getSuccessRate3());
    }

    @Test
    @DisplayName("일정 기간 이후 분석값 저장")
    void 기간_분석값_저장() {
        //given
        Analysis analysis = Analysis.builder()
                .idx(1L)
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(analysis)
                .build();

        Word word = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Word word2 = Word.builder()
                .idx(2L)
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Word word3 = Word.builder()
                .idx(2L)
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.of(2020, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Word word4 = Word.builder()
                .idx(2L)
                .word("사과")
                .level(3)
                .successLevel(3)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Analysis change_analysis = Analysis.builder()
                .idx(1L)
                .count(2)
                .level(2)
                .successRate1(100.0)
                .successRate2(66.6)
                .successRate3(100.0)
                .build();

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        LocalDateTime date = LocalDateTime.now().minusYears(1);
        List<Word> words = new ArrayList<>();
        words.add(word);
        words.add(word2);
        words.add(word3);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndDateAfter(member.getIdx(), date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 1, date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 2, date)).willReturn(words);
        ArrayList<Word> words3 = new ArrayList<>();
        words3.add(word3);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 3, date)).willReturn(words3);

        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 1, 1, date)).willReturn(new ArrayList<>());
        ArrayList<Word> words1 = new ArrayList<>();
        words1.add(word);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 2, 1, date)).willReturn(words1);
        ArrayList<Word> words2 = new ArrayList<>();
        words2.add(word2);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 2, 2, date)).willReturn(words2);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 1, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 2, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 3, date)).willReturn(words3);

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(change_analysis);

        //when
        AnalysisDto calculateDto = analysisService.calculate(member.getIdx(), date);

        //then
        assertEquals(calculateDto.getCount(), change_analysis.getCount());
        assertEquals(calculateDto.getLevel(), change_analysis.getLevel());
        assertEquals(calculateDto.getSuccessRate1(), change_analysis.getSuccessRate1());
        assertEquals(calculateDto.getSuccessRate2(), change_analysis.getSuccessRate2());
        assertEquals(calculateDto.getSuccessRate3(), change_analysis.getSuccessRate3());
    }

    @Test
    @DisplayName("전체_단어학습_난이도 설정 - 10개 미만")
    void 전체_단어학습_난이도_설정_10개미만() {
        //given
        Analysis analysis = Analysis.builder()
                .idx(1L)
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(analysis)
                .build();

        Word word = Word.builder()
                .idx(1L)
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Analysis change_analysis = Analysis.builder()
                .idx(1L)
                .count(7)
                .level(1)
                .successRate1(100.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        List<Word> words = new ArrayList<>();
        for (int i = 0; i < 7; i++) words.add(word);
        BDDMockito.given(wordRepository.findAllByMember_Idx(member.getIdx())).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 1)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 2)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 3)).willReturn(new ArrayList<>());

        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 1, 1)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 2, 1)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 2, 2)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 1)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 2)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 3)).willReturn(new ArrayList<>());

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(change_analysis);

        //when
        AnalysisDto resultDto = analysisService.calculate(member.getIdx());

        //then
        assertEquals(resultDto.getLevel(), 1);
    }

    @Test
    @DisplayName("전체_단어학습_난이도 설정 - 10개 이상")
    void 전체_단어학습_난이도_설정_10개이상() {
        //given
        Analysis analysis = Analysis.builder()
                .idx(1L)
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(analysis)
                .build();

        Word word = Word.builder()
                .idx(1L)
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Analysis change_analysis = Analysis.builder()
                .idx(1L)
                .count(10)
                .level(2)
                .successRate1(100.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        List<Word> words = new ArrayList<>();
        for (int i = 0; i < 10; i++) words.add(word);
        BDDMockito.given(wordRepository.findAllByMember_Idx(member.getIdx())).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 1)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 2)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfter(member.getIdx(), 3)).willReturn(new ArrayList<>());

        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 1, 1)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 2, 1)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 2, 2)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 1)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 2)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member.getIdx(), 3, 3)).willReturn(new ArrayList<>());

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(change_analysis);

        //when
        AnalysisDto resultDto = analysisService.calculate(member.getIdx());

        //then
        assertEquals(resultDto.getLevel(), 2);
    }

    @Test
    @DisplayName("기간_단어학습_난이도 설정 - 10개 미만")
    void 기간_단어학습_난이도_설정_10개미만() {
        //given
        Analysis analysis = Analysis.builder()
                .idx(1L)
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(analysis)
                .build();

        Word word = Word.builder()
                .idx(1L)
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Word word2 = Word.builder()
                .idx(2L)
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.of(2020, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Analysis change_analysis = Analysis.builder()
                .idx(1L)
                .count(7)
                .level(1)
                .successRate1(100.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        LocalDateTime date = LocalDateTime.now().minusWeeks(2);
        List<Word> words = new ArrayList<>();
        for (int i = 0; i < 7; i++) words.add(word);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndDateAfter(member.getIdx(), date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 1, date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 2, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 3, date)).willReturn(new ArrayList<>());

        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 1, 1, date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 2, 1, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 2, 2, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 1, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 2, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 3, date)).willReturn(new ArrayList<>());

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(change_analysis);

        //when
        AnalysisDto calculateDto = analysisService.calculate(member.getIdx(), date);

        //then
        assertEquals(calculateDto.getLevel(), 1);
    }

    @Test
    @DisplayName("기간_단어학습_난이도 설정 - 10개 이상")
    void 기간_단어학습_난이도_설정_10개이상() {
        //given
        Analysis analysis = Analysis.builder()
                .idx(1L)
                .count(0)
                .level(1)
                .successRate1(0.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .analysis(analysis)
                .build();

        Word word = Word.builder()
                .idx(1L)
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Word word2 = Word.builder()
                .idx(2L)
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.of(2020, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Analysis change_analysis = Analysis.builder()
                .idx(1L)
                .count(7)
                .level(2)
                .successRate1(100.0)
                .successRate2(0.0)
                .successRate3(0.0)
                .build();

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        LocalDateTime date = LocalDateTime.now().minusWeeks(2);
        List<Word> words = new ArrayList<>();
        for (int i = 0; i < 10; i++) words.add(word);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndDateAfter(member.getIdx(), date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 1, date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 2, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAfterAndDateAfter(member.getIdx(), 3, date)).willReturn(new ArrayList<>());

        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 1, 1, date)).willReturn(words);
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 2, 1, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 2, 2, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 1, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 2, date)).willReturn(new ArrayList<>());
        BDDMockito.given(wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateAfter(member.getIdx(), 3, 3, date)).willReturn(new ArrayList<>());

        BDDMockito.given(analysisRepository.findById(member.getAnalysis().getIdx())).willReturn(Optional.of(analysis));
        BDDMockito.given(analysisRepository.save(any())).willReturn(change_analysis);

        //when
        AnalysisDto calculateDto = analysisService.calculate(member.getIdx(), date);

        //then
        assertEquals(calculateDto.getLevel(), 2);
    }
}