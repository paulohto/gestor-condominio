package com.gestorcondominio.mssindico.repository;

import com.gestorcondominio.mssindico.entity.Sindico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISindicoRepository extends JpaRepository<Sindico, Long> {
}
