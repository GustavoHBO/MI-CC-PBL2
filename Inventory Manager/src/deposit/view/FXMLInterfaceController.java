/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deposit.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author gustavo
 */
public class FXMLInterfaceController implements Initializable {

    /* Statement of Pane */
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneRegister;

    /* Statement of TextField's */
 /* Login */
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldCnpjLogin;

    /* Register */
    @FXML
    private TextField textFieldSocialName;
    @FXML
    private TextField textFieldNumber;
    @FXML
    private TextField textFieldCpf;
    @FXML
    private TextField textFieldFantasyName;
    @FXML
    private TextField textFieldCnpj;
    @FXML
    private TextField textFieldLocationX;
    @FXML
    private TextField textFieldLocationY;

    /* Statement of Label's */
    @FXML
    private Label labelError;
    @FXML
    private Label labelErrorLogin;

    /* Statement of Button's */
 /* Login Buttons */
    @FXML
    private Button buttonRegisterLogin;
    @FXML
    private Button buttonLogin;

    /* Register Buttons */
    @FXML
    private Button buttonRegister;
    @FXML
    private Button buttonCancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /* Button Events Login */
    /**
     * Button Event register, turn the pane login in invisible and the pane
     * register in visible.
     */
    @FXML
    public void eventButtonRegisterLogin() {
        paneRegister.setVisible(true);
    }

    /**
     * Button Event Login, verify the fields cnpj and password, if the data is
     * correct the login is done.
     */
    @FXML
    public void eventButtonLogin() {
        if (textFieldCnpjLogin.getText().trim().isEmpty()) {
            labelErrorLogin.setText("ERROR: CNPJ inválido.");
            labelErrorLogin.setVisible(true);
        } else if (passwordFieldPassword.getText().trim().isEmpty()) {
            labelErrorLogin.setText("ERROR: Senha inválida.");
            labelErrorLogin.setVisible(true);
        } else {
            labelErrorLogin.setVisible(false);
        }
    }

    /* Button Events Register */
    /**
     * Button Event Register, if the information is correct the register is
     * done.
     */
    public void eventButtonRegister() {
        
        if (textFieldSocialName.getText().trim().isEmpty()) {
            labelError.setText("ERROR: Razão Social inválido.");
            labelError.setVisible(true);
        } else if (textFieldNumber.getText().trim().isEmpty()) {
            labelError.setText("ERROR: Número inválido.");
            labelError.setVisible(true);
        } else if (textFieldCpf.getText().trim().isEmpty()) {
            labelError.setText("ERROR: CPF inválido.");
            labelError.setVisible(true);
        } else if (textFieldFantasyName.getText().trim().isEmpty()) {
            labelError.setText("ERROR: Nome Fantasia inválido.");
            labelError.setVisible(true);
        } else if (textFieldCnpj.getText().trim().isEmpty()) {
            labelError.setText("ERROR: CNPJ inválido.");
            labelError.setVisible(true);
        } else if (textFieldLocationX.getText().trim().isEmpty()) {
            labelError.setText("ERROR: Posição X inválida.");
            labelError.setVisible(true);
        } else if (textFieldLocationY.getText().trim().isEmpty()) {
            labelError.setText("ERROR: Posição Y inválida.");
            labelError.setVisible(true);
        } else {
            labelError.setVisible(false);
        }
    }

    /**
     * Button Event Cancel, when clicked the pane is set invisible.
     */
    @FXML
    public void eventButtonCancel() {
        paneRegister.setVisible(false);
    }
}
