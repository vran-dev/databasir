package com.databasir.core.domain.user.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserPasswordRenewed {

    private Integer renewByUserId;

    private String nickname;

    private String email;

    private String newPassword;

    private LocalDateTime renewTime;
}
