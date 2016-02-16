package org.extenso;

import static org.junit.Assert.*;
import java.math.BigDecimal;

import org.junit.Test;

public class NumeroPorExtensoTest {

    @Test
    public void representacaoUnidade() {
        assertEquals("um", NumeroPorExtenso.get(1));
        assertEquals("nove", NumeroPorExtenso.get(9));
    } 
    
    @Test
    public void representacaoDezena() {
        assertEquals("dez", NumeroPorExtenso.get(10));
        assertEquals("noventa", NumeroPorExtenso.get(90));
    }
    
    @Test
    public void representacaoCentena() {
        assertEquals("cem", NumeroPorExtenso.get(100));
        assertEquals("novecentos", NumeroPorExtenso.get(900));
    }

    @Test
    public void conectorCasas_dezenaMaisUnidade() {
        assertEquals("vinte e dois", NumeroPorExtenso.get(22));
    }
    
    @Test
    public void conectorCasas_centenaMaisDezena() {
        assertEquals("duzentos e vinte", NumeroPorExtenso.get(220));
    }
    
    @Test
    public void conectorCasas_centenaMaisDezenaMaisUnidade() {
        assertEquals("duzentos e vinte e dois", NumeroPorExtenso.get(222));
    }
    
    @Test
    public void representacaoSegundaClasse() {
        assertEquals("dois mil", NumeroPorExtenso.get(2000));
    }
    
    @Test
    public void representacaoTerceiraClasse() {
        assertEquals("um milhão", NumeroPorExtenso.get(1000000));
    }
    
    @Test
    public void representacaoQuartaClasse() {
        assertEquals("um bilhão", NumeroPorExtenso.get(1000000000));
    }
    
    @Test
    public void representacaoQuintaClasse() {
        assertEquals("um trilhão", NumeroPorExtenso.get(1000000000000l));
    }
    
    @Test
    public void representacaoSextaClasse() {
        assertEquals("um quatrilhão", NumeroPorExtenso.get(1000000000000000l));
    }
    
    @Test
    public void conectorClasses() {
        assertEquals("um milhão, duzentos e trinta e quatro mil, quinhentos e sessenta e sete", 
            NumeroPorExtenso.get(1234567));
    }
    
    @Test
    public void onzeADezenoveNaoTemConector() {
        assertEquals("onze", NumeroPorExtenso.get(11));
        assertEquals("dezenove", NumeroPorExtenso.get(19));
    }
    
    @Test
    public void cem_cento() {
        assertEquals("cem", NumeroPorExtenso.get(100));
        assertEquals("cento e dez", NumeroPorExtenso.get(110));
        assertEquals("cento e um", NumeroPorExtenso.get(101));
    }
    
    @Test
    public void unidadeSegundaClasseComRepresentacao() {
        assertEquals("dois mil", NumeroPorExtenso.get(2000));
    }
    
    @Test
    public void unidadeSegundaClasseSemRepresentacao() {
        assertEquals("mil", NumeroPorExtenso.get(1000));
    }
    
    @Test
    public void classeZeradaNaoTemRepresentacao() {
        assertEquals("um trilhão, cento e vinte e um", NumeroPorExtenso.get(1000000000121l));
    }
    
    @Test
    public void segundaClasseNaoFlexionaNumero() {
        assertEquals("mil", NumeroPorExtenso.get(1000));
        assertEquals("dois mil", NumeroPorExtenso.get(2000));
    }
    
    @Test
    public void acimaDaSegundaClasseFlexionaNumero() {
        assertEquals("um milhão", NumeroPorExtenso.get(1000000));
        assertEquals("dois milhões", NumeroPorExtenso.get(2000000));
        
        assertEquals("um bilhão", NumeroPorExtenso.get(1000000000));
        assertEquals("dois bilhões", NumeroPorExtenso.get(2000000000));
        
        assertEquals("um trilhão", NumeroPorExtenso.get(1000000000000l));
        assertEquals("dois trilhões", NumeroPorExtenso.get(2000000000000l));
    }
    
    @Test
    public void ultimaClasseComValorConectaComESeCentenaEhZero() {
        assertEquals("mil e dez", NumeroPorExtenso.get(1010));
        assertEquals("um milhão e dez mil", NumeroPorExtenso.get(1010000));
    }
    
    @Test
    public void ultimaClasseComValorConectaComESeCentenaEhRazos() {
        assertEquals("mil e cem", NumeroPorExtenso.get(1100));
        assertEquals("um milhão e cem mil", NumeroPorExtenso.get(1100000));
    }
    
    @Test
    public void qualificador() {
        
        assertEquals("um dia", NumeroPorExtenso.get(1, "dia", "dias"));
        assertEquals("dois dias", NumeroPorExtenso.get(2, "dia", "dias"));
    }
    
    @Test
    public void decimalDuasCasas() {
        
        assertEquals("um centavo", 
            NumeroPorExtenso.get(new BigDecimal("0.01"), "real", "reais", "centavo", "centavos"));
        
        assertEquals("dez centavos", 
            NumeroPorExtenso.get(new BigDecimal("0.10"), "real", "reais", "centavo", "centavos"));
    }
    
    @Test
    public void decimalTresCasas() {
        
        assertEquals("um grama", 
            NumeroPorExtenso.get(new BigDecimal("0.001"), "quilo", "quilos", "grama", "gramas"));
        
        assertEquals("cem gramas", 
            NumeroPorExtenso.get(new BigDecimal("0.100"), "quilo", "quilos", "grama", "gramas"));
    }
    
    @Test
    public void decimalZerado() {

        assertEquals("um real", 
            NumeroPorExtenso.get(new BigDecimal("1.00"), "real", "reais", "centavo", "centavos"));
    }
    
    @Test
    public void inteiroMaisDecimal() {
        
        assertEquals("um real e um centavo", 
            NumeroPorExtenso.get(new BigDecimal("1.01"), "real", "reais", "centavo", "centavos"));
        
        assertEquals("dois reais e vinte centavos", 
            NumeroPorExtenso.get(new BigDecimal("2.20"), "real", "reais", "centavo", "centavos"));
    }
}
