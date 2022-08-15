package br.com.compass.site.repositories;

import br.com.compass.site.entities.CardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository <CardsEntity, Long> {
    boolean existsByCardNumber(String cardNumber);
}
