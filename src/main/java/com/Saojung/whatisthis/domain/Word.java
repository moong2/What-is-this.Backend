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
public class Word {
    @Id
    private int idx;
    @NotNull
    private String word;
    @NotNull
    private int level;
    @NotNull
    private int success_level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Member member;

    public Word(int idx, String word, int level, int success_level, Member member) {
        this.idx = idx;
        this.word = word;
        this.level = level;
        this.success_level = success_level;
        this.member = member;
    }
}
