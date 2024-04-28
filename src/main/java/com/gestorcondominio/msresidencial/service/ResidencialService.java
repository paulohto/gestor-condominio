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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        var residenciais = residencialRepository.findAll(pageRequest);
        return residenciais.map(x -> new CResidencialDTO(x, x.getLazeres()));

//        var residenciais = residencialRepository.findResidenciaisLazeres(pageRequest);
//        return residenciais.map(x -> new CResidencialDTO(x));

    }

    @Transactional(readOnly = true)
    public CResidencialDTO findById(Long id){
        var residencial = residencialRepository.findById(id)
                .orElseThrow(
                        () -> new DataBaseException("Residencial não encontrado com o ID: " + id)
                );
        //return CResidencialDTO.fromEntity(residencial);
        //return new CResidencialDTO(residencial, residencial.getLazeres());

//        var teste = residencial.getLazeres().stream()
//                .map(x -> new Lazer(x.getId(), x.getDescricao())).collect(Collectors.toSet());

//        var teste2 = residencial.getLazeres().stream().map(Lazer::getId).collect(Collectors.toList());
//        var lazer = lazerRepository.findAll(teste2);
//        var testelazer = lazer.map(x -> new CLazerDTO(x.getId(), x.getDescricao()));
//
//        var teste = residencial.getLazeres().stream()
//                .map(x -> new Lazer(x.getId(), x.getDescricao())).collect(Collectors.toSet());

        //residencial.getLazeres().size();
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

        entity.getLazeres().clear();
        //return entity;

        for (CLazerDTO lazerDTO: dto.getLazeres()) {
            Lazer lazer = lazerRepository.getOne(lazerDTO.getId());
            entity.getLazeres().add(lazer);
        }
    }

}
