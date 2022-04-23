package com.databasir.core.infrastructure.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverResult {

    private String driverFilePath;

    private File driverFile;

}
