package com.gestorcondominio.msresidencial.controller;

import com.gestorcondominio.msresidencial.dto.LazerDTO;
import com.gestorcondominio.msresidencial.service.LazerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/lazer")
public class LazerController {

    private final LazerService lazerService;

    public LazerController(LazerService lazerService) {
        this.lazerService = lazerService;
    }

    @PostMapping("/save")
    public ResponseEntity<LazerDTO> saveLazer(
            @Valid
            @RequestBody LazerDTO lazerDTO)
    {
        var lazerSaved = lazerService.saveLazer(lazerDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(lazerDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(lazerSaved);
    }

    @GetMapping("/findall")
    public ResponseEntity<Page<LazerDTO>> findAll(
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size);
        var lazeres = lazerService.findAll(pageRequest);
        return ResponseEntity.ok(lazeres);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<LazerDTO> findById(@PathVariable Long id){
        var lazer = lazerService.findById(id);
        return ResponseEntity.ok(lazer);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<LazerDTO> lazerUpdate(
//            @Valid @PathVariable Long id,
//            @RequestBody LazerDTO lazerDTO)
//    {
//        var lazerUpdated = lazerService.updateLazer(id, lazerDTO);
//        return ResponseEntity.ok(lazerUpdated);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLazer(@PathVariable Long id){
        lazerService.deleteLazerById(id);
        return ResponseEntity.noContent().build();
    }

}
