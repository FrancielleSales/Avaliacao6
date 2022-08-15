package br.com.compass.payments.producers;

import br.com.compass.payments.dtos.eventsDtos.PaymentStatusEventDto;
import br.com.compass.payments.dtos.paymentResponseDtos.PaymentResponseDto;
import br.com.compass.payments.enums.PaymentStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentStatusProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void updatePaymentStatus(Long orderId, PaymentResponseDto paymentResponseDto) {

        PaymentStatusEnum status;

        if (paymentResponseDto.getStatus().equals("APPROVED")) {
            status = PaymentStatusEnum.APPROVED;
        } else {
            status = PaymentStatusEnum.REJECTED;
        }

        log.info("updatePaymentStatus() - Updating payment status");
        PaymentStatusEventDto event = new PaymentStatusEventDto();
        event.setStatus(status);
        event.setId(orderId);
        final String routingKey = "payments.v1.payment-created";
        rabbitTemplate.convertAndSend(routingKey, event);
        log.info("updatePaymentStatus() - Payment status updated");

    }
}
