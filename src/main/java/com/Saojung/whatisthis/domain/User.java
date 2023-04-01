package com.Saojung.whatisthis.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    public User(String id, String password, String name, int age, String parent_password) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.parent_password = parent_password;
    }
}
