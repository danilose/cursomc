package com.example.cursomc.services;

import com.example.cursomc.domain.ItemPedido;
import com.example.cursomc.domain.PagamentoComBoleto;
import com.example.cursomc.domain.Pedido;
import com.example.cursomc.domain.enums.EstadoPagamento;
import com.example.cursomc.repositories.ItemPedidoRepository;
import com.example.cursomc.repositories.PagamentoRepository;
import com.example.cursomc.repositories.PedidoRepository;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;
    @Autowired
    private BoletoService boletoService;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private ClienteService clienteService;

    public Pedido find(Integer id) {
        Optional<Pedido> pedido = repo.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
    
    public Pedido insert(Pedido obj) {
    	obj.setId(null);
    	obj.setInstante(new Date());
    	obj.setCliente(clienteService.find(obj.getCliente().getId()));
    	obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
    	obj.getPagamento().setPedido(obj);
    	if (obj.getPagamento() instanceof PagamentoComBoleto) {
    		PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
    		boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
    	}
    	repo.save(obj);
    	pagamentoRepository.save(obj.getPagamento());
    	for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
    	itemPedidoRepository.saveAll(obj.getItens());
    	System.out.println(obj);
    	return obj;
    }
    
}
