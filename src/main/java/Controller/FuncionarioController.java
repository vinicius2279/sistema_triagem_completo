package Controller;

import Model.Funcionario;
import DAO.*;

import java.sql.SQLException;
import java.util.List;


public class FuncionarioController {

    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public FuncionarioController() throws SQLException {
    }


    public List<Funcionario> listarFuncionarios() throws SQLException {

        return funcionarioDAO.listar();
    }

    public void editarFuncionario(Funcionario funcionario) throws SQLException {

        funcionarioDAO.atualizar(funcionario);
    }

    public void deletarFuncionario(Funcionario funcionario) throws SQLException {
        funcionarioDAO.deletar(funcionario.getMatricula());
    }
}
