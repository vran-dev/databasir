package com.databasir.core.domain.group.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupCreateRequest {

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    @Size(min = 1, max = 20, message = "一个分组的组长最多为 20 人，最少为 1 人")
    private List<Integer> groupOwnerUserIds = new ArrayList<>();

}
