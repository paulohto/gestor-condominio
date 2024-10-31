package com.gestorcondominio.msunidades.service;

import com.gestorcondominio.msunidades.dto.DependenteDTO;
import com.gestorcondominio.msunidades.dto.relacionamentos.DependenteUnidadeDTO;
import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.exception.DataBaseException;
import com.gestorcondominio.msunidades.repository.IDependentesRepository;
import com.gestorcondominio.msunidades.repository.IUnidadesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DependenteService {

    private final IDependentesRepository dependentesRepository;
    private final IUnidadesRepository unidadesRepository;

    public DependenteService(IDependentesRepository dependentesRepository, IUnidadesRepository unidadesRepository) {
        this.dependentesRepository = dependentesRepository;
        this.unidadesRepository = unidadesRepository;
    }

//    @Transactional
//    public DependenteUnidadeDTO save(DependenteUnidadeDTO dto) {
//        try {
////            Unidade unidade = unidadesRepository.findById(dto.getId())
//            Unidade unidade = unidadesRepository.findById(dto.getUnidadeId())
//                    .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
//
//            Dependente dependente = DependenteUnidadeDTO.toEntity(dto, unidade);
//            dependente = dependentesRepository.save(dependente);
//            return DependenteUnidadeDTO.fromEntity(dependente);
//
//        } catch (EntityNotFoundException e) {
//            throw new DataBaseException("Erro ao salvar o dependente: " + e.getMessage());
//        } catch (DataIntegrityViolationException e) {
//            throw new DataBaseException("Erro de integridade de dados: " + e.getMessage());
//        }
//    }

    @Transactional
    public DependenteUnidadeDTO save(DependenteUnidadeDTO dto) {
        try {
            String unidadeEndereco = dto.getUnidadeEndereco();
            if (unidadeEndereco == null) {
                throw new IllegalArgumentException("O Numero da unidade não pode ser nulo.");
            }

            // Encontra a unidade pelo número
            Unidade unidade = unidadesRepository.findByUnidadeEndereco(unidadeEndereco)
                    .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));

            // Converte DTO para entidade Dependente
            Dependente dependente = DependenteUnidadeDTO.toEntity(dto, unidade);
            dependente = dependentesRepository.save(dependente);
            return DependenteUnidadeDTO.fromEntity(dependente);

        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Erro ao salvar o dependente: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Erro de integridade de dados: " + e.getMessage());
        }
    }


    // Método para salvar um Dependente
