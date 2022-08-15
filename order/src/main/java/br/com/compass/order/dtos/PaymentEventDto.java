package br.com.compass.order.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEventDto {

    private Long id;

    private String cpf;

    private BigDecimal total = BigDecimal.ZERO;

    private String paymentType;

    private OrderPaymentDto payment;
}
