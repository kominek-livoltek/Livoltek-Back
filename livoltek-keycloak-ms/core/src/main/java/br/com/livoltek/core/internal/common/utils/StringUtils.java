package br.com.livoltek.core.internal.common.utils;

public class StringUtils {
    public static String formatarCnpj(String cnpj) {
        return cnpj.substring(0, 2) + "." +
               cnpj.substring(2, 5) + "." +
               cnpj.substring(5, 8) + "/" +
               cnpj.substring(8, 12) + "-" +
               cnpj.substring(12, 14);
    }
}
