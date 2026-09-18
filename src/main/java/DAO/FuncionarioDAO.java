package DAO;

import Model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {


    Conexao conexao = new Conexao();
    Connection conn = conexao.getConnection();

    public FuncionarioDAO() throws SQLException {
    }

    public int inserir(Funcionario funcionario) throws SQLException {

        String sql =
                "INSERT INTO funcionarios (nome_funcionario) VALUES (?)";

        try (PreparedStatement stmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, funcionario.getNomeFuncionario());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException(
                "Não foi possível obter a matrícula gerada."
        );
    }

    public List<Funcionario> listar() throws SQLException {

        List<Funcionario> funcionariosList = new ArrayList<>();

        String sql = "SELECT f.matricula_funcionario, f.nome_funcionario, fl.admin " +
                "FROM funcionarios f " +
                "LEFT JOIN funcionario_login fl " +
                "ON fl.fk_matricula = f.matricula_funcionario";



            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int matricula =
                        rs.getInt("matricula_funcionario");

                String nomeFuncionario =
                        rs.getString("nome_funcionario");

                boolean admin =
                        rs.getBoolean("admin");

                Funcionario funcionario = new Funcionario(matricula, nomeFuncionario);
                funcionario.setAdmin(admin);
                funcionariosList.add(funcionario);

            }
            rs.close();
            stmt.close();

        return funcionariosList;

        }



    public void atualizar(Funcionario funcionarios) throws SQLException {

        String sql =
                "UPDATE funcionarios SET nome_funcionario = ? WHERE matricula_funcionario = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, funcionarios.getNomeFuncionario());
            stmt.setInt(2, funcionarios.getMatricula());

            stmt.executeUpdate();
            stmt.close();

    }

    public void deletar(int matricula) throws SQLException {

        String sqlLogin =
                "DELETE FROM funcionario_login WHERE fk_matricula = ?";

        String sqlFuncionario =
                "DELETE FROM funcionarios WHERE matricula_funcionario = ?";

        try (
                PreparedStatement stmtLogin =
                        conn.prepareStatement(sqlLogin);

                PreparedStatement stmtFuncionario =
                        conn.prepareStatement(sqlFuncionario)
        ) {

            stmtLogin.setInt(1, matricula);
            stmtLogin.executeUpdate();

            stmtFuncionario.setInt(1, matricula);
            stmtFuncionario.executeUpdate();
        }
    }

}