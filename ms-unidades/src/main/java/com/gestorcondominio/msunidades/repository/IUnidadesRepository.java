package com.gestorcondominio.msunidades.repository;

import com.gestorcondominio.msunidades.dto.UnidadeDTO;
import com.gestorcondominio.msunidades.dto.resumos.UnidadeResumoDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.entity.enums.CondicaoDoResidente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IUnidadesRepository extends JpaRepository<Unidade, Long> {

    @Query("SELECT u FROM Unidade u LEFT JOIN FETCH u.dependentes WHERE u.id = :unidadeId")
    Unidade findUnidadeWithDependentesById(@Param("unidadeId") Long unidadeId);


    // CONSULTA POR ENDEREÇO
    Optional<Unidade> findByUnidadeEndereco(String unidadeEndereco); // Alterado corretamente para unidadeEndereco

    // CONSULTA POR NOME RESIDENTE
    @Query("SELECT u FROM Unidade u WHERE " +
            "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(u.nomeResidente, 'á', 'a'), 'é', 'e'), " +
            "'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ç', 'c'), 'ã', 'a'), 'õ', 'o')) " +
            "LIKE LOWER(CONCAT('%', REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(:nomeResidente, " +
            "'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ç', 'c'), 'ã', 'a'), 'õ', 'o'), '%'))")
    List<Unidade> findUnidadesByNome(@Param("nomeResidente") String nomeResidente);

    // CONSULTA LISTA DE RESIDENTES POR CONDICAO (INQUILINO/PROPRIETARIO)
    @Query("SELECT u FROM Unidade u WHERE u.condicaoDoResidente = :status")
    List<Unidade> findByCondicaoDoResidente(@Param("status") CondicaoDoResidente status);

    @Query("SELECT COUNT(u) FROM Unidade u WHERE u.condicaoDoResidente = :status")
    long countByCondicaoDoResidente(@Param("status") CondicaoDoResidente status);

}
