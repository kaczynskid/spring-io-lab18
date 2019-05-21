package io.spring.lab.warehouse;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public abstract class SpringTestBase {

}
