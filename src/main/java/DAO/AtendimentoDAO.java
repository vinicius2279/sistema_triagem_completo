package DAO;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtendimentoDAO {

    Conexao conexao = new Conexao();
    Connection conn = conexao.getConnection();

    public AtendimentoDAO() throws SQLException {
    }

    public void novoAtendimento(Atendimento atendimento) throws SQLException {


        String sql = "INSERT INTO atendimentos(fk_id_paciente, fk_matricula, " +
                "emergencia, classificacao, status, data_hora) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, atendimento.getIdPaciente());
        stmt.setInt(2, atendimento.getMatricula());
        stmt.setBoolean(3, atendimento.getEmergencia());
        stmt.setString(4, atendimento.getClassificacao().name());
        stmt.setString(5, atendimento.getStatus().name());
        stmt.setTimestamp(6, Timestamp.valueOf(atendimento.getDataHora()));

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();

        int idatendimento = 0;

        if (rs.next()) {
            idatendimento = rs.getInt(1);
        } else {
            throw new SQLException("Falha ao obter ID do atendimento");
        }
        stmt.close();

        SintomaDAO sintomaDAO = new SintomaDAO();

        for (Sintoma s : atendimento.getSintomas()) {

            String sql2 = "INSERT INTO atendimento_sintoma (fk_id_atendimento, fk_id_sintoma) VALUES (?, ?)";

            PreparedStatement stmt2 = conn.prepareStatement(sql2);

            stmt2.setInt(1, idatendimento);
            stmt2.setInt(2, s.getIdSintoma());

            stmt2.executeUpdate();
            stmt2.close();
        }

    }

    public void listaAtendimentos() throws SQLException {

        List<Atendimento> dadosAtendimento = new ArrayList<>();

        String sql = "SELECT p.nomePaciente, f.nome_funcionario, a.classificacao, a.data_hora, a.status, " +
                "GROUP_CONCAT(s.descricao SEPARATOR ', ') AS sintomas " +
                "FROM atendimentos a " +
                "INNER JOIN funcionarios f ON f.matricula_funcionario = a.fk_matricula " +
                "INNER JOIN pacientes p ON p.id_paciente = a.fk_id_paciente " +
                "INNER JOIN atendimento_sintoma ats ON ats.fk_id_atendimento = a.id_atendimento " +
                "INNER JOIN sintomas s ON s.id_sintoma = ats.fk_id_sintoma " +
                "GROUP BY a.id_atendimento, p.nomePaciente, f.nome_funcionario, a.classificacao, a.status, a.data_hora " +
                "ORDER BY CASE a.classificacao " +
                "WHEN 'VERMELHA' THEN 1 " +
                "WHEN 'AMARELA' THEN 2 " +
                "WHEN 'VERDE' THEN 3 " +
                "WHEN 'AZUL' THEN 4 " +
                "ELSE 5 END, a.data_hora ASC";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Atendimento a = new Atendimento();

            a.setNomePaciente(rs.getString("nomePaciente"));
            a.setNomeFuncionario(rs.getString("nome_funcionario"));
            a.setClassificacao(ClassificacaoRisco.valueOf(rs.getString("classificacao")));
            a.setData(rs.getTimestamp("data_hora").toLocalDateTime());
            a.setSintomasTexto(rs.getString("sintomas"));
            a.setStatus(StatusAtendimento.valueOf(rs.getString("status")));

            dadosAtendimento.add(a);
        }

        rs.close();
        stmt.close();

    }

    public void atualizarStatus(Atendimento atendimento) throws SQLException {
        String sql =
                "UPDATE atendimentos SET status = ? WHERE id_atendimento = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, StatusAtendimento.ATENDIDO.name());
        stmt.setInt(2, atendimento.getIdAtendimento());

        stmt.executeUpdate();
        stmt.close();

    }

    public List<Atendimento> listarAguardando() throws SQLException {

        List<Atendimento> aguardandoAtendimento = new ArrayList<>();

        String sql = "SELECT a.id_atendimento, p.nomePaciente, f.nome_funcionario, a.classificacao, a.data_hora, a.status, " +
                "GROUP_CONCAT(s.descricao SEPARATOR ', ') AS sintomas " +
                "FROM atendimentos a " +
                "INNER JOIN funcionarios f ON f.matricula_funcionario = a.fk_matricula " +
                "INNER JOIN pacientes p ON p.id_paciente = a.fk_id_paciente " +
                "INNER JOIN atendimento_sintoma ats ON ats.fk_id_atendimento = a.id_atendimento " +
                "INNER JOIN sintomas s ON s.id_sintoma = ats.fk_id_sintoma " +
                "WHERE a.status = 'AGUARDANDO' " +
                "GROUP BY a.id_atendimento, p.nomePaciente, f.nome_funcionario, a.classificacao, a.status, a.data_hora " +
                "ORDER BY CASE a.classificacao " +
                "WHEN 'VERMELHA' THEN 1 " +
                "WHEN 'AMARELA' THEN 2 " +
                "WHEN 'VERDE' THEN 3 " +
                "WHEN 'AZUL' THEN 4 " +
                "ELSE 5 END, a.data_hora ASC";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Atendimento a = new Atendimento();

            a.setIdAtendimento(rs.getInt("id_atendimento"));
            a.setNomePaciente(rs.getString("nomePaciente"));
            a.setNomeFuncionario(rs.getString("nome_funcionario"));
            a.setClassificacao(ClassificacaoRisco.valueOf(rs.getString("classificacao")));
            a.setData(rs.getTimestamp("data_hora").toLocalDateTime());
            a.setSintomasTexto(rs.getString("sintomas"));
            a.setStatus(StatusAtendimento.valueOf(rs.getString("status")));

            aguardandoAtendimento.add(a);
        }

        rs.close();
        stmt.close();
        return aguardandoAtendimento;

    }

