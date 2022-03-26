package com.databasir.core.domain.project.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourcePropertyValue {

    @NotBlank
    private String key;

    @NotBlank
    private String value;

}
