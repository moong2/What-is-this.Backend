package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.MemberDto;
import com.Saojung.whatisthis.dto.WordDto;
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
        wordService = new WordService(wordRepository);
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

        Word word = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(word)).willReturn(returnWord);

        //when
        WordDto returnDto = wordService.study(wordDto);

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

        Word word = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .build();

        Word returnWord = Word.builder()
                .idx(1L)
                .word("사과")
                .level(2)
                .successLevel(1)
                .date(LocalDateTime.of(2023, 04, 28, 13, 52, 00))
                .build();

        WordDto wordDto = new WordDto(
                null, "사과", 2, 1, LocalDateTime.of(2023, 04, 28, 13, 52 ,00), member
        );

        BDDMockito.given(memberRepository.findById(member.getIdx())).willReturn(Optional.of(member));
        BDDMockito.given(wordRepository.save(word)).willReturn(returnWord);
        wordService.signUp(wordDto);

        List<Word>
        BDDMockito.given(wordRepository.findAllByMember_Idx(1L)).willReturn()

        //when

        //then
    }
}