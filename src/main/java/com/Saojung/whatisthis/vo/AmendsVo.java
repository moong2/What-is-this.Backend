package com.Saojung.whatisthis.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
public class AmendsVo {
    @NonNull
    private Long idx;
    private String amends;
    @NotNull
    private Integer goal;
    @NotNull
    private Integer remain;

    public AmendsVo(@NonNull Long idx) {
        this.idx = idx;
        this.amends = null;
        this.goal = 0;
        this.remain = 0;
    }

    public AmendsVo(@NonNull Long idx, String amends, @NonNull Integer goal, @NonNull Integer remain) {
        this.idx = idx;
        this.amends = amends;
        this.goal = goal;
        this.remain = remain;
    }
}
