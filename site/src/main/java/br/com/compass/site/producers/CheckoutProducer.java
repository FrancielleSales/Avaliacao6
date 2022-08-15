package br.com.compass.site.producers;

import br.com.compass.site.dtos.orderRequest.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckoutProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ModelMapper modelMapper;

    public void sendOrder (OrderDto checkoutDto){
        log.info("sendOrder() - Sending order to MS-Order");
        final String routingKey = "checkouts.v1.checkout-created";
        rabbitTemplate.convertAndSend(routingKey, checkoutDto);
        log.info("sendOrder() - Order send to MS-Order");
    }
}
