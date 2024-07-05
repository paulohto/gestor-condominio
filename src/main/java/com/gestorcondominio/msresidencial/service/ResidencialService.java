package com.gestorcondominio.msresidencial.service;

import com.gestorcondominio.msresidencial.client.SindicoClient;
import com.gestorcondominio.msresidencial.dto.LazerDTO;
import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
import com.gestorcondominio.msresidencial.dto.SindicoDTO;
import com.gestorcondominio.msresidencial.entity.Lazer;
import com.gestorcondominio.msresidencial.entity.Residencial;
import com.gestorcondominio.msresidencial.repository.ILazerRepository;
import com.gestorcondominio.msresidencial.repository.IResidencialRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestorcondominio.msresidencial.exception.service.DataBaseException;

import java.util.stream.Collectors;
import java.util.logging.Logger;

@Service
public class ResidencialService {
    private final IResidencialRepository residencialRepository;
    private final ILazerRepository lazerRepository;
    private final SindicoClient sindicoClient;
    public ResidencialService(IResidencialRepository residencialRepository, ILazerRepository lazerRepository, SindicoClient sindicoClient) {
        this.residencialRepository = residencialRepository;
        this.lazerRepository = lazerRepository;
        this.sindicoClient = sindicoClient;
    }

    @Transactional
    public ResidencialDTO saveResidencial(ResidencialDTO residencialDTO){
        Residencial entity = new Residencial();
        mapperDTOtoEntity(residencialDTO, entity);

        var residencialSaved = residencialRepository.save(entity);
        SindicoDTO sindicoDTO = sindicoClient.getSindicoById(residencialDTO.getSindicoId());

        return new ResidencialDTO(residencialSaved, residencialSaved.getLazeres());
    }

    @Transactional(readOnly = true)
    public Page<ResidencialDTO> findAll(PageRequest pageRequest){
        var residenciais = residencialRepository.findAll(pageRequest);
        residencialRepository.findResidenciaisLazeres(residenciais.stream().collect(Collectors.toList()));
        return residenciais.map(x -> new ResidencialDTO(x, x.getLazeres()));
    }

    @Transactional(readOnly = true)
    public ResidencialDTO findById(Long id){
        var residencial = residencialRepository.findById(id)
        //var residencial = residencialRepository.findResidencialByIdAndLazeres(id)
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
            logger.info("SERVICE: Nenhum Lazer encontrado para o residencial " + residencial.getNome());
        }

        return new ResidencialDTO( residencial, residencial.getLazeres() );
    }

    @Transactional
    public ResidencialDTO updateResidencial(Long id, ResidencialDTO residencialDTO){
        // Remove todas as relações do ID, dentro do TRY. Apresentou inconsistência ao salvar no BD
        residencialRepository.deleteAllLazerRelations(id);

        // Verifica se o síndico existe
        Long sindicoId = residencialDTO.getSindicoId();
        SindicoDTO sindicoDTO;

        try {
            sindicoDTO = sindicoClient.getSindicoById(sindicoId);
        } catch (FeignException.NotFound e) {
                throw new EntityNotFoundException("Síndico não encontrado com o ID fornecido.", e);
        }

        if (!sindicoDTO.getNome().equals(residencialDTO.getSindicoNome())) {
            throw new EntityNotFoundException("Nome do síndico não corresponde ao ID fornecido.");
        }

        try {
            Residencial residencialEntity = residencialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Residencial não encontrado com o ID: " + id));

            // Remove todas as relações
            residencialEntity.getLazeres().clear();

            // Add novas relações
            for (LazerDTO lazerDTO : residencialDTO.getLazeres()) {
                Lazer lazer = lazerRepository.findById(lazerDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Lazer não encontrado com o ID: " + lazerDTO.getId()));
                residencialEntity.getLazeres().add(lazer);
            }

            // Atualiza as informações do síndico
            residencialEntity.setSindicoId(sindicoId);
            residencialEntity.setSindicoNome(residencialDTO.getSindicoNome());

            // Atualiza os outros campos do Residencial
            residencialEntity.setNome(residencialDTO.getNome());
            residencialEntity.setEndereco(residencialDTO.getEndereco());
            residencialEntity.setCep(residencialDTO.getCep());
            residencialEntity.setBairro(residencialDTO.getBairro());
            residencialEntity.setCidade(residencialDTO.getCidade());
            residencialEntity.setUf(residencialDTO.getUf());
            residencialEntity.setValorCondominio(residencialDTO.getValorCondominio());
            residencialEntity.setElevador(residencialDTO.getElevador());
            residencialEntity.setEmpresaPortaria(residencialDTO.getEmpresaPortaria());
            residencialEntity.setEmpresaZeladoria(residencialDTO.getEmpresaZeladoria());
            residencialEntity.setEmpresaVigilancia(residencialDTO.getEmpresaVigilancia());
            residencialEntity.setEmpresaBoletos(residencialDTO.getEmpresaBoletos());
            residencialEntity.setQuantidadeUnidades(residencialDTO.getQuantidadeUnidades());
            residencialEntity.setQuantidadePublico(residencialDTO.getQuantidadePublico());
            residencialEntity.setQuantidadeUnidadesUtilizamApp(residencialDTO.getQuantidadeUnidadesUtilizamApp());
            residencialEntity.setQuantidadeUnidadesComPet(residencialDTO.getQuantidadeUnidadesComPet());
            residencialEntity.setQuantidadeUnidadesComVeiculo(residencialDTO.getQuantidadeUnidadesComVeiculo());

            residencialEntity = residencialRepository.save(residencialEntity);

            return new ResidencialDTO(residencialEntity, residencialEntity.getLazeres());

        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Residencial ou Lazer não encontrado com os IDs fornecidos.");
        }
    }

    //@Transactional
    public void deleteResidencial(Long id) {
        try {
            // Verifique se o Residencial existe
            Residencial residencial = residencialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Residencial não encontrado com ID: " + id));

            // Deleta todos os relacionamentos com lazeres
            residencialRepository.deleteAllLazerRelations(id);

            // Deleta o Residencial
            residencialRepository.deleteResidencialById(id);
        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Residencial não encontrado com ID: " + id);
        }
    }

    public void mapperDTOtoEntity(ResidencialDTO dto, Residencial entity) {
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setEndereco(dto.getEndereco());
        entity.setCep(dto.getCep());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setUf(dto.getUf());

        entity.setSindicoId(dto.getSindicoId());
        entity.setSindicoNome(dto.getSindicoNome());
        //entity.setSindicoTelefone(dto.getTelefone());
        //entity.setSindicoEmail(dto.getEmail());

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

        for (LazerDTO lazerDTO: dto.getLazeres()) {
            //Lazer lazer = lazerRepository.getOne(lazerDTO.getId());
            Lazer lazer = lazerRepository.getReferenceById(lazerDTO.getId());
            entity.getLazeres().add(lazer);
        }
    }

    private static final Logger logger = Logger.getLogger(ResidencialService.class.getName());
}
