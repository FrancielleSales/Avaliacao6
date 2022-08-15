package br.com.compass.site.dtos.orderRequest;

import br.com.compass.site.enums.PaymentStatusEnum;
import br.com.compass.site.enums.PaymentTypeEnum;
import br.com.compass.site.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

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

    @NotNull(message = "The statusPagamento field must be filled in with one of the available options: " +
            "Processando, Rejeitado ou Aprovado")
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