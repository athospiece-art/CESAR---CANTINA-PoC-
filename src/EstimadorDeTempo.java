import java.util.List;

/** Estima em quantos minutos uma lista de itens fica pronta. */
public interface EstimadorDeTempo {
    int minutosAtePronto(List<Item> itens);
}
