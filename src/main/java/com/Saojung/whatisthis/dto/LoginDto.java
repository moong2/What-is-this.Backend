package com.Saojung.whatisthis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
    @NotNull
    private String id;
    private String password;
    private String parentPassword;

    public LoginDto(@NonNull String id, String password, String parentPassword) {
        this.id = id;
        this.password = password;
        this.parentPassword = parentPassword;
    }
}
