package Controller;

import DAO.FuncionarioDAO;
import DAO.LoginDAO;
import Model.Funcionario;
import Model.*;

import java.sql.SQLException;

public class AdminController {

    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    LoginDAO loginDAO = new LoginDAO();

    public AdminController() throws SQLException {
    }

    public void cadastrarFuncionario(String nome, String senha, boolean admin) throws SQLException {

        Funcionario funcionario = new Funcionario();
        funcionario.setNomeFuncionario(nome);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        int matricula = funcionarioDAO.inserir(funcionario);

        LoginDAO loginDAO = new LoginDAO();
        loginDAO.criarLogin(matricula, senha, admin);
    }

    public void editarFuncionario(Funcionario funcionario, String senha, boolean admin) throws SQLException {

        if (!senha.isBlank()) {
            funcionarioDAO.atualizar(funcionario);
            loginDAO.atualizarLogin(funcionario.getMatricula(), senha, admin);
        }
        }
}


