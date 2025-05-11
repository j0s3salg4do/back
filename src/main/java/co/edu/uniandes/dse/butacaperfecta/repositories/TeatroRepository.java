package co.edu.uniandes.dse.butacaperfecta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;



@Repository
public interface TeatroRepository extends JpaRepository<TeatroEntity, Long> {
    public TeatroEntity findByNombreTeatro(String nombreTeatro);
    public TeatroEntity findByDireccionTeatro(String direccionTeatro);
    public TeatroEntity findByNombreTeatroAndDireccionTeatro(String nombreTeatro, String direccionTeatro);
    public TeatroEntity findByCapacidad(int capacidad);
}
