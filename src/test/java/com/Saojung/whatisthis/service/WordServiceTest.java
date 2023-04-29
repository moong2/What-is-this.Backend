package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.dto.WordDto;
import com.Saojung.whatisthis.exception.DateException;
import com.Saojung.whatisthis.exception.LevelException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
class WordServiceTest {
    @InjectMocks
    private WordService wordService;

    @Mock
    private WordRepository wordRepository;
    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        wordService = new WordService(wordRepository, memberRepository);
    }

    @Test
    @DisplayName("Create 테스트")
    void 단어_추가() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(any())).willReturn(returnWord);

        //when
        WordDto returnDto = wordService.create(wordDto);

        //then
        assertEquals(returnDto.getIdx(), returnWord.getIdx());
    }

    @Test
    @DisplayName("Read 테스트")
    void 단어_조회() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(any())).willReturn(returnWord);

        WordDto returnDto = wordService.create(wordDto);

        //when
        //then
        assertEquals(wordDto.getWord(), returnDto.getWord());
        assertEquals(wordDto.getLevel(), returnDto.getLevel());
        assertEquals(wordDto.getSuccessLevel(), returnDto.getSuccessLevel());
        assertEquals(wordDto.getDate(), returnDto.getDate());
        assertEquals(wordDto.getMember().getIdx(), returnDto.getMember().getIdx());
    }

    @Test
    @DisplayName("Update 테스트")
    void 단어정보_업데이트() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        Word changeWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(3)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(any())).willReturn(returnWord);

        WordDto returnDto = wordService.create(wordDto);

        //when
        returnDto.setLevel(3);
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(any())).willReturn(changeWord);

        WordDto changeDto = wordService.update(returnDto);

        //then
        assertEquals(returnDto.getIdx(), changeDto.getIdx());
        assertNotEquals(wordDto.getLevel(), changeDto.getLevel());
    }

    @Test
    @DisplayName("레벨이 음수일 경우")
    void 비정상적인_레벨_테스트() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", -1, -5, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        //when
        //then
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        assertThrows(LevelException.class, () -> {
            WordDto returnDto = wordService.create(wordDto);
        });
    }

    @Test
    @DisplayName("레벨이 3 초과일 경우")
    void 비정상적인_레벨_테스트2() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 4, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        //when
        //then
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        assertThrows(LevelException.class, () -> {
            WordDto returnDto = wordService.create(wordDto);
        });
    }

    @Test
    @DisplayName("제공 레벨이 성공 레벨보다 낮을 경우")
    void 비정상적인_레벨_테스트3() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", -1, -5, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        //when
        //then
        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        assertThrows(LevelException.class, () -> {
            WordDto returnDto = wordService.create(wordDto);
        });
    }

    @Test
    @DisplayName("Delete 테스트")
    void 삭제_테스트() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(any())).willReturn(returnWord);

        WordDto returnDto = wordService.create(wordDto);

        BDDMockito.given(wordRepository.findById(returnDto.getIdx())).willReturn(Optional.of(returnWord));

        //when
        wordService.delete(returnWord.getIdx());

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        List<Word> words = new ArrayList<>();
        BDDMockito.given(wordRepository.findAllByMember_Idx(member.getIdx())).willReturn(words);

        //then
        assertEquals(wordService.getAllWords(member.getIdx()).size(), 0);
    }

    @Test
    @DisplayName("레벨 조회 에러 테스트")
    void 레벨_조회_에러() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(any())).willReturn(returnWord);

        WordDto returnDto = wordService.create(wordDto);

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        //when
        //then
        assertThrows(LevelException.class, () -> {
            wordService.getWordsMatchLevels(1L, 2, 3);
        });
    }

    @Test
    @DisplayName("날짜 조회 에러 테스트")
    void 날짜_조회_에러() {
        //given
        Member member = Member.builder()
                .idx(1L)
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .member(member)
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(any())).willReturn(returnWord);

        WordDto returnDto = wordService.create(wordDto);

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));

        //when
        //then
        assertThrows(DateException.class, () -> {
            wordService.getWordsAfterDate(1L, LocalDateTime.now().plusMonths(2));
        });
    }
}