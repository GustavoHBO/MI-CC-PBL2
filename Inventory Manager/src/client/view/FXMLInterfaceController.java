/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.controller.Controller;
import client.model.Product;
import deposit.model.ProductProperty;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

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
    @FXML
    private Pane paneSeeAddProduct;
    @FXML
    private Pane paneCart;
    
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
    
            /* See Product */
    @FXML
    private Button buttonAddToCartSeeProduct;
    @FXML
    private Button buttonCancelSeeProduct;
    
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
    
            /* See Product */
    @FXML
    private TextField textFieldAmountSeeProduct;
    
    
    
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
    
            /* See Product */
    @FXML
    private Label labelNameSeeProduct;
    @FXML
    private Label labelIdSeeProduct;
    
    
        /* Statement TableView */
            /* Store */
    @FXML
    private TableView<ProductProperty> tableViewProductsStore;
    
        /* Statement TableView */
            /* Store */
    @FXML
    private TableColumn<ProductProperty, String> tableColumnIdStore;
    @FXML
    private TableColumn<ProductProperty, String> tableColumnNameStore;
    @FXML
    private TableColumn<ProductProperty, String> tableColumnPriceStore;
    
        /* ObservableList */
        /* Show Product */
    private ObservableList<ProductProperty> observableListProduct;
    
    private Controller controllerShop;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerShop = Controller.getInstance();
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
            try {
                paneStore.setVisible(true);
                labelErrorLogin.setVisible(false);
                textFieldCnpjLogin.setText("");
                passwordFieldPassLogin.setText("");
                controllerShop.login(textFieldCnpjLogin.getText().trim(), passwordFieldPassLogin.getText().trim());
            } catch (IOException ex) {
                labelErrorLogin.setText("ERROR: A conexão não pode ser estabelecida.");
                labelErrorLogin.setVisible(true);
            }
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
            
            try{
                controllerShop.registerClient(textFieldCpfRegister.getText(), textFieldNameRegister.getText().trim(), textFieldNumberRegister.getText(), textFieldPositionXRegister.getText(), textFieldPositionYRegister.getText(), passwordFieldPassRegister.getText().trim());
            } catch (SocketException e){
                labelErrorRegister.setText("ERROR: Não foi possível se conectar.");
            }
        }
    }
    
    /**
     * Button Event Cancel, turn the paneRegister and labelErrorRegister in invisible.
     */
    @FXML
    private void eventButtonCancelRegister() {
        paneRegister.setVisible(false);
        labelErrorRegister.setVisible(false);
    }

                                /* Store */
    /**
     * Button event Search Store, search the products with the name or id past in TextField "textFieldSearchStore".
     */
    @FXML
    private void eventButtonSearchStore() {
        if (textFieldSearchStore.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID ou Nome inválido.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            
        }
    }
    
    /**
     * Button event See Cart, show the cart and the items that are in it. Show the pane "paneCart"
     */
    @FXML
    private void eventButtonSeeCartStore(){
        paneCart.setVisible(true);
    }
    
    /**
     * Button event Add Cart Store, add the product selected in table showing the pane "paneSeeAddProduct" for choose the amount.
     */
    @FXML
    private void eventButtonAddCartStore(){
//        ProductProperty p = tableViewProductsStore.getSelectionModel().getSelectedItem();
//        System.out.println(p.getId());
        paneSeeAddProduct.setVisible(true);
        
    }
    
    /**
     * Button event Cancel Store, turn the pane "paneStore" in invisible.
     */
    @FXML
    private void eventButtonCancelStore() {
        paneStore.setVisible(false);
    }
    
                                /* See Product */
    /**
     * Button event Add to Cart See Product, show the pane "paneSeeAddProduct" for the user can add most products in your cart.
     */
    @FXML
    private void eventButtonAddToCartSeeProduct() {
        
    }
    
    /**
     * Button event Cancel See Product, turn the pane "paneSeeAddProduct" in invisible.
     */
    @FXML
    private void eventButtonCancelSeeProduct() {
        paneSeeAddProduct.setVisible(false);
    }
    
    /**
     * Load the data on list of the products in a table.
     */
    @FXML
    public void loadDataTableView() {
        Iterator<Product> it;
        Product product;

        if (controllerShop.getListProduct() != null) {
            it = controllerShop.getListProduct().iterator();
            observableListProduct = FXCollections.observableArrayList();
            while (it.hasNext()) {
                product = it.next();
                observableListProduct.add(new ProductProperty(product.getId(), product.getName(), product.getPrice(), product.getAmount()));
            }
            tableColumnIdStore.setCellValueFactory(cellData -> cellData.getValue().getId());
            tableColumnNameStore.setCellValueFactory(cellData -> cellData.getValue().getName());
            tableColumnPriceStore.setCellValueFactory(cellData -> cellData.getValue().getPrice());

            tableViewProductsStore.setItems(observableListProduct);
        }
    }
}
