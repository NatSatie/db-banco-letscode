package br.com.letscode.dbbanco.conta;

import br.com.letscode.dbbanco.entities.cliente.Cliente;
import br.com.letscode.dbbanco.entities.cliente.ClientePF;
import br.com.letscode.dbbanco.entities.cliente.TipoCliente;
import br.com.letscode.dbbanco.entities.conta.Conta;
import br.com.letscode.dbbanco.entities.conta.TipoConta;
import br.com.letscode.dbbanco.repository.ClienteRepository;
import br.com.letscode.dbbanco.repository.ContaRepository;
import br.com.letscode.dbbanco.service.ClienteService;
import br.com.letscode.dbbanco.service.ContaService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaServiceTests {
    @InjectMocks
    ContaService contaService;

    @Mock
    ContaRepository contaRepository;

    @Mock
    ClienteRepository clienteRepository;

    @Test
    public void criarContaTeste() {
        Cliente cliente = new Cliente("Teste4", "test4@gmail.com", "(11) 90099-0040");
        Conta conta = new Conta(cliente, TipoConta.CONTA_POUPANCA, 101, 123456, TipoCliente.PESSOA_JURIDICA);
        Conta contaResultado = new Conta(1234, cliente, TipoConta.CONTA_POUPANCA, 101, 123456, TipoCliente.PESSOA_JURIDICA);

        Mockito.when(clienteRepository.existsById(cliente.getId())).thenReturn(true);
        Mockito.when(contaRepository.save(conta)).thenReturn(contaResultado);

        contaResultado = contaService.criarConta(conta);

        Assertions.assertNotNull(contaResultado.getNumeroConta());
        Assertions.assertEquals("Teste4", contaResultado.getCliente().getNome());
        Assertions.assertEquals("test4@gmail.com", contaResultado.getCliente().getEmail());
        Assertions.assertEquals("(11) 90099-0040", contaResultado.getCliente().getTelefone());
        Assertions.assertEquals(101, contaResultado.getAgencia());
        Assertions.assertEquals(123456, contaResultado.getSenha());
        Assertions.assertEquals(TipoCliente.PESSOA_JURIDICA, contaResultado.getTipoCliente());
    }

    @Test
    public void listarTodasContasTeste() {
        List<Conta> contasList = new ArrayList<>();
        contasList.add(new Conta());
        contasList.add(new Conta());
        contasList.add(new Conta());

        Mockito.when(contaRepository.findAll()).thenReturn(contasList);

        List<Conta> contas = contaService.listarTodasContas();

        Assertions.assertNotNull(contas);
        Assertions.assertFalse(contas.isEmpty());
        Assertions.assertEquals(3, contas.size());
    }
}
