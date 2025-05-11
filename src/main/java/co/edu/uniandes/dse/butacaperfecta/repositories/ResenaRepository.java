package co.edu.uniandes.dse.butacaperfecta.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import java.util.List;



@Repository
public interface ResenaRepository extends JpaRepository<ResenaEntity, Long> {

    Optional<ResenaEntity> findByClienteAndTeatro(ClienteEntity cliente, TeatroEntity teatro);

    Optional<ResenaEntity> findByClienteAndObraDeTeatro(ClienteEntity cliente, ObraDeTeatroEntity obraDeTeatro);

    List<ResenaEntity> findByObraDeTeatro(ObraDeTeatroEntity obraDeTeatro);

    List<ResenaEntity> findByTeatro(TeatroEntity teatro);

    List<ResenaEntity> findByCliente(ClienteEntity cliente);

    List<ResenaEntity> findByCalificacion(Integer calificacion);

    


    
    
    
}
