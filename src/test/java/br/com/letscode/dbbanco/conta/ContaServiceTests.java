package br.com.letscode.dbbanco.conta;

import br.com.letscode.dbbanco.entities.cliente.Cliente;
import br.com.letscode.dbbanco.entities.cliente.ClientePF;
import br.com.letscode.dbbanco.entities.cliente.TipoCliente;
import br.com.letscode.dbbanco.entities.conta.Conta;
import br.com.letscode.dbbanco.entities.conta.TipoConta;
import br.com.letscode.dbbanco.service.ClienteService;
import br.com.letscode.dbbanco.service.ContaService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaServiceTests {
    @Autowired
    ContaService contaService;

    @Autowired
    ClienteService clienteService;

    @Test
    public void salvarCriarConta() {
        Cliente cliente = new Cliente("Teste4", "test4@gmail.com", "(11) 90099-0040");
        Conta contaPoupanca = new Conta(cliente, TipoConta.CONTA_POUPANCA, 101, 123456, TipoCliente.PESSOA_JURIDICA);

        clienteService.salvarCliente(cliente);
        Conta contaSalva = contaService.criarConta(contaPoupanca);

        Assertions.assertNotNull(contaSalva.getNumeroConta());
        Assertions.assertEquals(contaSalva.getCliente(), cliente);
    }
}
