package com.databasir.core.domain.user.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserNicknameUpdateRequest {

    @NotBlank
    @Size(max = 32, min = 1)
    private String nickname;

}
