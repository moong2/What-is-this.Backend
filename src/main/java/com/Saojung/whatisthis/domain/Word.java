package com.Saojung.whatisthis.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @NotNull
    private String word;
    @NotNull
    private Integer level;
    @NotNull
    private Integer successLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_idx")
    private Member member;

    public Word(Long idx, @NonNull String word, @NonNull Integer level, @NonNull Integer successLevel, Member member) {
        this.idx = idx;
        this.word = word;
        this.level = level;
        this.successLevel = successLevel;
        this.member = member;
    }
}
