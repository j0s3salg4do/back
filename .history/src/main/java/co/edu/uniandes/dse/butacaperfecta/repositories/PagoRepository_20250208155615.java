package co.edu.uniandes.dse.butacaperfecta.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.PagoEntity;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
    List<PagoEntity> findAll();
}
