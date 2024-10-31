package com.gestorcondominio.msresidencial.repository;

import com.gestorcondominio.msresidencial.entity.Residencial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IResidencialRepository extends JpaRepository<Residencial, Long> {

    @Query("SELECT obj FROM Residencial obj LEFT JOIN FETCH obj.lazeres WHERE obj IN :residenciais")
    List<Residencial> findResidenciaisLazeres(List<Residencial> residenciais);


    @Query(value = "SELECT * FROM residencial_lazer", nativeQuery = true)
    List<Object[]> findAllResidencialLazer();

    @Query(value = "SELECT r.id as residencialId, l.id as lazerId, l.descricao as lazerDescricao " +
            "FROM tb_residencial r " +
            "LEFT JOIN tb_residencial_lazer rl ON r.id = rl.residencial_id " +
            "LEFT JOIN tb_lazer l ON rl.lazer_id = l.id " +
            "WHERE r.id = :id", nativeQuery = true)
    List<Object[]> findByResidencialId(Long id);

    @Query("SELECT r FROM Residencial r LEFT JOIN FETCH r.lazeres WHERE r.id = :id")
    Optional<Residencial> findByIdWithLazeres(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tb_residencial_lazer WHERE residencial_id = :residencialId", nativeQuery = true)
    void deleteAllLazerRelations(@Param("residencialId") Long residencialId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Residencial r WHERE r.id = :residencialId")
    void deleteResidencialById(@Param("residencialId") Long residencialId);

    //TESTE SINDICO
    @Query("SELECT r FROM Residencial r WHERE r.sindicoId = :sindicoId")
    List<Residencial> findResidenciaisBySindicoId(@Param("sindicoId") Long sindicoId);

    @Query("SELECT r FROM Residencial r WHERE LOWER(r.sindicoNome) = LOWER(:sindicoNome)")
    List<Residencial> findResidenciaisBySindicoNome(@Param("sindicoNome") String sindicoNome);

}
