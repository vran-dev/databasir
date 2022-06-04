package com.databasir.dao.strategy;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;
import org.jooq.meta.TableDefinition;

public class DatabasirPojoNamingStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        if (mode == Mode.DEFAULT && definition instanceof TableDefinition) {
            String javaClassName = super.getJavaClassName(definition, mode);
            if (javaClassName.endsWith("Table")) {
                return javaClassName;
            } else {
                return javaClassName + "Table";
            }
        }

        return super.getJavaClassName(definition, mode);
    }
}
