package com.databasir.core.domain.user.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserCreated {

    private Integer userId;

    private String nickname;

    private String email;

    private String username;

    private String rawPassword;

    private String source;
}
