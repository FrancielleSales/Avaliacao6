package br.com.compass.payments.dtos.eventsDtos;

import br.com.compass.payments.dtos.OrderPaymentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEventDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("orderValue")
    private BigDecimal orderValue = BigDecimal.ZERO;

    @JsonProperty("paymentType")
    private String paymentType;

    @JsonProperty("payment")
    private OrderPaymentDto payment;

}
