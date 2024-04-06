package com.gestorcondominio.msresidencial.service;

import com.gestorcondominio.msresidencial.dto.LazerDTO;
import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import com.gestorcondominio.msresidencial.exception.DataBaseException;
import com.gestorcondominio.msresidencial.repository.ILazerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LazerService {

    private final ILazerRepository lazerRepository;

    public LazerService(ILazerRepository lazerRepository) {
        this.lazerRepository = lazerRepository;
    }

    @Transactional
    public LazerDTO saveLazer(LazerDTO lazerDTO){
        var lazerEntity = lazerDTO.toEntity(lazerDTO);
        var lazerSaved = lazerRepository.save(lazerEntity);
        return lazerDTO.fromEntity(lazerSaved);
    }

    @Transactional(readOnly = true)
    public Page<LazerDTO> findAll(PageRequest pageRequest){
        var lazeres = lazerRepository.findAll(pageRequest);
        return lazeres.map(LazerDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public LazerDTO findById(Long id){
        var lazer = lazerRepository.findById(id)
                .orElseThrow(
                        () -> new DataBaseException("Lazer não encontrado com o ID: " + id)
                );
        return LazerDTO.fromEntity(lazer);
    }

    @Transactional
    public LazerDTO updateLazer(Long id, LazerDTO lazerDTO){
        try{
            Lazer lazerEntity = lazerRepository.getReferenceById(id);
            LazerDTO.mapperDTOtoEntity(lazerDTO, lazerEntity);
            lazerEntity = lazerRepository.save(lazerEntity);

            return lazerDTO.fromEntity(lazerEntity);
        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Lazer não encontrado com o ID: " + id);
        }
    }

    @Transactional
    public void deleteLazerById(Long id){
        if( lazerRepository.existsById(id) ) {
            lazerRepository.deleteById(id);
        } else {
            throw new DataBaseException("Lazer não encontrado com o ID: " + id);
        }
    }

}
