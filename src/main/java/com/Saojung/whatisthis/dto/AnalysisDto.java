package com.Saojung.whatisthis.dto;

import com.Saojung.whatisthis.domain.Analysis;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class AnalysisDto {
    @NotNull
    private Long idx;
    @NotNull
    private Integer count;
    @NotNull
    private Integer level;
    @NotNull
    private Double successRate1;
    @NotNull
    private Double successRate2;
    @NotNull
    private Double successRate3;

    public AnalysisDto(@NonNull Long idx, @NonNull Integer count, @NonNull Integer level, @NonNull Double successRate1, @NonNull Double successRate2, @NonNull Double successRate3) {
        this.idx = idx;
        this.count = count;
        this.level = level;
        this.successRate1 = successRate1;
        this.successRate2 = successRate2;
        this.successRate3 = successRate3;
    }

    public static AnalysisDto from(Analysis analysis) {
        return AnalysisDto.builder()
                .idx(analysis.getIdx())
                .count(analysis.getCount())
                .level(analysis.getLevel())
                .successRate1(analysis.getSuccessRate1())
                .successRate2(analysis.getSuccessRate2())
                .successRate3(analysis.getSuccessRate3())
                .build();
    }
}
