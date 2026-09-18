package Model;

public enum StatusAtendimento {
    AGUARDANDO(1),
    ATENDIDO(2);

    private final int status;

    StatusAtendimento(int status) {
        this.status = status;
    }
    public int getStatus() {
        return this.status;

    }
    }
