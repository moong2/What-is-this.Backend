package com.Saojung.whatisthis.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
public class LoginVo {
    @NotNull
    private String userId;
    private String password;
    private String parentPassword;

    public LoginVo(@NonNull String userId, String password, String parentPassword) {
        this.userId = userId;
        this.password = password;
        this.parentPassword = parentPassword;
    }
}
