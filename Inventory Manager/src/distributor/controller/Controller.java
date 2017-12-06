/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributor.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Control the actions these server.
 * @author Gustavo Henrique.
 */
public class Controller implements Serializable{
    
                                 /* Statement of Variables */
    /* Instance of controller */
    private static Controller controller;
    
    private final String STRINGSAVESEPARATOR = "!=";
    
    private ArrayList<String> listServers;
    
    private Iterator<String> iteratorDist;
    
                            /* Design Pattern Singleton */
    
    /* The constructor is private for use the singleton */
    private Controller(){
        listServers = new ArrayList<>();
        try {
            readAllData();
        } catch (IOException ex) {
            System.out.println("ERROR: Não foi possível ler os dados armazenados.");
        }
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
        controller = null;
    }
    
                                                /* End Singleton */
                                                    /* Methods */
    
    /**
     * Register the server.
     * @param server - Server to register.
     * @return 0 - If the server can't be register, 1 - Is the server was registered.
     * @throws java.io.IOException - If the register can't be saved.
     */
    public int registerServer(String server) throws IOException{
        if(findServer(server) == null){
            listServers.add(server);
            saveAllData();
            return 1;
        }
        return 0;
    }

    /**
     * Search the server using the data of the server.
     * @param server - Server to search.
     * @return null - If the server not exist, server - If the server exist.
     */
    private String findServer(String server){
        Iterator<String> it = listServers.iterator();
        
        while(!listServers.isEmpty()){
            if(it.next().equals(server)){
                return server;
            } else if(!it.hasNext()){
                return null;
            }
        }
        return null;
    }
    
    /**
     * Return the serve that will to receive the connection.
     * @return null - If don't have servers registered, server - Server that will to receive the connection.
     */
    public String getServer(){
        if(iteratorDist == null || !iteratorDist.hasNext()){
            iteratorDist = listServers.iterator();
        }
        return iteratorDist.next();
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
        Iterator<String> it;

        File file = new File("./backup/distributor/servers");
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File("./backup/distributor/servers/data.im");
        if (file.exists()) {// Delete the archive for that to save the new.
            file.delete();
        }
        file.createNewFile();
        
        if(listServers == null){
            return 0;
        } else if (listServers.isEmpty()){
            return 0;
        } else {
            os = new FileOutputStream(file);
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            it = listServers.iterator();
            while (it.hasNext()) {
                bw.write(it.next());
                if(it.hasNext()){// If have next, then create new line for next server.
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
        File file = new File("./backup/distributor/servers/data.im");
        if (file.exists()) {
            FileReader fileReader;
            fileReader = new FileReader(file);// FileReader it read the archive.
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(fileReader);//BufferedReader it store the data read in a buffer.

            if (listServers == null) {
                listServers = new ArrayList<>();
            }

            while (bufferedReader.ready()) {
                listServers.add(bufferedReader.readLine());
            }
            bufferedReader.close();
            fileReader.close();
        }
    }
}
