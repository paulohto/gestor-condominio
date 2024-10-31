package com.gestorcondominio.msresidencial.service;

import com.gestorcondominio.msresidencial.dto.LazerDTO;
import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import com.gestorcondominio.msresidencial.exception.service.ControllerNotFoundException;
import com.gestorcondominio.msresidencial.exception.service.DataBaseException;
import com.gestorcondominio.msresidencial.repository.ILazerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@Service
public class LazerService {


    private final ILazerRepository lazerRepository;
    private static final Logger logger = LoggerFactory.getLogger(LazerService.class);
    public LazerService(ILazerRepository lazerRepository) {
        this.lazerRepository = lazerRepository;
    }

    @Transactional
    public LazerDTO saveLazer(LazerDTO lazerDTO){
        var entity = new Lazer();
        mapperDTOtoEntity(lazerDTO, entity);
        Lazer lazerSaved = lazerRepository.save(entity);
        return new LazerDTO(lazerSaved);
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
            mapperDTOtoEntity(lazerDTO, lazerEntity);
            lazerEntity = lazerRepository.save(lazerEntity);

            return lazerDTO.fromEntity(lazerEntity);
        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Lazer não encontrado com o ID: " + id);
        }
    }

    //@Transactional
    public void deleteLazer(Long id){
        Optional<Lazer> lazerOptional = lazerRepository.findById(id);
        try {
            if (!lazerOptional.isPresent()) {
                throw new EntityNotFoundException("Lazer com ID: " + id + " não encontrado.");
            }
            lazerRepository.deleteById(id);

        }  catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade da base");
            }
    }

    private void mapperDTOtoEntity(LazerDTO dto, Lazer entity){
        //entity.setId(dto.getId());
        entity.setDescricao(dto.getDescricao());
    }

}
