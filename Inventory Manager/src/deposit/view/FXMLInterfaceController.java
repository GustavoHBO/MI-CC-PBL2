/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deposit.view;

import deposit.controller.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

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
    private Pane paneCreateProduct;
    @FXML
    private Pane paneChangeProduct;

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
    @FXML
    private TextField textFieldIdProduct;
        /* Change Product */
    @FXML
    private TextField textFieldChangeNameProduct;
    @FXML
    private TextField textFieldChangeAmountProduct;
    @FXML
    private TextField textFieldChangePriceProduct;
    @FXML
    private TextField textFieldChangeIdProduct;

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
        /* Change Product */
    @FXML
    private Label labelErrorChangeProduct;

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
        /* Change Product */
    @FXML
    private Button buttonChangeProduct;
    @FXML
    private Button buttonCancelChangeProduct;

    Controller controllerDeposit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controllerDeposit = Controller.getInstance();
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
            
            try {
                controllerDeposit.readAllData();
            } catch (IOException ex) {
                /* If there is an error reading the stored data */
                JOptionPane.showMessageDialog(null, "Ouve erro ao tentar recuperar os produtos já cadastrados.");
            }
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
    
                                            /* Button Events Manager */
    /**
     * Button Event Create Product, when clicked the pane "paneCreateProcuct" is
     * set visible.
     */
    @FXML
    public void eventButtonCreate() {
        paneCreateProduct.setVisible(true);
    }
    /**
     * Button Event Change Product, when clicked the pane "paneChangeProcuct" is
     * set visible.
     */
    @FXML
    public void eventButtonChange() {
        paneChangeProduct.setVisible(true);
    }
    
                                            /* Button Events Create Product */

    /**
     * Button Event Cancel Product, when clicked the pane "paneCreateProcuct" is set invisible.
     */
    @FXML
    public void eventButtonCancelProduct() {
        paneCreateProduct.setVisible(false);
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
            labelErrorCreateProduct.setVisible(true);
        } else if(textFieldIdProduct.getText().trim().isEmpty() || !textFieldIdProduct.getText().matches("[0-9]+")){
            labelErrorCreateProduct.setText("ERROR: Digite um ID válido.");
            labelErrorCreateProduct.setVisible(true);
        } else {
            labelErrorCreateProduct.setVisible(false);
            try {
                controllerDeposit.registerProduct(textFieldIdProduct.getText().trim(), textFieldNameProduct.getText().trim(), textFieldPriceProduct.getText().trim(), textFieldAmountProduct.getText().trim());
            } catch (IOException ex) {
                Logger.getLogger(FXMLInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
                                                /* Button Events Change Product */
    /**
     * Button Event Change, when clicked will modify the data of the product, using the id.
     */
    public void eventButtonChangeProduct() {
        if (textFieldChangeNameProduct.getText().trim().isEmpty()) {
            labelErrorChangeProduct.setText("ERROR: Digite um nome válido.");
            labelErrorChangeProduct.setVisible(true);
        } else if (textFieldChangeAmountProduct.getText().trim().isEmpty()) {
            labelErrorChangeProduct.setText("ERROR: Digite uma quantidade de produtos.");
            labelErrorChangeProduct.setVisible(true);
        } else if (textFieldChangePriceProduct.getText().trim().isEmpty()) {
            labelErrorChangeProduct.setText("ERROR: Digite um preço.");
            labelErrorChangeProduct.setVisible(true);
            labelErrorChangeProduct.setVisible(true);
        } else if (textFieldChangeIdProduct.getText().trim().isEmpty() || !textFieldChangeIdProduct.getText().matches("[0-9]+")) {
            labelErrorChangeProduct.setText("ERROR: Digite um ID válido.");
            labelErrorChangeProduct.setVisible(true);
        } else {
            labelErrorChangeProduct.setVisible(false);
            try {
                int inf = controllerDeposit.changeProduct(textFieldChangeIdProduct.getText().trim(), textFieldChangeNameProduct.getText().trim(), textFieldChangePriceProduct.getText().trim(), textFieldChangeAmountProduct.getText().trim());
                if (inf == 1) {
                    JOptionPane.showMessageDialog(null, "Produto alterado!");
                } else {
                    JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Button Event Cancel Product, when clicked the pane "paneCreateProcuct" is set invisible.
     */
    @FXML
    public void eventButtonCancelChangeProduct() {
        paneChangeProduct.setVisible(false);
    }
}