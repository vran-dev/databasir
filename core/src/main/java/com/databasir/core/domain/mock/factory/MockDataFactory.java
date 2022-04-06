package com.databasir.core.domain.mock.factory;

public interface MockDataFactory {

    boolean accept(MockColumnRule rule);

    String create(MockColumnRule rule);

}
