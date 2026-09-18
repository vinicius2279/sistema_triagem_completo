package View;

import Controller.*;
import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuAdminViewController {

    private UsuarioLogado usuarioLogado;

    private final AdminController adminController = new AdminController();

    private final FuncionarioController funcionarioController = new FuncionarioController();

    private final PacienteController pacienteController = new PacienteController();

    public MenuAdminViewController() throws SQLException {
    }

    @FXML
    public void initialize() {

        colunaMatriculaFuncionario.setCellValueFactory(
                new PropertyValueFactory<>("matricula")
        );

        colunaNomeFuncionario.setCellValueFactory(
                new PropertyValueFactory<>("nomeFuncionario")
        );

        colunaAdmin.setCellValueFactory(
                new PropertyValueFactory<>("admin")
        );

        colunaIdPaciente.setCellValueFactory(
                new PropertyValueFactory<>("idPaciente")
        );

        colunaNomePaciente.setCellValueFactory(
                new PropertyValueFactory<>("nomePaciente")
        );

        colunaCpfPaciente.setCellValueFactory(
                new PropertyValueFactory<>("cpf")
        );

        colunaIdadePaciente.setCellValueFactory(
                new PropertyValueFactory<>("idade")
        );

        colunaConvenioPaciente.setCellValueFactory(
                new PropertyValueFactory<>("convenio")
        );
    }


    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoSenha;
    @FXML
    private CheckBox campoAdmin;
    @FXML
    private TableColumn<Funcionario, Integer> colunaMatriculaFuncionario;

    @FXML
    private TableColumn<Funcionario, Boolean> colunaAdmin;
    @FXML
    private TableColumn<Funcionario, String> colunaNomeFuncionario;

    @FXML
    private TableColumn<Paciente, Integer> colunaIdPaciente;

    @FXML
    private TableColumn<Paciente, String> colunaNomePaciente;

    @FXML
    private TableColumn<Paciente, String> colunaCpfPaciente;

    @FXML
    private TableColumn<Paciente, Integer> colunaIdadePaciente;

    @FXML
    private TableColumn<Paciente, String> colunaConvenioPaciente;

    public void setUsuarioLogado(UsuarioLogado usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    @FXML
    private TableView<Funcionario> tableFuncionarios;

    @FXML
    private TableView<Paciente> tablePacientes;

    @FXML
    private TextField campoNomePaciente;

    @FXML
    private TextField campoCpfPaciente;

    @FXML
    private TextField campoIdadePaciente;

    @FXML
    private TextField campoConvenioPaciente;

    @FXML
    private void adicionarFuncionario() {

        try {

            String nome = campoNome.getText();
            String senha = campoSenha.getText();
            boolean admin = campoAdmin.isSelected();
            adminController.cadastrarFuncionario(nome, senha, admin);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Funcionário cadastrado com sucesso.");
            alert.showAndWait();

            listarFuncionarios();

        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(
                    "Não foi possível cadastrar o funcionário.\n" +
                            e.getMessage()
            );
            alert.showAndWait();
        }
    }

    @FXML
    private void editarFuncionario() {

        Funcionario selecionado = tableFuncionarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um funcionário.");
            alert.showAndWait();
            return;
        }

        try {

            String novoNome = campoNome.getText();
            if (!novoNome.isBlank()) {
                selecionado.setNomeFuncionario(novoNome);
            }

            boolean admin = campoAdmin.isSelected();

            String novaSenha = campoSenha.getText();

            boolean tentouAlterar =
                    !campoNome.getText().isBlank()
                            || !campoAdmin.isSelected();

            if (tentouAlterar && novaSenha.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Informe a senha para realizar alterações.");
                alert.showAndWait();
                return;
            }
                adminController.editarFuncionario(
                        selecionado,
                        novaSenha,
                        admin
                );

            listarFuncionarios();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Funcionário atualizado com sucesso.");
            alert.showAndWait();

        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(
                    "Não foi possível atualizar o funcionário.\n" +
                            e.getMessage()
            );
            alert.showAndWait();
        }
    }

    @FXML
    private void excluirFuncionario() {

        Funcionario selecionado =
                tableFuncionarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um funcionário.");
            alert.showAndWait();
            return;
        }

        try {
            funcionarioController.deletarFuncionario(selecionado);
            tableFuncionarios.getItems().remove(selecionado);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Funcionário excluído com sucesso.");
            alert.showAndWait();

        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(
                    "Não foi possível excluir o funcionário.\n"
            );
            alert.showAndWait();
        }
    }


    @FXML
    private void listarFuncionarios() throws SQLException {

        List<Funcionario> funcionarios = funcionarioController.listarFuncionarios();
        tableFuncionarios.getItems().setAll(funcionarios);
    }

    @FXML
    private void listarPacientes() throws SQLException {

        List<Paciente> pacientes =
                pacienteController.listarPacientes();

        tablePacientes.getItems().setAll(pacientes);
    }

    @FXML
    private void editarPaciente() {

        Paciente selecionado =
                tablePacientes.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um paciente.");
            alert.showAndWait();
            return;
        }

        try {

            String novoNome = campoNomePaciente.getText();
            String novoCpf = campoCpfPaciente.getText();
            String novaIdade = campoIdadePaciente.getText();
            String novoConvenio = campoConvenioPaciente.getText();

            if (!novoNome.isBlank()) {
                selecionado.setNomePaciente(novoNome);
            }

            if (!novoCpf.isBlank()) {
                selecionado.setCpf(novoCpf);
            }

            if (!novaIdade.isBlank()) {
                selecionado.setIdade(
                        Integer.parseInt(novaIdade)
                );
            }

            if (!novoConvenio.isBlank()) {
                selecionado.setConvenio(novoConvenio);
            }

            pacienteController.editarPaciente(selecionado);

            listarPacientes();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Paciente atualizado com sucesso.");
            alert.showAndWait();

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("A idade deve conter apenas números.");
            alert.showAndWait();

        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(
                    "Não foi possível atualizar o paciente.\n" +
                            e.getMessage()
            );
            alert.showAndWait();
        }
    }
@FXML
    public void excluirPaciente() {

        Paciente selecionado = tablePacientes.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um paciente.");
            alert.showAndWait();
            return;
        }
        try {
            pacienteController.deletarPaciente(selecionado);
            tablePacientes.getItems().remove(selecionado);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Paciente excluído com sucesso.");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(
                    "Não foi possível excluir o paciente.\n" +
                            e.getMessage());
            alert.showAndWait();
        }

        }



    @FXML
    private void voltarMenu(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/Login.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.getScene().setRoot(root);
    }

}