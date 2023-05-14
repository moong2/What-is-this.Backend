package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.AnalysisDto;
import com.Saojung.whatisthis.exception.LevelException;
import com.Saojung.whatisthis.exception.NoAnalysisException;
import com.Saojung.whatisthis.exception.NoMemberException;
import com.Saojung.whatisthis.repository.AnalysisRepository;
import com.Saojung.whatisthis.repository.MemberRepository;
import com.Saojung.whatisthis.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalysisService {

    private final MemberRepository memberRepository;
    private final WordRepository wordRepository;
    private final AnalysisRepository analysisRepository;

    public AnalysisDto update(AnalysisDto analysisDto) {
        if (analysisRepository.findById(analysisDto.getIdx()).orElse(null) == null)
            throw new NoAnalysisException("분석 데이터가 존재하지 않습니다.");

        Analysis analysis;
        try {
            analysis = Analysis.builder()
                    .idx(analysisDto.getIdx())
                    .count(analysisDto.getCount())
                    .level(analysisDto.getLevel())
                    .successRate1(analysisDto.getSuccessRate1())
                    .successRate2(analysisDto.getSuccessRate2())
                    .successRate3(analysisDto.getSuccessRate3())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        System.out.println("analysis.getCount() = " + analysis.getCount());
        System.out.println("analysis = " + analysis.getLevel());
        System.out.println("analysis.getSuccessRate1() = " + analysis.getSuccessRate1());
        System.out.println("analysis.getSuccessRate2()\\ = " + analysis.getSuccessRate2());
        System.out.println("analysis = " + analysis.getSuccessRate3());

        if (analysis.getCount() < 0 || analysis.getLevel() < 0 ||
                analysis.getLevel() > 3 ||
                analysis.getSuccessRate1() < 0.0 || analysis.getSuccessRate1() > 100.0 ||
                analysis.getSuccessRate2() < 0.0 || analysis.getSuccessRate2() > 100.0 ||
                analysis.getSuccessRate3() < 0.0 || analysis.getSuccessRate3() > 100.0)
            throw new LevelException("비정상적인 값입니다.");

        return AnalysisDto.from(analysisRepository.save(analysis));
    }

    public AnalysisDto calculate(Long member_idx) {
        if (memberRepository.findById(member_idx).orElse(null) == null)
            throw new NoMemberException("회원이 존재하지 않습니다.");

        Optional<Analysis> analysis = analysisRepository.findById(memberRepository.findById(member_idx).get().getAnalysis().getIdx());
        if (analysis.equals(Optional.empty()))
            throw new NoAnalysisException("분석 데이터가 존재하지 않습니다.");

        Analysis returnAnalysis;

        List<Word> words = wordRepository.findAllByMember_Idx(member_idx);
        Integer count = words.size();

        Integer l1 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqual(member_idx, 1).size();
        Integer l2 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqual(member_idx, 2).size();
        Integer l3 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqual(member_idx, 3).size();

        Integer l11 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member_idx, 1, 1).size();
        Integer l21 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member_idx, 2, 1).size();
        Integer l22 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member_idx, 2, 2).size();
        Integer l31 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member_idx, 3, 1).size();
        Integer l32 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member_idx, 3, 2).size();
        Integer l33 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevel(member_idx, 3, 3).size();

        Integer s1 = l11 + l21 + l31 + l22 + l32 + l33;
        Integer s2 = l22 + l32 + l33;
        Integer s3 = l33;

        try {
            Double r1, r2, r3;
            if (s1 == 0 && l1 == 0) r1 = 0.0;
            else r1 = s1 * 1.0 / l1 * 100.0;
            if (s2 == 0 && l2 == 0) r2 = 0.0;
            else r2 = s2 * 1.0 / l2 * 100.0;
            if (s3 == 0 && l3 == 0) r3 = 0.0;
            else r3 = s3 * 1.0 / l3 * 100.0;

            Integer level = 1;
            if (l1 + l2 >= 10 && r1 >= 75.0) level = 2;
            if (l2 + l3 >= 10 && r2 >= 75.0) level = 3;

            returnAnalysis = Analysis.builder()
                    .idx(analysis.get().getIdx())
                    .count(count)
                    .level(level)
                    .successRate1(r1)
                    .successRate2(r2)
                    .successRate3(r3)
                    .build();

            return this.update(AnalysisDto.from(returnAnalysis));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public AnalysisDto calculate(Long member_idx, LocalDateTime date) {
        if (memberRepository.findById(member_idx).orElse(null) == null)
            throw new NoMemberException("회원이 존재하지 않습니다.");

        Optional<Analysis> analysis = analysisRepository.findById(memberRepository.findById(member_idx).get().getAnalysis().getIdx());
        if (analysis.equals(Optional.empty()))
            throw new NoAnalysisException("분석 데이터가 존재하지 않습니다.");

        Analysis returnAnalysis;

        List<Word> words = wordRepository.findAllByMember_IdxAndDateGreaterThanEqual(member_idx, date);
        Integer count = words.size();

        Integer l1 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqualAndDateGreaterThanEqual(member_idx, 1, date).size();
        Integer l2 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqualAndDateGreaterThanEqual(member_idx, 2, date).size();
        Integer l3 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqualAndDateGreaterThanEqual(member_idx, 3, date).size();

        Integer l11 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThanEqual(member_idx, 1, 1, date).size();
        Integer l21 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThanEqual(member_idx, 2, 1, date).size();
        Integer l22 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThanEqual(member_idx, 2, 2, date).size();
        Integer l31 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThanEqual(member_idx, 3, 1, date).size();
        Integer l32 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThanEqual(member_idx, 3, 2, date).size();
        Integer l33 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateGreaterThanEqual(member_idx, 3, 3, date).size();

        Integer s1 = l11 + l21 + l31 + l22 + l32 + l33;
        Integer s2 = l22 + l32 + l33;
        Integer s3 = l33;

        try {
            Double r1, r2, r3;
            if (s1 == 0 && l1 == 0) r1 = 0.0;
            else r1 = s1 * 1.0 / l1 * 100.0;
            if (s2 == 0 && l2 == 0) r2 = 0.0;
            else r2 = s2 * 1.0 / l2 * 100.0;
            if (s3 == 0 && l3 == 0) r3 = 0.0;
            else r3 = s3 * 1.0 / l3 * 100.0;

            Integer level = 1;
            if (l1 + l2 >= 10 && r1 >= 75.0) level = 2;
            if (l2 + l3 >= 10 && r2 >= 75.0) level = 3;

            returnAnalysis = Analysis.builder()
                    .idx(analysis.get().getIdx())
                    .count(count)
                    .level(level)
                    .successRate1(r1)
                    .successRate2(r2)
                    .successRate3(r3)
                    .build();

            return this.update(AnalysisDto.from(returnAnalysis));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public AnalysisDto calculate(Long member_idx, LocalDateTime date1, LocalDateTime date2) {
        if (memberRepository.findById(member_idx).orElse(null) == null)
            throw new NoMemberException("회원이 존재하지 않습니다.");

        Optional<Analysis> analysis = analysisRepository.findById(memberRepository.findById(member_idx).get().getAnalysis().getIdx());
        if (analysis.equals(Optional.empty()))
            throw new NoAnalysisException("분석 데이터가 존재하지 않습니다.");

        Analysis returnAnalysis;

        List<Word> words = wordRepository.findAllByMember_IdxAndDateBetween(member_idx, date1, date2);
        Integer count = words.size();

        Integer l1 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqualAndDateBetween(member_idx, 1, date1, date2).size();
        Integer l2 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqualAndDateBetween(member_idx, 2, date1, date2).size();
        Integer l3 = wordRepository.findAllByMember_IdxAndLevelGreaterThanEqualAndDateBetween(member_idx, 3, date1, date2).size();

        Integer l11 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateBetween(member_idx, 1, 1, date1, date2).size();
        Integer l21 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateBetween(member_idx, 2, 1, date1, date2).size();
        Integer l22 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateBetween(member_idx, 2, 2, date1, date2).size();
        Integer l31 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateBetween(member_idx, 3, 1, date1, date2).size();
        Integer l32 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateBetween(member_idx, 3, 2, date1, date2).size();
        Integer l33 = wordRepository.findAllByMember_IdxAndLevelAndSuccessLevelAndDateBetween(member_idx, 3, 3, date1, date2).size();

        Integer s1 = l11 + l21 + l31 + l22 + l32 + l33;
        Integer s2 = l22 + l32 + l33;
        Integer s3 = l33;

        try {
            Double r1, r2, r3;
            if (s1 == 0 && l1 == 0) r1 = 0.0;
            else r1 = s1 * 1.0 / l1 * 100.0;
            if (s2 == 0 && l2 == 0) r2 = 0.0;
            else r2 = s2 * 1.0 / l2 * 100.0;
            if (s3 == 0 && l3 == 0) r3 = 0.0;
            else r3 = s3 * 1.0 / l3 * 100.0;

            Integer level = 1;
            if (l1 + l2 >= 10 && r1 >= 75.0) level = 2;
            if (l2 + l3 >= 10 && r2 >= 75.0) level = 3;

            returnAnalysis = Analysis.builder()
                    .idx(analysis.get().getIdx())
                    .count(count)
                    .level(level)
                    .successRate1(r1)
                    .successRate2(r2)
                    .successRate3(r3)
                    .build();

            return this.update(AnalysisDto.from(returnAnalysis));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer getLevel(Long member_idx) {
        if (memberRepository.findById(member_idx).orElse(null) == null)
            throw new NoMemberException("회원이 존재하지 않습니다.");

        Optional<Analysis> analysis = analysisRepository.findById(memberRepository.findById(member_idx).get().getAnalysis().getIdx());
        if (analysis.equals(Optional.empty()))
            throw new NoAnalysisException("분석 데이터가 존재하지 않습니다.");

        try {
            AnalysisDto calculate = this.calculate(member_idx);
            AnalysisDto calculateDate = this.calculate(member_idx, LocalDateTime.now().minusWeeks(2));

            return (calculate.getLevel() > calculateDate.getLevel()) ? calculate.getLevel() : calculateDate.getLevel();
        } catch (Exception e) {
            throw e;
        }
    }
}
