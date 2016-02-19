package org.extenso;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            
            String entry = "";

            do {
                System.out.print("Escreva um número inteiro ou um valor em reais: ");
                entry = in.nextLine().replaceAll("\\.", "");
                
                try {
                    
                    if (entry.contains(",")) {
                        
                        System.out.println(
                            NumeroPorExtenso.get(new BigDecimal(entry.replace(",", ".")), "real", "reais", "centavo", "centavos"));

                    } else {

                        System.out.println(NumeroPorExtenso.get(Long.parseLong(entry)));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Número inválido.");
                    continue;
                }


            } while (!"".equals(entry));

        }
    }
}
