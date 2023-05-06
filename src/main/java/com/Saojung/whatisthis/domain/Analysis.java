package com.Saojung.whatisthis.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Analysis() {
        this.count = 0;
        this.level = 1;
        this.successRate1 = 0.0;
        this.successRate2 = 0.0;
        this.successRate3 = 0.0;
    }

    public Analysis(Long idx, @NonNull Integer count, @NonNull Integer level, @NonNull Double successRate1, @NonNull Double successRate2, @NonNull Double successRate3) {
        this.idx = idx;
        this.count = count;
        this.level = level;
        this.successRate1 = successRate1;
        this.successRate2 = successRate2;
        this.successRate3 = successRate3;
    }
}
