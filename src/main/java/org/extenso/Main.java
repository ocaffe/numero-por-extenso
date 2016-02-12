package org.extenso;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {
            
            String entry = "";

            do {
                System.out.print("Escreva um número: ");
                entry = in.nextLine();

                long numero = 0;

                try {

                    numero = Long.parseLong(entry.replaceAll("\\.", ""));

                } catch (NumberFormatException e) {
                    System.out.println("Número inválido.");
                    continue;
                }

                System.out.println("O seu número por extenso: " + NumeroPorExtenso.get(numero));

            } while (!"".equals(entry));

        }
    }
}
