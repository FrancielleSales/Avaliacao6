package br.com.compass.site.repositories;

import br.com.compass.site.entities.ClientsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository <ClientsEntity, String> {
    Page<ClientsEntity> findByCpf(String cpf, Pageable sort);
}
