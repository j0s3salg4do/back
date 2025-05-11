package co.edu.uniandes.dse.butacaperfecta.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;

@Repository
public interface BoletoRepository extends JpaRepository<BoletoEntity, Long> {
    public List<BoletoEntity> findByFuncionId(Long funcionId);
    public List<BoletoEntity> findByClienteId(Long clienteId);
    public List<BoletoEntity> findByFuncionIdAndClienteId(Long funcionId, Long clienteId);
    public List<BoletoEntity> findByFuncionIdAndSillaId(Long funcionId, Long sillaId);
    public List<BoletoEntity> findByFuncionIdAndClienteIdAndSillaId(Long funcionId, Long clienteId, Long sillaId);
}
