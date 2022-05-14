package br.com.letscode.dbbanco.conta;

import br.com.letscode.dbbanco.entities.cliente.Cliente;
import br.com.letscode.dbbanco.entities.cliente.TipoCliente;
import br.com.letscode.dbbanco.entities.conta.Conta;
import br.com.letscode.dbbanco.entities.conta.ContaFactory;
import br.com.letscode.dbbanco.entities.conta.TipoConta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ContaFactoryTests {

    @Test
    public void contaFactoryTeste(){
        Cliente cliente = new Cliente("Teste", "test@gmail.com", "(11) 90099-0040");
        Conta contaPoupanca = new Conta(cliente, TipoConta.CONTA_POUPANCA, 101, 123456, TipoCliente.PESSOA_JURIDICA);
        Conta contaCorrente = new Conta(cliente, TipoConta.CONTA_CORRENTE, 101, 123456, TipoCliente.PESSOA_JURIDICA);
        Conta contaInvestimento = new Conta(cliente, TipoConta.CONTA_INVESTIMENTO, 101, 123456, TipoCliente.PESSOA_JURIDICA);
        BigDecimal value = BigDecimal.valueOf(100);
        BigDecimal valueCorrente = value.multiply(BigDecimal.valueOf(1.005));
        BigDecimal valueInvestimento = value.multiply(BigDecimal.valueOf(1.02));

        Assertions.assertEquals(ContaFactory.valorTipoConta(contaPoupanca, value), value);
        Assertions.assertEquals(ContaFactory.valorTipoConta(contaCorrente, value), valueCorrente);
        Assertions.assertEquals(ContaFactory.valorTipoConta(contaInvestimento, value), valueInvestimento);
    }


}
