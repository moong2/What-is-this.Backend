package com.Saojung.whatisthis.dto;

import com.Saojung.whatisthis.domain.Amends;
import com.Saojung.whatisthis.domain.Analysis;
import com.Saojung.whatisthis.domain.Member;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MemberDto {
    @NotNull
    private Long idx;
    @NotNull
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    @NotNull
    private String parentPassword;

    private Analysis analysis;
    private Amends amends;

    public MemberDto(Long idx, @NonNull String id, @NonNull String password, @NonNull String name, LocalDate birth, @NonNull String parentPassword, Analysis analysis, Amends amends) {
        this.idx = idx;
        this.id = id;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.parentPassword = parentPassword;
        this.analysis = analysis;
        this.amends = amends;
    }

    public static MemberDto from(Member member) {
        if (member == null) return null;

        return MemberDto.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .password(member.getPassword())
                .name(member.getName())
                .birth(member.getBirth())
                .parentPassword(member.getParentPassword())
                .analysis(member.getAnalysis())
                .amends(member.getAmends())
                .build();
    }
}
