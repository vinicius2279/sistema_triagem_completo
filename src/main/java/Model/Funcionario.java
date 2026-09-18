package Model;

public class Funcionario {

    public int matricula;
    public String nomeFuncionario;

    public Funcionario() {
    }

    public Funcionario(int matricula, String nomeFuncionario) {
        this.matricula = matricula;
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getNomeFuncionario() {
        return this.nomeFuncionario;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    @Override
    public String toString() {
        return "Matrícula: " + matricula +
                "\nNome: " + nomeFuncionario +
                "\n--------------------------";
    }

    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}



