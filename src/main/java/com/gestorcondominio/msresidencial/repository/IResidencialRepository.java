package com.gestorcondominio.msresidencial.repository;

import com.gestorcondominio.msresidencial.entity.Residencial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IResidencialRepository extends JpaRepository<Residencial, Long> {
//    @Query("SELECT obj FROM Residencial obj LEFT JOIN FETCH obj.lazeres")
//    Page<Residencial> findResidenciaisLazeres(Pageable pageable);

    @Query("SELECT obj FROM Residencial obj LEFT JOIN FETCH obj.lazeres WHERE obj IN :residenciais")
    List<Residencial> findResidenciaisLazeres(List<Residencial> residenciais);

}
