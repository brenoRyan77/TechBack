package br.com.fujideia.iesp.tecback.util;

public class CpfRgUtil {
	
    public CpfRgUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean validaCPF(String cpf) {
        // Verifica se o CPF contém apenas números e tem 11 dígitos
        if (!cpf.matches("\\d{11}")) {
            return false;
        }

        // Verifica se o CPF possui dígitos repetidos
        boolean digitosRepetidos = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                digitosRepetidos = false;
                break;
            }
        }
        if (digitosRepetidos) {
            return false;
        }

        // Calcula os dígitos verificadores
        int soma1 = 0;
        int soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }
        soma2 += Character.getNumericValue(cpf.charAt(9)) * 2;
        int digito1 = 11 - (soma1 % 11);
        int digito2 = 11 - (soma2 % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }
        if (digito2 > 9) {
            digito2 = 0;
        }

        // Verifica se os dígitos verificadores estão corretos
        return (Character.getNumericValue(cpf.charAt(9)) == digito1) && (Character.getNumericValue(cpf.charAt(10)) == digito2);
    }


    public static boolean validarRG(String rg) {
        // Verifica se a string é nula ou vazia
        if (rg == null || rg.isEmpty()) {
            return false;
        }

        // Verifica se a string contém apenas dígitos numéricos
        return rg.matches("\\d+");
    }
}
