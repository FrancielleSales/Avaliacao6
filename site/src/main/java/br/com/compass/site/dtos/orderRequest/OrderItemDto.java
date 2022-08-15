package br.com.compass.site.dtos.orderRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    @NotBlank(message="The nome field cannot be left blank")
    @JsonProperty("nome")
    private String name;

    @NotNull(message="The dataCriacao field cannot be left blank")
    @JsonProperty("dataCriacao")
    private String creationDate;

    @NotNull(message="The dataValidade field cannot be left blank")
    @JsonProperty("dataValidade")
    private String expirationDate;

    @Positive(message = "The field qtd cannot be negative")
    @NotNull(message = "The field qtd cannot be left blank")
    @JsonProperty("qtd")
    private int qtd;

    @Positive(message = "The valor field cannot be negative")
    @NotNull(message="The valor field cannot be left blank")
    @JsonProperty("valor")
    private BigDecimal value;

    @JsonProperty("descricao")
    private String description;
}
