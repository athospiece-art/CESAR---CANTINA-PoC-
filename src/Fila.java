/** Uma fila de atendimento. Nova fila = classe nova, sem mexer nas existentes. */
public interface Fila {
    String nome();
    boolean aceita(Item item);
    int atendentes();
}
