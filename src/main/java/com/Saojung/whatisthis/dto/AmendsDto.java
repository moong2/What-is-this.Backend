package com.Saojung.whatisthis.dto;

import com.Saojung.whatisthis.domain.Amends;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class AmendsDto {
    @NotNull
    private Long idx;
    private String amends;
    @NotNull
    private Integer goal;
    @NotNull
    private Integer remain;

    public AmendsDto(@NonNull Long idx, String amends, @NonNull Integer goal, @NonNull Integer remain) {
        this.idx = idx;
        this.amends = amends;
        this.goal = goal;
        this.remain = remain;
    }

    public static AmendsDto from(Amends amends) {
        return AmendsDto.builder()
                .idx(amends.getIdx())
                .amends(amends.getAmends())
                .goal(amends.getGoal())
                .remain(amends.getRemain())
                .build();
    }
}
