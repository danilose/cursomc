package com.example.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    CategoriaService categoriaService;

    //@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> listar(@PathVariable Integer id) {

        Categoria categoria = categoriaService.buscar(id);

        return ResponseEntity.ok().body(categoria);
    }
}
