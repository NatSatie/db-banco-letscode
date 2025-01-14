package br.com.letscode.dbbanco.controller;


import br.com.letscode.dbbanco.entities.Utils;
import br.com.letscode.dbbanco.entities.cliente.Cliente;
import br.com.letscode.dbbanco.entities.conta.Conta;
import br.com.letscode.dbbanco.entities.conta.ContaFactory;
import br.com.letscode.dbbanco.entities.conta.TipoConta;
import br.com.letscode.dbbanco.exception.ClienteDuplicadoException;
import br.com.letscode.dbbanco.exception.ClienteNaoEncontradoException;
import br.com.letscode.dbbanco.exception.ContaExistenteException;
import br.com.letscode.dbbanco.exception.ContaNaoEncontradoException;
import br.com.letscode.dbbanco.repository.ContaRepository;

import br.com.letscode.dbbanco.service.ClienteService;
import br.com.letscode.dbbanco.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/contas")
@Slf4j
public class ContaController {
    private final ContaService contaService;
    private final ClienteService clienteService;

    public ContaController(ContaService contaService, ClienteService clienteService){
        this.contaService = contaService;
        this.clienteService = clienteService;
    }

    @PostMapping("novo")
    public ResponseEntity<String> criarConta(@Valid @RequestBody Conta conta) {
        try {
            this.contaService.criarConta(conta);
            return new ResponseEntity<>("Conta cadastrada com sucesso", HttpStatus.CREATED);
        } catch (ClienteNaoEncontradoException e) {
            return new ResponseEntity<>("Cliente base de ID " + conta.getCliente().getId() + " não encontrado", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("cliente-{id}")
    public ResponseEntity<List<Conta>> selecionarContaPorCliente(@PathVariable("id") Integer clienteID) {
        List<Conta> contas = this.contaService.selecionaContasPorClienteID(clienteID);
        return new ResponseEntity<>(contas, HttpStatus.OK);
    }

    @GetMapping("{conta}")
    public ResponseEntity selecionarContaByNumeroConta(@PathVariable("conta") Integer numeroConta){
        Conta conta = this.contaService.selecionaContaByNumeroConta(numeroConta);
        ResponseEntity response = new ResponseEntity(conta, HttpStatus.OK);
        return response;
    }

    @ExceptionHandler
    public ResponseEntity tratarContaNaoEncontrado(ContaNaoEncontradoException e) {
        ResponseEntity response = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }
    @ExceptionHandler
    public ResponseEntity tratarContaExistente(ContaExistenteException e) {
        ResponseEntity response = new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        return response;
    }
    @GetMapping("listatodascontas")
    public ResponseEntity listarTodasContas(){
        List<Conta> listaconta = this.contaService.listarTodasContas();
        ResponseEntity response = new ResponseEntity(listaconta, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("deleteconta-{numeroConta}")
    public ResponseEntity deleteConta(@PathVariable("numeroConta") Integer numeroConta){
        this.contaService.deletarConta(numeroConta);
        return ResponseEntity.ok("Conta deletada com sucesso.");
    }

    @PutMapping("alterarsenha-{senha}")
    public ResponseEntity alterarSenha(@PathVariable("senha") Integer senha, @RequestBody Integer numeroConta ){
        this.contaService.alterarSenha(senha, numeroConta);
        ResponseEntity response = new ResponseEntity("Senha atualizada com sucesso", HttpStatus.OK);
        return response;
    }
}
