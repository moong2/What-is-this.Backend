package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.domain.Word;
import com.Saojung.whatisthis.dto.AnalysisDto;
import com.Saojung.whatisthis.exception.LevelException;
import com.Saojung.whatisthis.exception.NoAnalysisException;
import com.Saojung.whatisthis.repository.AnalysisRepository;
import com.Saojung.whatisthis.repository.MemberRepository;
import com.Saojung.whatisthis.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (analysis.getCount() < 0 || analysis.getLevel() < 0 ||
                analysis.getLevel() > 3 ||
                analysis.getSuccessRate1() < 0.0 || analysis.getSuccessRate1() > 100.0 ||
                analysis.getSuccessRate2() < 0.0 || analysis.getSuccessRate2() > 100.0 ||
                analysis.getSuccessRate3() < 0.0 || analysis.getSuccessRate3() > 100.0)
            throw new LevelException("비정상적인 값입니다.");

        return AnalysisDto.from(analysisRepository.save(analysis));
    }

    public AnalysisDto calculate(Long member_idx) {
        Optional<Analysis> analysis = analysisRepository.findById(memberRepository.findById(member_idx).get().getAnalysis().getIdx());
        if (analysis.equals(Optional.empty()))
            throw new NoAnalysisException("분석 데이터가 존재하지 않습니다.");

        Analysis returnAnalysis;

        List<Word> words = wordRepository.findAllByMember_Idx(member_idx);
        Integer count = 0;
        Integer l1 = 0, l2 = 0, l3 = 0;
        Integer s1 = 0, s2 = 0 ,s3 = 0;

        //s1 = (1, 1) + (2, 1) + (3, 1) + (2, 2) + (3, 2) + (3, 3)
        //s2 = (2, 2) + (3, 2) + (3, 3)
        //s3 = (3, 3)
        for (Word word : words) {
            count++;

            if (word.getLevel() >= 1) l1++;
            if (word.getLevel() >= 2) l2++;
            if (word.getLevel() == 3) l3++;

            if (word.getSuccessLevel() >= 1) s1++;
            if (word.getSuccessLevel() >= 2) s2++;
            if (word.getSuccessLevel() == 3) s3++;
        }

        try {
            if (l1 == 0) l1 = 1;
            if (l2 == 0) l2 = 1;
            if (l3 == 0) l3 = 1;

            returnAnalysis = Analysis.builder()
                    .idx(analysis.get().getIdx())
                    .count(count)
                    .level(analysis.get().getLevel())
                    .successRate1(s1 * 1.0 / l1 * 100.0)
                    .successRate2(s2 * 1.0 / l2 * 100.0)
                    .successRate3(s3 * 1.0 / l3 * 100.0)
                    .build();

            return this.update(AnalysisDto.from(returnAnalysis));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
