package com.gestorcondominio.msresidencial.client;

import com.gestorcondominio.msresidencial.dto.SindicoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-sindico", url = "http://localhost:8081/sindico")
public interface SindicoClient {
    @GetMapping("/findbyid/{id}")
    SindicoDTO getSindicoById(@PathVariable("id") Long id);
}
