package com.gestorcondominio.msresidencial.controller;

import com.gestorcondominio.msresidencial.dto.ResidencialDTO;
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

    public ResidencialController(ResidencialService residencialService, ResidencialLazerService residencialLazerService) {
        this.residencialService = residencialService;
        this.residencialLazerService = residencialLazerService;
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

//    @GetMapping("/findbyid/{id}")
//    public ResponseEntity<ResidencialDTO> findById(@PathVariable Long id){
//        var residencial = residencialService.findById(id);
//
//        // Agora vamos logar os detalhes dos Lazeres, se houver
//        if (residencial.getLazeres() != null && !residencial.getLazeres().isEmpty()) {
//            logger.info("Lazeres encontrados para o residencial " + residencial.getNome() + ":");
//            for (LazerDTO lazer : residencial.getLazeres()) {
//                logger.info("ID do Lazer: " + lazer.getId() + ", Descrição do Lazer: " + lazer.getDescricao());
//            }
//        } else {
//            logger.info("CONTROLLER: Nenhum Lazer encontrado para o residencial " + residencial.getNome() );
//        }
//
//        return ResponseEntity.ok(residencial);
//    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<ResidencialDTO> getResidencialWithLazer(@PathVariable Long id) {
        ResidencialDTO residencialDTO = residencialLazerService.findResidencialWithLazerById(id);
        if (residencialDTO == null) {
            return ResponseEntity.notFound().build();
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

    private static final Logger logger = Logger.getLogger(ResidencialService.class.getName());
}
