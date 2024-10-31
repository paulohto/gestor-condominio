package com.gestorcondominio.msresidencial.controller;

import com.gestorcondominio.msresidencial.client.SindicoClient;
import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
import com.gestorcondominio.msresidencial.dto.ResidencialResponseDTO;
import com.gestorcondominio.msresidencial.dto.SindicoDTO;
import com.gestorcondominio.msresidencial.service.ResidencialLazerService;
import com.gestorcondominio.msresidencial.service.ResidencialService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
//import java.util.logging.Logger;

@RestController
@RequestMapping("/residencial")
public class ResidencialController {
    private static final Logger logger = LoggerFactory.getLogger(ResidencialController.class);

    private final ResidencialService residencialService;
    private final ResidencialLazerService residencialLazerService;
    private final SindicoClient sindicoClient;

    public ResidencialController(ResidencialService residencialService, ResidencialLazerService residencialLazerService, SindicoClient sindicoClient) {
        this.residencialService = residencialService;
        this.residencialLazerService = residencialLazerService;
        this.sindicoClient = sindicoClient;
    }

    @PostMapping("/save")
    public ResponseEntity<ResidencialDTO> saveResidencial(@Valid @RequestBody ResidencialDTO residencialDTO){
        var residencialSaved = residencialService.saveResidencial(residencialDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(residencialSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(residencialSaved);
    }

    @GetMapping("/findall")
    public ResponseEntity<Page<ResidencialDTO>> findAll(
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size);
        var residenciais = residencialService.findAll(pageRequest);
        return ResponseEntity.ok(residenciais);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ResidencialDTO> getResidencialWithLazer(@PathVariable Long id) {
        ResidencialDTO residencialDTO = residencialLazerService.findResidencialWithLazerById(id);
        if (residencialDTO == null) {
            return ResponseEntity.notFound().build();
        }

        // Obter informações do sindico caso exista Sindico cadastrado no Residencial
        if(residencialDTO.getSindicoId() != null) {
            SindicoDTO sindicoDTO = sindicoClient.getSindicoById(residencialDTO.getSindicoId());
            residencialDTO.setSindico(sindicoDTO);
        }
        return ResponseEntity.ok(residencialDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResidencialDTO> updateResidencial(
            @PathVariable Long id,
            @Valid @RequestBody ResidencialDTO residencialDTO)
    {
        ResidencialDTO updatedResidencial = residencialService.updateResidencial(id, residencialDTO);
        return ResponseEntity.ok(updatedResidencial);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteResidencial(@PathVariable Long id) {
        residencialService.deleteResidencial(id);
        return ResponseEntity.noContent().build();
    }

    ///TESTANDO SINDICO
    @GetMapping("/test/{id}")
    public ResponseEntity<SindicoDTO> testSindicoClient(@PathVariable Long id) {
        System.out.println("Making request to sindico service for id: " + id);
        SindicoDTO sindico = sindicoClient.getSindicoById(id);
        return ResponseEntity.ok(sindico);
    }

    @GetMapping("/by-sindico-id")
    public List<ResidencialResponseDTO> getResidenciaisBySindicoId(@RequestParam("sindicoId") Long sindicoId) {
        return residencialService.findResidenciaisBySindicoId(sindicoId);
    }

    @GetMapping("/by-sindico-nome")
    public List<ResidencialResponseDTO> getResidenciaisBySindicoNome(@RequestParam("sindicoNome") String sindicoNome) {
        logger.info("Recebido pedido para buscar residenciais pelo nome do síndico: {}", sindicoNome);
        return residencialService.findResidenciaisBySindicoNome(sindicoNome);
    }


    //private static final Logger logger = Logger.getLogger(ResidencialService.class.getName());
}
