package br.com.compass.order.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class OrderItemOfferDto {

    private Long id;

    @NotBlank(message="The nome field cannot be left blank")
    @JsonProperty("nome")
    private String name;

    @NotNull(message="The dataCriacao field cannot be left blank")
    @JsonProperty("dataCriacao")
    private LocalDateTime creationDate;

    @NotNull(message="The dataValidade field cannot be left blank")
    @JsonProperty("dataValidade")
    private LocalDateTime expirationDate;

    @Positive(message = "The valor field cannot be negative")
    @NotNull(message="The valor field cannot be left blank")
    @JsonProperty("valor")
    private BigDecimal value;

    @Positive(message = "The desconto field cannot be negative")
    @NotNull(message="The desconto field cannot be left blank")
    @JsonProperty("desconto")
    private BigDecimal discount;

    @JsonProperty("descricao")
    private String description;
}
