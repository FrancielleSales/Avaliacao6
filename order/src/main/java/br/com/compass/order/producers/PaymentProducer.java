package br.com.compass.order.producers;

import br.com.compass.order.dtos.OrderDto;
import br.com.compass.order.dtos.PaymentEventDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ModelMapper modelMapper;

    public void sendPayment (OrderDto paymentDto){
        log.info("createOrder() - Sending payment to MS-Payment");
        PaymentEventDto event = modelMapper.map(paymentDto, PaymentEventDto.class);
        final String routingKey = "orders.v1.order-created";
        rabbitTemplate.convertAndSend(routingKey, event);
        log.info("createOrder() - Payment send to MS-Payment");
    }
}
