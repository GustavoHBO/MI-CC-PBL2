/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2017 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 */
package server.controller;

import server.model.Deposit;
import server.model.Client;
import server.model.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This controller control the connections of the server with the clients and
 * deposits.
 *
 * @author Gustavo Henrique
 */
public class Controller {

    /* Instace static for establish the design patter Singleton */
    private static Controller instance;
    
    /* Statement of variables */
    
    private final String TOKENSEPARATOR = "!=";
    private final int LENGTHSERVERPROTOCOL = 4;
    private final int LENGTHPROTOCOL = 2;
    
    private ArrayList<Deposit> listDeposit;
    private ArrayList<Product> listProduct;
    private ArrayList<Client> listClient;
    
    private ArrayList<String> listAssociationTable;// Association the product with deposits.
    private ArrayList<String> listAssociationTableProductReserve;// Association the product reserved.

    private int posX;
    private int posY;
    

    /* Turn the constructor in private for use only an instance of the class */
    private Controller() {
        listClient = new ArrayList<>();
        listProduct = new ArrayList<>();
        listClient = new ArrayList<>();
        listAssociationTable = new ArrayList<>();
        listAssociationTableProductReserve = new ArrayList<>();
    }

    /**
     * Return the instance of the class.
     * @return instance - Instance of the class.
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Reset the instance of controller.
     */
    public void resetController() {
        instance = null;
    }

                                   /* Control */
                                   /* Client */
    /**
     * Register the client if he not was registered.
     * @param cpf - CPF of the client;
     * @param nome - Name of the client;
     * @param numero - Number of the client;
     * @param posX - Position in x of the client;
     * @param posY - Position in y of the client;
     * @param password - Password of the client;
     * @return 0 - If the client not was registered, 1 - If the client was registered.
     * @throws java.io.IOException - If the data can't be stored.
     */
    public int registerClient(String cpf, String nome, String numero, String posX, String posY, String password) throws IOException{
        Client client;
        if(findClient(cpf) == null){
            client = new Client(cpf, nome, numero, posX, posY, password);
            
            /* This code below is only explain the communication */
            
            /* Start code explain */
            System.out.println("Cadastrando novo cliente:");
            
            System.out.println("Cpf: " + cpf);
            System.out.println("Nome: " + nome);
            System.out.println("Numero: " + numero);
            System.out.println("PosiçãoX: " + posX);
            System.out.println("PosiçãoY: " + posY);
            System.out.println("Senha: " + password);
            System.out.println("Nome: " + nome);
            
            /* End code explain */
            
            getListClient().add(client);
            saveClient();
            return 1;
        }
        return 0;
    }
    
    /**
     * Search the client using the cpf him.
     * @param cpf - CPF of the client.
     * @return null - If the client with this cpf not exist, client - If the client exist.
     */
    private Client findClient(String cpf){
        Iterator<Client> it = getListClient().iterator();
        Client client;
        while(it.hasNext()){
            client = it.next();
            if(client.getCpf().equals(cpf)){
                return client;
            }
        }
        return null;
    }
                                  /* Deposit */
    
    /**
     * Register the deposit if he not was registered.
     * @param cpf - CPF of the deposit;
     * @param cnpj - Name of the deposit;
     * @param nameSocial - Number of the deposit;
     * @param fantasyName - Number of the deposit;
     * @param number - Number of the deposit;
     * @param posX - Position in x of the deposit;
     * @param posY - Position in y of the deposit;
     * @param password - Password of the deposit;
     * @return 0 - If the deposit not was registered, 1 - If the deposit was registered.
     */
    public int registerDeposit(String cpf, String cnpj, String nameSocial, String fantasyName, String number, String password, int posX, int posY){
        Deposit deposit;
        if(findDeposit(cpf) == null){
            deposit = new Deposit(cpf, cnpj, fantasyName, fantasyName, number, password, posX, posY);
            
            /* This code below is only explain the communication */
            
            /* Start code explain */
            System.out.println("\nCadastrando novo Deposito:");
            
            System.out.println("Cpf: " + cpf);
            System.out.println("CNPJ: " + cnpj);
            System.out.println("Nome Social: " + nameSocial);
            System.out.println("Nome Fantasia: " + fantasyName);
            System.out.println("Senha: " + password);
            System.out.println("Numero: " + number);
            System.out.println("PosiçãoX: " + posX);
            System.out.println("PosiçãoY: " + posY);
            System.out.println("");
            
            /* End code explain */
            
            getListDeposit().add(deposit);
            return 1;
        }
        return 0;
    }
    
    /**
     * Search the deposit using the cnpj him.
     * @param cnpj - CNPJ of the deposit.
     * @return null - If the deposit with this cnpj not exist, deposit - If the deposit exist.
     */
    private Deposit findDeposit(String cnpj){
        Iterator<Deposit> it = getListDeposit().iterator();
        Deposit deposit;
        while(it.hasNext()){
            deposit = it.next();
            if(deposit.getCnpj().equals(cnpj)){
                return deposit;
            }
        }
        return null;
    }
    
