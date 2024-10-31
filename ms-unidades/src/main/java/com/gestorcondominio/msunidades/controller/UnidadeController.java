package com.gestorcondominio.msunidades.controller;

import com.gestorcondominio.msunidades.dto.UnidadeDTO;
import com.gestorcondominio.msunidades.dto.relacionamentos.UnidadeDependenteDTO;
import com.gestorcondominio.msunidades.dto.resumos.DependenteResumoDTO;
import com.gestorcondominio.msunidades.dto.resumos.UnidadeResumoDTO;
import com.gestorcondominio.msunidades.entity.Unidade;
import com.gestorcondominio.msunidades.entity.enums.CondicaoDoResidente;
import com.gestorcondominio.msunidades.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @PostMapping("/save")
    public ResponseEntity<UnidadeDTO> saveUnidade(@Valid @RequestBody UnidadeDTO unidadeDTO){
        var unidadeSaved = unidadeService.saveUnidade(unidadeDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(unidadeSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(unidadeSaved);
    }

    @GetMapping("/findall")
    //@Transactional(readOnly = true)
    public ResponseEntity<Page<UnidadeDTO>> findAll(
            @RequestParam (value = "page", defaultValue = "0") int page,
            @RequestParam (value = "size", defaultValue = "10") int size
            ){
        PageRequest pageRequest = PageRequest.of(page, size);
        var unidades = unidadeService.findAll(pageRequest);
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<UnidadeDependenteDTO> findById(@PathVariable Long id){
        var unidade = unidadeService.finById(id);
        return  ResponseEntity.ok(unidade);
    }

    @GetMapping("/findbyunidadeendereco/{unidadeEndereco}")
    public ResponseEntity<UnidadeDependenteDTO> findUnidadeByUnidadeEndereco(@PathVariable String unidadeEndereco){
        var unidade = unidadeService.findUnidadeByUnidadeEndereco(unidadeEndereco);
        return ResponseEntity.ok(unidade);
    }

    @GetMapping("/findbynome/{nomeResidente}")
    public ResponseEntity<List<UnidadeResumoDTO>> findDependentesByNome(@PathVariable("nomeResidente") String nomeResidente){
        var dependentes = unidadeService.findUnidadesByNome(nomeResidente);
        return ResponseEntity.ok(dependentes);
    }

    @GetMapping("/condicaoresidente/listar")
    public List<Unidade> getUnidadesByResponsavelStatus(@RequestParam("status") CondicaoDoResidente status) {
        return unidadeService.getUnidadesByCondicaoDoResidente(status);
    }

    @GetMapping("/condicaoresidente/quantidade")
    public Map<String, Long> getQuantidadePorCondicaoDoResidente() {
        Map<String, Long> quantidadePorCondicao = new HashMap<>();
        quantidadePorCondicao.put("Inquilinos", unidadeService.countInquilinos());
        quantidadePorCondicao.put("Proprietarios", unidadeService.countProprietarios());
        return quantidadePorCondicao;
    }


//    @GetMapping("/findbyid-2")
//    public ResponseEntity<UnidadeDependenteDTO> getUnidadeWithDependentes(@RequestParam("unidadeId") Long unidadeId) {
//        UnidadeDependenteDTO unidadeDependenteDTO = unidadeService.findUnidadeWithDependentesById(unidadeId);
//        return ResponseEntity.ok(unidadeDependenteDTO);
//    }

    @GetMapping("/findbyid-2")
    public ResponseEntity<UnidadeDTO> getUnidadeWithDependentes(@RequestParam("unidadeId") Long unidadeId) {
        UnidadeDTO unidadeDTO = unidadeService.findUnidadeWithDependentesById(unidadeId);
        return ResponseEntity.ok(unidadeDTO);
    }


//    @GetMapping("/by-sindico-id")
//    public List<ResidencialResponseDTO> getResidenciaisBySindicoId(@RequestParam("sindicoId") Long sindicoId) {
//        return residencialService.findResidenciaisBySindicoId(sindicoId);
//    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UnidadeDTO> updateUnidade(
            @Valid @PathVariable Long id,
            @RequestBody UnidadeDTO unidadeDTO)
    {
        var unidadeUpdated = unidadeService.updateUnidade(id, unidadeDTO);
        return ResponseEntity.ok(unidadeUpdated);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUnidade(@PathVariable Long id){
        unidadeService.deleteUnidade(id);
        return ResponseEntity.noContent().build();
    }


}
