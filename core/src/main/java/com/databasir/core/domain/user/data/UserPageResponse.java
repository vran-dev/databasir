package com.databasir.core.domain.user.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserPageResponse {

    private Integer id;

    private String username;

    private String nickname;

    private String email;

    private Boolean enabled;

    private Boolean isSysOwner;

    private List<Integer> inGroupIds = new ArrayList<>();

    private LocalDateTime createAt;
}
