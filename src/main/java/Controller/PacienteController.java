package Controller;

import Model.Paciente;
import DAO.PacienteDAO;

import java.sql.SQLException;
import java.util.List;

public class PacienteController {

    PacienteDAO pacienteDAO = new PacienteDAO();

    public PacienteController() throws SQLException {
    }

    public List<Paciente> listarPacientes() throws SQLException {

      return pacienteDAO.listar();
    }

    public void editarPaciente(Paciente paciente)
            throws SQLException {

        pacienteDAO.atualizar(paciente);
    }
    public void deletarPaciente(Paciente paciente) throws SQLException {

        try {
            pacienteDAO.deletar(paciente.getIdPaciente());

        } catch (SQLException e) {

            if (e.getErrorCode() == 1451) {
                throw new SQLException(
                        "Paciente possui atendimentos vinculados e não pode ser excluído.", e);
            }
            throw e;
        }
    }
}
