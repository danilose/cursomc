package com.example.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Estado;
import com.example.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repo;

    public List<Estado> findAll() {
    	return repo.findAllByOrderByNome();
    }
    
}
