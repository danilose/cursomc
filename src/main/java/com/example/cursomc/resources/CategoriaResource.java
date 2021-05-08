package com.example.cursomc.resources;

import com.example.cursomc.domain.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @GetMapping //@RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar() {

        Categoria cat1 = new Categoria(1, "Informática");
        Categoria cat2 = new Categoria(1, "Escritório");

//        List<Categoria> lista = new ArrayList<>();
//        lista.add(cat1);
//        lista.add(cat2);

        List<Categoria> lista = List.of(cat1, cat2);

        return lista;
    }
}
