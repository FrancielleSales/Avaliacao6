package br.com.compass.order.entities;

import br.com.compass.order.enums.BrandEnum;
import br.com.compass.order.enums.CurrencyEnum;
import br.com.compass.order.enums.ExpirationMonthEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders_payments")
public class OrderPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardNumber;

    private String cardName;

    private String securityCode;

    @Enumerated(EnumType.STRING)
    private BrandEnum brand;

    @Enumerated(EnumType.STRING)
    private ExpirationMonthEnum expirationMonth;

    private String expirationYear;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    private BigDecimal value;
}
