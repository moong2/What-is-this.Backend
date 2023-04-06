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
    private String picture;
    private Integer goal;

    public Amends(Long idx, String amends, Integer times, String picture, Integer goal) {
        this.idx = idx;
        this.amends = amends;
        this.times = times;
        this.picture = picture;
        this.goal = goal;
    }
}
