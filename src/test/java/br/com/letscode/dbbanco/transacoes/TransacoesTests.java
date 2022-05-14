package br.com.letscode.dbbanco.transacoes;

import br.com.letscode.dbbanco.entities.cliente.Cliente;
import br.com.letscode.dbbanco.entities.cliente.TipoCliente;
import br.com.letscode.dbbanco.entities.conta.Conta;
import br.com.letscode.dbbanco.entities.conta.TipoConta;
import br.com.letscode.dbbanco.repository.ClienteRepository;
import br.com.letscode.dbbanco.repository.ContaRepository;
import br.com.letscode.dbbanco.service.ClienteService;
import br.com.letscode.dbbanco.service.ContaService;
import br.com.letscode.dbbanco.service.TransacaoService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TransacoesTests {
    @InjectMocks
    private TransacaoService transacaoService;
    @InjectMocks
    private ContaService contaService;
    @Mock
    private ContaRepository contaRepository;

    /*@Test
    public void saqueTeste() throws Exception {
        Cliente cliente = new Cliente("Teste4", "test4@gmail.com", "(11) 90099-0040");
        Conta contaPoupanca = new Conta(cliente, TipoConta.CONTA_POUPANCA, 101, 123456, TipoCliente.PESSOA_JURIDICA);

        contaService.criarConta(contaPoupanca);
        Mockito.when(transacaoService.depositar(0, BigDecimal.valueOf(10000)))
                .then(System.out.println("Transacao realizada"));
    }*/
}
