package com.Saojung.whatisthis.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Integer count;
    @NotNull
    private Integer level;
    @NotNull
    private double success_rate_1;
    @NotNull
    private double success_rate_2;
    @NotNull
    private double success_rate_3;

    public Analysis(Long id, @NonNull Integer count, @NonNull Integer level, @NonNull double success_rate_1, @NonNull double success_rate_2, @NonNull double success_rate_3) {
        this.id = id;
        this.count = count;
        this.level = level;
        this.success_rate_1 = success_rate_1;
        this.success_rate_2 = success_rate_2;
        this.success_rate_3 = success_rate_3;
    }
}
