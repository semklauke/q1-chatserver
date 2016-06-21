/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdchatclient;

import java.lang.reflect.Method;

/**
 *
 * @author semklauke
 */
public abstract class SDClient extends Client {
    
    protected boolean loginModus;
    public boolean loggedIn;
    protected String username;
    protected String password;
    
    public SDClient(String pIPAdresse, int pPortNr, String username, String password) {
        super(pIPAdresse, pPortNr);
        loginModus = false;
        loggedIn = false;
        this.username = username;
        this.password = password;
    }

    @Override
    public void processMessage(String pMessage) {
        String number = pMessage.substring(0,3);
        String msg = pMessage.substring(3);
      
        try {
             Method method = this.getClass().getDeclaredMethod("MSG_" + number, new Class[] { String.class });
             method.invoke(this, new Object[] {msg});
             

        } catch (Exception ex) {
            System.out.print("Methode Invoke Error");
            ex.printStackTrace();
        }
    }
    
    
    //Server connected
    public void MSG_220(String m) {
        loginModus = true;
        send("260"+username);//username
    }
    
    //Server said OK
    public void MSG_250(String m) {
        if (loginModus) {
            send("261"+password); //password
        }
    }
    
    //Server said loged in
    public void MSG_262(String m) {
        loggedIn = true;
        loginModus = false;
        userLoggedIn();
    }
    
    //server sends connected clients
    public void MSG_271(String m) {
        if (!loggedIn)
            return;
        gotClientList(m.split(";"));
    }
    
    //server sends Message from users
    public void MSG_280(String m) {
        if (!loggedIn)
            return;
        String parts[] = m.split(";");
        if (parts.length != 2)
            gotServerError("Text Message Error");
        else
            gotText(parts[0], parts[1]);
    }
    
    //server sends private message from user
    public void MSG_281(String m) {
        if (!loggedIn)
            return;
        String parts[] = m.split(";");
        if (parts.length != 2)
            gotServerError("Private Text Message Error");
        else
            gotPrivateText(parts[0], parts[1]);
        
    }
    
    //user joind chat
    public void MSG_290(String m) {
        if (!loggedIn)
            return;
        userConnected(m);
    }
    
    //user left chat
    public void MSG_291(String m) {
        if (!loggedIn)
            return;
        userDisconnected(m);
    }
    
    //error on server side occured
    public void MSG_400(String m) {
        System.out.println("Serevr Error: "+m);
        gotServerError(m);
    }
   
    public abstract void userLoggedIn();
    public abstract void gotClientList(String[] clients);
    public abstract void gotText(String name, String msg);
    public abstract void gotPrivateText(String name, String msg);
    public abstract void gotServerError(String errorMsg);
    public abstract void userConnected(String name);
    public abstract void userDisconnected(String name);
}
