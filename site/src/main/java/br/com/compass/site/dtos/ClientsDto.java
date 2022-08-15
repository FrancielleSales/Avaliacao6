package br.com.compass.site.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientsDto {

    @NotBlank(message="The cpf field cannot be left blank")
    @JsonProperty("cpf")
    private String cpf;

    @NotBlank(message="The nome field cannot be left blank")
    @JsonProperty("nome")
    private String name;

    @NotNull(message="The dataCriacao field cannot be left blank")
    @JsonProperty("dataCriacao")
    private LocalDateTime creationDate;

    @NotNull(message="The dataValidade field cannot be left blank")
    @JsonProperty("dataValidade")
    private LocalDateTime expirationDate;

    @Valid
    @JsonProperty("cartoes")
    private List<CardsDto> cards = new ArrayList<>();
}
