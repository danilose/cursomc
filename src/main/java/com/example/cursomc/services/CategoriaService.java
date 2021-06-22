package com.example.cursomc.services;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> categoria = repo.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
    
    public Categoria insert(Categoria obj) {
    	obj.setId(null); // remove o id se tiver e isso "força" a criar um novo registro
    	return repo.save(obj);
    }
    
    public Categoria update(Categoria obj) {
    	find(obj.getId());
    	return repo.save(obj);
    }
}
