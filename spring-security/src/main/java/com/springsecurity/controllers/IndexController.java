package com.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/seguro")
   public String indexSecured(){
       return "Endpoint asegurado";
   }

   @GetMapping("/inseguro")
    public String indexNotSEcured(){
        return "Endpoint no asegurado";
   }

   @GetMapping("/bienvenida")
    public String bienvenida(){
        return "Hola";
   }
}
