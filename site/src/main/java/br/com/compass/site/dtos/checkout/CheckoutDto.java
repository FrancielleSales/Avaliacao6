package br.com.compass.site.dtos.checkout;

import br.com.compass.site.entities.checkout.ClientsCheckoutEntity;
import br.com.compass.site.entities.checkout.ItemsCheckoutEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {

    private Long id;

    @Valid
    @NotNull(message = "The field client_info cannot be left null")
    @JsonProperty("cliente_info")
    private ClientsCheckoutDto clientsCheckout;

    @Valid
    @NotNull(message = "The field itens cannot be left null")
    @JsonProperty("itens")
    private List<ItemsCheckoutDto> itemsCheckout = new ArrayList<>();
}
