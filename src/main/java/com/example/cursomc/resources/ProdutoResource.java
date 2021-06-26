package com.example.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomc.domain.Produto;
import com.example.cursomc.dto.ProdutoDTO;
import com.example.cursomc.resources.utils.URL;
import com.example.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService service;

    //@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<Produto> find(@PathVariable Integer id) {

        Produto obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    //@RequestMapping(method = RequestMethod.GET, value = "/page")
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
    		@RequestParam(value = "nome", defaultValue = "") String nome, 
    		@RequestParam(value = "categorias", defaultValue = "") String categorias, 
    		@RequestParam(value = "page", defaultValue = "0") Integer page, 
    		@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
    		@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
    		@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
    	String nomeDecoded = URL.decodeParam(nome);
    	List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> lista = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listaDto = lista.map(obj -> new ProdutoDTO(obj));
        return ResponseEntity.ok().body(listaDto);
    }

}
