package co.edu.uniandes.dse.butacaperfecta.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.butacaperfecta.entities.SillaEntity;

@Repository
public interface SillaRepository extends JpaRepository<SillaEntity, Long> {
    List<SillaEntity> findAll();
    
}
