package br.com.compass.payments.dtos.eventsDtos;

import br.com.compass.payments.enums.PaymentStatusEnum;
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
