package com.gestorcondominio.msunidades.service;

import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.dto.UnidadeDTO;
import com.gestorcondominio.msunidades.dto.relacionamentos.UnidadeDependenteDTO;
import com.gestorcondominio.msunidades.dto.resumos.UnidadeResumoDTO;
import com.gestorcondominio.msunidades.entity.Dependente;
import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.entity.enums.CondicaoDoResidente;
import com.gestorcondominio.msunidades.exception.DataBaseException;
import com.gestorcondominio.msunidades.repository.IDependentesRepository;
import com.gestorcondominio.msunidades.repository.IUnidadesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnidadeService {
    private final IUnidadesRepository unidadesRepository;
    private final IDependentesRepository dependentesRepository;

    public UnidadeService(IUnidadesRepository unidadesRepository, IDependentesRepository dependentesRepository) {
        this.unidadesRepository = unidadesRepository;
        this.dependentesRepository = dependentesRepository;
    }

    @Transactional
    public UnidadeDTO saveUnidade(UnidadeDTO unidadeDTO) {
        // Converte o DTO em entidade
        Unidade entity = UnidadeDTO.toEntity(unidadeDTO);
        // Salva a unidade no banco de dados
        Unidade unidadeSaved = unidadesRepository.save(entity);

        // Retorna o DTO com os dados da unidade salva
        return UnidadeDTO.fromEntity(unidadeSaved);
    }

    @Transactional(readOnly = true)
    public Page<UnidadeDTO> findAll(PageRequest pageRequest){
        var unidades = unidadesRepository.findAll(pageRequest);

        // Carregar explicitamente as coleções
//        unidades.forEach(unidade -> {
//            unidade.getDependentes().size(); // Força a inicialização
//            unidade.getVeiculos().size();    // Força a inicialização
//            unidade.getPets().size();        // Força a inicialização
//        });

        return unidades.map(UnidadeDTO::fromEntity);
    }

//    @Transactional(readOnly = true)
//    public Page<ResidencialDTO> findAll(PageRequest pageRequest){
//        var residenciais = residencialRepository.findAll(pageRequest);
//        residencialRepository.findResidenciaisLazeres(residenciais.stream().collect(Collectors.toList()));
//        return residenciais.map(x -> new ResidencialDTO(x, x.getLazeres()));
//    }

//    @Transactional(readOnly = true)
//    public UnidadeDTO finById(Long id){
//        var unidade = unidadesRepository.findById(id)
//                .orElseThrow(
//                        () -> new DataBaseException("Unidade não encontrado com o ID: " + id)
//                );
//
//        // Forçar a inicialização das coleções
//       // unidade.getDependentes().size();
//        unidade.getVeiculos().size();
//        unidade.getPets().size();
//
//        return UnidadeDTO.fromEntity(unidade);
//    }

    @Transactional(readOnly = true)
    public UnidadeDependenteDTO finById(Long id){
        var unidade = unidadesRepository.findById(id)
                .orElseThrow(
                        () -> new DataBaseException("Unidade não encontrado com o ID: " + id)
                );

        // Forçar a inicialização das coleções
        unidade.getDependentes().size();
        unidade.getVeiculos().size();
        unidade.getPets().size();

        return UnidadeDependenteDTO.fromEntity(unidade);
    }

    @Transactional(readOnly = true)
    public UnidadeDependenteDTO findUnidadeByUnidadeEndereco(String unidadeEndereco){
        // Encontra a unidade pelo Endereço
        Unidade unidade = unidadesRepository.findByUnidadeEndereco(unidadeEndereco)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));

        // Forçar a inicialização das coleções
        unidade.getDependentes().size();
        unidade.getVeiculos().size();
        unidade.getPets().size();

        return UnidadeDependenteDTO.fromEntity(unidade);
    }

    @Transactional(readOnly = true)
    public List<UnidadeResumoDTO> findUnidadesByNome(String nomeResidente){
        List<Unidade> unidades = unidadesRepository.findUnidadesByNome(nomeResidente);
        if (unidades.isEmpty()) {
            throw new EntityNotFoundException("Residentes com o nome " + nomeResidente + " não encontrados");
        }
        return unidades.stream()
                .map(UnidadeResumoDTO::toResumoDTOWithDependentes)
                .collect(Collectors.toList());
    }

    //////
