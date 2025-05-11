package co.edu.uniandes.dse.butacaperfecta.repositories;

import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AsientoRepository extends JpaRepository<AsientoEntity, Long> {
    List<AsientoEntity> findAll();
    
}
