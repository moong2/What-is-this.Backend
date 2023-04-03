package com.Saojung.whatisthis.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String id;
    private int count;
    private int level;
    private double success_rate_1;
    private double success_rate_2;
    private double success_rate_3;
}