    /**
     * Search for the product using your ID and your deposit ID.
     * @param cnpj - CNPJ of the deposit.
     * @return null - If the product with this id not exist or if deposit with this id not exist, product - If the product exist on deposit.
     */
    private Product findProductAssociation(String idProduct, String idDeposit){
        Iterator<String> it = getListAssociationTable().iterator();
        Iterator<Product> itP = getListProduct().iterator();
        String associationString;
        String[] associationStringSplited;
        Product product;
        while(it.hasNext()){
            associationString = it.next();
            associationStringSplited = associationString.split(TOKENSEPARATOR);
            if(associationStringSplited[0].equals(idDeposit)){
                while(itP.hasNext()){
                    product = itP.next();
                    if(product.getId().equals(idProduct)){
                        return product;
                    }
                }
            }
        }
        return null;
    }
    
    private int removeProduct(String idProduct, int amount, String idDeposit) {
        Product product;

        product = findProductAssociation(idProduct, idDeposit);
        if (product.getAmount() > amount) {
            product.setAmount(product.getAmount() - amount);
            return 1;
        }
        return 0;
    }
    
    private void reserveProduct(String idProduct, int amount, String cpf){
    }
    
                                    /* Store Data */
    
    /**
     * Save the data in an archive .im
     * @throws IOException - If the archive can't be create.
     */
    public void saveAllData() throws IOException {
        saveProduct();
        saveClient();
        saveDeposit();
    }
    
    /**
     * Save the data of the products.
     * @throws IOException - If the file cannot be created.
     */
    private void saveProduct() throws IOException{
        OutputStream os;
        OutputStreamWriter osw;
        BufferedWriter bw;
        Iterator<Product> it;
        Product product;

        File file = new File("./backup/server/product");
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File("./backup/server/product/data.im");
        if (file.exists()) {// Delete the archive for that to save the new.
            file.delete();
        }
        file.createNewFile();
        
        if(getListProduct() == null || getListProduct().isEmpty()){
            return;
        } else {
            /* Storage Products */
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            it = getListProduct().iterator();
            bw.write(Product.getIdentifier());// Write the actual identifier.
            bw.newLine();
            while (it.hasNext()) {
                product = it.next();
                bw.write(product.getIdRegister());
                bw.write(TOKENSEPARATOR);
                bw.write(product.getId());
                bw.write(TOKENSEPARATOR);
                bw.write(product.getName());
                bw.write(TOKENSEPARATOR);
                bw.write(product.getPrice());
                bw.write(TOKENSEPARATOR);
                bw.write(Integer.toString(product.getAmount()));
                if(it.hasNext()){// If have next, then create new line for next product.
                    bw.newLine();
                }
            }
            bw.close();
            osw.close();
            os.close();
        }
    }
    
    /**
     * Save the data of the clients.
     * @throws IOException - If the file cannot be created.
     */
    private void saveClient() throws IOException{
        OutputStream os;
        OutputStreamWriter osw;
        BufferedWriter bw;
        Iterator<Client> it;
        Client client;

        File file = new File("./backup/server/client");
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File("./backup/server/client/data.im");
        if (file.exists()) {// Delete the archive for that to save the new.
            file.delete();
        }
        file.createNewFile();
        
        if(getListClient() == null || getListClient().isEmpty()){
            return;
        } else {
            /* Storage Client */
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            it = getListClient().iterator();
            bw.write(Client.getIdentifier());// Write the actual identifier.
            bw.newLine();
            while (it.hasNext()) {
                client = it.next();
                bw.write(client.getIdRegister());
                bw.write(TOKENSEPARATOR);
                bw.write(client.getCpf());
                bw.write(TOKENSEPARATOR);
                bw.write(client.getNome());
                bw.write(TOKENSEPARATOR);
                bw.write(client.getNumero());
                bw.write(TOKENSEPARATOR);
                bw.write(client.getPassword());
                bw.write(TOKENSEPARATOR);
                bw.write(client.getPosX());
                bw.write(TOKENSEPARATOR);
                bw.write(client.getPosY());
                if(it.hasNext()){// If have next, then create new line for next product.
                    bw.newLine();
                }
            }
            bw.close();
            osw.close();
            os.close();
        }
    }
    
