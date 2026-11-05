import java.util.Locale;

/** Formatação de valores em reais: 13.5 vira "R$ 13,50". */
public final class Dinheiro {
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private Dinheiro() { }

    public static String formatar(double valor) {
        return String.format(PT_BR, "R$ %.2f", valor);
    }
}
