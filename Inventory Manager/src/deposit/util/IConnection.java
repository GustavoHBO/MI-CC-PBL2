/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deposit.util;

/**
 * Interface for use in connections between the deposit and servers.
 * @author Gustavo Henrique
 */
public interface IConnection {
    
    public String getIpServer();
    
    /**
     * Data solicitation, 
     * @param ip
     * @return 
     */
    public String dataSolicitation(String ip);
    
    public String verifyConnection(String ip, int timeSecunds, int times);
}
