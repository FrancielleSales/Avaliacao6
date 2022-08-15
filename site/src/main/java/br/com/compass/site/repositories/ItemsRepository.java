package br.com.compass.site.repositories;

import br.com.compass.site.entities.ItemsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ItemsRepository extends JpaRepository <ItemsEntity, Long> {

    Page<ItemsEntity> findById(Long id, Pageable sort);

    ItemsEntity findBySkuId(String skuId);

    boolean existsBySkuId(String skuId);
}
