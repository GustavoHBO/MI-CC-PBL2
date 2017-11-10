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
    @FXML
    private Pane paneManager;
    @FXML
    private Pane paneCreateProcuct;

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
    @FXML
    private PasswordField passwordFieldPass;
    @FXML
    private PasswordField passwordFieldPass2;
    
        /* Create Product */
    @FXML
    private TextField textFieldNameProduct;
    @FXML
    private TextField textFieldAmountProduct;
    @FXML
    private TextField textFieldPriceProduct;

    /* Statement of Label's */
        /* Register */
    @FXML
    private Label labelError;
        /* Login */
    @FXML
    private Label labelErrorLogin;
        /* Create Product */
    @FXML
    private Label labelErrorCreateProduct;

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
    
        /* Manager */
    @FXML
    private Button buttonCreate;
    @FXML
    private Button buttonChange;
    @FXML
    private Button buttonRemove;
    
        /* Create Product */
    @FXML
    private Button buttonCreateProduct;
    @FXML
    private Button buttonCancelProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
            paneManager.setVisible(true);
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
        } else if (passwordFieldPass.getText().trim().isEmpty()) {
            labelError.setText("ERROR: Digite a senha.");
            labelError.setVisible(true);
        } else if (!passwordFieldPass.getText().equals(passwordFieldPass2.getText())) {
            labelError.setText("ERROR: As senhas não conferem.");
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
    
                                            /* Button Events Create Product */
    
    /**
     * Button Event Create Product, when clicked the pane "paneCreateProcuct" is
     * set visible.
     */
    @FXML
    public void eventButtonCreate() {
        paneCreateProcuct.setVisible(true);
    }
    
    /**
     * Button Event Cancel Product, when clicked the pane "paneCreateProcuct" is set invisible.
     */
    @FXML
    public void eventButtonCancelProduct() {
        paneCreateProcuct.setVisible(false);
    }
    
    /**
     * Button Event Create Product, when clicked the fields name, amount and price is verified.
     */
    @FXML
    public void eventButtonCreateProduct(){
        if(textFieldNameProduct.getText().trim().isEmpty()){
            labelErrorCreateProduct.setText("ERROR: Digite um nome válido.");
            labelErrorCreateProduct.setVisible(true);
        } else if(textFieldAmountProduct.getText().trim().isEmpty()){
            labelErrorCreateProduct.setText("ERROR: Digite uma quantidade de produtos.");
            labelErrorCreateProduct.setVisible(true);
        } else if(textFieldPriceProduct.getText().trim().isEmpty()){
            labelErrorCreateProduct.setText("ERROR: Digite um preço.");
            labelErrorCreateProduct.setVisible(true);
        } else {
            labelErrorCreateProduct.setVisible(false);
        }
    }
}