//    @Transactional(readOnly = true)
//    public UnidadeDependenteDTO findUnidadeWithDependentesById(Long unidadeId) {
//        Unidade unidade = unidadesRepository.findById(unidadeId)
//                .orElseThrow(() -> new RuntimeException("Unidade não encontrada para o ID: " + unidadeId));
//
//        Set<Dependente> dependentes = dependentesRepository.findDependentesByUnidadeId(unidadeId);
//
//        unidade.setDependentes(dependentes); // Certifique-se de que o método setDependentes exista na entidade Unidade
//
//        return UnidadeDependenteDTO.fromEntity(unidade);
//    }

    @Transactional(readOnly = true)
    public UnidadeDTO findUnidadeWithDependentesById(Long unidadeId) {
        Unidade unidade = unidadesRepository.findById(unidadeId)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada para o ID: " + unidadeId));

        // Buscando os dependentes da unidade e convertendo para DependenteResumoDTO
        List<DependenteResumoDTO> dependentesResumidos = dependentesRepository.findDependentesByUnidadeId(unidadeId)
                .stream()
                .map(DependenteResumoDTO::toResumoDTO)
                .collect(Collectors.toList());

        // Convertendo a unidade para UnidadeDTO
        UnidadeDTO unidadeDTO = new UnidadeDTO(unidade);

        // Setando os dependentes resumidos no UnidadeDTO
        unidadeDTO.setDependentes(dependentesResumidos);

        return unidadeDTO;
    }

    @Transactional(readOnly = true)
    public List<Unidade> getUnidadesByCondicaoDoResidente(CondicaoDoResidente status) {
        return unidadesRepository.findByCondicaoDoResidente(status);
    }

    @Transactional(readOnly = true)
    public long countInquilinos() {
        return unidadesRepository.countByCondicaoDoResidente(CondicaoDoResidente.INQUILINO);
    }

    @Transactional(readOnly = true)
    public long countProprietarios() {
        return unidadesRepository.countByCondicaoDoResidente(CondicaoDoResidente.PROPRIETARIO);
    }

//    @Transactional(readOnly = true)
//    public UnidadeDependenteDTO findUnidadeWithDependentesById(Long unidadeId) {
//        Unidade unidade = unidadesRepository.findById(unidadeId)
//                .orElseThrow(() -> new RuntimeException("Unidade não encontrada para o ID: " + unidadeId));
//
//        List<DependenteResumoDTO> dependentesResumidos = dependentesRepository.findDependentesByUnidadeId(unidadeId)
//                .stream()
//                .map(DependenteResumoDTO::toResumoDTO)
//                .collect(Collectors.toList());
//
//        UnidadeDTO unidadeDTO = new UnidadeDTO(unidade);
//        unidadeDTO.setDependentes(dependentesResumidos);
//
//        return UnidadeDependenteDTO.fromUnidadeDTO(unidadeDTO);
//    }


    //////

    @Transactional
    public UnidadeDTO updateUnidade(Long id, UnidadeDTO unidadeDTO){
        Unidade unidadeEntity = unidadesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrado para o ID: " + id));

        unidadeDTO.mapperDTOtoEntity(unidadeDTO, unidadeEntity);
        unidadeEntity = unidadesRepository.save(unidadeEntity);
        return unidadeDTO.fromEntity(unidadeEntity);

    }

//    @Transactional
//    public LazerDTO updateLazer(Long id, LazerDTO lazerDTO){
//        try{
//            Lazer lazerEntity = lazerRepository.getReferenceById(id);
//            mapperDTOtoEntity(lazerDTO, lazerEntity);
//            lazerEntity = lazerRepository.save(lazerEntity);
//
//            return lazerDTO.fromEntity(lazerEntity);
//        } catch (EntityNotFoundException e) {
//            throw new DataBaseException("Lazer não encontrado com o ID: " + id);
//        }
//    }

    public void deleteUnidade(Long id){
        Optional<Unidade> unidadeOptional = unidadesRepository.findById(id);
        try{
            if (!unidadeOptional.isPresent()){
                throw new EntityNotFoundException("Unidade com ID: " + id + " não encontrado.");
            }
            unidadesRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Violação de integridade da base");
        }
    }

}
