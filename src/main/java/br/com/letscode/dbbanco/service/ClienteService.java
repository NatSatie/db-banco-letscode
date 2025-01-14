package br.com.letscode.dbbanco.service;

import br.com.letscode.dbbanco.entities.cliente.Cliente;
import br.com.letscode.dbbanco.entities.cliente.ClientePF;
import br.com.letscode.dbbanco.entities.cliente.ClientePJ;
import br.com.letscode.dbbanco.entities.conta.Conta;
import br.com.letscode.dbbanco.exception.ClienteJaCadastradoException;
import br.com.letscode.dbbanco.exception.ClienteDuplicadoException;
import br.com.letscode.dbbanco.exception.ClienteDuplicadoException;
import br.com.letscode.dbbanco.exception.ClienteNaoEncontradoException;
import br.com.letscode.dbbanco.repository.ClientePFRepository;
import br.com.letscode.dbbanco.repository.ClientePJRepository;
import br.com.letscode.dbbanco.repository.ClienteRepository;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.cert.Extension;

import java.util.List;

@Service
public class ClienteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);
    @Autowired
    private final ClienteRepository clienteRepository;
    @Autowired
    private final ClientePFRepository clientePFRepository;
    @Autowired
    private final ClientePJRepository clientePJRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          ClientePFRepository clientePFRepository, ClientePJRepository clientePJRepository) {
        this.clienteRepository = clienteRepository;
        this.clientePFRepository = clientePFRepository;
        this.clientePJRepository = clientePJRepository;
    }

    public Cliente salvarCliente(Cliente cliente){
        if (this.clienteRepository.existsById(cliente.getId())) {
            LOGGER.warn("Cliente já Existe na Base de Dados");
            LOGGER.error("Não Foi possivel realizar a Requisição de novo Cliente");
            throw new ClienteDuplicadoException();
        } else {
            LOGGER.info("Requisição de Novo Cliente Aceita");
            return clienteRepository.save(cliente);
        }
    }

    public ClientePF salvarClientePF(ClientePF clientePF){
        if (this.clientePFRepository.existsById(clientePF.getId())) {
            LOGGER.error("Cliente Pessoa Física já cadastrado");
            throw new ClienteJaCadastradoException();
        }
        if (!this.clienteRepository.existsById(clientePF.getCliente().getId())) {
            LOGGER.error("Cliente base não encontrado");
            throw new ClienteNaoEncontradoException();
        }

        LOGGER.info("Requisição de Novo Cliente Pessoa Física Aceita");
        return this.clientePFRepository.save(clientePF);
    }

    public ClientePJ salvarClientePJ(ClientePJ clientePJ){
        if (this.clientePJRepository.existsById(clientePJ.getId())) {
            LOGGER.error("Cliente Pessoa Jurídica já cadastrado");
            throw new ClienteJaCadastradoException();
        }

        if (!this.clienteRepository.existsById(clientePJ.getCliente().getId())) {
            LOGGER.error("Cliente base não encontrado");
            throw new ClienteNaoEncontradoException();
        }

        LOGGER.info("Requisição de Novo Cliente Pessoa Jurídica Aceita");
        return this.clientePJRepository.save(clientePJ);
    }

    public boolean existsById(int id) {
        return this.clienteRepository.existsById(id);
    }

    public Cliente selecionaClienteById(Integer idCliente) {
        LOGGER.info("Procurando cliente de ID " + idCliente + ".");
        return this.clienteRepository.findById(idCliente)
                .orElseThrow(() -> {
                    LOGGER.error("Erro ao realizar requisição de Cliente");
                    return new ClienteNaoEncontradoException();
                });
    }

    public List<Cliente> listarTodosClientes() {
        return this.clienteRepository.findAll();
    }

    /*public Cliente createPF(String nome, String email, String telefone, String cpf, LocalDate data_nascimento) {
        var createCliente = new Cliente(nome, email, telefone);
        var cliente = clienteRepository.save(createCliente);

        var createClientePF = new ClientePF(cpf, data_nascimento, createCliente);
        clientePFRepository.save(createClientePF);
        System.out.println("Cadastrado de cliente realizado, siga para a criação de conta!\n");
        return cliente;
    }

    public Cliente createPJ(String nome, String email, String telefone, String cnpj, LocalDate data_abertura) {
        var createCliente = new Cliente(nome, email, telefone);
        var cliente = clienteRepository.save(createCliente);

        var createClientePJ = new ClientePJ(cnpj, data_abertura, createCliente);
        clientePJRepository.save(createClientePJ);
        System.out.println("Cadastrado de cliente realizado, siga para a criação de conta!\n");
        return cliente;
    }*/
}
