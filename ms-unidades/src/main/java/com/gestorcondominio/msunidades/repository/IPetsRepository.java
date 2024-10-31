package com.gestorcondominio.msunidades.repository;

import com.gestorcondominio.msunidades.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPetsRepository extends JpaRepository<Pet, Long> {
}
