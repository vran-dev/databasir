package com.databasir.core.domain.project.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DataSourcePropertyValue {

    @NotBlank
    private String key;

    @NotBlank
    private String value;

}
