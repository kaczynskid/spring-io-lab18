package io.spring.lab.store;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.cloud.bootstrap.enabled=false"
})
public abstract class SpringTestBase {

}
