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
public class Member {
    @Id
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private Integer age;
    @NotNull
    private String parent_password;

    @OneToOne
    @JoinColumn(name = "analysis_id")
    private Analysis analysis;
    @OneToOne
    @JoinColumn(name = "amends_id")
    private Amends amends;

    public Member(String id, @NotNull String password, @NotNull String name, Integer age, @NotNull String parent_password, Analysis analysis, Amends amends) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.parent_password = parent_password;
        this.analysis = analysis;
        this.amends = amends;
    }
}
