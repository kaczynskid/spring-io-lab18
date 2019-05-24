package io.spring.lab.warehouse.item;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.SubscribableChannel;

@Configuration
@EnableBinding(ItemStreamsConfig.ItemStreamsBinding.class)
public class ItemStreamsConfig {

    public static final String STOCK_UPDATE = "stockUpdate";

    public interface ItemStreamsBinding {

        @Input(STOCK_UPDATE)
        SubscribableChannel stockUpdate();

    }
}
