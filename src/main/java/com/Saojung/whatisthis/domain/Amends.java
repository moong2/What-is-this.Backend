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
public class Amends {
    @Id
    private String id;
    private String amends;
    private int times;
    private String picture;
    private int goal;
}
