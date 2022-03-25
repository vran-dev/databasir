package com.databasir.core;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = CoreTestApplication.class)
@ActiveProfiles("ut")
@ExtendWith(SpringExtension.class)
@Sql({"classpath:sql/init.sql"})
public class BaseTest {
}
