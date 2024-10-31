package com.gestorcondominio.mssindico.service;

import com.gestorcondominio.mssindico.client.ResidencialClient;
import com.gestorcondominio.mssindico.dto.ResidencialDTO;
import com.gestorcondominio.mssindico.dto.SindicoDTO;
import com.gestorcondominio.mssindico.entity.Sindico;
import com.gestorcondominio.mssindico.exception.service.DataBaseException;
import com.gestorcondominio.mssindico.repository.ISindicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class SindicoService {

    private final ISindicoRepository sindicoRepository;
    private final ResidencialClient residencialClient;

    public SindicoService(ISindicoRepository sindicoRepository, ResidencialClient residencialClient) {
        this.sindicoRepository = sindicoRepository;
        this.residencialClient = residencialClient;
    }

    public SindicoDTO saveSindico(SindicoDTO sindicoDTO){

        logger.info("Saving Sindico with DTO: " + sindicoDTO);

        // Verifique se os valores necessários estão presentes
        if (sindicoDTO.residencialId() == null) {
            throw new IllegalArgumentException("Residencial ID não pode ser nulo.");
        }


        var sindicoEntity = sindicoDTO.toEntity(sindicoDTO);
        logger.info("Converted SindicoDTO to Entity: " + sindicoEntity);

        var sindicoSaved = sindicoRepository.save(sindicoEntity);
        logger.info("Sindico saved with ID: " + sindicoSaved.getId());

        //Relação com ms-residencial
        //ResidencialDTO residencialDTO = residencialClient.getResidencialById( sindicoDTO.residencialId() );
        ResidencialDTO residencialDTO = null;
        try {
            residencialDTO = residencialClient.getResidencialById(sindicoDTO.residencialId());
            logger.info("Fetched Residencial for Sindico ID: " + sindicoDTO.residencialId());
        } catch (Exception e) {
            logger.severe("Failed to fetch Residencial for Sindico ID: " + sindicoDTO.residencialId() + ", error: " + e.getMessage());
            throw new DataBaseException("Erro ao buscar Residencial: " + e.getMessage());
        }

        // Atualize os campos necessários no DTO
        // Crie uma nova instância de SindicoDTO com os campos atualizados
        var updatedSindicoDTO = new SindicoDTO(
                sindicoSaved.getId(),
                sindicoSaved.getNome(),
                sindicoSaved.getCpf(),
                sindicoSaved.getCnpj(),
                sindicoSaved.getNascimento(),
                sindicoSaved.getTelefone(),
                sindicoSaved.getWhatsapp(),
                sindicoSaved.getEmail(),
                sindicoSaved.getCep(),
                sindicoSaved.getEndereco(),
                residencialDTO.getId(),
                residencialDTO.getNome()
        );
        logger.info("Set ResidencialNome in SindicoDTO: " + sindicoDTO);

        return sindicoDTO.fromEntity(sindicoSaved);
    }

    public Page<SindicoDTO> findAll(PageRequest pageRequest){
        var sindicos = sindicoRepository.findAll(pageRequest);
        return sindicos.map(SindicoDTO::fromEntity);
    }

    public SindicoDTO findById(Long id){
        var sindico = sindicoRepository.findById(id)
                .orElseThrow(
                        () -> new DataBaseException("Id não encontrado: " + id)
                );
        return SindicoDTO.fromEntity(sindico);
    }

    public SindicoDTO updateSindico(Long id, SindicoDTO sindicoDTO){
        try {
            Sindico sindicoEntity = sindicoRepository.getReferenceById(id);
            SindicoDTO.mapperDTOtoEntity(sindicoDTO, sindicoEntity);
            sindicoEntity = sindicoRepository.save(sindicoEntity);
            return SindicoDTO.fromEntity(sindicoEntity);
        } catch (EntityNotFoundException e) {
            throw new DataBaseException("Id não encontrado: " + id);
        }
    }

    public void deleteSindico(Long id){
        if ( sindicoRepository.existsById(id) ) {
            sindicoRepository.deleteById(id);
        } else {
            throw new DataBaseException("Id não encontrado: " + id);
        }
    }

    public void mapperDTOtoEntity(SindicoDTO dto, Sindico entity) {
        entity.setId(dto.id());
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setCnpj(dto.cnpj());
        entity.setNascimento(dto.nascimento());
        entity.setTelefone(dto.telefone());
        entity.setWhatsapp(dto.whatsapp());
        entity.setEmail(dto.email());
        entity.setCep(dto.cep());
        entity.setEndereco(dto.endereco());

        entity.setResidencialId(dto.residencialId());
        entity.setResidencialNome(dto.residencialNome());

    }

    private static final Logger logger = Logger.getLogger(SindicoService.class.getName());

}

