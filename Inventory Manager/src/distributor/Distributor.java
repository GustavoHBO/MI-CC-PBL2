/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributor;

import distributor.controller.Controller;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class Distributor {
    
            /* Statement of the Variables */
    private DatagramSocket socketDist;
    
    private final int PORTDIST = 55600;
    private final int LCDPROTOCOL = 4;
    private final int LCPROTOCOL = 2;
    private final int TIMEOUT = 500;
    
    private final String TOKENSEPARATOR = "!=";
    
    private Controller controllerDist;
    
    byte[] receiveData = null;
    byte[] sendData = null;
    
    /**
     * Method main, start the distributor.
     * @param args - Arguments.
     */
    public static void main(String[] args) {
        try {
            System.out.println("Distribuidor Iniciado!\n");
            new Distributor().startDistributor();
        } catch (IOException ex) {
            System.out.println("Error: Não foi possível iniciar o servidor.");
        }
    }
    
    /**
     * Start the receptions of the communications and process the all clients.
     * @throws SocketException - If the port chose is busy or occur other error.
     */
    private void startDistributor() throws SocketException, IOException {

        Thread thread;
        Runnable run;
        byte[] dataByte;

                /* Starting the socket in port choose */
        socketDist = new DatagramSocket(this.PORTDIST); // if the port is busy then a exception is launched.
        controllerDist = Controller.getInstance(); // Get the actual controller.
        controllerDist.readAllData();
        
        while (true) {
            dataByte = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(dataByte, dataByte.length);
            socketDist.receive(datagramPacket);
            System.out.println("INFO: Nova Conexão\n");
            System.out.println("Mensagem: "+(new String(datagramPacket.getData())));
            run = new Runnable() {
                @Override
                public void run() {
                    String data = new String(datagramPacket.getData());
                    InetAddress ipSender;
                    int portSender;
                    ipSender = datagramPacket.getAddress();
                    portSender = datagramPacket.getPort();
                    identifyAction(data, ipSender, portSender);
                }

                private void identifyAction(String data, InetAddress ipSender, int portSender) {
                    String initCode = data.substring(0, LCPROTOCOL);
                    int lastCodeIndex = data.lastIndexOf(initCode);
                    if (lastCodeIndex == 0) {
                        return;
                    }
                    String endCode = data.substring(lastCodeIndex, lastCodeIndex + LCPROTOCOL);
                    if (initCode.equals(endCode)) {
                        data = data.substring(LCPROTOCOL, lastCodeIndex);
                        switch (initCode) {
                            case "D0":// Test the connection.
                                sendDatagramPacket("0xD0reply0xD0", ipSender, portSender);
                                break;
                            case "D1":// Register a server.
                                try {
                                    int i = controllerDist.registerServer(data);
                                    if(i == 0){
                                        System.out.println("INFO: Um server tentou se registrar novamente.");
                                        sendDatagramPacket("0xD1notregistered0xD1", ipSender, portSender);
                                    } else {
                                        System.out.println("INFO: Um novo server foi registrado.");
                                        sendDatagramPacket("0xD1registered0xD1", ipSender, portSender);
                                    }
                                } catch (IOException ex) {
                                    System.out.println("ERROR: Não foi possível armazenar os dados.");
                                }
                                
                                break;
                            case "D2":// Solicitation a server.
                                String server;
                                InetAddress ip;
                                int j;
                                for (int i = 0; i < controllerDist.amountServers(); i++) {
                                    try {
                                        server = controllerDist.getServer();
                                        ip = InetAddress.getByName(server.substring(0, server.indexOf(TOKENSEPARATOR)));
                                        j = sendTestDatagramPacket(ip, portSender);
                                        if (j == 1) {
                                            sendDatagramPacket(server.split(TOKENSEPARATOR)[0], ipSender, portSender);
                                            System.out.println("Ip do server" );
                                            return;
                                        }
                                    } catch (UnknownHostException ex) {
                                        System.out.println("ERROR: Não foi possível identificar um servidor registrado anteriormente.");
                                    }
                                }
                                break;
                            case "D8":
                                break;
                            case "D9":
                                break;
                            default:
                                break;
                        }
                    }
                }
            };
            thread = new Thread(run);
            thread.start();
        }

    }
    
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
            socketDist.send(sendPacket);
            System.out.println("Enviando: " + data + "\nPara: " + ip +":"+port);
        } catch (IOException ex) {
            System.out.println("ERROR: Um pacote não pode ser enviado.");
        }
    }
    
    /**
     * Send an data string test for a client with ip and a port.
     * @param data - Data to send.
     * @param ip - Ip of destiny.
     * @param port - Port of destiny.
     */
    private int sendTestDatagramPacket(InetAddress ip, int port) {
        try {
            String data = "0x00testconnection0x00";
            String dataReply;
            DatagramSocket socketTester = new DatagramSocket();
            socketTester.setSoTimeout(TIMEOUT);
            DatagramPacket sendPacket;
            DatagramPacket receivePacket;
            byte[] receiveByte = new byte[1024];
            receivePacket = new DatagramPacket(receiveByte, receiveByte.length);
            sendData = data.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
            System.out.println("INFO: Testando conexão com o servidor");
            System.out.println("IP: " + ip.getHostAddress() + " Port: " + port);
            for(int i = 0; i < 5; i++){
                System.out.println("Testando: 0" + i);
                socketTester.send(sendPacket);
                try{
                socketTester.receive(receivePacket);
                dataReply = new String(receivePacket.getData());
                    if(dataReply.equals("0x00connected0x00")){
                        return 1;
                    }
                } catch (SocketException e){
                    System.out.println("AVISO: Host não conectado.");
                    if(i < 4){
                        System.out.println("AVISO: Testando novamente.");
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR: Um pacote não pode ser enviado.");
        }
        return 0;
    }
}
