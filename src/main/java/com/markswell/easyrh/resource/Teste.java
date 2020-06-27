package com.markswell.easyrh.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class Teste {

    @GetMapping
    public String getHello(){
        return "Deu bom!";
    }
}
