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
public class SDClient extends Client {

    public SDClient(String pIPAdresse, int pPortNr) {
        super(pIPAdresse, pPortNr);
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
        
    }
    
    //Server said OK
    public void MSG_250(String m) {
        
    }
    
    //Server said loged in
    public void MSG_262(String m) {
        
    }
    
    //server sends connected clients
    public void MSG_271(String m) {
        
    }
    
    //server sends Message from users
    public void MSG_280(String m) {
        
    }
    
    //server sends private message from user
    public void MSG_281(String m) {
        
    }
    
    //user joind chat
    public void MSG_290(String m) {
        
    }
    
    //user left chat
    public void MSG_291(String m) {
        
    }
    
    //error on server side occured
    public void MSG_400(String m) {
        
    }
}
