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
package client.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author gustavo
 */
public class Controller {
    
    
                                /* Statement of Variables */
    
    /* Instance of controller */
    private static Controller controller;
    
    private DatagramSocket socketControl;
    private DatagramPacket datagramPacket;
    
    byte[] receiveData = null;
    byte[] sendData = null;
    
    private final String TOKESEPARATOR = "!=";
    
    private final int LCDPROTOCOL = 4;
    private final int LCPROTOCOL = 2;
    
    private String ipDist = "127.0.0.1";
    private int portDist = 55660;
    
    private boolean getServer = true;
    
                            /* Design Pattern Singleton */
    
    /* The constructor is private for use the singleton */
    private Controller(){}
    
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
    
                                                  /* Communications */
    /**
     * Get the server using the distributor.
     */
    private void getServer() throws SocketException{
        socketControl = new DatagramSocket();
        
        TimerTask timerTask;
        Timer timer = new Timer(true);
        getServer = true;
        while(getServer){
            
            datagramPacket = new DatagramPacket(sendData, portDist);
            
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    sendDatagramPacket("D2serverD2", ipDist, portDist);
                    try {
                        socketControl.receive(datagramPacket);
                        String data = new String(datagramPacket.getData());
                        String initCode = data.substring(0, LCDPROTOCOL);
                        int lastCodeIndex = data.lastIndexOf(initCode);
                        if (lastCodeIndex == 0) {
                            return;
                        }
                        String endCode = data.substring(lastCodeIndex, lastCodeIndex + LCDPROTOCOL);
                        if (initCode.equals(endCode)) {
                            String[] dataSplited = data.split(TOKESEPARATOR);
                            ipDist = dataSplited[0];
                            portDist = Integer.parseInt(dataSplited[1]);
                            getServer = false;
                        }
                    } catch (IOException ex) {
                        System.out.println("ERROR: A packet can't be sent");
                    }
                }
            };
            
            timer.scheduleAtFixedRate(timerTask, 0, 10 * 1000);
            
        }
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
            socketControl.send(sendPacket);
        } catch (IOException ex) {
            System.out.println("ERROR: A packet don't can to be send");
        }
    }
}
