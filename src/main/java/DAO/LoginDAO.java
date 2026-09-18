package DAO;

import Model.UsuarioLogado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {


    public LoginDAO() throws SQLException {
    }

    public void criarLogin(int matricula, String senha, boolean admin)
            throws SQLException {

        String sql =
                "INSERT INTO funcionario_login " +
                        "(fk_matricula, senha, admin) " +
                        "VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            stmt.setString(2, senha);
            stmt.setBoolean(3, admin);

            stmt.executeUpdate();
        }
    }
    Conexao conexao = new Conexao();
    Connection conn = conexao.getConnection();


    public UsuarioLogado autenticar(int matricula, String senha) throws SQLException {

        String sql =
               "SELECT fk_matricula, admin " +
               "FROM funcionario_login " +
               "WHERE fk_matricula = ? AND senha = ?";


        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, matricula);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    boolean admin = rs.getBoolean("admin");
                    return new UsuarioLogado(matricula, admin);

                }

            }
        }
        return null;
    }

    public void atualizarLogin(int matricula, String senha, boolean admin)
            throws SQLException {

        String sql =
                "UPDATE funcionario_login " +
                        "SET senha = ?, admin = ? " +
                        "WHERE fk_matricula = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, senha);
            stmt.setBoolean(2, admin);
            stmt.setInt(3, matricula);

            stmt.executeUpdate();
        }
    }

}