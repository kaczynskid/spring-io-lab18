package io.spring.lab.store;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.spring.lab.store.item.ItemsClient;
import io.spring.lab.store.special.SpecialClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class StoreApplicationTests {

	@MockBean
	ItemsClient items;

	@MockBean
	SpecialClient specials;

	@Test
	public void contextLoads() {
	}
}
