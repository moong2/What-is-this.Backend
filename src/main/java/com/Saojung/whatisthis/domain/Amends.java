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
public class Amends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String amends;
    private Integer times;
    private Integer goal;

    public Amends(Long idx, String amends, Integer times, Integer goal) {
        this.idx = idx;
        this.amends = amends;
        this.times = times;
        this.goal = goal;
    }
}
