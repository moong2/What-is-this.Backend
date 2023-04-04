package com.Saojung.whatisthis.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Analysis {
    @Id
    private String id;
    @NotNull
    private int count;
    @NotNull
    private int level;
    @NotNull
    private double success_rate_1;
    @NotNull
    private double success_rate_2;
    @NotNull
    private double success_rate_3;

    public Analysis(String id, int count, int level, double success_rate_1, double success_rate_2, double success_rate_3) {
        this.id = id;
        this.count = count;
        this.level = level;
        this.success_rate_1 = success_rate_1;
        this.success_rate_2 = success_rate_2;
        this.success_rate_3 = success_rate_3;
    }
}
