package br.com.compass.site.dtos;

import br.com.compass.site.enums.BrandEnum;
import br.com.compass.site.enums.CurrencyEnum;
import br.com.compass.site.enums.ExpirationMonthEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardsDto {

    @NotBlank(message="The nomeCartao field cannot be left blank")
    @JsonProperty("nomeCartao")
    private String cardName;

    @NotBlank(message="The numeroCartao field cannot be left blank")
    @JsonProperty("numeroCartao")
    private String cardNumber;

    @NotBlank(message="The codigoSeguranca field cannot be left blank")
    @JsonProperty("codigoSeguranca")
    private String securityCode;

    @NotNull(message="The mesValidade field must be filled in with the available option: " +
            "1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 or 12")
    @JsonProperty("mesValidade")
    private ExpirationMonthEnum expirationMonth;

    @NotBlank(message="The anoValidade field cannot be left blank")
    @JsonProperty("anoValidade")
    private String expirationYear;

    @NotNull(message = "The marca field must be filled in with one of the available options: " +
            "    MASTERCARD or VISA")
    @JsonProperty("marca")
    private BrandEnum brand;

    @NotNull(message = "The moeda field must be filled in with the available option: BRL")
    @JsonProperty("moeda")
    private CurrencyEnum currency;
}
