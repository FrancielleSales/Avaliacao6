package br.com.compass.order.dtos;

import br.com.compass.order.enums.PaymentStatusEnum;
import br.com.compass.order.enums.PaymentTypeEnum;
import br.com.compass.order.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotBlank(message = "The cpf field cannot be left blank")
    @JsonProperty("cpf")
    private String cpf;

    @NotNull(message = "The itens field cannot be left blank.")
    @Valid
    @JsonProperty("itens")
    private List<OrderItemDto> items;

    @Positive(message = "The total field cannot be negative")
    @NotNull(message = "Total field cannot be left null")
    @JsonProperty("total")
    private BigDecimal total;

    //@NotNull(message = "The status field must be filled in with one of the available options: " +
    //        "Em andamento, Finalizado ou Cancelado")
    @JsonProperty("status")
    private StatusEnum status;

    //@NotNull(message = "The statusPagamento field must be filled in with one of the available options: " +
    //        "Processando, Rejeitado ou Aprovado")
    @JsonProperty("statusPagamento")
    private PaymentStatusEnum paymentStatus;

    @NotNull(message = "The tipoPagamento field must be filled in with one of the available options: " +
            "CREDIT_CARD, PIX ou BANK_PAYMENT_SLIP")
    @JsonProperty("tipoPagamento")
    private PaymentTypeEnum paymentType;

    @Valid
    @JsonProperty("pagamento")
    private OrderPaymentDto payment;
}