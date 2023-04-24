package com.Saojung.whatisthis.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginVo {
    @NotNull
    private String id;
    private String password;
    private String parentPassword;

    public LoginVo(@NonNull String id, String password, String parentPassword) {
        this.id = id;
        this.password = password;
        this.parentPassword = parentPassword;
    }
}
