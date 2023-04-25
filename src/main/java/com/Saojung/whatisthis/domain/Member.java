package com.Saojung.whatisthis.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "analysis_idx")
    private Analysis analysis;
    @OneToOne
    @JoinColumn(name = "amends_idx")
    private Amends amends;

    public Member(Long idx, @NonNull String userId, @NonNull String password, @NonNull String name, LocalDate birth, @NonNull String parentPassword, Analysis analysis, Amends amends) {
        this.idx = idx;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.parentPassword = parentPassword;
        this.analysis = analysis;
        this.amends = amends;
    }
}
