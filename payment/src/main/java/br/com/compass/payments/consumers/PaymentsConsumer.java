package br.com.compass.payments.consumers;

import br.com.compass.payments.clients.PbBankClient;
import br.com.compass.payments.dtos.eventsDtos.PaymentEventDto;
import br.com.compass.payments.dtos.paymentRequestDtos.PaymentRequestCardDto;
import br.com.compass.payments.dtos.paymentRequestDtos.PaymentRequestCustomerDto;
import br.com.compass.payments.dtos.paymentRequestDtos.PaymentRequestDto;
import br.com.compass.payments.dtos.paymentResponseDtos.PaymentResponseDto;
import br.com.compass.payments.entities.PaymentEntity;
import br.com.compass.payments.producers.PaymentStatusProducer;
import br.com.compass.payments.repositories.PaymentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static br.com.compass.payments.utils.StringToHexadecimalConverter.convertStringToHex;

@Slf4j
@Component
public class PaymentsConsumer {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PbBankClient pbBankClient;

    @Autowired
    private PaymentStatusProducer paymentStatusProducer;

    @RabbitListener(queues = "orders.v1.order-created")
    public void onPaymentCreated(PaymentEventDto event) {

        log.info("onPaymentCreated() - START - Receiving payment - id {} Value {}",
                event.getId(), event.getOrderValue());

        try {
            PaymentEntity paymentSave = modelMapper.map(event, PaymentEntity.class);
            paymentsRepository.save(paymentSave);
            log.info("onPaymentCreated() - END - Payment received");

            pbBankClient.auth();

            // Received post
            PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
            paymentRequestDto.setSellerId("a05607e1-e7ee-4793-84bd-12851c6a7037");
            paymentRequestDto.setPaymentType(event.getPaymentType());
            paymentRequestDto.setTransactionAmount(event.getPayment().getValue());
            paymentRequestDto.setCurrency(event.getPayment().getCurrency());

            /// customer
            PaymentRequestCustomerDto customer = new PaymentRequestCustomerDto();
            customer.setDocumentNumber(event.getCpf());
            paymentRequestDto.setCustomer(customer);

            /// card
            PaymentRequestCardDto card = new PaymentRequestCardDto();
            card.setNumberToken(convertStringToHex(event.getPayment().getCardNumber()));
            card.setCardholderName(event.getPayment().getCardName());
            card.setSecurityCode(event.getPayment().getSecurityCode());
            card.setBrand(event.getPayment().getBrand());
            card.setExpirationMonth(event.getPayment().getExpirationMonth());
            card.setExpirationYear(event.getPayment().getExpirationYear());
            paymentRequestDto.setCard(card);

            PaymentResponseDto response = pbBankClient.processPayment(paymentRequestDto);

            paymentStatusProducer.updatePaymentStatus(event.getId(), response);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}