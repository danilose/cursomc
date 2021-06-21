package com.example.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomc.domain.Pedido;
import com.example.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    PedidoService pedidoService;

    //@RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @GetMapping
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> listar(@PathVariable Integer id) {

        Pedido pedido = pedidoService.buscar(id);

        return ResponseEntity.ok().body(pedido);
    }
}
