package com.gestorcondominio.msresidencial.controller;

import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
import com.gestorcondominio.msresidencial.dto.ResidencialLazerDTO;
import com.gestorcondominio.msresidencial.service.ResidencialService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/residencial")
public class ResidencialController {

    private final ResidencialService residencialService;

    public ResidencialController(ResidencialService residencialService) {
        this.residencialService = residencialService;
    }

//    @PostMapping("/save")
//    public ResponseEntity<ResidencialDTO> saveResidencial(@Valid @RequestBody ResidencialDTO residencialDTO){
//        var residencialSaved = residencialService.saveResidencial(residencialDTO);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(residencialSaved.id()).toUri();
//        return ResponseEntity.created(uri).body(residencialSaved);
//    }

    @PostMapping("/save")
    public ResponseEntity<ResidencialDTO> saveResidencial(@Valid @RequestBody ResidencialLazerDTO residencialLazerDTO) {
        var residencialSaved = residencialService.saveResidencial(residencialLazerDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(residencialSaved.id()).toUri();
        return ResponseEntity.created(uri).body(residencialSaved);
    }

//    @PostMapping("/save")
//    public ResponseEntity<ResidencialDTO> saveResidencial(@Valid @RequestBody ResidencialLazerDTO residencialLazerDTO){
//        var residencialSaved = residencialService.saveResidencial(residencialLazerDTO);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(residencialSaved.id()).toUri();
//        return ResponseEntity.created(uri).body(residencialSaved);
//    }


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
    public ResponseEntity<ResidencialDTO> findById(@PathVariable Long id){
        var residencial = residencialService.findById(id);
        return ResponseEntity.ok(residencial);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResidencialDTO> residencialUpdate(
            @Valid @PathVariable Long id,
            @RequestBody ResidencialDTO residencialDTO)
    {
        var residencialUpdated = residencialService.updateResidencial(id, residencialDTO);
        return ResponseEntity.ok(residencialUpdated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteResidencial(@PathVariable Long id){
        residencialService.deleteResidencialById(id);
        return ResponseEntity.noContent().build();
    }
}
