package br.com.compass.order.dtos;

import br.com.compass.order.enums.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusEventDto {

    private long id;

    private PaymentStatusEnum status;
}
