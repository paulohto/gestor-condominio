package com.gestorcondominio.msresidencial.service;

import com.gestorcondominio.msresidencial.dto.LazerDTO;
import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
import com.gestorcondominio.msresidencial.entity.Residencial;
import com.gestorcondominio.msresidencial.exception.service.DataBaseException;
import com.gestorcondominio.msresidencial.repository.ILazerRepository;
import com.gestorcondominio.msresidencial.repository.IResidencialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResidencialLazerService {

    private final IResidencialRepository residencialRepository;
    private final ILazerRepository lazerRepository;

    public ResidencialLazerService(IResidencialRepository residencialRepository, ILazerRepository lazerRepository) {
        this.residencialRepository = residencialRepository;
        this.lazerRepository = lazerRepository;
    }

    @Transactional(readOnly = true)
    public ResidencialDTO findResidencialWithLazerById(Long id) {
        try{
            // Busque o residencial pelo ID
            Residencial residencial = residencialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Residencial não encontrado com o ID: " + id));

//        Optional<Residencial> residencialOpt = residencialRepository.findById(id);
//        if (!residencialOpt.isPresent()) {
//            return null;
//        }
//
//        Residencial residencial = residencialOpt.get();

            // Busque os lazeres associados ao residencial
            List<LazerDTO> lazerDTOs = residencialRepository.findByResidencialId(id).stream()
                    .map(result -> new LazerDTO((Long) result[1], (String) result[2]))
                    .collect(Collectors.toList());

            // Para registrar o horário de criação
            Instant dataDeCriacao = residencial.getDataDeCriacao();
            int dataDeCriacaoNano = (dataDeCriacao != null) ? dataDeCriacao.getNano() : 0;

            // Retorne o DTO do residencial com os lazeres
            return new ResidencialDTO(
                    residencial.getId(),
                    residencial.getNome(),
                    residencial.getEndereco(),
                    residencial.getCep(),
                    residencial.getBairro(),
                    residencial.getCidade(),
                    residencial.getUf(),
                    lazerDTOs,

                    residencial.getSindicoId(),
                    residencial.getSindicoNome(),

                    residencial.getValorCondominio(),
                    residencial.getElevador(),
                    residencial.getEmpresaPortaria(),
                    residencial.getEmpresaZeladoria(),
                    residencial.getEmpresaVigilancia(),
                    residencial.getEmpresaBoletos(),
                    residencial.getQuantidadeUnidadesUtilizamApp(),
                    residencial.getQuantidadeUnidadesComPet(),
                    residencial.getQuantidadeUnidadesComVeiculo(),
                    residencial.getQuantidadeUnidades(),
                    dataDeCriacaoNano
                    //residencial.getDataDeCriacao().getNano()
            );

        } catch (EntityNotFoundException  e){
            throw new DataBaseException("Residencial not found with ID: " + id);
        }
    }
}
