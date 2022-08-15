package br.com.compass.payments.dtos.paymentRequestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {

    @JsonProperty("seller_id")
    private String sellerId;

    @JsonProperty("customer")
    private PaymentRequestCustomerDto customer;

    @JsonProperty("payment_type")
    private String paymentType;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @JsonProperty("card")
    private PaymentRequestCardDto card;
}
