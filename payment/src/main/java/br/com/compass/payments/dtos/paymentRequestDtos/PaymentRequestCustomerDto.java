package br.com.compass.payments.dtos.paymentRequestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestCustomerDto {

    @JsonProperty("document_type")
    private final String documentType = "CPF";

    @JsonProperty("document_number")
    private String documentNumber;
}