//    public Dependente save(DependenteUnidadeDTO dto) {
//        Unidade unidade = unidadesRepository.findById(dto.getUnidade().getUnidade())
//                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
//        Dependente dependente = DependenteUnidadeDTO.toEntity(dto, unidade);
//        return dependentesRepository.save(dependente);
//    }
//
//    public DependenteUnidadeDTO getDependenteUnidadeDTO(Long dependenteId) {
//        Dependente dependente = dependentesRepository.findById(dependenteId)
//                .orElseThrow(() -> new RuntimeException("Dependente não encontrado"));
//        Unidade unidade = dependente.getUnidade();
//
//        UnidadeResumoDTO unidadeResumoDTO = UnidadeResumoDTO.fromEntity(unidade);
//
//        return DependenteUnidadeDTO.fromEntity(dependente, unidadeResumoDTO);
//    }

    @Transactional(readOnly = true)
    public Page<DependenteDTO> findAll(PageRequest pageRequest){
        var dependentes = dependentesRepository.findAll(pageRequest);
        return dependentes.map(DependenteDTO::fromEntity);

    }

    @Transactional(readOnly = true)
    public DependenteDTO findById(Long dependenteId) {
        Optional<Dependente> dependenteOpt = dependentesRepository.findById(dependenteId);
        if (dependenteOpt.isPresent()) {
            Dependente dependente = dependenteOpt.get();
            return DependenteDTO.fromEntity(dependente);
        } else {
            throw new EntityNotFoundException("Dependente não encontrado com o ID: " + dependenteId);
        }
    }

    // CONSULTAR DEPENDETES PELA UNIDADE ENDERECO
    @Transactional(readOnly = true)
    public List<DependenteResumoDTO> findDependentesByUnidadeEndereco(String unidadeEndereco){
        // Encontra a unidade pelo Endereço
        Unidade unidade = unidadesRepository.findByUnidadeEndereco(unidadeEndereco)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));

        // Mapeia os dependentes para DependenteDTO
        return unidade.getDependentes().stream()
                .map(DependenteResumoDTO::toResumoDTOWithUnidade)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DependenteResumoDTO> findDependentesByNome(String nomeDependente){
        List<Dependente> dependentes = dependentesRepository.findDependentesByNome(nomeDependente);
        if (dependentes.isEmpty()) {
            throw new EntityNotFoundException("Dependentes com o nome " + nomeDependente + " não encontrados");
        }
        return dependentes.stream()
                .map(DependenteResumoDTO::toResumoDTOWithUnidade)
                .collect(Collectors.toList());
    }

    @Transactional
    public DependenteResumoDTO updateDependente(Long id, DependenteResumoDTO dto){
        Dependente dependente = dependentesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dependente não encontrado para o ID: " + id));

        // Verificar se o `unidadeEndereco` foi alterado
        if (dto.getUnidadeEndereco() != null &&
                !dto.getUnidadeEndereco().equals(dependente.getUnidadeInfo().getUnidadeEndereco())) {

            // Desassociar o dependente da unidade anterior
            Unidade unidadeAntiga = dependente.getUnidadeInfo();
            if (unidadeAntiga != null) {
                unidadeAntiga.getDependentes().remove(dependente);
                unidadesRepository.save(unidadeAntiga);  // Atualizar a unidade antiga
            }

            // Buscar a nova unidade pelo novo `unidadeEndereco`
            Unidade novaUnidade = unidadesRepository.findByUnidadeEndereco(dto.getUnidadeEndereco())
                    .orElseThrow(() -> new RuntimeException("Unidade não encontrada para o endereço: " + dto.getUnidadeEndereco()));

            // Mapear a nova unidade diretamente no método de mapeamento
            dependente = dto.mapperDTOtoEntity2(dependente, novaUnidade);
        } else {
            // Atualizar outros campos do dependente
            dto.mapperDTOtoEntity2(dependente, null); // Se não houve mudança na unidade, apenas atualiza outros campos
        }

        // Salvar as alterações no dependente
        dependentesRepository.save(dependente);

        // Retornar o dependente atualizado como DependenteResumoDTO
        return DependenteResumoDTO.toResumoDTO(dependente);
    }

    /*** BLOCO: Consulta por faixa etária (idade entre dois valores) ***/
    public List<DependenteResumoDTO> findDependentesByFaixaEtaria(int idadeMinima, int idadeMaxima) {
        List<Dependente> dependentes = dependentesRepository.findAll();
        return dependentes.stream()
                .filter(dependente -> {
                    int idade = calcularIdade(dependente.getNascimento());
                    return idade >= idadeMinima && idade <= idadeMaxima;
                })
                .map(DependenteResumoDTO::toResumoDTO)
                .collect(Collectors.toList());
    }

    // Consulta por idade exata
    public List<DependenteResumoDTO> findDependentesByIdadeExata(int idade) {
        List<Dependente> dependentes = dependentesRepository.findAll();
        return dependentes.stream()
                .filter(dependente -> calcularIdade(dependente.getNascimento()) == idade)
                .map(DependenteResumoDTO::toResumoDTO)
                .collect(Collectors.toList());
    }

    // Função auxiliar para calcular a idade
    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
    /*** FIM DO BLOCO: idades ***/

    public void deleteDependente(Long id){
        Optional<Dependente> dependeteOptional = dependentesRepository.findById(id);
        try {
            if (!dependeteOptional.isPresent()) {
                throw new EntityNotFoundException("Dependente com ID: " + id + " não encontrado.");
            }
            dependentesRepository.deleteById(id);
        } catch (DataIntegrityViolationException e ){
            throw new DataBaseException("Violação de integridade da base");
        }
    }

}
