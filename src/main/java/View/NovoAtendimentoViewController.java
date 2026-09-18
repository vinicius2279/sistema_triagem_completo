package View;

import DAO.SintomaDAO;
import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NovoAtendimentoViewController {

    @FXML
    private TextField campoNome;

    @FXML
    private VBox boxSintomas;

    @FXML
    private TextField campoCpf;

    @FXML
    private TextField campoIdade;

    @FXML
    private TextField campoConvenio;

    private int matricula;

    private SintomaDAO sintomaDAO = new SintomaDAO();

    public NovoAtendimentoViewController() throws SQLException {
    }

    @FXML
    public void initialize() {

        try {

            List<Sintoma> sintomasDisponiveis =
                    sintomaDAO.listarTodos();

            for (Sintoma sintoma : sintomasDisponiveis) {

                CheckBox checkBox =
                        new CheckBox(sintoma.getDescricao());

                checkBox.setUserData(sintoma);

                boxSintomas.getChildren().add(checkBox);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void confirmarAtendimento() {

        try {

            String nome = campoNome.getText();
            String cpf = campoCpf.getText();
            String convenio = campoConvenio.getText();
            String idade = campoIdade.getText();

            List<Sintoma> sintomasSelecionados =
                    new ArrayList<>();

            for (Node node : boxSintomas.getChildren()) {

                CheckBox checkBox = (CheckBox) node;

                if (checkBox.isSelected()) {

                    Sintoma sintoma =
                            (Sintoma) checkBox.getUserData();

                    sintomasSelecionados.add(sintoma);
                }
            }

            Paciente paciente = new Paciente();

            paciente.setNomePaciente(nome);
            paciente.setIdade(Integer.parseInt(idade));
            paciente.setConvenio(convenio);
            paciente.setCpf(cpf);

            paciente.cadastrar();

            Atendimento atendimento = new Atendimento();

            atendimento.setMatricula(matricula);

            atendimento.setSintomas(sintomasSelecionados);

            atendimento.finalizarCadastro(paciente);

            Alert alert =
                    new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Sucesso");
            alert.setHeaderText(null);

            alert.setContentText(
                    "Atendimento cadastrado com sucesso!\n\n" +
                            "Paciente: " + paciente.getNomePaciente() +
                            "\nClassificação: " + atendimento.getClassificacao() +
                            "\nStatus: " + atendimento.getStatus()
            );

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Erro");
            alert.setHeaderText(null);

            alert.setContentText(
                    "Não foi possível cadastrar o atendimento.\n" +
                            e.getMessage()
            );

            alert.showAndWait();

            e.printStackTrace();
        }
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @FXML
    private void voltarMenu(javafx.event.ActionEvent event)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/View/Login.fxml")
        );

        Parent root = loader.load();

        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        stage.getScene().setRoot(root);
    }
}