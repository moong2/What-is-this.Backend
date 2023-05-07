package com.Saojung.whatisthis.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class AnalysisVo {
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

    public AnalysisVo(@NonNull Long idx, @NonNull Integer count, @NonNull Integer level, @NonNull Double successRate1, @NonNull Double successRate2, @NonNull Double successRate3) {
        this.idx = idx;
        this.count = count;
        this.level = level;
        this.successRate1 = successRate1;
        this.successRate2 = successRate2;
        this.successRate3 = successRate3;
    }
}
