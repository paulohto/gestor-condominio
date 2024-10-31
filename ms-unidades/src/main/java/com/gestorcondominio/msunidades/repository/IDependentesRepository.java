package com.gestorcondominio.msunidades.repository;

import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IDependentesRepository extends JpaRepository<Dependente, Long> {

    // CONSULTA POR ID
    @Query("SELECT d FROM Dependente d WHERE d.unidadeInfo.id = :unidadeId")
    Set<Dependente> findDependentesByUnidadeId(Long unidadeId);

    // CONSULTA POR ENDEREÇO
    @Query("SELECT d FROM Dependente d WHERE d.unidadeInfo.unidadeEndereco = :unidadeEndereco")
    Set<Dependente> findDependentesByUnidadeEndereco(String unidadeEndereco);

    // CONSULTA POR NOME DEPENDENTE
    @Query("SELECT d FROM Dependente d WHERE " +
            "LOWER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(d.nome, 'á', 'a'), 'é', 'e'), " +
            "'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ç', 'c'), 'ã', 'a'), 'õ', 'o')) " +
            "LIKE LOWER(CONCAT('%', REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(:nome, " +
            "'á', 'a'), 'é', 'e'), 'í', 'i'), 'ó', 'o'), 'ú', 'u'), 'ç', 'c'), 'ã', 'a'), 'õ', 'o'), '%'))")
    List<Dependente> findDependentesByNome(@Param("nome") String nome);

}
