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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member;
    private String amends;
    private int times;
    private String picture;
    private int goal;

    public Amends(Member member, String amends, int times, String picture, int goal) {
        this.member = member;
        this.amends = amends;
        this.times = times;
        this.picture = picture;
        this.goal = goal;
    }
}
