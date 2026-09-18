package Model;

public enum ClassificacaoRisco {

    VERMELHA(1),
    AMARELA(2),
    VERDE(3),
    AZUL(4),
    MANUAL(5);

    private final int prioridade;

    ClassificacaoRisco(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getPrioridade() {
        return prioridade;
    }


}