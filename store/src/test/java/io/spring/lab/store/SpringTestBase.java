package io.spring.lab.store;

import org.junit.runner.RunWith;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.cloud.bootstrap.enabled=false",
        "spring.test.webclient.mockrestserviceserver.enabled=false"
})
@AutoConfigureStubRunner(ids = {
        "io.spring.lab:warehouse",
        "io.spring.lab:marketing"
}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public abstract class SpringTestBase {

}
