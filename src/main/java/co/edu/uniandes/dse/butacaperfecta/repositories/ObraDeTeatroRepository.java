package co.edu.uniandes.dse.butacaperfecta.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;

@Repository
public interface ObraDeTeatroRepository extends JpaRepository<ObraDeTeatroEntity, Long> {

    public List<ObraDeTeatroEntity> findByTitulo(String titulo);

    public List<ObraDeTeatroEntity> findByGenero(String genero);

    public List<ObraDeTeatroEntity> findByDuracion(Integer duracion);

    public List<ObraDeTeatroEntity> findByTituloAndGenero(String titulo, String genero);

    public List<ObraDeTeatroEntity> findByGeneroAndDuracion (String genero, Integer duracion);

    public List<ObraDeTeatroEntity> findByDuracionBetween(Integer duracion, Integer duracion2);

    public List<ObraDeTeatroEntity>findBySinopsisContainingIgnoreCase(String palabra);
}
