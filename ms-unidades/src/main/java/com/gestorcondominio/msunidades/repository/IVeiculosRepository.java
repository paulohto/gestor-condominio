package com.gestorcondominio.msunidades.repository;

import com.gestorcondominio.msunidades.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVeiculosRepository extends JpaRepository<Veiculo, Long> {
}
