package br.com.compass.site.entities.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checkout")
public class CheckoutEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_clientes_checkout")
    private ClientsCheckoutEntity clientsCheckout;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id_itens_checkout")
    private List<ItemsCheckoutEntity> itemsCheckout = new ArrayList<>();
}
