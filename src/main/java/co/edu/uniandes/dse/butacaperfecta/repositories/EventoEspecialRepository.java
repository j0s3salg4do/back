package co.edu.uniandes.dse.butacaperfecta.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.EventoEspecialEntity;

@Repository
public interface EventoEspecialRepository extends JpaRepository<EventoEspecialEntity, Long> {

    public List<EventoEspecialEntity> findByNombreEvento(String nombreEvento);

    public List<EventoEspecialEntity> findByFecha(Date fecha);

    public List<EventoEspecialEntity> findByNombreEventoAndFecha(String nombreEvento, Date fecha);

    public List<EventoEspecialEntity> findByFechaLessThan(Date fecha);
}
