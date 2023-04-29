package com.Saojung.whatisthis.dto;

import com.Saojung.whatisthis.domain.Member;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class WordDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @NotNull
    private String word;
    @NotNull
    private Integer level;
    @NotNull
    private Integer successLevel;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private Member member;

    public WordDto(Long idx, @NonNull String word, @NonNull Integer level, @NonNull Integer successLevel, @NonNull LocalDateTime date, Member member) {
        this.idx = idx;
        this.word = word;
        this.level = level;
        this.successLevel = successLevel;
        this.date = date;
        this.member = member;
    }
}
