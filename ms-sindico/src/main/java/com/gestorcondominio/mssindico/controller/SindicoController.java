package com.gestorcondominio.mssindico.controller;

import com.gestorcondominio.mssindico.client.ResidencialClient;
import com.gestorcondominio.mssindico.dto.ResidencialDTO;
import com.gestorcondominio.mssindico.dto.SindicoDTO;
import com.gestorcondominio.mssindico.service.SindicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/sindico")
public class SindicoController {

    private final SindicoService sindicoService;
    private final ResidencialClient residencialClient;

    public SindicoController(SindicoService sindicoService, ResidencialClient residencialClient) {
        this.sindicoService = sindicoService;
        this.residencialClient = residencialClient;
    }

    // TESTANDO COMUNICAÇÃO COM MS-RESIDENCIAL
    @GetMapping("/test-residencial/{id}")
    public ResponseEntity<ResidencialDTO> testResidencialClient(@PathVariable Long id) {
        ResidencialDTO residencial = residencialClient.getResidencialById(id);
        return ResponseEntity.ok(residencial);
    }

    @PostMapping("/save")
    public ResponseEntity<SindicoDTO> saveSindico(@Valid @RequestBody SindicoDTO sindicoDTO){
        var sindicoSaved = sindicoService.saveSindico(sindicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sindicoSaved.id()).toUri();
        return ResponseEntity.created(uri).body(sindicoSaved);
    }

    @GetMapping("/findall")
    public ResponseEntity<Page<SindicoDTO>> findAll(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size)
    {
        PageRequest pageRequest = PageRequest.of(page,size);
        var sindicos = sindicoService.findAll(pageRequest);
        return ResponseEntity.ok(sindicos);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<SindicoDTO> findById(@PathVariable Long id){
        System.out.println("Request received for id: " + id); // TESTE DE COMUNICAÇÃO FEIGN
        var sindicoDTO = sindicoService.findById(id);

        // Obter informações do residencial
//        ResidencialDTO residencial = residencialClient.getResidencialById( sindicoDTO.getResidencialId());
//        sindicoDTO.setResidencial(residencial);

        return ResponseEntity.ok(sindicoDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SindicoDTO> updateSindico(
            @Valid @PathVariable Long id,
            @RequestBody SindicoDTO sindicoDTO)
    {
        var sindicoUpdated = sindicoService.updateSindico(id,sindicoDTO);
        return ResponseEntity.ok(sindicoUpdated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSindico(@Valid @PathVariable Long id){
        sindicoService.deleteSindico(id);
        return ResponseEntity.noContent().build();
    }

}
