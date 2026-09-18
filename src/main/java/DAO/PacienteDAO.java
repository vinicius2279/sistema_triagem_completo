package DAO;

import Model.Paciente;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class PacienteDAO {

    Conexao conexao = new Conexao();
    Connection conn = conexao.getConnection();

    public PacienteDAO() throws SQLException {
    }

// CREATE

    public void inserir(Paciente paciente) throws SQLException {

        String sql = "INSERT INTO pacientes (nomePaciente, cpf, idade, convenio)" +
                " VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, paciente.getNomePaciente());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.setString(4, paciente.getConvenio());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                paciente.setIdPaciente(rs.getInt(1));
            }

            rs.close();
            stmt.close();

    }

    // READ

    public List<Paciente> listar() throws SQLException {

        List<Paciente> lpacientes = new ArrayList<>();

        String sql = "SELECT * FROM pacientes";


        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            int idPaciente = rs.getInt("id_paciente");
            String nomePaciente = rs.getString("nomePaciente");
            String cpf = rs.getString("cpf");
            int idade = rs.getInt("idade");
            String convenio = rs.getString("convenio");

            Paciente paciente = new Paciente(idPaciente, nomePaciente, cpf, idade, convenio);
            lpacientes.add(paciente);
        }

        stmt.close();
        return lpacientes;
    }


    public void atualizar(Paciente pacientes) throws SQLException {


        String sql = "UPDATE pacientes SET nomePaciente = ?, cpf = ?, idade = ?, convenio = ? WHERE id_paciente = ?";


        PreparedStatement stmt = conn.prepareStatement(sql);


        stmt.setString(1, pacientes.getNomePaciente());
        stmt.setString(2, pacientes.getCpf());
        stmt.setInt(3, pacientes.getIdade());
        stmt.setString(4, pacientes.getConvenio());
        stmt.setInt(5, pacientes.getIdPaciente());

        stmt.executeUpdate();
        stmt.close();

    }


    public void deletar(int idPaciente) throws SQLException {

        String sql = "DELETE FROM pacientes WHERE id_paciente = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, idPaciente);

        stmt.executeUpdate();
        stmt.close();

    }

    public Paciente buscaId(int idPaciente) throws SQLException {

        String sql = "SELECT * FROM pacientes WHERE id_paciente = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPaciente);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            return new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nomePaciente"),
                    rs.getString("cpf"),
                    rs.getInt("idade"),
                    rs.getString("convenio")
            );
        }

        return null;
    }


}
