/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdchatserver;

/**
 *
 * @author Klauke.Sem
 */
public class User {
    public String username;
    public String ipAdress;
    public int portNumber;
    public boolean loggedIn;
    
    public User(String ip, int port) {
        this.username = null;
        this.ipAdress = ip;
        this.portNumber = port;
        loggedIn = false;
    }
    public void setUsername(String u) {
        this.username = u;
    }
}
