package br.com.compass.site.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDto {

    private long id;

    @NotBlank(message="The nome field cannot be left blank")
    @JsonProperty("nome")
    private String name;

    @NotNull(message="The dataValidade field cannot be left blank")
    @JsonProperty("dataValidade")
    private LocalDateTime expirationDate;

    @Positive(message = "The valor field cannot be negative")
    @NotNull(message="The valor field cannot be left blank")
    @JsonProperty("valor")
    private BigDecimal value;

    @JsonProperty("descricao")
    private String description;

    @Positive(message = "The estoque field cannot be negative")
    @NotNull(message="The estoque field cannot be left blank")
    @JsonProperty("estoque")
    private Long stock;

    @JsonProperty("skuid")
    private String skuId;
}