public List<Atendimento> listarAtendidos() throws SQLException {

    List<Atendimento> atendimentosAtendidos = new ArrayList<>();

    String sql = "SELECT p.nomePaciente, f.nome_funcionario, a.classificacao, a.data_hora, a.status, " +
            "GROUP_CONCAT(s.descricao SEPARATOR ', ') AS sintomas " +
            "FROM atendimentos a " +
            "INNER JOIN funcionarios f ON f.matricula_funcionario = a.fk_matricula " +
            "INNER JOIN pacientes p ON p.id_paciente = a.fk_id_paciente " +
            "INNER JOIN atendimento_sintoma ats ON ats.fk_id_atendimento = a.id_atendimento " +
            "INNER JOIN sintomas s ON s.id_sintoma = ats.fk_id_sintoma " +
            "WHERE a.status = 'ATENDIDO' " +
            "GROUP BY a.id_atendimento, p.nomePaciente, f.nome_funcionario, a.classificacao, a.status, a.data_hora " +
            "ORDER BY CASE a.classificacao " +
            "WHEN 'VERMELHA' THEN 1 " +
            "WHEN 'AMARELA' THEN 2 " +
            "WHEN 'VERDE' THEN 3 " +
            "WHEN 'AZUL' THEN 4 " +
            "ELSE 5 END, a.data_hora ASC";

    PreparedStatement stmt = conn.prepareStatement(sql);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {

        Atendimento a = new Atendimento();
        a.setNomePaciente(rs.getString("nomePaciente"));
        a.setNomeFuncionario(rs.getString("nome_funcionario"));
        a.setClassificacao(ClassificacaoRisco.valueOf(rs.getString("classificacao")));
        a.setData(rs.getTimestamp("data_hora").toLocalDateTime());
        a.setSintomasTexto(rs.getString("sintomas"));
        a.setStatus(StatusAtendimento.valueOf(rs.getString("status")));

        atendimentosAtendidos.add(a);
    }

    rs.close();
    stmt.close();
    return atendimentosAtendidos;

}
}




