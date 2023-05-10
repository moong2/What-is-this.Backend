package com.Saojung.whatisthis.repository;

import com.Saojung.whatisthis.domain.Member;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WordRepositoryTest {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("단어 추가")
    void 단어추가() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now())
                .build();

        //when
        Word saveWord = wordRepository.save(word);

        //then
        assertEquals(wordRepository.findAll().size(), 1);
    }

    @Test
    @DisplayName("NotNull Attribute 테스트")
    void NotNull_확인() {
        assertThrows(NullPointerException.class, () -> {
            Word word = Word.builder()
                    .word("사과")
                    .level(1)
                    .build();
        });
    }

    @Test
    @DisplayName("학습 단어 정보 조회")
    void 학습_단어_정보_조회() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now())
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
    @DisplayName("빈 DB 조회")
    void 빈db_조회() {
        //given

        //when
        List<Word> findWord = wordRepository.findAllByMember_Idx(1L);

        //then
        assertEquals(findWord.size(), 0);
    }

    @Test
    @DisplayName("등록되지 않은 단어 조회")
    void 등록되지_않은_단어_조회() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now())
                .build();
        
        Word saveWord = wordRepository.save(word);
        wordRepository.deleteAll();

        //when
        //then
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            wordRepository.getReferenceById(saveWord.getIdx());
        });
    }

    @Test
    @DisplayName("멤버 인덱스로 조회")
    void 멤버_인덱스로_조회() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Member member2 = Member.builder()
                .userId("another_castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now())
                .member(member2)
                .build();

        Member saveMember = memberRepository.save(member);
        memberRepository.save(member2);

        wordRepository.save(word);
        wordRepository.save(word2);

        //when
        List<Word> findWords = wordRepository.findAllByMember_Idx(saveMember.getIdx());

        //then
        assertEquals(findWords.size(), 1);
    }

    @Test
    @DisplayName("학습 순서대로 조회")
    void 학습순서대로_조회() {
        //given
        Member member = Member.builder()
                .userId("castlehi")
                .password("password")
                .name("박성하")
                .birth(LocalDate.of(2000, 06, 17))
                .parentPassword("p_password")
                .build();

        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("과자")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now().plusMonths(2))
                .member(member)
                .build();

        Member saveMember = memberRepository.save(member);

        wordRepository.save(word);
        wordRepository.save(word2);
        
        //when
        List<Word> findWords = wordRepository.findAllByMember_IdxOrderByDate(saveMember.getIdx());

        //then
        assertEquals(findWords.get(0).getWord(), "사과");
        assertEquals(findWords.get(1).getWord(), "과자");
    }

    @Test
    @DisplayName("난이도 선택 조회")
    void 단어학습_결과_중_원하는_난이도_통과_단어_조회() {
        //given
        Member member = Member.builder()
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
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Member saveMember = memberRepository.save(member);

        wordRepository.save(word);
        wordRepository.save(word2);

        //when
        List<Word> findWords = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(saveMember.getIdx(), 2, 2);

        //then
        assertEquals(findWords.size(), 1);
    }

    @Test
    @DisplayName("일정 날짜 기준 이후의 단어학습만 조회")
    void 일정날짜_기준_이후의_단어학습_조회() {
        //given
        Member member = Member.builder()
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
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now().minusYears(1))
                .member(member)
                .build();

        Member saveMember = memberRepository.save(member);

        wordRepository.save(word);
        wordRepository.save(word2);

        //when
        LocalDate now = LocalDate.now();
        LocalDate before2Weeks = now.minusWeeks(2);
        List<Word> findWords = wordRepository.findAllByMember_IdxAndDateGreaterThan(saveMember.getIdx(), LocalDateTime.of(before2Weeks.getYear(), before2Weeks.getMonth(), before2Weeks.getDayOfMonth(), 00, 00, 00));

        //then
        assertEquals(findWords.size(), 1);
    }

    @Test
    @DisplayName("일정날짜, 일정 난이도 기준 조회")
    void 일정날짜_기준_이후에_일정_난이도로_학습한_단어_조회() {
        //given
        Member member = Member.builder()
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
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now().minusWeeks(1))
                .member(member)
                .build();

        Member saveMember = memberRepository.save(member);

        wordRepository.save(word);
        wordRepository.save(word2);

        //when
        LocalDate now = LocalDate.now();
        LocalDate before2Weeks = now.minusWeeks(2);

        List<Word> findWords = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThan(saveMember.getIdx(), 2, 2, LocalDateTime.of(before2Weeks.getYear(), before2Weeks.getMonth(), before2Weeks.getDayOfMonth(), 00, 00, 00));

        //then
        assertEquals(findWords.size(), 1);
    }

    @Test
    @DisplayName("학습 단어 제거")
    void 학습_단어_제거() {
        //given
        Word word = Word.builder()
                .word("사과")
                .level(1)
                .successLevel(1)
                .date(LocalDateTime.now())
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
                .date(LocalDateTime.now())
                .build();

        Word saveWord = wordRepository.save(word);

        //when
        wordRepository.update(word.getWord(), word.getLevel() + 1, word.getSuccessLevel(), word.getIdx());
        Optional<Word> changeWord = wordRepository.findById(saveWord.getIdx());

        //then
        assertEquals(changeWord.get().getWord(), saveWord.getWord());
        assertNotEquals(changeWord.get().getLevel(), saveWord.getLevel());
    }

    @Test
    @DisplayName("기간 동안 학습한 단어 제공")
    void 기간_학습_단어_제공() {
        //given
        Member member = Member.builder()
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
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now().minusYears(1))
                .member(member)
                .build();

        Member saveMember = memberRepository.save(member);

        Word save_word1 = wordRepository.save(word);
        Word save_word2 = wordRepository.save(word2);

        //when
        List<Word> words = wordRepository.findAllByMember_IdxAndDateBetween(saveMember.getIdx(), LocalDateTime.now().minusWeeks(8), LocalDateTime.now());

        //then
        assertEquals(words.get(0).getIdx(), save_word1.getIdx());
    }

    @Test
    @DisplayName("기간 동안 일정 기준 이상 학습한 단어 제공")
    void 기간_학습_난이도_기준_단어_제공() {
        //given
        Member member = Member.builder()
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
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now().minusYears(1))
                .member(member)
                .build();

        Word word3 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now().minusWeeks(2))
                .member(member)
                .build();

        Member saveMember = memberRepository.save(member);

        Word save_word1 = wordRepository.save(word);
        Word save_word2 = wordRepository.save(word2);
        Word save_word3 = wordRepository.save(word3);

        //when
        List<Word> words = wordRepository.findAllByMember_IdxAndLevelGreaterThanAndDateBetween(saveMember.getIdx(), 1, LocalDateTime.now().minusWeeks(8), LocalDateTime.now());

        //then
        assertEquals(words.size(), 2);
    }

    @Test
    @DisplayName("기간 동안 일정 기준 이상 학습 성공한 단어 제공")
    void 기간_학습_난이도_성공난이도_기준_단어_제공() {
        //given
        Member member = Member.builder()
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
                .date(LocalDateTime.now())
                .member(member)
                .build();

        Word word2 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now().minusYears(1))
                .member(member)
                .build();

        Word word3 = Word.builder()
                .word("사과")
                .level(2)
                .successLevel(2)
                .date(LocalDateTime.now().minusWeeks(2))
                .member(member)
                .build();

        Member saveMember = memberRepository.save(member);

        Word save_word1 = wordRepository.save(word);
        Word save_word2 = wordRepository.save(word2);
        Word save_word3 = wordRepository.save(word3);

        //when
        List<Word> words = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateBetween(saveMember.getIdx(), 2, 2, LocalDateTime.now().minusWeeks(8), LocalDateTime.now());

        //then
        assertEquals(words.size(), 1);
    }
}