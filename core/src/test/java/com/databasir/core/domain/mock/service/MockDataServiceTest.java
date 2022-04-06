package com.databasir.core.domain.mock.service;

import com.databasir.core.BaseTest;
import com.databasir.core.domain.mock.MockDataService;
import com.databasir.core.domain.mock.generator.MockDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MockDataServiceTest extends BaseTest {

    @Autowired
    private MockDataService mockDataService;

    @Autowired
    private MockDataGenerator mockDataGenerator;

    @Test
    public void test() {
        String sql = mockDataGenerator.createInsertSql(15,3L, "event_reward_history");
        System.out.println(sql);
    }
}
