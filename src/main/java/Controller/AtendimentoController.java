package Controller;

import Model.Atendimento;
import Model.Paciente;
import DAO.AtendimentoDAO;

import java.sql.SQLException;
import java.util.List;

public class AtendimentoController {

    AtendimentoDAO atendimentoDAO = new AtendimentoDAO();

    public AtendimentoController() throws SQLException {
    }

    public void listarAtendimentos() throws SQLException {

        atendimentoDAO.listaAtendimentos();
    }

    public void finalizarAtendimento(Atendimento atendimento) throws SQLException {
        atendimentoDAO.atualizarStatus(atendimento);
        }

    public List<Atendimento> listarFila() throws SQLException {
       return atendimentoDAO.listarAguardando();
    }

    public List<Atendimento> atendimentosFinalizados() throws SQLException {
        return atendimentoDAO.listarAtendidos();
    }


}