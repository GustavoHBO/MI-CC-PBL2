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
package server;

import server.controller.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server, receive the connections of the clients and processes.
 * @author Gustavo Henrique
 */
public class Server {
    
    private Controller controllerServer = null;
    
            /* Port UDP */
    private final int PORTSERVER = 55601;
    
            /* Port TCP */
    private final int PORTTCP = 55500;
    
    private final int LENGTHCODEPROTOCOL = 2;
    private final int LENGTHCODEPROTOCOLSERVER = 4;
    
    private final String TOKENSEPARATOR = "!=";
    
    private String ipDist;
    private int portDist;
    
    
            /* Connection UDP */
    private DatagramSocket serverSocket = null;
    
            /* Connection TCP */
    private ServerSocket socketTCP;
    
    byte[] receiveData = null;
    byte[] sendData = null;
    
    public static void main(String args[]) {
        Server server;
        try {
            server = new Server();
            server.startServerUDP();
            server.startServerTCP();
        } catch (SocketException ex) {
            System.out.println("ERROR: Não é possível estabelecer qualquer conexão!");
        } catch (UnknownHostException ex) {
            System.out.println("ERROR: O Host não é reconhecido!");
        } catch (IOException ex) {
            System.out.println("ERROR: Não foi possível estabelecer a comunicação TCP.");
        }
    }
    /*_______________________________________________________________________________________________________________*/
    /**
     * Start the server, and register him on cloud.
     * @throws SocketException - If the ip or port is in user.
     * @throws UnknownHostException  - Case the host be unknown.
     */
    private void startServerUDP() throws SocketException, UnknownHostException {
        String reply;
        controllerServer = Controller.getInstance(); // Get the instance of the controller.
        serverSocket = new DatagramSocket(PORTSERVER); // Start the ServerSocket on port selected.
        receiveData = new byte[1024]; // Array of the bytes to storage the datagram received.
        
        /* Now, the server will send a packet to distribuitor for register the server*/
        sendDatagramPacket(mountDataRegisterCloud(), InetAddress.getByName(ipDist), portDist);
        reply = replyServer();
        
        if(reply.equals("registered")){
            System.out.println("INFO: Servidor registrado.");
        } else {
            System.out.println("INFO: Servidor já registrado anteriormente.");
        }
        
        while (true) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                
                Runnable run;
                run = new Runnable() {
                    @Override
                    public void run() {
                        String data = new String(receivePacket.getData());
                        identifyAction(data);
                    }
                    
                    private void identifyAction(String data) {
                        InetAddress ipSender = receivePacket.getAddress();
                        int portSender = receivePacket.getPort();
                        String initCode = data.substring(0, LENGTHCODEPROTOCOL);
                        String endCode;
                        int lastCodeIndex = data.lastIndexOf(initCode);
                        if (lastCodeIndex == 0) {
                            return;
                        }
                        endCode = data.substring(lastCodeIndex, lastCodeIndex + LENGTHCODEPROTOCOL);
                        if (initCode.equals(endCode)) {
                            data = data.substring(LENGTHCODEPROTOCOL, lastCodeIndex);
                            System.out.println("Recebido: " + data);
                            switch (initCode) {
                                case "00":// Test the connection with it.
                                    sendDatagramPacket("0xS0connected0xS0", receivePacket.getAddress(), receivePacket.getPort());
                                    break;
                                case "01":
                                    String[] dataSplited = data.split(TOKENSEPARATOR);
                                    String reply = "";
                                    int i;
                                    try {
                                        i = controllerServer.registerClient(dataSplited[0], dataSplited[1], dataSplited[2], dataSplited[3], dataSplited[4], dataSplited[5]);
                                        if(i == 1){
                                            System.out.println("INFOR: Novo cliente registrado.");
                                            reply = "0xS1registered0xS1";
                                        } else {
                                            System.out.println("INFOR: Cliente já registrado.");
                                            reply = "0xS1notregistered0xS1";
                                        }
                                        sendDatagramPacket(reply, ipSender, portSender);
                                    } catch (IOException ex) {
                                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                    break;
                                case "02":
                                    System.out.println("Registrar Cliente");
                                    String[] replySplited;
                                    replySplited = data.split(TOKENSEPARATOR);
                                    int registed;
                                    try {
                                        registed = controllerServer.registerClient(replySplited[0], replySplited[1], replySplited[2], replySplited[3], replySplited[4], replySplited[5]);
                                        if(registed == 1){
                                            sendDatagramPacket("0x02registed0x02", ipSender, portSender);
                                        } else {
                                            sendDatagramPacket("0x02notregisted0x02", ipSender, portSender);
                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    break;
                                case "09":
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                };
                new Thread(run).start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void startServerTCP() throws IOException {
        Thread thread;
        Runnable run;

        socketTCP = new ServerSocket(PORTTCP);
        while (true) {
            Socket client = socketTCP.accept();
            run = new Runnable() {
                @Override
                public void run() {
                    System.out.println("Iniciou conexão");
                }

                private void identifyAction(Scanner scanner) {

                }
            };
            thread = new Thread(run);
            thread.start();
        }
        
        
    }

    /*_______________________________________________________________________________________________________________*/
    /**
     * Send an data string for a client with ip and a port.
     * @param data - Data to send.
     * @param ip - Ip of destiny.
     * @param port - Port of destiny.
     */
    private void sendDatagramPacket(String data, InetAddress ip, int port) {
        try {
            DatagramPacket sendPacket;
            sendData = data.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
            serverSocket.send(sendPacket);
            System.out.println("Enviando: " + data);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*_______________________________________________________________________________________________________________*/
    /**
     * Return the reply of server.
     * @return string - Reply of server.
     */
    public String replyServer() {
        byte[] dataReceive;
        DatagramPacket packetReceive;
        String data = "";
        String codeProtocol, lastCodeProtocol;
        int indexLastCode;
        
        dataReceive = new byte[1024];
        packetReceive = new DatagramPacket(dataReceive, dataReceive.length);
        try {
            serverSocket.receive(packetReceive);//To await the reply of server.
            data = new String(packetReceive.getData());// Convert the data bytes for char.
            System.out.println("Chegou: " + data);
            if(data.trim().isEmpty() || data.length() < LENGTHCODEPROTOCOL *2){
                return "";
            }
            codeProtocol = data.substring(0, LENGTHCODEPROTOCOLSERVER);// Get the code of protocol.
            data = data.substring(LENGTHCODEPROTOCOLSERVER);// Cut the first code.
            indexLastCode = data.lastIndexOf(codeProtocol);// Get the last index of code.
            lastCodeProtocol = data.substring(indexLastCode, indexLastCode + LENGTHCODEPROTOCOLSERVER);// Get the last code.
            data = data.substring(0, indexLastCode);// Now data have only the data
            if (!codeProtocol.equals(lastCodeProtocol)) {// If the protocol is wrong the data is discarded.
                data = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);// Logger default
        }
        return data;
    }
    
    /*_______________________________________________________________________________________________________________*/
    /**
     * This method registers these server on cloud, sending the IP, Port, Position X and Position Y. This method ask some
     * data using the prompt.
     * @return dataSend - If the data inserted are valid, null - In case of an error in reading the input data.
     */
    public String mountDataRegisterCloud() {
        /* Now, the server will send a packet to cloud for register the ip */
        String dataSend = ""; // This will contains the ip.
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));// IOS - For read in terminal the ip and port of the cloud.
        try {
            System.out.println("Bem-Vindo!");
            String input;
            String myIp = "";
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface i = (NetworkInterface) e.nextElement();
                Enumeration ds = i.getInetAddresses();
                while (ds.hasMoreElements()) {
                    InetAddress myself = (InetAddress) ds.nextElement();
                    if (!myself.isLoopbackAddress() && myself.isSiteLocalAddress()) {
                        myIp = myself.getHostAddress();
                    }
                }
            }
            dataSend = "D1" + myIp + controllerServer.getTOKENSEPARATOR() + PORTSERVER + controllerServer.getTOKENSEPARATOR() + controllerServer.getPosX() + controllerServer.getTOKENSEPARATOR() + controllerServer.getPosY() + "D1";
            do {
                System.out.println("\nDigite o IP do servidor!");
                input = inFromUser.readLine();
            } while (!input.matches("\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}"));
            this.ipDist = input;
            input = "";
            do {
                System.out.println("\nDigite a PORTA do servidor!");
                input = inFromUser.readLine();
            } while (!input.matches("\\d{4}\\d?"));
            this.portDist = Integer.parseInt(input);
        } catch (IOException ex) {
            System.out.println("ERROR: Impossível ler os dados de entrada!");
        }
        return dataSend;
    }
}
