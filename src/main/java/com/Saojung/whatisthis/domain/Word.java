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
    private Integer idx;
    @NotNull
    private String word;
    @NotNull
    private Integer level;
    @NotNull
    private Integer success_level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Member member;

    public Word(Integer idx, @NotNull String word, @NotNull Integer level, @NotNull Integer success_level, Member member) {
        this.idx = idx;
        this.word = word;
        this.level = level;
        this.success_level = success_level;
        this.member = member;
    }
}
