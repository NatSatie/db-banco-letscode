package br.com.letscode.dbbanco.utils;

import br.com.letscode.dbbanco.entities.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UtilsTests {

    @InjectMocks
    Utils utils;

    @Test
    void formatarDataTeste() {
        String data = "10/10/2022";
        Assertions.assertEquals(utils.formatarData(data), LocalDate.of(2022, 10, 10));
    }

    @Test
    void formatarValorTeste() {
        BigDecimal valor = BigDecimal.valueOf(10000.654);
        Assertions.assertEquals(utils.FormatValor(valor), BigDecimal.valueOf(10000.65));
    }
}
