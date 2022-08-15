package br.com.compass.payments.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentDto {

    @JsonProperty("nomeCartao")
    private String cardName;

    @JsonProperty("numeroCartao")
    private String cardNumber;

    @JsonProperty("codigoSeguranca")
    private String securityCode;

    @JsonProperty("marca")
    private String brand;

    @JsonProperty("mesValidade")
    private String expirationMonth;

    @JsonProperty("anoValidade")
    private String expirationYear;

    @JsonProperty("moeda")
    private String currency;

    @JsonProperty("valor")
    private BigDecimal value;

}
