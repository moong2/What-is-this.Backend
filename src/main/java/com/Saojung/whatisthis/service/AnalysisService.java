package com.Saojung.whatisthis.service;

import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.dto.AnalysisDto;
import com.Saojung.whatisthis.exception.LevelException;
import com.Saojung.whatisthis.exception.NoAnalysisException;
import com.Saojung.whatisthis.repository.AnalysisRepository;
import com.Saojung.whatisthis.repository.MemberRepository;
import com.Saojung.whatisthis.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalysisService {

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
}
