package co.edu.uniandes.dse.butacaperfecta.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;

import java.util.Date;



@Repository
public interface FuncionRepository extends JpaRepository<FuncionEntity, Long> {
    public List<FuncionEntity> findByFecha(Date fecha);
    public List<FuncionEntity> findByHora(String hora);
    public List<FuncionEntity> findByFechaAndHora(Date fecha, String hora);
    public List<FuncionEntity> findByObraDeTeatro(ObraDeTeatroEntity obraDeTeatro);
    public List<FuncionEntity> findByFechaAndHoraAndObraDeTeatro(Date fecha, String hora, ObraDeTeatroEntity obraDeTeatro);
    public List<FuncionEntity> findByFechaAndObraDeTeatro(Date fecha, ObraDeTeatroEntity obraDeTeatro);
    public List<FuncionEntity> findByHoraAndObraDeTeatro(String hora, ObraDeTeatroEntity obraDeTeatro);   
}
