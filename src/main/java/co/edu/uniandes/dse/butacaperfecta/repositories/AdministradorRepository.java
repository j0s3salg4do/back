package co.edu.uniandes.dse.butacaperfecta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.uniandes.dse.butacaperfecta.entities.AdministradorEntity;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorEntity, Long> {
    public AdministradorEntity findByCorreoUsuario(String correo);
    public AdministradorEntity findByNombreUsuario(String nombre);
    public AdministradorEntity findByContrasenaUsuario(String contrasena);
    public AdministradorEntity findByNombreUsuarioAndCorreoUsuario(String nombre, String correo);
    public AdministradorEntity findByNombreUsuarioAndContrasenaUsuario(String nombre, String contrasena);
   
}
