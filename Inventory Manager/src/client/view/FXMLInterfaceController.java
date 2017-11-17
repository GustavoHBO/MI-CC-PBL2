/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Control the interface of the client, allowed buys.
 * @author Gustavo Henrique
 */
public class FXMLInterfaceController implements Initializable{
    
        /* Statement Pane's */
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneRegister;
    @FXML
    private Pane paneStore;
    
        /* Statement Button's */
            /* Login */
    @FXML
    private Button buttonLoginLogin;
    @FXML
    private Button buttonRegisterLogin;
    
            /* Register */
    @FXML
    private Button buttonRegisterRegister;
    @FXML
    private Button buttonCancelRegister;
    
            /* Store */
    @FXML
    private Button buttonSearchStore;
    @FXML
    private Button buttonSeeCartStore;
    @FXML
    private Button buttonAddCartStore;
    @FXML
    private Button buttonCancelStore;
    
        /* Statement TextField's */
            /* Login */
    @FXML
    private TextField textFieldCnpjLogin;
    
            /* Register */
    @FXML
    private TextField textFieldNameRegister;
    @FXML
    private TextField textFieldNumberRegister;
    @FXML
    private TextField textFieldPositionXRegister;
    @FXML
    private TextField textFieldPositionYRegister;
    @FXML
    private TextField textFieldCpfRegister;

            /* Store */
    @FXML
    private TextField textFieldSearchStore;
    
    
        /* Statement PasswordField */
            /* Login */
    @FXML
    private PasswordField passwordFieldPassLogin;
    
            /* Register */
    @FXML
    private PasswordField passwordFieldPassRegister;
    @FXML
    private PasswordField passwordFieldRePassRegister;
    
        /* Statement Label */
            /* Login */
    @FXML
    private Label labelErrorLogin;
    
            /* Register */
    @FXML
    private Label labelErrorRegister;
    
        /* Statement TableView */
            /* Store */
    @FXML
    private TableView tableViewProductsStore;
    
        /* Statement TableView */
            /* Store */
    @FXML
    private TableColumn tableColumnIdStore;
    @FXML
    private TableColumn tableColumnNameStore;
    @FXML
    private TableColumn tableColumnPriceStore;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
                                    /* Event's */
                                     /* Login */
    /**
     * Button Event, get the data in fields and verify the parameters.
     */
    @FXML
    private void eventButtonLoginLogin(){
        if(textFieldCnpjLogin.getText().trim().isEmpty()){
            labelErrorLogin.setText("ERROR: CNPJ inválido.");
            labelErrorLogin.setVisible(true);
        } else if (passwordFieldPassLogin.getText().trim().isEmpty()){
            labelErrorLogin.setText("ERROR: Senha inválida.");
            labelErrorLogin.setVisible(true);
        } else {
            labelErrorLogin.setVisible(false);
        }
    }
    
    /**
     * Button Event Register, turn the pane register in visible.
     */
    @FXML
    private void eventButtonRegisterLogin(){
        paneRegister.setVisible(true);
    }
    
                                      /* Register */
    
    /**
     * Button Event Register Register, get the data in the fields and verify, if all is ok, then they are send to server.
     */
    @FXML
    private void eventButtonRegisterRegister(){
        if(textFieldNameRegister.getText().trim().isEmpty()){
            labelErrorRegister.setText("ERROR: Nome inválido.");
            labelErrorRegister.setVisible(true);
        } else if (textFieldNumberRegister.getText().trim().isEmpty()){
            labelErrorRegister.setText("ERROR: Número inválido.");
            labelErrorRegister.setVisible(true);
        } else if (textFieldPositionXRegister.getText().trim().isEmpty()){
            labelErrorRegister.setText("ERROR: Posição X inválida.");
            labelErrorRegister.setVisible(true);
        } else if (textFieldPositionYRegister.getText().trim().isEmpty()){
            labelErrorRegister.setText("ERROR: Posição Y inválida.");
            labelErrorRegister.setVisible(true);
        } else if (textFieldCpfRegister.getText().trim().isEmpty()){
            labelErrorRegister.setText("ERROR: CPF inválido.");
            labelErrorRegister.setVisible(true);
        } else if (passwordFieldPassRegister.getText().trim().isEmpty()){
            labelErrorRegister.setText("ERROR: Senha inválida.");
            labelErrorRegister.setVisible(true);
        } else if (!passwordFieldPassRegister.getText().trim().equals(passwordFieldRePassRegister.getText().trim())){
            labelErrorRegister.setText("ERROR: As senhas não correspondem.");
            labelErrorRegister.setVisible(true);
        } else {
            labelErrorRegister.setVisible(false);
        }
    }
    
    /**
     * Button Event Cancel, turn the paneRegister and labelErrorRegister in invisible.
     */
    @FXML
    private void eventButtonCancelRegister(){
        paneRegister.setVisible(false);
        labelErrorRegister.setVisible(false);
    }
}
