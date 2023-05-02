package com.Saojung.whatisthis.vo;

import com.Saojung.whatisthis.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WordVo {
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

    public WordVo(Long idx, @NonNull String word, @NonNull Integer level, @NonNull Integer successLevel, @NonNull LocalDateTime date) {
        this.idx = idx;
        this.word = word;
        this.level = level;
        this.successLevel = successLevel;
        this.date = date;
    }
}
