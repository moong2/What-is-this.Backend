package com.Saojung.whatisthis.vo;

import com.Saojung.whatisthis.domain.Amends;
import com.Saojung.whatisthis.domain.Analysis;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberVo {
    @NotNull
    private Long idx;
    @NotNull
    private String userId;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @NotNull
    private String parentPassword;

    public MemberVo(Long idx, @NonNull String userId, @NonNull String password, @NonNull String name, LocalDate birth, @NonNull String parentPassword) {
        this.idx = idx;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.parentPassword = parentPassword;
    }
}
