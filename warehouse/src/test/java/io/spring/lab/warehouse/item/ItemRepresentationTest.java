package io.spring.lab.warehouse.item;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.TestPropertySource;

import io.spring.lab.warehouse.SpringTestBase;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@TestPropertySource(properties = {
        "spring.jackson.serialization.indent-output=true"
})
public class ItemRepresentationTest extends SpringTestBase {

    @Autowired
    JacksonTester<ItemRepresentation> json;

    @Test
    public void shouldSerializeItemRepresentation() throws Exception {
        // given ItemRepresentation
        ItemRepresentation item = new ItemRepresentation("A", 100, BigDecimal.valueOf(40.0), null);

        // write JSON using above JacksonTester
        JsonContent<ItemRepresentation> result = json.write(item);

        // assert JSON properties
        assertThat(result).extractingJsonPathStringValue("@.name")
                .isEqualTo("A");
        assertThat(result).extractingJsonPathNumberValue("@.count")
                .isEqualTo(100);
        assertThat(result).extractingJsonPathNumberValue("@.price")
                .isEqualTo(40.0);
    }

    @Test
    public void shouldDeserializeItemRepresentation() throws Exception {
        // given JSON to be parsed
        String data = "{\"name\":\"A\",\"count\":100,\"price\":40.0}";

        // parse JSON using above JacksonTester
        ItemRepresentation result = json.parse(data).getObject();

        // assert ItemRepresentation properties
        assertThat(result.getName()).isEqualTo("A");
        assertThat(result.getStock()).isEqualTo(100);
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(40.0));
    }
}
