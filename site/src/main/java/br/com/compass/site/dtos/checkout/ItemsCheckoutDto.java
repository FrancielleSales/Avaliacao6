package br.com.compass.site.dtos.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsCheckoutDto {

    private Long id;

    @NotBlank(message = "The field skuId cannot be left blank")
    @JsonProperty("skuId")
    private String skuId;

    @Positive(message = "The field qtd cannot be negative")
    @NotNull(message = "The field qtd cannot be left blank")
    @JsonProperty("qtd")
    private int qtd;
}
