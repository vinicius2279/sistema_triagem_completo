package View;

import Controller.*;
import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class MenuViewController {

    int matricula;

    private UsuarioLogado usuarioLogado;

    AtendimentoController atendimentoController =
            new AtendimentoController();

    // =========================
    // TABELAS
    // =========================

    @FXML
    private TableView<Atendimento> tableAguardando;

    @FXML
    private TableView<Atendimento> tableFinalizados;


    // =========================
    // COLUNAS - AGUARDANDO
    // =========================

    @FXML
    private TableColumn<Atendimento, String> colunaPacienteAguardando;

    @FXML
    private TableColumn<Atendimento, String> colunaFuncionarioAguardando;

    @FXML
    private TableColumn<Atendimento, ClassificacaoRisco> colunaClassificacaoAguardando;

    @FXML
    private TableColumn<Atendimento, LocalDateTime> colunaDataAguardando;

    @FXML
    private TableColumn<Atendimento, StatusAtendimento> colunaStatusAguardando;

    @FXML
    private TableColumn<Atendimento, String> colunaSintomasAguardando;


    // =========================
    // COLUNAS - FINALIZADOS
    // =========================

    @FXML
    private TableColumn<Atendimento, String> colunaPacienteFinalizados;

    @FXML
    private TableColumn<Atendimento, String> colunaFuncionarioFinalizados;

    @FXML
    private TableColumn<Atendimento, ClassificacaoRisco> colunaClassificacaoFinalizados;

    @FXML
    private TableColumn<Atendimento, LocalDateTime> colunaDataFinalizados;

    @FXML
    private TableColumn<Atendimento, StatusAtendimento> colunaStatusFinalizados;

    @FXML
    private TableColumn<Atendimento, String> colunaSintomasFinalizados;


    public MenuViewController() throws SQLException {
    }


    public void initialize() {

        configurarColunasAguardando();
        configurarColunasFinalizados();

        configurarCores(tableAguardando);
        configurarCores(tableFinalizados);

        try {
            atualizarTabelas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void configurarColunasAguardando() {

        colunaPacienteAguardando.setCellValueFactory(
                new PropertyValueFactory<>("nomePaciente"));

        colunaFuncionarioAguardando.setCellValueFactory(
                new PropertyValueFactory<>("nomeFuncionario"));

        colunaClassificacaoAguardando.setCellValueFactory(
                new PropertyValueFactory<>("classificacao"));

        colunaDataAguardando.setCellValueFactory(
                new PropertyValueFactory<>("data"));

        colunaSintomasAguardando.setCellValueFactory(
                new PropertyValueFactory<>("sintomasTexto"));

        colunaStatusAguardando.setCellValueFactory(
                new PropertyValueFactory<>("status"));
    }


    private void configurarColunasFinalizados() {

        colunaPacienteFinalizados.setCellValueFactory(
                new PropertyValueFactory<>("nomePaciente"));

        colunaFuncionarioFinalizados.setCellValueFactory(
                new PropertyValueFactory<>("nomeFuncionario"));

        colunaClassificacaoFinalizados.setCellValueFactory(
                new PropertyValueFactory<>("classificacao"));

        colunaDataFinalizados.setCellValueFactory(
                new PropertyValueFactory<>("data"));

        colunaSintomasFinalizados.setCellValueFactory(
                new PropertyValueFactory<>("sintomasTexto"));

        colunaStatusFinalizados.setCellValueFactory(
                new PropertyValueFactory<>("status"));
    }


    private void configurarCores(TableView<Atendimento> tabela) {

        tabela.setRowFactory(tv -> new TableRow<Atendimento>() {

            @Override
            protected void updateItem(
                    Atendimento atendimento,
                    boolean empty) {

                super.updateItem(atendimento, empty);

                getStyleClass().removeAll(
                        "linha-vermelha",
                        "linha-amarela",
                        "linha-verde",
                        "linha-azul"
                );

                if (empty || atendimento == null) {
                    return;
                }

                switch (atendimento.getClassificacao()) {

                    case VERMELHA:
                        getStyleClass().add("linha-vermelha");
                        break;

                    case AMARELA:
                        getStyleClass().add("linha-amarela");
                        break;

                    case VERDE:
                        getStyleClass().add("linha-verde");
                        break;

                    case AZUL:
                        getStyleClass().add("linha-azul");
                        break;
                }
            }
        });
    }


    private void atualizarTabelas() throws SQLException {

        listarAguardando();
        listarFinalizados();
    }


    private void listarAguardando() throws SQLException {

        List<Atendimento> atendimentoAguardando =
                atendimentoController.listarFila();

        tableAguardando
                .getItems()
                .setAll(atendimentoAguardando);
    }


    private void listarFinalizados() throws SQLException {

        List<Atendimento> atendimentoFinalizados =
                atendimentoController.atendimentosFinalizados();

        tableFinalizados
                .getItems()
                .setAll(atendimentoFinalizados);
    }


    @FXML
    private void finalizarAtendimento() throws SQLException {

        Atendimento selecionado =
                tableAguardando
                        .getSelectionModel()
                        .getSelectedItem();

        if (selecionado == null) {
            return;
        }

        atendimentoController
                .finalizarAtendimento(selecionado);

        atualizarTabelas();
    }


    @FXML
    private void novoAtendimentoFX(
            javafx.event.ActionEvent event)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/View/NovoAtendimento.fxml"));

        Parent root = loader.load();

        NovoAtendimentoViewController controller =
                loader.getController();

        controller.setMatricula(
                usuarioLogado.getMatricula());

        Node node = (Node) event.getSource();

        Stage stage =
                (Stage) node.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }


    public void setUsuarioLogado(
            UsuarioLogado usuarioLogado) {

        this.usuarioLogado = usuarioLogado;
    }


    @FXML
    private void voltarMenu(
            javafx.event.ActionEvent event)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/View/Login.fxml")
        );

        Parent root = loader.load();

        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        stage.getScene().setRoot(root);
    }
}