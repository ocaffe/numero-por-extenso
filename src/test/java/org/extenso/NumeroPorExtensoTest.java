package org.extenso;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumeroPorExtensoTest {

    @Test
    public void representacaoUnidade() {
        assertEquals("um", extenso(1));
        assertEquals("nove", extenso(9));
    } 
    
    @Test
    public void representacaoDezena() {
        assertEquals("dez", extenso(10));
        assertEquals("noventa", extenso(90));
    }
    
    @Test
    public void representacaoCentena() {
        assertEquals("cem", extenso(100));
        assertEquals("novecentos", extenso(900));
    }

    @Test
    public void conectorCasas_dezenaMaisUnidade() {
        assertEquals("vinte e dois", extenso(22));
    }
    
    @Test
    public void conectorCasas_centenaMaisDezena() {
        assertEquals("duzentos e vinte", extenso(220));
    }
    
    @Test
    public void conectorCasas_centenaMaisDezenaMaisUnidade() {
        assertEquals("duzentos e vinte e dois", extenso(222));
    }
    
    @Test
    public void representacaoSegundaClasse() {
        assertEquals("dois mil", extenso(2000));
    }
    
    @Test
    public void representacaoTerceiraClasse() {
        assertEquals("um milhão", extenso(1000000));
    }
    
    @Test
    public void representacaoQuartaClasse() {
        assertEquals("um bilhão", extenso(1000000000));
    }
    
    @Test
    public void representacaoQuintaClasse() {
        assertEquals("um trilhão", extenso(1000000000000l));
    }
    
    @Test
    public void representacaoSextaClasse() {
        assertEquals("um quatrilhão", extenso(1000000000000000l));
    }
    
    @Test
    public void conectorClasses() {
        assertEquals("um milhão, duzentos e trinta e quatro mil, quinhentos e sessenta e sete", extenso(1234567));
    }
    
    @Test
    public void onzeADezenoveNaoTemConector() {
        assertEquals("onze", extenso(11));
        assertEquals("dezenove", extenso(19));
    }
    
    @Test
    public void cem_cento() {
        assertEquals("cem", extenso(100));
        assertEquals("cento e dez", extenso(110));
        assertEquals("cento e um", extenso(101));
    }
    
    @Test
    public void unidadeSegundaClasseComRepresentacao() {
        assertEquals("dois mil", extenso(2000));
    }
    
    @Test
    public void unidadeSegundaClasseSemRepresentacao() {
        assertEquals("mil", extenso(1000));
    }
    
    @Test
    public void classeZeradaNaoTemRepresentacao() {
        assertEquals("um trilhão, cento e vinte e um", extenso(1000000000121l));
    }
    
    @Test
    public void segundaClasseNaoFlexionaNumero() {
        assertEquals("mil", extenso(1000));
        assertEquals("dois mil", extenso(2000));
    }
    
    @Test
    public void acimaDaSegundaClasseFlexionaNumero() {
        assertEquals("um milhão", extenso(1000000));
        assertEquals("dois milhões", extenso(2000000));
        
        assertEquals("um bilhão", extenso(1000000000));
        assertEquals("dois bilhões", extenso(2000000000));
        
        assertEquals("um trilhão", extenso(1000000000000l));
        assertEquals("dois trilhões", extenso(2000000000000l));
    }
    
    @Test
    public void ultimaClasseComValorConectaComESeCentenaEhZero() {
        assertEquals("mil e dez", extenso(1010));
        assertEquals("um milhão e dez mil", extenso(1010000));
    }
    
    @Test
    public void ultimaClasseComValorConectaComESeCentenaEhRazos() {
        assertEquals("mil e cem", extenso(1100));
        assertEquals("um milhão e cem mil", extenso(1100000));
    }
    
    private String extenso(long valor) {
        return NumeroPorExtenso.get(valor);
    }
}
