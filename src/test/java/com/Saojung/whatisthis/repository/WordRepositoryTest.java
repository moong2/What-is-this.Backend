package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WordRepositoryTest {

    @Autowired
    private WordRepository wordRepository;

    @Test
    @DisplayName("단어 추가")
    void 단어추가() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .build();

        //when
        Word saveWord = wordRepository.save(word);

        //then
        assertEquals(wordRepository.findAll().size(), 1);
    }

    @Test
    @DisplayName("학습 단어 정보 조회")
    void 학습_단어_정보_조회() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .build();

        Word saveWord = wordRepository.save(word);

        //when
        Optional<Word> findWord = wordRepository.findById(saveWord.getIdx());

        //then
        assertEquals(saveWord.getWord(), findWord.get().getWord());
        assertEquals(saveWord.getLevel(), findWord.get().getLevel());
        assertEquals(saveWord.getSuccessLevel(), findWord.get().getSuccessLevel());
        assertEquals(saveWord.getIdx(), findWord.get().getIdx());
    }

    @Test
    @DisplayName("학습 단어 제거")
    void 학습_단어_제거() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .build();

        Word saveWord = wordRepository.save(word);

        //when
        wordRepository.delete(saveWord);

        //then
        assertEquals(wordRepository.findAll().size(), 0);
    }

    @Test
    @DisplayName("학습 단어 업데이트")
    void 학습_단어_업데이트() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .build();

        Word saveWord = wordRepository.save(word);

        //when
        wordRepository.update(word.getWord(), word.getLevel() + 1, word.getSuccessLevel(), word.getIdx());
        Optional<Word> changeWord = wordRepository.findById(saveWord.getIdx());

        //then
        assertEquals(changeWord.get().getWord(), saveWord.getWord());
        assertNotEquals(changeWord.get().getLevel(), saveWord.getLevel());
    }
}