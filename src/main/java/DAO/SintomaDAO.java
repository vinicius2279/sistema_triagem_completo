package DAO;

import Model.ClassificacaoRisco;
import Model.Sintoma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SintomaDAO {

    Conexao conexao = new Conexao();
    Connection conn = conexao.getConnection();

    public SintomaDAO() throws SQLException {
    }

    public List<Sintoma> listarTodos() throws SQLException {

        List<Sintoma> sintomas = new ArrayList<>();

        String sql = "SELECT * FROM sintomas ORDER BY descricao ASC";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Sintoma sintoma = new Sintoma();

            sintoma.setIdSintoma(
                    rs.getInt("id_sintoma")
            );

            sintoma.setDescricao(
                    rs.getString("descricao")
            );

            sintoma.setClassificacao(
                    ClassificacaoRisco.valueOf(
                            rs.getString("classificacao")
                    )
            );

            sintomas.add(sintoma);
        }

        rs.close();
        stmt.close();

        return sintomas;
    }
    public Sintoma buscaDescricao(String descricao) throws SQLException {

            String sql = "SELECT * FROM sintomas WHERE descricao = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, descricao);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Sintoma sintoma = new Sintoma();

                sintoma.setIdSintoma(rs.getInt("id_sintoma"));
                sintoma.setDescricao(rs.getString("descricao"));
                sintoma.setClassificacao(ClassificacaoRisco.valueOf(rs.getString("classificacao"))
                );

                rs.close();
                stmt.close();

                return sintoma;
            }

        return null;
    }
}