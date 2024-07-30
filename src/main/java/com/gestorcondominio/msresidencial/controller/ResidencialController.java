package com.gestorcondominio.msresidencial.controller;

import com.gestorcondominio.msresidencial.client.SindicoClient;
import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
import com.gestorcondominio.msresidencial.dto.SindicoDTO;
import com.gestorcondominio.msresidencial.service.ResidencialLazerService;
import com.gestorcondominio.msresidencial.service.ResidencialService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.logging.Logger;

@RestController
@RequestMapping("/residencial")
public class ResidencialController {

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

    private static final Logger logger = Logger.getLogger(ResidencialService.class.getName());
}
