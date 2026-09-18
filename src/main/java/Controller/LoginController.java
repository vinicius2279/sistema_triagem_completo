package Controller;

import DAO.LoginDAO;
import Model.UsuarioLogado;
import java.sql.SQLException;

public class LoginController {

    LoginDAO loginDAO = new LoginDAO();

    public LoginController() throws SQLException {
    }

    public UsuarioLogado iniciarAtendimentoFX(int matricula, String senha) throws SQLException {


            UsuarioLogado usuarioLogado =
                    loginDAO.autenticar(matricula, senha);

            if (usuarioLogado != null) {
                return usuarioLogado;
            }

        return null;
    }

    public UsuarioLogado iniciarAdminFX(int matricula, String senha)
            throws SQLException {

        UsuarioLogado usuario =
                loginDAO.autenticar(matricula, senha);

        if (usuario != null && usuario.isAdmin()) {
            return usuario;
        }

        return null;
    }

    }



