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
 * "Portions Copyrighted 2017 Gautstafr Inc."
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
 * Contributor(s): Gustavo Henrique
 */
package client.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class Controller {
    
    
                                /* Statement of Variables */
    
    /* Instance of controller */
    private static Controller controller;
    
    /* Communication UDP */
    private DatagramSocket socketUDP;
    
    byte[] receiveData = null;
    byte[] sendData = null;
    
    /* Communication TCP */
    private Socket socketTCP;
    
    private final String TOKENSEPARATOR = "!=";
    
    private final int LCDPROTOCOL = 4;
    private final int LCPROTOCOL = 2;
    private final int TIMEOUT = 5; // Time in seconds.
    private final int TIMETRY = 3;
    
    private String ipDist = "127.0.0.1";
    private int portDist = 55600;
    
    private String ipServer = null;
    private int portServer = 0;
    
                            /* Design Pattern Singleton */
    
    /* The constructor is private for use the singleton */
    private Controller(){
        try {
            getServer();
        } catch (SocketException ex) {
            System.out.println("Error: Erro ao tentar obter um servidor.");
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
    
                                                /* Methods of control */
    
    /**
     * This method is responsible for get the id and the password for do login on server.
     * @param loginID - Identify code.
     * @param pass - Password.
     * @return 1 - The login is done, 1 - If the login is wrong.
     * @throws java.io.IOException - If the connection can't be started.
     */
    public int login(String loginID, String pass) throws IOException{
        getServer();
        
        if(ipServer != null){
            sendTimeoutDatagramPacket("", ipServer, portServer);
        }
        
        return 0;
    }
    
    /**
     * Register the client on server
     * @param cpf - CPF
     * @param name - Name
     * @param number - Number
     * @param posX - Position X
     * @param posY - Position Y
     * @param password - Password
     * @throws java.net.SocketException - If the connection can't be established.
     */
    public void registerClient(String cpf, String name, String number, String posX, String posY, String password) throws SocketException {

        String reply;
        getServer();
        if (ipServer != null) {
            /* Send all data of the client */
            System.out.println("Cheguei aqui");
            reply = sendTimeoutDatagramPacket("02" + cpf + TOKENSEPARATOR
                    + name + TOKENSEPARATOR
                    + number + TOKENSEPARATOR
                    + posX + TOKENSEPARATOR
                    + posY + TOKENSEPARATOR
                    + password + "02", ipServer, portServer);
            if (reply.equals("registered")) {

            }
        }
    }
    
                                                  /* Communications */
                                                  
                                                        /* UDP */
    
    /**
     * Get the server using the distributor.
     */
    private void getServer() throws SocketException {

        Runnable run;
        Thread thread;
        
        socketUDP = new DatagramSocket();
        
        run = new Runnable() {
            @Override
            public void run() {
                byte[] receiveData = new byte[1024];
                DatagramPacket datagramPacket;
                datagramPacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    socketUDP.setSoTimeout(TIMEOUT * 1000);
                    for (int i = 0; i < TIMETRY; i++) {
                        System.out.println("INFO: Tentando conectar ao distribuidor.");
                        try {
                            sendDatagramPacket("D2getserverD2", ipDist, portDist);
                            socketUDP.receive(datagramPacket);
                            String data = new String(datagramPacket.getData());

                            System.out.println("Recebido: " + data);

                            String initCode = data.substring(0, LCDPROTOCOL);
                            data = data.substring(LCDPROTOCOL);
                            int lastCodeIndex = data.lastIndexOf(initCode);
                            if (lastCodeIndex == 0) {
                                return;
                            }
                            String endCode = data.substring(lastCodeIndex, lastCodeIndex + LCDPROTOCOL);
                            data = data.substring(0, lastCodeIndex);
                            if (initCode.equals(endCode)) {
                                if (data.equals("withoutserver")) { // If the distributor not have a server connected.
                                    ipServer = null;
                                    portServer = 0;
                                } else {
                                    String[] dataSplited = data.split(TOKENSEPARATOR);
                                    System.out.println("IP: " + dataSplited[0] + ", Port: " + dataSplited[1]);
                                    ipServer = dataSplited[0];
                                    portServer = Integer.parseInt(dataSplited[1]);
                                }
                            }
                            return;
                        } catch (SocketTimeoutException e) {
                            System.out.println("INFO: Tentativa " + i + 1 + " falhou.");
                            if (i < 4) {
                                System.out.println("INFO: Distribuidor não disponível.");
                                return;
                            }
                        }
                    }

                } catch (IOException ex) {
                    System.out.println("ERROR: A packet can't be sent");
                }
            }
        };

        thread = new Thread(run);
        thread.start();
    }
    
    
    /**
     * Send an data string for a client with ip and a port.
     * @param data - Data to send.
     * @param ip - Ip of destiny.
     * @param port - Port of destiny.
     */
    private void sendDatagramPacket(String data, String ip, int port) {
        try {
            DatagramPacket sendPacket;
            sendData = data.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ip), port);
            socketUDP.send(sendPacket);
            System.out.println("Enviando: " + data);
        } catch (IOException ex) {
            System.out.println("ERROR: A packet don't can to be send");
        }
    }
    
    /**
     * Send an data string test for a client with ip and a port.
     * @param data - Data to send.
     * @param ip - Ip of destiny.
     * @param port - Port of destiny.
     */
    private String sendTimeoutDatagramPacket(String data, String ip, int port) {
        System.out.println("Enviando: " + data);
        try {
            String dataReply;
            InetAddress ipSend = InetAddress.getByName(ip);
            DatagramSocket socketTester = new DatagramSocket();
            socketTester.setSoTimeout(TIMEOUT * 1000);
            DatagramPacket sendPacket;
            DatagramPacket receivePacket;
            byte[] receiveByte = new byte[1024];
            receivePacket = new DatagramPacket(receiveByte, receiveByte.length);
            sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length, ipSend, port);
            for (int i = 0; i < 3; i++) {
                socketTester.send(sendPacket);
                try {
                    socketTester.receive(receivePacket);
                    System.out.println("Recebi: " + new String(receivePacket.getData()));
                    dataReply = new String(receivePacket.getData());
                    return dataReply;
                } catch (SocketTimeoutException e) {
                    if (i >= 4) {
                        System.out.println("AVISO: Host não conectado.");
                        getServer();
                    } else {
                        System.out.println("AVISO: Testando novamente.");
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR: Um pacote não pode ser enviado.");
        }
        return null;
    }

    
    
    
                                                        /* TCP */
    
    /**
     * Send the data using the communication TCP.
     * @param data - Data to send.
     * @throws IOException - Case the communication can't be started.
     */
    private void sendData(String data) throws IOException {

        if (ipServer != null && portServer != 0) {

            socketTCP = new Socket(ipServer, portServer);
            PrintStream outPut = new PrintStream(socketTCP.getOutputStream());
            outPut.write(data.getBytes());
            
            System.out.println("Enviado: " + data);
        }
    }
}