    /**
     * Save the data of the deposit.
     * @throws IOException - If the file cannot be created.
     */
    private void saveDeposit() throws IOException{
        OutputStream os;
        OutputStreamWriter osw;
        BufferedWriter bw;
        Iterator<Deposit> it;
        Deposit deposit;
        
        File file = new File("./backup/server/deposit");
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File("./backup/server/deposit/data.im");
        if (file.exists()) {// Delete the archive for that to save the new.
            file.delete();
        }
        file.createNewFile();
        
        if(getListDeposit() == null || getListDeposit().isEmpty()){
            return;
        } else {
            /* Storage Client */
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            it = getListDeposit().iterator();
            bw.write(Product.getIdentifier());// Write the actual identifier.
            bw.newLine();
            while (it.hasNext()) {
                deposit = it.next();
                bw.write(deposit.getIdRegister());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getCpf());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getCnpj());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getSocialName());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getFantasyName());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getNumber());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getPassword());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getPosX());
                bw.write(TOKENSEPARATOR);
                bw.write(deposit.getPosY());
                if(it.hasNext()){// If have next, then create new line for next product.
                    bw.newLine();
                }
            }
            bw.close();
            osw.close();
            os.close();
        }
    }

    /**
     * Read all data in storage and put in a list.
     * @throws IOException - If the file can't to be read.
     */
    public void readAllData() throws IOException {
        readProductData();
        readClientData();
    }
    
    /**
     * Read the data of the product on archive and put in list.
     * @throws FileNotFoundException - If the file not exist.
     * @throws IOException - If the file can't be read.
     */
    private void readProductData() throws FileNotFoundException, IOException{
        File file = new File("./backup/server/product/data.im");
        if (!file.exists()) {
            //Return, because the else catch the rest.
        } else {
            FileReader fileReader;
            fileReader = new FileReader(file);// FileReader it read the archive.
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(fileReader);//BufferedReader it store the data read in a buffer.
            Product product;
            String dataLine;
            String[] dataLineSplited;

            if (getListProduct() == null)
                listProduct = new ArrayList<>();
            if(bufferedReader.ready())
                Product.setIdentifier(Integer.parseInt(bufferedReader.readLine()));
            while (bufferedReader.ready()) {
                dataLine = bufferedReader.readLine();
                dataLineSplited = dataLine.split(TOKENSEPARATOR);
                product = new Product();
                product.setIdRegister(Integer.parseInt(dataLineSplited[0]));
                product.setId(dataLineSplited[1]);
                product.setName(dataLineSplited[2]);
                product.setPrice(dataLineSplited[3]);
                product.setAmount(Integer.parseInt(dataLineSplited[4]));
                getListProduct().add(product);
            }
            bufferedReader.close();
            fileReader.close();
        }
    }
    
    /**
     * Read the data of the client on archive and put in list.
     * @throws FileNotFoundException - If the file not exist.
     * @throws IOException - If the file can't be read.
     */
    private void readClientData() throws FileNotFoundException, IOException{
        File file = new File("./backup/server/client/data.im");
        if (file.exists()){
            FileReader fileReader;
            fileReader = new FileReader(file);// FileReader it read the archive.
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(fileReader);//BufferedReader it store the data read in a buffer.
            Client client;
            String dataLine;
            String[] dataLineSplited;

            if (getListProduct() == null)
                listProduct = new ArrayList<>();
            if(bufferedReader.ready())
                Product.setIdentifier(Integer.parseInt(bufferedReader.readLine()));
            while (bufferedReader.ready()) {
                dataLine = bufferedReader.readLine();
                dataLineSplited = dataLine.split(TOKENSEPARATOR);
                client = new Client();
                client.setIdRegister(Integer.parseInt(dataLineSplited[0]));
                client.setCpf(dataLineSplited[1]);
                client.setNome(dataLineSplited[2]);
                client.setNumero(dataLineSplited[3]);
                client.setPassword(dataLineSplited[4]);
                client.setPosX(dataLineSplited[5]);
                client.setPosY(dataLineSplited[6]);
                getListClient().add(client);
            }
            bufferedReader.close();
            fileReader.close();
        }
    }
    
                                  /* Encapsulate fields */
    
    /**
     * Return the TOKENSEPARATOR.
     * @return the TOKENSEPARATOR
     */
    public String getTOKENSEPARATOR() {
        return TOKENSEPARATOR;
    }

    /**
     * Return the position X.
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Set the position X.
     * @param posX the posX to set
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Return the position Y.
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set the position Y.
     * @param posY the posY to set
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    /**
     * Return the list with the products registered.
     * @return the listProduct - List of products.
     */
    public ArrayList<Product> getListProduct() {
        return listProduct;
    }

    /**
     * @return the listClient
     */
    public ArrayList<Client> getListClient() {
        return listClient;
    }

    /**
     * @return the listDeposit
     */
    public ArrayList<Deposit> getListDeposit() {
        return listDeposit;
    }

    /**
     * @param listDeposit the listDeposit to set
     */
    public void setListDeposit(ArrayList<Deposit> listDeposit) {
        this.listDeposit = listDeposit;
    }

    /**
     * @return the listAssociationTable
     */
    public ArrayList<String> getListAssociationTable() {
        return listAssociationTable;
    }

    /**
     * @param listAssociationTable the listAssociationTable to set
     */
    public void setListAssociationTable(ArrayList<String> listAssociationTable) {
        this.listAssociationTable = listAssociationTable;
    }
}
