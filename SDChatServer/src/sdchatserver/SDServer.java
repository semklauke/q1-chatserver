/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdchatserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Klauke.Sem
 */
public abstract class SDServer extends Server {
    
    
    public List <User> user;
   
    
    public SDServer(int pPortNr) {
        super(pPortNr);
        user = new LinkedList<User>();
        
    }
    
    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        User u = new User(pClientIP, pClientPort);
        user.add(u);
        userOpensConnection(u);
        this.send(pClientIP, pClientPort, "220");
    }
    
    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        String number = pMessage.substring(0,3);
        String msg = pMessage.substring(3);
      
        try {
             Method method = this.getClass().getDeclaredMethod("MSG_" + number, new Class[] { String.class, User.class});
             User u = this.getUser(pClientIP, pClientPort);
             if (u != null)
                method.invoke(this, new Object[] {msg, u});
             else
                this.send(pClientIP, pClientPort, "400");

        } catch (Exception ex) {
            System.out.print("Methode Invoke Error");
            ex.printStackTrace();
        }
       
    }
    
    @Override
    public void processClosedConnection(String pClientIP, int pClientPort) {
        //this.sendToAll("S");
    }
    
    public User getUser(String ip, int port) {
        for (User u : user) {
            if ((u.ipAdress == null ? ip == null : u.ipAdress.equals(ip))
                && u.portNumber == port) {
                return u;
            }
        }
        return null;
    }
    
    public User getUser(String name) {
        for (User u : user) {
            if (u.username.equals(name))
                return u;
            
        }
        return null;
    }
    
    public void sendToAllBut(String pMessage, String ip, int port) {
        ServerConnection lSerververbindung;
        
        hatVerbindungen.zumAnfang();
        
        while (!hatVerbindungen.istDahinter())
        {
            lSerververbindung = (Server.ServerConnection) hatVerbindungen.aktuelles();
            if (lSerververbindung.partnerAdresse().equals(ip) && lSerververbindung.partnerPort() == port)
                //System.out.print("msg send to"+ip+":"+port);
                lSerververbindung.send("250");
            else
                lSerververbindung.send(pMessage);
            hatVerbindungen.vor();
        }   
    }
    
    public void sendToAllBut(String pMessage, User u) {
        ServerConnection lSerververbindung;
        
        hatVerbindungen.zumAnfang();
        
        while (!hatVerbindungen.istDahinter())
        {
            lSerververbindung = (Server.ServerConnection) hatVerbindungen.aktuelles();
            if (lSerververbindung.partnerAdresse().equals(u.ipAdress) && lSerververbindung.partnerPort() == u.portNumber)
                //System.out.print("msg send to"+ip+":"+port);
                lSerververbindung.send("250");
            else
                lSerververbindung.send(pMessage);
            hatVerbindungen.vor();
        }   
    }
    
    public void sendToUser(User u, String msg) {
        this.send(u.ipAdress, u.portNumber, msg);
    }
    
    public void ok(User u) {
        this.sendToUser(u, "250");
    }
    
    public void error(User u, String errorMsg) {
        this.sendToUser(u, "400" + errorMsg);
        System.out.println(errorMsg);
        errorWithUser(u, errorMsg);
    }
    
    
    public void MSG_260(String m, User u) {
        try {
            //Check if username is in databas
            BufferedReader in = new BufferedReader(new FileReader("logins.txt"));
            String line;
            while((line = in.readLine()) != null) {
                String splitLine[] = line.split(";");
                if (splitLine[0].equals(m)) {
                    u.setUsername(m);
                    ok(u);
                    return;
                }
            }
            error(u, "User Not In Database");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("loginFile Error");
            error(u, "Couldn't Read Database");
            return;
        }
    }

    public void MSG_261(String m, User u) {
        //Check password
        try {
            BufferedReader in = new BufferedReader(new FileReader("logins.txt"));
            String line;
            while((line = in.readLine()) != null) {
                String splitLine[] = line.split(";");
                if (splitLine[0].equals(u.username) && splitLine[1].equals(m)) {
                    sendToUser(u, "262"); // send logend In
                    u.loggedIn = true;
                    userLoggedIn(u);
                    return;
                }
            }
            error(u, "Password Incorrect");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("loginFile Error");
            error(u, "Couldn't Read Database");
            return;
        }

    }

    public void MSG_270(String m, User u) { 
        if (u.loggedIn) {
            String reString = "271";
            for (User usr : user) {
                if (usr.username != null && usr.loggedIn)
                    reString += usr.username+";";
            }
        }
    }


    public void MSG_280(String m, User u) {
       // get text Message from user
        if (u.loggedIn) {
            sendToAllBut(m, u);
            gotText(m, u);
        }
        else
            error(u, "User Not Logged In");
    }

    public void MSG_281(String m, User u) {
        //get Private Message from user
        if (u.loggedIn) {
            String users[] = m.split(";");
            if (users.length == 0)
                error(u, "No Destination(s) Selected/No Message");
            else {
                int lastIndex = users.length-1;
                String msg = users[lastIndex];
                int c = 0;
                for (String un : users) {
                    if (c == lastIndex)
                        break;
                    sendToUser(getUser(un), "281"+u.username+";"+msg);
                    c++;
                }
                
                   
                gotPrivateText(msg, u, users);
                ok(u);
            }
        } else
            error(u, "User Not Logged In");

    }
    
    
    public abstract void gotText(String m, User u);
    public abstract void gotPrivateText(String m, User u, String[] us);
    public abstract void userLoggedIn(User u);
    public abstract void userOpensConnection(User u);
    public abstract void userDisconnected(User u);
    public abstract void errorWithUser(User u);
    public abstract void errorWithUser(User u, String errorMsg);
    
    
    
    
    
}
