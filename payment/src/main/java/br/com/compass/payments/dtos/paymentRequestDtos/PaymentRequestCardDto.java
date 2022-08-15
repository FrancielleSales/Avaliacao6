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
public class PaymentRequestCardDto {

    @JsonProperty("number_token")
    private String numberToken;

    @JsonProperty("cardholder_name")
    private String cardholderName;

    @JsonProperty("security_code")
    private String securityCode;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("expiration_month")
    private String expirationMonth;

    @JsonProperty("expiration_year")
    private String expirationYear;
}
