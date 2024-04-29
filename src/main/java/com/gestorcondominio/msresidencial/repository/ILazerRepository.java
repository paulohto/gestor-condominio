package com.gestorcondominio.msresidencial.repository;

import com.gestorcondominio.msresidencial.entity.Lazer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ILazerRepository extends JpaRepository<Lazer, Long> {
    //Lazer findByLazer(String lazer); // da video-alua
}
