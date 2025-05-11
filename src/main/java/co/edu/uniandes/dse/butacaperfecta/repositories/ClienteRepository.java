package co.edu.uniandes.dse.butacaperfecta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    public ClienteEntity findByCorreoUsuario(String correo);
    public ClienteEntity findByNombreUsuario(String nombre);
    public ClienteEntity findByContrasenaUsuario(String contrasena);
    public ClienteEntity findByNombreUsuarioAndCorreoUsuario(String nombre, String correo);
    public ClienteEntity findByNombreUsuarioAndContrasenaUsuario(String nombre, String contrasena);
    
}
