package com.databasir.dao.strategy;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

public class DatabasirPojoNamingStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        if (mode == Mode.POJO) {
            String javaClassName = super.getJavaClassName(definition, mode);
            if (javaClassName.endsWith("Pojo")) {
                return javaClassName;
            } else {
                return javaClassName + "Pojo";
            }
        } else {
            return super.getJavaClassName(definition, mode);
        }
    }
}
