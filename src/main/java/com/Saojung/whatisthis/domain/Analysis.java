package com.Saojung.whatisthis.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Analysis {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member;
    private int count;
    private int level;
    private double success_rate_1;
    private double success_rate_2;
    private double success_rate_3;

    public Analysis(Member member, int count, int level, double success_rate_1, double success_rate_2, double success_rate_3) {
        this.member = member;
        this.count = count;
        this.level = level;
        this.success_rate_1 = success_rate_1;
        this.success_rate_2 = success_rate_2;
        this.success_rate_3 = success_rate_3;
    }
}
