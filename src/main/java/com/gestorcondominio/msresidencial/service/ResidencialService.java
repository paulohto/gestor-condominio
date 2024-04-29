package com.gestorcondominio.msresidencial.service;

import com.gestorcondominio.msresidencial.dto.CLazerDTO;
import com.gestorcondominio.msresidencial.dto.CResidencialDTO;
import com.gestorcondominio.msresidencial.dto.LazerDTO;
import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import com.gestorcondominio.msresidencial.repository.ILazerRepository;
import com.gestorcondominio.msresidencial.repository.IResidencialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestorcondominio.msresidencial.exception.DataBaseException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class ResidencialService {
    private final IResidencialRepository residencialRepository;
    private final ILazerRepository lazerRepository;
    public ResidencialService(IResidencialRepository residencialRepository, ILazerRepository lazerRepository) {
        this.residencialRepository = residencialRepository;
        this.lazerRepository = lazerRepository;
    }

    public CResidencialDTO saveResidencial(CResidencialDTO residencial){
        Residencial entity = new Residencial();
        mapperDTOtoEntity(residencial, entity);
        var residencialSaved = residencialRepository.save(entity);
        return new CResidencialDTO(residencialSaved, residencialSaved.getLazeres());
    }

    @Transactional(readOnly = true)
    public Page<CResidencialDTO> findAll(PageRequest pageRequest){
//        Procedimento anterior sem findResidenciaisLazeres
//        var residenciais = residencialRepository.findAll(pageRequest);
//        return residenciais.map(x -> new CResidencialDTO(x, x.getLazeres()));
        var residenciais = residencialRepository.findAll(pageRequest);
        residencialRepository.findResidenciaisLazeres(residenciais.stream().collect(Collectors.toList()));
        return residenciais.map(x -> new CResidencialDTO(x, x.getLazeres()));
    }

    @Transactional(readOnly = true)
    public CResidencialDTO findById(Long id){
        //var residencial = residencialRepository.findById(id)
        var residencial = residencialRepository.findResidencialByIdWithLazeres(id)
                .orElseThrow(
                        () -> new DataBaseException("Residencial não encontrado com o ID: " + id)
                );

        // Agora vamos logar os detalhes dos Lazeres, se houver
        if (residencial.getLazeres() != null && !residencial.getLazeres().isEmpty()) {
            logger.info("Lazeres encontrados para o residencial " + residencial.getNome() + ":");
            for (Lazer lazer : residencial.getLazeres()) {
                logger.info("ID do Lazer: " + lazer.getId() + ", Descrição do Lazer: " + lazer.getDescricao());
            }
        } else {
            logger.info("Nenhum Lazer encontrado para o residencial " + residencial.getNome());
        }

        return new CResidencialDTO( residencial, residencial.getLazeres() );

    }

    @Transactional
    public CResidencialDTO updateResidencial(Long id, CResidencialDTO residencialDTO){
        try{
            Residencial residencialEntity = residencialRepository.getReferenceById(id);
            mapperDTOtoEntity(residencialDTO, residencialEntity);
            residencialEntity = residencialRepository.save(residencialEntity);

            //return CResidencialDTO.fromEntity(residencialEntity);
            return new CResidencialDTO(residencialEntity, residencialEntity.getLazeres());

        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Residencial não encontrado com o ID: " + id);
        }
    }

    //@Transactional
    public void deleteResidencialById(Long id){
        if( residencialRepository.existsById(id) ) {
            residencialRepository.deleteById(id);
        } else {
            throw new DataBaseException("Residencial não encontrado com o ID: " + id);
        }
    }

    public void mapperDTOtoEntity(CResidencialDTO dto, Residencial entity) {
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEndereco(dto.getEndereco());
        entity.setCep(dto.getCep());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setUf(dto.getUf());

        entity.setValorCondominio(dto.getValorCondominio());
        entity.setElevador(dto.getElevador());
        entity.setEmpresaPortaria(dto.getEmpresaPortaria());
        entity.setEmpresaZeladoria(dto.getEmpresaZeladoria());
        entity.setEmpresaVigilancia(dto.getEmpresaVigilancia());
        entity.setEmpresaBoletos(dto.getEmpresaBoletos());
        entity.setQuantidadeUnidades(dto.getQuantidadeUnidades());
        entity.setQuantidadePublico(dto.getQuantidadePublico());
        entity.setQuantidadeUnidadesUtilizamApp(dto.getQuantidadeUnidadesUtilizamApp());
        entity.setQuantidadeUnidadesComPet(dto.getQuantidadeUnidadesComPet());
        entity.setQuantidadeUnidadesComVeiculo(dto.getQuantidadeUnidadesComVeiculo());

        // Inicializa a lista de lazêres
        entity.setLazeres(new HashSet<>());

        entity.getLazeres().clear();
        //entity.getLazeres();

        for (CLazerDTO lazerDTO: dto.getLazeres()) {
            Lazer lazer = lazerRepository.getOne(lazerDTO.getId());
            entity.getLazeres().add(lazer);
        }
    }

    private static final Logger logger = Logger.getLogger(ResidencialService.class.getName());


}
