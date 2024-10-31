package com.gestorcondominio.mssindico.client;

import com.gestorcondominio.mssindico.dto.ResidencialDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-residencial", url = "http://localhost:8080/residencial")
public interface ResidencialClient {

    @GetMapping("/findbyid/{id}")
    ResidencialDTO getResidencialById(@PathVariable("id") Long id);

}

