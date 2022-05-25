package com.databasir.core.domain.document.generator.plantuml;

import com.databasir.core.domain.document.generator.DocumentFileType;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlantUmlPngFileGenerator extends BasePlantUmlFileGenerator {

    @Override
    public boolean support(DocumentFileType type) {
        return type == DocumentFileType.PLANT_UML_ER_PNG;
    }

    @Override
    protected FileFormatOption fileFormatOption() {
        return new FileFormatOption(FileFormat.PNG);
    }
}
