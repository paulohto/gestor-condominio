package com.gestorcondominio.msresidencial.service;

import com.gestorcondominio.msresidencial.dto.LazerDTO;
import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
import com.gestorcondominio.msresidencial.dto.ResidencialLazerDTO;
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

@Service
public class ResidencialService {

    private final IResidencialRepository residencialRepository;
    private final ILazerRepository lazerRepository;

    public ResidencialService(IResidencialRepository residencialRepository, ILazerRepository lazerRepository) {
        this.residencialRepository = residencialRepository;
        this.lazerRepository = lazerRepository;
    }

    @Transactional
    public ResidencialDTO saveResidencial(ResidencialDTO residencialDTO){
        var residencialEntity = residencialDTO.toEntity(residencialDTO);

        // Carrega os lazeres com base nos IDs fornecidos
        List<Long> lazerIds = (List<Long>) residencialDTO.lazeres().get();
        List<Lazer> lazeres = lazerRepository.findAllById(lazerIds);

        // Define os lazeres na entidade Residencial
        residencialEntity.setLazeres(lazeres);

        var residencialSaved = residencialRepository.save(residencialEntity);
        return residencialDTO.fromEntity(residencialSaved);
    }

//    @Transactional
//    public ResidencialDTO saveResidencial(ResidencialLazerDTO residencialLazerDTO){
//        Residencial residencialEntity = residencialLazerDTO.toEntity();
//
//        // Aqui você precisa carregar os lazeres com base nos IDs fornecidos
//        List<Lazer> lazeres = lazerRepository.findAllById(residencialLazerDTO.idsLazeres());
//
//        // Defina os lazeres para o residencial
//        residencialEntity.setLazeres(lazeres);
//
//        Residencial residencialSaved = residencialRepository.save(residencialEntity);
//
//        return ResidencialDTO.fromEntity(residencialSaved);
//}

    @Transactional(readOnly = true)
    public Page<ResidencialDTO> findAll(PageRequest pageRequest){
        var residenciais = residencialRepository.findAll(pageRequest);
        return residenciais.map(ResidencialDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public ResidencialDTO findById(Long id){
        var residencial = residencialRepository.findById(id)
                .orElseThrow(
                        () -> new DataBaseException("Residencial não encontrado com o ID: " + id)
                );
        return ResidencialDTO.fromEntity(residencial);
    }

    @Transactional
    public ResidencialDTO updateResidencial(Long id, ResidencialDTO residencialDTO){
        try{

            Residencial residencialEntity = residencialRepository.getReferenceById(id);
            ResidencialDTO.mapperDTOtoEntity(residencialDTO, residencialEntity);
            residencialEntity = residencialRepository.save(residencialEntity);

            return ResidencialDTO.fromEntity(residencialEntity);

        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Residencial não encontrado com o ID: " + id);
        }
    }

    @Transactional
    public void deleteResidencialById(Long id){
        if( residencialRepository.existsById(id) ) {
            residencialRepository.deleteById(id);
        } else {
            throw new DataBaseException("Residencial não encontrado com o ID: " + id);
        }
    }
}
