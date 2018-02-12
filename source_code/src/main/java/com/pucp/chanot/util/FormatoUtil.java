package com.pucp.chanot.util;

import java.text.Normalizer;

/**
 *
 * @author alulab14
 */
public class FormatoUtil {

    // evalúa si es un signo de puntuación
    public static boolean isPunctuation(String s) {
        char P[] = {'.', ':', ',', ';', '(', ')', '"', '-', '?', '¿', '!', '¡'};
        if (s.length() == 1) {
            char c = s.charAt(0);
            for (char car : P) {
                if (car == c) {
                    return true;
                }
            }
        }
        return false;
    }

    // evalúa si es un símbolo
    public static boolean isSymbol(String s) {
        char P[] = {'$', '%', '+', '-', '*', '=', '<', '>'};
        if (s.length() == 1) {
            char c = s.charAt(0);
            for (char car : P) {
                if (car == c) {
                    return true;
                }
            }
        }
        return false;
    }

    // normaliza una cadena
    public static String normalize(String s) {
        String aux = Normalizer.normalize(s, Normalizer.Form.NFD);
        aux = aux.replaceAll("[^\\p{ASCII}]", "");
        aux = aux.toLowerCase();
        return aux;
    }

    // evalúa si es un entero
    public static boolean isNumber(String s) {
        try {
            int entero = Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
