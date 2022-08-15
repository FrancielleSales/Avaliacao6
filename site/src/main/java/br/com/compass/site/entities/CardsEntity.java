package br.com.compass.site.entities;

import br.com.compass.site.enums.BrandEnum;
import br.com.compass.site.enums.CurrencyEnum;
import br.com.compass.site.enums.ExpirationMonthEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="clientes_cartoes")
public class CardsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardName;

    private String cardNumber;

    private String securityCode;

    @Enumerated(EnumType.STRING)
    private ExpirationMonthEnum expirationMonth;

    private String expirationYear;

    @Enumerated(EnumType.STRING)
    private BrandEnum brand;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;
}
