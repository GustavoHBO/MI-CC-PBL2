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

/**
 *
 * @author gustavo
 */
public class Distributor {
    
            /* Statement of the Variables */
    private DatagramSocket socketDist;
    private DatagramPacket datagramPacket;
    
    private final int PORTDIST = 55600;
    private final int LCDPROTOCOL = 4;
    private final int LCPROTOCOL = 2;
    
    private final String TOKESEPARATOR = "!=";
    
    private Controller controllerDist;
    
    byte[] receiveData = null;
    byte[] sendData = null;
    
    /**
     * Method main, start the distributor.
     * @param args - Arguments.
     */
    public static void main(String[] args) {
        try {
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
        
        //sendDatagramPacket("0x00NONE0x00", InetAddress.getByName("localhost"), 55000);// The UDP isn't sending the first message.

        while (true) {
            dataByte = new byte[1024];
            datagramPacket = new DatagramPacket(dataByte, dataByte.length);
            socketDist.receive(datagramPacket);

            run = new Runnable() {
                @Override
                public void run() {
                    String data;
                    InetAddress ipSender;
                    int portSender;
                    data = new String(datagramPacket.getData());
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
                        System.out.println("Recebido:" + data);
                        switch (initCode) {
                            case "D0":// Test the connection.
                                sendDatagramPacket("0xD0reply0xD0", ipSender, portSender);
                                break;
                            case "D1":// Register a server.
                                try {
                                    int i = controllerDist.registerServer(data);
                                    if(i == 0){
                                        System.out.println("INFO: Um server tentou se registrar novamente.");
                                    } else {
                                        System.out.println("INFO: Um novo server foi registrado.");
                                    }
                                } catch (IOException ex) {
                                    System.out.println("ERROR: Não foi possível armazenar os dados.");
                                }
                                sendDatagramPacket("0xD1registered0xD1", ipSender, portSender);
                                break;
                            case "D2":// Solicitation a server.
                                sendDatagramPacket("D2" + controllerDist.getServer() + "D2", ipSender, portSender);
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
}
