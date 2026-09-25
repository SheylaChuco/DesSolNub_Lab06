package com.techcorp.securedocs.controller;

import com.techcorp.securedocs.model.Departamento;
import com.techcorp.securedocs.repository.DepartamentoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departamentos")

public class DepartamentoController {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoController(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    @GetMapping
    public List<Departamento> listar() {
        return departamentoRepository.findAll();
    }
}