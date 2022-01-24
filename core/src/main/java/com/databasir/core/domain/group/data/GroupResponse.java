package com.databasir.core.domain.group.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupResponse {

    private Integer id;

    private String name;

    private String description;

    private List<GroupOwnerResponse> groupOwners = new ArrayList<>();

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    @Data
    public static class GroupOwnerResponse {

        private Integer id;

        private String nickname;

        private String email;

    }
}
