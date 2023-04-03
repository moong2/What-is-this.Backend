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
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    private int age;
    private String parent_password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", referencedColumnName = "id")
    private Analysis analysis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amends_id", referencedColumnName = "id")
    private Amends amends;
}
