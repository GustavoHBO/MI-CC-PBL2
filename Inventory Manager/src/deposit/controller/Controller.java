/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deposit.controller;

import deposit.model.Product;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class control the Inventory Manager.
 * @author Gustavo Henrique.
 */
public class Controller {
    
                                /* Statement of Variables */
    /* Instance of controller */
    private static Controller controller;
    
    private final String STRINGSAVESEPARATOR = "!=";
    
    private ArrayList<Product> listProduct;// List with all the products registered.
    
                            /* Design Pattern Singleton */
    
    /* The constructor is private for use the singleton */
    private Controller(){
        listProduct = new ArrayList<>();
    }
    
    /**
     * Return the instance of controller.
     * @return controller - An instance.
     */
    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }
    
    /**
     * Reset the controller.
     */
    public static void resetController(){
        controller = new Controller();
    }
    
                                                /* End Singleton */
    
                                                /* Methods of control */
    
    /**
     * Register the product created with the parameters received.
     * @param id - Identifier of the product.
     * @param name - Name of the product.
     * @param price - Price of the product.
     * @param amount - Amount of the product that is on inventory.
     * @return 0 - If the product is registered, 1 - If the product was registered.
     * @throws java.io.IOException - If can't save the data.
     */
    public int registerProduct(String id, String name, String price, String amount) throws IOException{
        
        Product product = new Product(id, name, price, Integer.parseInt(amount));
        
        if(getListProduct() == null)// If the list is null, create a new list.
            listProduct = new ArrayList<>();
        
        if (findProduct(id) != null) {
            return 0;
        } else {
            getListProduct().add(product);
            saveAllData();
            return 1;
        }
    }
    
    /**
     * This method will modify the data of the product, using the id.
     * @param id - Id of the product.
     * @param name - The name of the product.
     * @param price - The price of the product.
     * @param amount - The amount of the product.
     * @return 0 - Case the product not has be identify.
     * @throws java.io.IOException - Case the archive don't can be read.
     */
    public int changeProduct(String id, String name, String price, String amount) throws IOException {

        Product product;

        if (getListProduct() == null) {// If the list is null, create a new list.
            listProduct = new ArrayList<>();
            return 0;
        }

        product = findProduct(id);

        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            product.setAmount(Integer.parseInt(amount));
            saveAllData();
            return 1;
        }
        return 0;
    }
    
    /**
     * Remove the product with id equal the code.
     * @param code - Identifier of the product.
     * @return 0 - If the product not exist, 1 - If the product was excluded.
     * @throws java.io.IOException - If the file of backup can't be created.
     */
    public int removeProduct(String code) throws IOException{
        Iterator<Product> it;
        Product product;
        if(getListProduct() == null){
            return 0;
        } else {
            product = findProduct(code);
            if(product == null){
                return 0;
            }
            getListProduct().remove(product); // Remove the product.
            saveAllData();
            return 1;
        }
    }
    
    /**
     * Return the product with name or id equal the code.
     * @param code - Id or name of the product.
     * @return null - If the product not exists, product - If the product exist.
     */
    public Product findProduct(String code) {
        if (getListProduct() == null) {
            return null;
        } else if (getListProduct().isEmpty()) {
            return null;
        } else {
            Iterator<Product> it = getListProduct().iterator();
            Product product;
            while(it.hasNext()){
                product = it.next();
                if(product.getId().equals(code) || product.getName().equals(code)){// Verify if the product have the same id or name of the code.
                    return product;
                }
            }
        }
        return null;
    }
    
                                                /* Methods in storage */
    /**
     * Save the data in an archive .im
     * @return 0 - If the list is null or empty, 1 case the data was save.
     * @throws IOException - If the archive can't be create.
     */
    public int saveAllData() throws IOException {
        OutputStream os;
        OutputStreamWriter osw;
        BufferedWriter bw;
        Iterator<Product> it;
        Product product;

        File file = new File("./backup/deposit/product");
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File("./backup/deposit/product/data.im");
        if (file.exists()) {// Delete the archive for that to save the new.
            file.delete();
            file.createNewFile();
        }
        
        if(getListProduct() == null){
            return 0;
        } else if (getListProduct().isEmpty()){
            return 0;
        } else {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            it = getListProduct().iterator();
            while (it.hasNext()) {
                product = it.next();
                bw.write(product.getId());
                bw.write(STRINGSAVESEPARATOR);
                bw.write(product.getName());
                bw.write(STRINGSAVESEPARATOR);
                bw.write(product.getPrice());
                bw.write(STRINGSAVESEPARATOR);
                bw.write(Integer.toString(product.getAmount()));
                if(it.hasNext()){// If have next, then create new line for next product.
                    bw.newLine();
                }
            }
            bw.close();
            osw.close();
            os.close();
        }
        return 1;
    }

    /**
     * Read all data in storage and put in a list.
     * @throws IOException - If the file can't to be read.
     */
    public void readAllData() throws IOException {
        File file = new File("./backup/deposit/product/data.im");
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

            if (getListProduct() == null) {
                listProduct = new ArrayList<>();
            }

            while (bufferedReader.ready()) {
                dataLine = bufferedReader.readLine();
                dataLineSplited = dataLine.split(STRINGSAVESEPARATOR);
                product = new Product(dataLineSplited[0], dataLineSplited[1], dataLineSplited[2], Integer.parseInt(dataLineSplited[3]));
                getListProduct().add(product);
                System.out.println(product.getName());
            }
            bufferedReader.close();
            fileReader.close();
        }
    }

    /**
     * Return the list with the products registered.
     * @return the listProduct - List of products.
     */
    public ArrayList<Product> getListProduct() {
        return listProduct;
    }
}
