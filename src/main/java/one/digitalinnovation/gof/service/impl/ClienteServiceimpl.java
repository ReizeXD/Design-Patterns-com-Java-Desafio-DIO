package one.digitalinnovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

@Service
public class ClienteServiceimpl implements ClienteService {
   @Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService; 


    @Override
    public void atualizar(Long id, Cliente cliente) {
        // TODO Auto-generated method stub
        Optional<Cliente> clienteBD= clienteRepository.findById(id);
        if(clienteBD.isPresent()){
            salvarClienteComCep(cliente);
        }
        
    }

    @Override
    public Cliente buscarPorId(Long id) {
        // TODO Auto-generated method stub
        Optional<Cliente> cliente=clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public Iterable<Cliente> buscarTodos() {
        // TODO Auto-generated method stub
        return clienteRepository.findAll();
    }

    @Override
    public void deletar(Long id) {
        // TODO Auto-generated method stub
        clienteRepository.deleteById(id);
        
    }

    @Override
    public void inserir(Cliente cliente) {
        // TODO Auto-generated method stub
        salvarClienteComCep(cliente);
    }
    
    private void salvarClienteComCep(Cliente cliente){
        String cep = cliente.getEndereco().getCep();
        Endereco endereco= enderecoRepository.findById(cep).orElseGet(()->{
        Endereco novoEndereco = viaCepService.consultarCep(cep);
        enderecoRepository.save(novoEndereco);
        return novoEndereco;});
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
        }
}

