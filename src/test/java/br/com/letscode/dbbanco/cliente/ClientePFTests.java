package br.com.letscode.dbbanco.cliente;

import br.com.letscode.dbbanco.entities.cliente.Cliente;
import br.com.letscode.dbbanco.entities.cliente.ClientePF;
import br.com.letscode.dbbanco.exception.ClienteJaCadastradoException;
import br.com.letscode.dbbanco.repository.ClientePFRepository;
import br.com.letscode.dbbanco.repository.ClientePJRepository;
import br.com.letscode.dbbanco.repository.ClienteRepository;
import br.com.letscode.dbbanco.service.ClienteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientePFTests {

    @InjectMocks
    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClientePJRepository clientePJRepository;
    @Mock
    private ClientePFRepository clientePFRepository;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp(){
        closeable = MockitoAnnotations.openMocks(this);
        clienteService = new ClienteService(clienteRepository, clientePFRepository, clientePJRepository);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    public void salvarClientePFTeste() {
        Cliente cliente = new Cliente("Teste2", "test1@gmail.com", "(11) 90099-0040");
        ClientePF clientePF = new ClientePF("123.123.123-23", LocalDate.of(1999, 12, 12), cliente);
        ClientePF clientePFRetorno = new ClientePF(0, "123.123.123-23", LocalDate.of(1999, 12, 12), cliente);

        Mockito.when(clientePFRepository.save(clientePF)).thenReturn(clientePFRetorno);
        Mockito.when(clientePFRepository.existsById(clientePF.getId())).thenReturn(false);
        Mockito.when(clienteRepository.existsById(clientePF.getCliente().getId())).thenReturn(true);

        clientePFRetorno = clienteService.salvarClientePF(clientePF);

        Assertions.assertNotNull(clientePFRetorno.getCliente());
        Assertions.assertEquals(0, clientePFRetorno.getId());
        Assertions.assertEquals("Teste2", clientePFRetorno.getCliente().getNome());
        Assertions.assertEquals("test1@gmail.com", clientePFRetorno.getCliente().getEmail());
        Assertions.assertEquals("(11) 90099-0040", clientePFRetorno.getCliente().getTelefone());
        Assertions.assertEquals("123.123.123-23", clientePFRetorno.getCPF());
        Assertions.assertEquals(LocalDate.of(1999, 12, 12), clientePFRetorno.getDataNascimento());
    }
}
