package Model;

import DAO.PacienteDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class Paciente {

    public static List<Paciente> pacientes = new ArrayList<>();

    private int idPaciente;
    private String nomePaciente;
    private String cpf;
    private int idade;
    private String convenio;


    public Paciente(String nomePaciente, String cpf, int idade, String convenio) {

        this.nomePaciente = nomePaciente;
        this.cpf = cpf;
        this.idade = idade;
        this.convenio = convenio;

    }

    public Paciente(int idPaciente, String nomePaciente, String cpf, int idade, String convenio) {

        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.cpf = cpf;
        this.idade = idade;
        this.convenio = convenio;

    }

    public void cadastrar() throws SQLException {

        PacienteDAO pacienteDAO = new PacienteDAO();

        pacientes.add(this);
        pacienteDAO.inserir(this);
    }

    public Paciente() {
    }

    public void entradaPaciente() throws SQLException {

    PacienteDAO pacienteDAO = new PacienteDAO();
    pacientes.add(this);
    pacienteDAO.inserir(this);
}


    @Override
    public String toString() {
        return
                "ID do paciente" + getIdPaciente() +
                        " \nNome: " + getNomePaciente() +
                        " \nCPF: " + getCpf() +
                        " \nIdade: " + getIdade() +
                        " \nConvênio: " + getConvenio() +
                        " \n--------------------------";
    }

    public int getIdPaciente() {
        return this.idPaciente;
    }

    public String getNomePaciente() {
        return this.nomePaciente;
    }

    public String getConvenio() {
        return this.convenio;
    }

    public int getIdade() {
        return this.idade;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
}
