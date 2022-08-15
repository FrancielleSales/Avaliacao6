package br.com.compass.order.dtos;

import br.com.compass.order.enums.BrandEnum;
import br.com.compass.order.enums.CurrencyEnum;
import br.com.compass.order.enums.ExpirationMonthEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentDto {

    private Long id;

    @NotBlank(message="The nomeCartao field cannot be left blank")
    @JsonProperty("nomeCartao")
    private String cardName;

    @NotBlank(message="The numeroCartao field cannot be left blank")
    @JsonProperty("numeroCartao")
    private String cardNumber;

    @NotBlank(message="The codigoSeguranca field cannot be left blank")
    @JsonProperty("codigoSeguranca")
    private String securityCode;

    @NotNull(message = "The marca field must be filled in with one of the available options: " +
            "    MASTERCARD or VISA")
    @JsonProperty("marca")
    private BrandEnum brand;

    @NotNull(message="The mesValidade field must be filled in with the available option: " +
            "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 or 12")
    @JsonProperty("mesValidade")
    private ExpirationMonthEnum expirationMonth;

    @NotBlank(message="The anoValidade field cannot be left blank")
    @JsonProperty("anoValidade")
    private String expirationYear;

    @NotNull(message = "The moeda field must be filled in with the available option: BRL")
    @JsonProperty("moeda")
    private CurrencyEnum currency;

    @Positive(message = "The valor field cannot be negative")
    @NotNull(message="The valor field cannot be left blank")
    @JsonProperty("valor")
    private BigDecimal value;
}
