package org.extenso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumeroPorExtenso {
    
    private NumeroPorExtenso() {}
    
    public static String get(BigDecimal numero, String qualificadorSingular, String qualificadorPlural, 
        String qualificadorDecimalSingular, String qualificadorDecimalPlural) {

        String extenso = "";
        String conector = "";
        
        long parteInteira = numero.longValue();
        
        if (parteInteira > 0) {
            extenso = get(parteInteira, qualificadorSingular, qualificadorPlural);
            conector = " e ";
        }
        
        long parteDecimal = numero.remainder(BigDecimal.ONE).movePointRight(numero.scale()).longValue();
        
        if (parteDecimal > 0) {
            extenso += conector + get(parteDecimal, qualificadorDecimalSingular, qualificadorDecimalPlural);
        }
        
        return extenso;
    }
    
    public static String get(long numero, String qualificadorSingular, String qualificadorPlural) {
        
        String qualificador = numero == 1 ? qualificadorSingular : qualificadorPlural;
        
        return get(numero) + " " + qualificador;
    }
    
    public static String get(long numero) {

        int[] rawAlgarismos = getAlgarismos(numero);

        List<Classe> classes = buildClassesFromAlgarismos(rawAlgarismos);

        return buildExtensoFromClasses(classes);
    }

    private static List<Classe> buildClassesFromAlgarismos(int[] rawAlgarismos) {

        List<Classe> classes = new ArrayList<>();

        int numeroClasse = 0;
        Classe classe = new Classe(numeroClasse++);
        Algarismo algarismoADireita = null;
        int casa = 0;
        
        for (int algarismoPos = rawAlgarismos.length - 1; algarismoPos >= 0; algarismoPos--) {

            Algarismo algarismo = AlgarismoFactory.create(casa, rawAlgarismos[algarismoPos], algarismoADireita, classe);

            algarismoADireita = algarismo;

            classe.algarismos.add(algarismo);

            casa++;
            if (casa == 3 || algarismoPos == 0) {
                classes.add(classe);
                classe = new Classe(numeroClasse++);
                casa = 0;
            }
        }
        return classes;
    }

    private static String buildExtensoFromClasses(List<Classe> classes) {

        StringBuilder extenso = new StringBuilder();

        for (Classe classeZ : classes) {

            StringBuilder extensoClasse = new StringBuilder();

            for (Algarismo algarismo : classeZ.algarismos) {
                extensoClasse.insert(0, algarismo.conector() + algarismo.representacao());
            }

            extenso.insert(0, classeZ.conector() + extensoClasse + classeZ.representacao());
        }
        
        return extenso.toString().trim();
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

class AlgarismoFactory {
    
    private static final Map<Integer, Class<?>> casas = new HashMap<>();

    static {
        casas.put(0, Unidade.class);
        casas.put(1, Dezena.class);
        casas.put(2, Centena.class);
    }

    static Algarismo create(int casa, int valor, Algarismo algarismoADireita, Classe classe) {
        
        try {
            return (Algarismo) casas.get(casa)
                .getConstructor(int.class, Algarismo.class, Classe.class)
                .newInstance(valor, algarismoADireita, classe);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}

class Unidade extends Algarismo {

    private static final String[] unidadePorExtenso = {"", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove"};

    public Unidade(int valor, Algarismo algarismoADireita, Classe classe) {
        super(valor, algarismoADireita, classe);
    }

    @Override
    String conector() {
        if (valor != 0 && algarismoAEsquerda != null && algarismoAEsquerda.valor != 1) {
            return " e ";
        }
        return "";
    }

    @Override
    String representacao() {
        if ((classe.isSegundaClasse() && valor == 1) 
            || (algarismoAEsquerda != null && algarismoAEsquerda.valor == 1)) {
            
            return "";
        }
        return unidadePorExtenso[valor];
    }
}

class Dezena extends Algarismo {

    private static final String[] 
        dezenaPorExtenso = {"", "dez", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"};

    private static final String[] 
        onzeADezenove = {"", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"};

    public Dezena(int valor, Algarismo algarismoADireita, Classe classe) {
        super(valor, algarismoADireita, classe);
    }

    @Override
    String conector() {
        if (valor != 0 && algarismoAEsquerda != null) {
            return " e ";
        }
        return "";
    }
    
    @Override
    String representacao() {
        if (valor == 1 && algarismoADireita.valor > 0) {
            return onzeADezenove[algarismoADireita.valor];
        }

        return dezenaPorExtenso[valor];
    }
}

class Centena extends Algarismo {

    private static final String[] centenaPorExtenso = {"", "cem", "duzentos", "trezentos", "quatrocentos", 
        "quinhentos", "seissentos", "setecentos", "oitocentos", "novecentos"};
    
    private static final String CENTO = "cento";
    
    public Centena(int valor, Algarismo algarismoADireita, Classe classe) {
        super(valor, algarismoADireita, classe);
    }

    @Override
    String conector() {
        return "";
    }

    @Override
    String representacao() {
        if (valor == 1 
            && ((algarismoADireita.valor > 0) || (algarismoADireita.algarismoADireita.valor > 0))) {
            
            return CENTO;
        }
        return centenaPorExtenso[valor];
    }
}

abstract class Algarismo {

    protected Algarismo algarismoADireita;
    protected Algarismo algarismoAEsquerda;
    protected int valor;
    protected Classe classe;

    Algarismo(int valor, Algarismo algarismoADireita, Classe classe) {

        this.valor = valor;
        this.classe = classe;
        this.algarismoADireita = algarismoADireita;

        if (algarismoADireita != null) {
            algarismoADireita.algarismoAEsquerda = this;
        }
    }

    abstract String representacao();

    abstract String conector();
}

class Classe {

    private static final String[] classeSingular = {"", " mil", " milhão", " bilhão", " trilhão", " quatrilhão"};
    private static final String[] classePlural = {"", " mil", " milhões", " bilhões", " trilhões", " quatrilhões"};

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
        StringBuilder total = new StringBuilder();
        
        for (Algarismo algarismo : algarismos) {
            total.insert(0, algarismo.valor);
        }
        return Integer.parseInt(total.toString());
    }

    private boolean souUltimaClasseAEsquerda() {
        return algarismos.get(algarismos.size() - 1).algarismoAEsquerda == null;
    }

    private int valorCentena() {
        return algarismos.get(algarismos.size() - 1).valor;
    }
}
