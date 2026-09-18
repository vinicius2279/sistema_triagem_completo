package Model;

import DAO.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Atendimento {

    public List<Atendimento> atendimentos = new ArrayList<>();
    private int idAtendimento;
    private int idPaciente;
    private String nomeFuncionario;
    private String nomePaciente;
    private int matriculaFuncionario;
    private List<Sintoma> sintomas = new ArrayList<>();
    private boolean emergencia;
    private ClassificacaoRisco classificacao;
    String descricao;
    int prioridade;
    private LocalDateTime agora = LocalDateTime.now();
    private StatusAtendimento status;
    private String sintomasTexto;


    public Atendimento(int matriculaFuncionario, int idPaciente,
                       boolean emergencia, ClassificacaoRisco classificacao,
                       StatusAtendimento status, LocalDateTime agora) {

        this.matriculaFuncionario = matriculaFuncionario;
        this.idPaciente = idPaciente;
        this.emergencia = emergencia;
        this.classificacao = classificacao;
        this.agora = agora;
        this.status = status;
    }

    public Atendimento() {
    }

    public void finalizarCadastro(Paciente paciente) throws SQLException {

        setIdPaciente(paciente.getIdPaciente());
        setStatus(StatusAtendimento.AGUARDANDO);
        classificarAtendimento();
        adicionarAtendimento(this);
    }

    public void adicionarAtendimento(Atendimento entrada) throws SQLException {

        AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
        atendimentos.add(entrada);
        atendimentoDAO.novoAtendimento(entrada);

    }

    public void novoAtendimento(Paciente paciente) throws SQLException {

        setIdPaciente(paciente.getIdPaciente());
        SintomaDAO sintomaDAO = new SintomaDAO();
        Sintoma sintoma = sintomaDAO.buscaDescricao(descricao);
        if (sintoma != null) {
            sintomas.add(sintoma);

        }
        setStatus(StatusAtendimento.AGUARDANDO);
        classificarAtendimento();
        adicionarAtendimento(this);
    }

    private void classificarAtendimento() {

        prioridade = 0;

        for (Sintoma sintoma : sintomas) {

            if (sintoma == null)
                continue;

            int prioridadeSintoma = sintoma.getClassificacao().getPrioridade();

            if (prioridade == 0 || prioridadeSintoma < prioridade) {
                prioridade = prioridadeSintoma;
            }
        }

        switch (prioridade) {

            case 1:
                setClassificacao(ClassificacaoRisco.VERMELHA);
                setEmergencia(true);
                break;
            case 2:
                setEmergencia(false);
                setClassificacao(ClassificacaoRisco.AMARELA);
                break;
            case 3:
                setEmergencia(false);
                setClassificacao(ClassificacaoRisco.VERDE);
                break;
            default:
                setEmergencia(false);
                setClassificacao(ClassificacaoRisco.MANUAL);
        }

    }

    @Override
    public String toString() {
        return
                "Nome do paciente: " + nomePaciente +
                        "\nFuncionário: " + nomeFuncionario +
                        "\nClassificação: " + classificacao +
                        "\nData e Hora: " + getDataHora() +
                        "\nSintomas: " + getSintomasTexto() +
                        "\nStatus: " + getStatus() +
                        "\n-------------------";
    }


    public LocalDateTime getData() {
        return agora;
    }

    public void setSintomasTexto(String sintomasTexto) {
        this.sintomasTexto = sintomasTexto;
    }

    public void setSintomas(List<Sintoma> sintomas) {
        this.sintomas = sintomas;
    }


    public String getSintomasTexto() {
        return this.sintomasTexto;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void setMatricula(int matriculaFuncionario) {
        this.matriculaFuncionario = matriculaFuncionario;
    }

    public int getIdPaciente() {
        return this.idPaciente;
    }

    public List<Sintoma> getSintomas() {
        return sintomas;
    }

    public String getNomeFuncionario() {
        return this.nomeFuncionario;
    }
    public String getNomePaciente() {
        return this.nomePaciente;
    }

    public int getMatricula() {
        return this.matriculaFuncionario;
    }

    public ClassificacaoRisco getClassificacao() {
        return classificacao;
    }

    public boolean getEmergencia() {
        return this.emergencia;
    }

    public void setEmergencia(boolean emergencia) {
        this.emergencia = emergencia;
    }

    public void setClassificacao(ClassificacaoRisco classificacao) {
        this.classificacao = classificacao;
    }

    public int getIdAtendimento() {
        return this.idAtendimento;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public LocalDateTime getDataHora() {
        return agora;
    }

    public void setData(LocalDateTime agora) {
        this.agora = agora;
    }

    public StatusAtendimento getStatus() {
        return this.status;
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status;
    }
}





