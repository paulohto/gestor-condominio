package com.gestorcondominio.msresidencial.repository;

import com.gestorcondominio.msresidencial.entity.Residencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResidencialRepository extends JpaRepository<Residencial, Long> {

}
