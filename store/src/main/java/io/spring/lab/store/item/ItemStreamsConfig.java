package io.spring.lab.store.item;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableBinding(ItemStreamsConfig.ItemStreamsBinding.class)
public class ItemStreamsConfig {

    public static final String CHECKOUT_ITEM = "checkoutItem";

    public interface ItemStreamsBinding {

        @Output(CHECKOUT_ITEM)
        MessageChannel checkoutItem();
    }
}
