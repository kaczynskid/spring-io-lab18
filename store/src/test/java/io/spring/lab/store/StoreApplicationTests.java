package io.spring.lab.store;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

@SpringBootTest
@AutoConfigureStubRunner(ids = {
		"io.spring.lab:warehouse",
		"io.spring.lab:marketing"
}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class StoreApplicationTests extends SpringDbTestBase {

	@Test
	public void contextLoads() {
	}
}
