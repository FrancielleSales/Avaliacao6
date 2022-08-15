package br.com.compass.order.entities;

import br.com.compass.order.enums.PaymentStatusEnum;
import br.com.compass.order.enums.PaymentTypeEnum;
import br.com.compass.order.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cpf;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id_request")
    private List<OrderItemEntity> items;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_payment")
    private OrderPaymentEntity payment;
}
