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
public class Word {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member user;

    @Id
    private int idx;
    private String word;
    private int level;
    private int success_level;

    public Word(Member user, int idx, String word, int level, int success_level) {
        this.user = user;
        this.idx = idx;
        this.word = word;
        this.level = level;
        this.success_level = success_level;
    }
}
