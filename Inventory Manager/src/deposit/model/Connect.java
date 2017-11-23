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
package deposit.model;

import client.util.IConnect;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class Connect implements IConnect{

    private final int PORTDIST = 55000;
    
    private final int LENGTHCODEPROTOCOL = 2;
    private final int LENGTHCODEPROTOCOLSERVER = 4;
    
            /* Connection UDP */
    private DatagramSocket socketUDP;
    private DatagramPacket receivePacket;
    
            /* Connection TCP */
    private ServerSocket socketTCP;
    private Socket client;
    
    /**
     * Constructor of the class.
     * @throws SocketException - If the socket can't to been created.
     */
    public Connect() throws SocketException{
        socketUDP = new DatagramSocket(PORTDIST);
    }
    
    /**
     * Send the packet using the connection UDP.
     * @param data - Data to send.
     * @param ip - Ip of the destiny.
     * @param port - Port of the destiny.
     */
    @Override
    public void sendDatagramPacket(String data, String ip, int port) {
        DatagramPacket packet;
        try {
            packet = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName(ip), port);
            socketUDP.send(packet);
        } catch (UnknownHostException ex) {
            System.out.println("ERROR: O Host não foi reconhecido.");
        } catch (IOException ex) {
            System.out.println("ERROR: Não foi possível enviar um datagram packet.");
        }
    }

    /**
     * Receiver the packet using the connection UDP.
     *
     * @return data - Received data.
     */
    @Override
    public String receiveDatagramPacket() {

        byte[] receiveData;

        receiveData = new byte[1024];
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            socketUDP.receive(receivePacket);
            return new String(receivePacket.getData());
        } catch (IOException ex) {
            System.out.println("ERROR: Não foi possível receber os dados.");
        }
        return null;
    }

    /**
     * Send the data using the connection TCP.
     * @param data - Data to send.
     */
    @Override
    public void sendData(String data) {
        if(client == null){
            return;
        } else if (client.isConnected()){
            return;
        } else {
            try {
                PrintStream saida = new PrintStream(client.getOutputStream());
                saida.println(data);
            } catch (IOException ex) {
                System.out.println("ERROR: Não foi possível enviar os dados.");
            }
        }
    }

    /**
     * Receiver the data using the connection TCP.
     *
     * @return data - Received data.
     */
    @Override
    public String receiveData(String ip, int port) {
        if (client == null) {
            return null;
        } else {
            Scanner s;
            try {
                s = new Scanner(client.getInputStream());
                while (s.hasNextLine()) {
                    return s.nextLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * Connect with the server.
     * @param ip - Ip of server.
     * @param port - Port of the server.
     * @throws UnknownHostException - If the host is unknown.
     * @throws IOException - If the connection can't to be connect.
     */
    public void connect(String ip, int port) throws UnknownHostException, IOException {
        client = new Socket(InetAddress.getByName(ip), port);
    }
}
