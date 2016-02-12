package org.extenso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class NumeroPorExtenso {

    public static String get(long numero) {

        int[] rawAlgarismos = getAlgarismos(numero);

        Algarismo algarismoADireita = null;

        int numeroClasse = 0;
        List<Classe> classes = new ArrayList<>();

        for (int algarismoPos = rawAlgarismos.length - 1; algarismoPos >= 0;) {

            Classe classe = new Classe(numeroClasse++);

            for (int casa = 0; casa < 3 && algarismoPos >= 0; casa++) {

                Algarismo algarismo = AlgarismoFactory.create(casa, rawAlgarismos[algarismoPos--], algarismoADireita, classe);

                algarismoADireita = algarismo;

                classe.algarismos.add(algarismo);
            }

            classes.add(classe);
        }

        String extenso = "";

        for (Classe classe : classes) {

            String extensoClasse = "";

            for (Algarismo algarismo : classe.algarismos) {
                extensoClasse = algarismo.conector() + algarismo.representacao() + extensoClasse;
            }

            extenso = classe.conector() + extensoClasse + classe.representacao() + extenso;
        }

        return extenso.trim();
    }

    private static int[] getAlgarismos(long numero) {

        String algarismos = Long.toString(numero);

        int[] result = new int[algarismos.length()];

        for (int i = 0; i < algarismos.length(); i++) {
            result[i] = algarismos.charAt(i) - '0';
        }

        return result;
    }
}

class AlgarismoFactory<T> {

    final private static Map<Integer, Function<Integer, Algarismo>> casas = new HashMap<>();

    static {
        casas.put(0, (Integer valor) -> new Unidade(valor));
        casas.put(1, (Integer valor) -> new Dezena(valor));
        casas.put(2, (Integer valor) -> new Centena(valor));
    }

    static Algarismo create(int casa, int valor, Algarismo algarismoADireita, Classe classe) {
        
        Algarismo algarismo = casas.get(casa).apply(valor);
        
        algarismo.encadeiaAlgarismo(algarismoADireita);
        algarismo.setClasse(classe);
        
        return algarismo;
    }
}

class Classe {

    private final String[] classeSingular = {"", " mil", " milhão", " bilhão", " trilhão", " quatrilhão"};
    private final String[] classePlural = {"", " mil", " milhões", " bilhões", " trilhões", " quatrilhões"};

    private int numeroClasse;

    List<Algarismo> algarismos = new ArrayList<>();

    Classe(int numeroClasse) {
        this.numeroClasse = numeroClasse;
    }

    String conector() {
        
        if (totalClasse() == 0 || souUltimaClasseAEsquerda() || valorCentena() == 0) {
            return "";
        }
        
        if (totalClasse() % 100 == 0) {
            return " e ";
        }
        
        return ", ";
    }

    String representacao() {
        
        if (totalClasse() == 0) {
            return "";
        }
        
        if (totalClasse() == 1) {
            return classeSingular[numeroClasse];
        }
        return classePlural[numeroClasse];
    }

    boolean isSegundaClasse() {
        return numeroClasse == 1;
    }
    
    private int totalClasse() {
        String total = "";
        
        for (Algarismo algarismo : algarismos) {
            total = algarismo.valor + total;
        }
        return new Integer(total);
    }

    private boolean souUltimaClasseAEsquerda() {
        return algarismos.get(algarismos.size() - 1).algarismoAEsquerda == null;
    }

    private int valorCentena() {
        return algarismos.get(algarismos.size() - 1).valor;
    }
}

class Unidade extends Algarismo {

    private final static String[] unidade = {"", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove"};

    Unidade(int valor) {
        super(valor);
    }

    String conector() {
        if (valor != 0 && algarismoAEsquerda != null && algarismoAEsquerda.valor != 1) {
            return " e ";
        }
        return "";
    }

    String representacao() {
        if ((segundaClasse() && valor == 1) 
            || (algarismoAEsquerda != null && algarismoAEsquerda.valor == 1)) {
            
            return "";
        }
        return unidade[valor];
    }

    private boolean segundaClasse() {
        return classe.isSegundaClasse();
    }
}

class Dezena extends Algarismo {

    private final static String[] 
        dezena = {"", "dez", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"};

    private final static String[] 
        onzeADezenove = {"", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"};

    Dezena(int valor) {
        super(valor);
    }

    String conector() {
        if (valor != 0 && algarismoAEsquerda != null) {
            return " e ";
        }
        return "";
    }
    
    String representacao() {
        if (valor == 1 && algarismoADireita.valor > 0) {
            return onzeADezenove[algarismoADireita.valor];
        }

        return dezena[valor];
    }
}

class Centena extends Algarismo {

    private final static String[] 
        centena = {"", "cem", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seissentos", "setecentos", "oitocentos", "novecentos"};
    
    private final static String cento = "cento";
    
    Centena(int valor) {
        super(valor);
    }

    String conector() {
        return "";
    }

    String representacao() {
        if (valor == 1 
            && ((algarismoADireita.valor > 0) || (algarismoADireita.algarismoADireita.valor > 0))) {
            
            return cento;
        }
        return centena[valor];
    }
}

abstract class Algarismo {

    protected Algarismo algarismoADireita;
    protected Algarismo algarismoAEsquerda;
    protected int valor;
    protected Classe classe;

    Algarismo(int valor) {
        this.valor = valor;
    }

    abstract String representacao();

    abstract String conector();

    void setClasse(Classe classe) {
        this.classe = classe;
    }

    void encadeiaAlgarismo(Algarismo algarismoADireita) {
        this.algarismoADireita = algarismoADireita;

        if (algarismoADireita != null) {
            algarismoADireita.algarismoAEsquerda = this;
        }
    }
}
