package br.com.compass.order.consumers;

import br.com.compass.order.enums.PaymentStatusEnum;
import br.com.compass.order.enums.StatusEnum;
import br.com.compass.order.dtos.PaymentStatusEventDto;
import br.com.compass.order.entities.OrderEntity;
import br.com.compass.order.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @RabbitListener(queues = "payments.v1.payment-created")
    public void updateOrderWithPaymentStatus(PaymentStatusEventDto event) {

        log.info("updateOrderWithPaymentStatus() - START - Updating payment status - id {}", event.getId());

        try {
            OrderEntity paymentUpdate = orderRepository.findById(event.getId()).get();
            paymentUpdate.setPaymentStatus(event.getStatus());

            if(event.getStatus().equals(PaymentStatusEnum.APPROVED)){
                paymentUpdate.setStatus(StatusEnum.FINISHED);
            } else {
                paymentUpdate.setStatus(StatusEnum.CANCELED);
            }

            orderRepository.save(paymentUpdate);

            log.info("updateOrderWithPaymentStatus() - END - Payment status updated- id {}", event.getId());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
