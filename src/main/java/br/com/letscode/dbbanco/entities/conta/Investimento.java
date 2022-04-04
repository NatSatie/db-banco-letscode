package br.com.letscode.dbbanco.entities.conta;

import br.com.letscode.dbbanco.entities.cliente.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@NoArgsConstructor
@Setter
@Getter

@Entity
@PrimaryKeyJoinColumn(name = "conta_id")
public class Investimento extends ContaDefault {
    public Investimento(Cliente cliente, String agencia, String senha) {
        super(cliente, TipoConta.CONTA_INVESTIMENTO, agencia, senha);
    }


}
