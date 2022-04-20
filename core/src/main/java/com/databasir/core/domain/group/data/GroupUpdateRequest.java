package com.databasir.core.domain.group.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupUpdateRequest {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private List<Integer> groupOwnerUserIds = new ArrayList<>();

}
