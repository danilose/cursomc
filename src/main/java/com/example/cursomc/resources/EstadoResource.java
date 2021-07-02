package com.example.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomc.domain.Cidade;
import com.example.cursomc.domain.Estado;
import com.example.cursomc.dto.CidadeDTO;
import com.example.cursomc.dto.EstadoDTO;
import com.example.cursomc.services.CidadeService;
import com.example.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    EstadoService service;
    @Autowired
    CidadeService cidadeService;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> lista = service.findAll();
        List<EstadoDTO> listaDto = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDto);
    }
    
    //@RequestMapping(method = RequestMethod.GET, value = "/{id}/cidades")
    @GetMapping("/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> lista = cidadeService.find(estadoId);
        List<CidadeDTO> listaDto = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDto);
    }
}
