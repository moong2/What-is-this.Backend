package com.Saojung.whatisthis.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @NotNull
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private Integer age;
    @NotNull
    private String parent_password;

    @OneToOne
    @JoinColumn(name = "analysis_idx")
    private Analysis analysis;
    @OneToOne
    @JoinColumn(name = "amends_idx")
    private Amends amends;

    public Member(Long idx, @NonNull String id, @NonNull String password, @NonNull String name, Integer age, @NonNull String parent_password, Analysis analysis, Amends amends) {
        this.idx = idx;
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.parent_password = parent_password;
        this.analysis = analysis;
        this.amends = amends;
    }
}
