package com.databasir.core.domain.mock.factory;

import com.databasir.dao.enums.MockDataType;
import com.github.javafaker.Faker;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;
import java.util.UUID;

@Component
@Order(0)
public class FakerMockDataFactory implements MockDataFactory {

    @Override
    public boolean accept(MockColumnRule rule) {
        return MockDataType.fakerTypes().contains(rule.getMockDataType());
    }

    @Override
    public String create(MockColumnRule rule) {
        Faker faker = new Faker();
        StringJoiner joiner = new StringJoiner("", "'", "'");
        switch (rule.getMockDataType()) {
            case FULL_NAME:
                joiner.add(faker.name().username());
                return joiner.toString();
            case PHONE:
                joiner.add(faker.phoneNumber().cellPhone());
                return joiner.toString();
            case FULL_ADDRESS:
                joiner.add(faker.address().fullAddress());
                return joiner.toString();
            case AVATAR_URL:
                joiner.add(faker.avatar().image());
                return joiner.toString();
            case UUID:
                joiner.add(UUID.randomUUID().toString());
                return joiner.toString();
            case EMAIL:
                joiner.add(faker.name().username() + "@generated" + UUID.randomUUID().toString()
                        .replace("-", "").toString());
                return joiner.toString();
            default:
                return "''";
        }
    }
}
