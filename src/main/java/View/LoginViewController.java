package View;

import Controller.LoginController;
import Model.UsuarioLogado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginViewController {

    LoginController loginController = new LoginController();
    int matricula;
    String senha;

    @FXML
    private TextField campoSenha;

    @FXML
    private TextField campoMatricula;

    public LoginViewController() throws SQLException {
    }

    @FXML

    public void loginAtendente() throws SQLException, IOException {

        if (campoMatricula.getText().isBlank() || campoSenha.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText(null);
            alert.setContentText("Preencha a matrícula e a senha.");
            alert.showAndWait();

            return;
        }
        matricula = Integer.parseInt(campoMatricula.getText());
        senha = campoSenha.getText();
        UsuarioLogado usuarioLogado = loginController.iniciarAtendimentoFX(matricula, senha);

        if (usuarioLogado != null) {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/MenuAtendimento.fxml"));
            Parent root = loader.load();
            MenuViewController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);
            Stage stage = (Stage) campoMatricula.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Matrícula ou senha incorretas.");
            alert.showAndWait();
        }
    }

    @FXML
    public void loginAdmin() throws SQLException, IOException {

        if (campoMatricula.getText().isBlank() || campoSenha.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText(null);
            alert.setContentText("Preencha a matrícula e a senha.");
            alert.showAndWait();

            return;
        }
        matricula = Integer.parseInt(campoMatricula.getText());
        senha = campoSenha.getText();

        UsuarioLogado usuarioLogado =
                loginController.iniciarAdminFX(matricula, senha);

        if (usuarioLogado != null) {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/MenuAdmin.fxml"));

            Parent root = loader.load();

            MenuAdminViewController controller = loader.getController();
            controller.setUsuarioLogado(usuarioLogado);

            Stage stage = (Stage) campoMatricula.getScene().getWindow();
            stage.getScene().setRoot(root);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Usuário sem permissão de administrador ou dados incorretos");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

}


