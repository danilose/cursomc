package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cursomc.domain.Cidade;
import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.Endereco;
import com.example.cursomc.domain.enums.Perfil;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.dto.ClienteNewDTO;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.repositories.EnderecoRepository;
import com.example.cursomc.security.UserSS;
import com.example.cursomc.services.exceptions.AuthorizationException;
import com.example.cursomc.services.exceptions.DataIntegrityException;
import com.example.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
    @Autowired
    private ClienteRepository repo;
    @Autowired
    private EnderecoRepository enderecoRepo;

    public Cliente find(Integer id) {
    	UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
        Optional<Cliente> cliente = repo.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
    	obj.setId(null); // remove o id se tiver e isso "força" a criar um novo registro
    	obj = repo.save(obj);
    	enderecoRepo.saveAll(obj.getEnderecos());
    	return obj;
    }
    
    public Cliente update(Cliente obj) {
    	Cliente newObj = find(obj.getId());
    	updateData(newObj, obj);
    	return repo.save(newObj);
    }
    
    public void delete(Integer id) {
    	find(id);
    	try {
    		repo.deleteById(id);
    	}
    	catch(DataIntegrityViolationException e) {
    		throw new DataIntegrityException("Não é possível excluir pois há pedidos relacionados");
    	}
    }
    
    public List<Cliente> findAll() {
    	return repo.findAll();
    }
    
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	return repo.findAll(pageRequest);
    }
    
    public Cliente fromDTO(ClienteDTO objDto) {
    	return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }
    
    public Cliente fromDTO(ClienteNewDTO objDto) {
    	Cliente cliente = new Cliente(
    			null, 
    			objDto.getNome(), 
    			objDto.getEmail(), 
    			objDto.getCpfOuCnpj(), 
    			TipoCliente.toEnum(objDto.getTipo()),
    			pe.encode(objDto.getSenha())
    			);
    	
    	cliente.getTelefones().add(objDto.getTelefone1());
    	
    	if (objDto.getTelefone2() != null) {
    		cliente.getTelefones().add(objDto.getTelefone2());
		}
    	if (objDto.getTelefone3() != null) {
    		cliente.getTelefones().add(objDto.getTelefone3());
		}
    	
    	Cidade cidade = new Cidade(
    			objDto.getCidadeId(), 
    			null, 
    			null
    			);
    	
    	Endereco endereco = new Endereco(
    			null, 
    			objDto.getLogradouro(), 
    			objDto.getNumero(), 
    			objDto.getComplemento(), 
    			objDto.getBairro(), 
    			objDto.getCep(), 
    			cliente, 
    			cidade
    			);
    	
    	cliente.getEnderecos().add(endereco);
    	
    	return cliente; 
    }
    
    private void updateData(Cliente newObj, Cliente obj) {
    	newObj.setNome(obj.getNome());
    	newObj.setEmail(obj.getEmail());
    }

}
