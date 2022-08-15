package br.com.compass.site.dtos.checkout;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientsCheckoutDto {

    private Long id;

    @NotBlank(message = "The field clienteId cannot be left blank")
    @JsonProperty("clienteId")
    private String clientId;

    @NotBlank(message = "The field cartaoId cannot be left blank")
    @JsonProperty("cartaoId")
    private String cardId;
}
