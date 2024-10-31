package com.gestorcondominio.msunidades.controller;

import com.gestorcondominio.msunidades.dto.DependenteDTO;
import com.gestorcondominio.msunidades.dto.UnidadeDTO;
import com.gestorcondominio.msunidades.dto.relacionamentos.DependenteUnidadeDTO;
import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.service.DependenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/dependentes")
public class DependenteController {

    private final DependenteService dependenteService;

    public DependenteController(DependenteService dependenteService) {
        this.dependenteService = dependenteService;
    }

    @PostMapping("/save")
    public ResponseEntity<DependenteUnidadeDTO> saveDependente(@Valid @RequestBody DependenteUnidadeDTO dto) {
        var dependenteSaved = dependenteService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dependenteSaved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dependenteSaved);
    }

    @GetMapping("/findall")
    public ResponseEntity<Page<DependenteDTO>> findAll(
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        var dependentes = dependenteService.findAll(pageRequest);
        return ResponseEntity.ok(dependentes);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<DependenteDTO> findById(@PathVariable Long id){
        var dependente = dependenteService.findById(id);
        return ResponseEntity.ok(dependente);
    }

    @GetMapping("/findbyunidadeendereco/{unidadeEndereco}")
    public ResponseEntity<List<DependenteResumoDTO>> findDependentesByUnidadeEndereco(@PathVariable String unidadeEndereco){
        var dependente = dependenteService.findDependentesByUnidadeEndereco(unidadeEndereco);
        return ResponseEntity.ok(dependente);
    }

    @GetMapping("/findbynome/{nomeDependente}")
    public ResponseEntity<List<DependenteResumoDTO>> findDependentesByNome(@PathVariable("nomeDependente") String nomeDependente){
        var dependentes = dependenteService.findDependentesByNome(nomeDependente);
        return ResponseEntity.ok(dependentes);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DependenteResumoDTO> updateDependente(
            @Valid @PathVariable Long id,
            @RequestBody DependenteResumoDTO dto
    ){
        var dependenteUpdated = dependenteService.updateDependente(id, dto);
        return ResponseEntity.ok(dependenteUpdated);
    }

    // Consulta por faixa de idade
    @GetMapping("/findbyidade")
    public List<DependenteResumoDTO> findDependentesByIdade(
            @RequestParam(required = false) Integer entre,
            @RequestParam(required = false) Integer a
    ) {
        if (entre != null && a != null) {
            return dependenteService.findDependentesByFaixaEtaria(entre, a);
        } else if (entre != null) {
            return dependenteService.findDependentesByIdadeExata(entre);
        } else {
            throw new IllegalArgumentException("Parâmetros de idade inválidos");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDependete(@PathVariable Long id){
        dependenteService.deleteDependente(id);
        return ResponseEntity.noContent().build();
    }

}
