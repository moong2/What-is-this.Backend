package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.WordDto;
import com.Saojung.whatisthis.exception.DateException;
import com.Saojung.whatisthis.exception.LevelException;
import com.Saojung.whatisthis.exception.NoMemberException;
import com.Saojung.whatisthis.exception.NoWordException;
import com.Saojung.whatisthis.repository.MemberRepository;
import com.Saojung.whatisthis.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WordService {

    private final WordRepository wordRepository;
    private final MemberRepository memberRepository;

    public WordDto create(WordDto wordDto) {
        if (memberRepository.findById(wordDto.getMember().getIdx()).orElse(null) == null)
            throw new NoMemberException("학습한 회원이 존재하지 않습니다.");

        Word word;
        try {
            word = Word.builder()
                    .word(wordDto.getWord())
                    .level(wordDto.getLevel())
                    .successLevel(wordDto.getSuccessLevel())
                    .date(wordDto.getDate())
                    .member(wordDto.getMember())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (word.getLevel() < 0 || word.getLevel() > 3 ||
                word.getSuccessLevel() < 0 || word.getSuccessLevel() > 3 ||
                word.getSuccessLevel() > word.getLevel())
            throw new LevelException("비정상적인 레벨입니다.");

        return WordDto.from(wordRepository.save(word));
    }

    public WordDto update(WordDto wordDto) {
        if (memberRepository.findById(wordDto.getMember().getIdx()).orElse(null) == null)
            throw new NoMemberException("학습한 회원이 존재하지 않습니다.");

        Word word;
        try {
            word = Word.builder()
                    .word(wordDto.getWord())
                    .level(wordDto.getLevel())
                    .successLevel(wordDto.getSuccessLevel())
                    .date(wordDto.getDate())
                    .member(wordDto.getMember())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (word.getLevel() < 0 || word.getLevel() > 3 ||
                word.getSuccessLevel() < 0 || word.getSuccessLevel() > 3 ||
                word.getSuccessLevel() > word.getLevel())
            throw new LevelException("비정상적인 레벨입니다.");

        return WordDto.from(wordRepository.save(word));
    }

    public void delete(Long idx) {
        Optional<Word> findWord = wordRepository.findById(idx);

        if (findWord.equals(Optional.empty()))
            throw new NoWordException("삭제할 단어가 존재하지 않습니다.");

        wordRepository.delete(findWord.get());
    }

    public List<WordDto> getAllWords(Long memberIdx) {
        if (memberRepository.findById(memberIdx).orElse(null) == null)
            throw new NoMemberException("학습한 회원이 존재하지 않습니다.");

        List<Word> findWords = wordRepository.findAllByMember_Idx(memberIdx);

        List<WordDto> returnWords = new ArrayList<>();
        for (Word findWord : findWords) {
            returnWords.add(WordDto.from(findWord));
        }

        return returnWords;
    }

    public List<WordDto> getWordsMatchLevels(Long memberIdx, Integer level, Integer sLevel) {
        if (memberRepository.findById(memberIdx).orElse(null) == null)
            throw new NoMemberException("학습한 회원이 존재하지 않습니다.");

        if (level < sLevel)
            throw new LevelException("레벨 기준이 잘못되었습니다.");

        List<Word> findWords = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(memberIdx, level, sLevel);

        List<WordDto> returnWords = new ArrayList<>();
        for (Word findWord : findWords) {
            returnWords.add(WordDto.from(findWord));
        }

        return returnWords;
    }

    public List<WordDto> getWordsByDate(Long memberIdx) {
        if (memberRepository.findById(memberIdx).orElse(null) == null)
            throw new NoMemberException("학습한 회원이 존재하지 않습니다.");

        List<Word> findWords = wordRepository.findAllByMember_IdxOrderByDate(memberIdx);

        List<WordDto> returnWords = new ArrayList<>();
        for (Word findWord : findWords) {
            returnWords.add(WordDto.from(findWord));
        }

        return returnWords;
    }

    public List<WordDto> getWordsGreaterThanDate(Long memberIdx, LocalDateTime date) {
        if (memberRepository.findById(memberIdx).orElse(null) == null)
            throw new NoMemberException("학습한 회원이 존재하지 않습니다.");

        if (date.isAfter(LocalDateTime.now()))
            throw new DateException("기준 날짜가 잘못되었습니다.");

        List<Word> findWords = wordRepository.findAllByMember_IdxAndDateGreaterThan(memberIdx, date);

        List<WordDto> returnWords = new ArrayList<>();
        for (Word findWord : findWords) {
            returnWords.add(WordDto.from(findWord));
        }

        return returnWords;
    }

    public List<WordDto> getWordsMatchLevelsGreaterThanDate(Long memberIdx, Integer level, Integer sLevel, LocalDateTime date) {
        if (memberRepository.findById(memberIdx).orElse(null) == null)
            throw new NoMemberException("학습한 회원이 존재하지 않습니다.");

        if (date.isAfter(LocalDateTime.now()))
            throw new DateException("기준 날짜가 잘못되었습니다.");
        if (level < sLevel)
            throw new LevelException("레벨 기준이 잘못되었습니다.");

        List<Word> findWords = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThan(memberIdx, level, sLevel, date);

        List<WordDto> returnWords = new ArrayList<>();
        for (Word findWord : findWords) {
            returnWords.add(WordDto.from(findWord));
        }

        return returnWords;
    }
}
