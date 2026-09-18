package Model;

public class UsuarioLogado {

    private int matricula;
    private boolean admin;
    private int senha;

    public UsuarioLogado(int matricula, int senha, boolean admin) {
        this.matricula = matricula;
        this.senha = senha;
        this.admin = admin;
    }
    public UsuarioLogado() {}

    public UsuarioLogado(int matricula, boolean admin) {
    this.matricula = matricula;
    this.admin = admin;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public int getSenha() {
        return this.senha;
    }

    public boolean isAdmin() {
        return this.admin;
    }

}