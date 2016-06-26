/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdchatclient;

import java.awt.Color;
import static java.lang.Math.abs;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author semklauke
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    
    private SDClient client;
    private DefaultListModel chatModel;
    private DefaultListModel clientsModel;
    protected String[] colors = {"#91580f", "#f8a700", "#f78b00","#58dc00", "#287b00", "#a8f07a", "#4ae8c4", "#3b88eb", "CA38BE", 
    "#21610B", "#3824aa", "#a700ff", "#d300e7", "#2E2EFE", "#FFFF00", "#81F781", "#2E64FE"};
    protected String infoPrefix = "<span style='font-weight:bold;'>";
    protected String privatePrefix = "<span style='font-weight:bold;'>[P]</span>";
    protected String selfPrefix = "<span style='font-weight:bold;'>>></span>";
    
    public MainWindow() {
        initComponents();
        
        client = null;
        clientList.setSelectionModel(new DefaultListSelectionModel() {
            private static final long serialVersionUID = 1L;

            boolean gestureStarted = false;

            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(!gestureStarted){
                    if (isSelectedIndex(index0)) {
                        super.removeSelectionInterval(index0, index1);
                    } else {
                    super.addSelectionInterval(index0, index1);
            }
        }
        gestureStarted = true;
    }

    @Override
    public void setValueIsAdjusting(boolean isAdjusting) {
        if (isAdjusting == false) {
            gestureStarted = false;
        }
    }

});
        /*chatModel = new DefaultListModel();
        clientsModel = new DefaultListModel();*/
        
    }
    
    public String getColorForName(String name) {
        int summ = 0;
        int hash = 7;
        for(char c : name.toCharArray()) 
            summ =(int)c + (summ << 5) - summ;
        summ = abs(summ);
        return colors[(summ%colors.length)];
    }
    
    public String wrapHtml(String s) {
        return "<html>"+s+"</html>";
    }
    
    public void txtMessage(String name, String msg, boolean self) {
        String clientMsg = self ? selfPrefix+" "+msg : "<span style='color: "+getColorForName(name)+";'>"+name+"</span>: "+msg;
        chatModel.addElement(wrapHtml(clientMsg));
    }
    
    public void txtMessage(String name, String msg) {
        txtMessage(name, msg, false);
    }
    
    public void txtMessage(String msg) {
        txtMessage(null, msg, true);
    }
    
    public void privateMessage(String names[], String msg) {
        
        String newMsg = selfPrefix;
        newMsg += " " + privatePrefix + " to ";
        int i = 0;
        for (String s : names) {
            String c = getColorForName(s);
            if (i==0)
                newMsg += "<span style='color:"+c+";'>"+s+"</span>";
            if (i>0)
                newMsg += ", "+"<span style='color:"+c+";'>"+s+"</span>";
            i++;
        }
        newMsg += ": "+msg;
        chatModel.addElement(wrapHtml(newMsg));
    }
    
    public void privateMessage(String name, String msg) {
        String newMsg = privatePrefix+ " <span style='color: "+getColorForName(name)+";'>"+name+"</span>: "+msg;
        chatModel.addElement(wrapHtml(newMsg));
    }
    
    public void info(String info) {
        chatModel.addElement(wrapHtml(infoPrefix +" "+info+"</span>"));
    }
    
    private SDClient createClient() {
        String adress[] = adressTextField.getText().split(":");
        if (adress.length != 2) {
            System.out.println("Adress invalid: "+adressTextField.getText());
            JOptionPane.showMessageDialog(null, "Adress invalid: "+adressTextField.getText());
            return null;
        }
        return new SDClient(adress[0], Integer.parseInt(adress[1]), userTextField.getText(), passTextField.getText()) {

            @Override
            public void userLoggedIn() {
                info("logged in");
                send("270");
                statusLable.setText("Online");
                statusLable.setForeground(Color.green);

            }

            @Override
            public void gotClientList(String[] clients) {
                clientsModel.clear();
                for (String c : clients) {
                    String n = "<span style='color: "+getColorForName(c)+";'>"+c+"</span>";
                    clientsModel.addElement(wrapHtml(n));
                }
            }

            @Override
            public void gotText(String name, String msg) {
                txtMessage(name, msg);
            }

            @Override
            public void gotPrivateText(String name, String msg) {
                privateMessage(name, msg);
            }

            @Override
            public void gotServerError(String errorMsg) {
                System.out.println("Server Error: "+ errorMsg);
                JOptionPane.showMessageDialog(null, "Server Error: \n"+errorMsg);
            }

            @Override
            public void userConnected(String name) {
                String n = "<span style='color: "+getColorForName(name)+"';>"+name+"</span>";
                info(n+" connected");
                clientsModel.addElement(wrapHtml(n));
            }

            @Override
            public void userDisconnected(String name) {
                String n = "<span style='color: "+getColorForName(name)+"';>"+name+"</span>";
                info(n+" disconnected");
                clientsModel.removeElement(wrapHtml(n));
                
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chatModel = new DefaultListModel();
        clientsModel = new DefaultListModel();

        userTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        passTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        adressTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        statusLable = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientList = new javax.swing.JList(clientsModel);
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatList = new javax.swing.JList(chatModel);
        sendButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        messageField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Username:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Password:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Adresse:");

        adressTextField.setMinimumSize(new java.awt.Dimension(15, 28));
        adressTextField.setPreferredSize(new java.awt.Dimension(270, 28));
        adressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adressTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Status:");

        statusLable.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        statusLable.setForeground(new java.awt.Color(255, 0, 0));
        statusLable.setText("Offline");
        statusLable.setFocusTraversalPolicyProvider(true);
        statusLable.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(clientList);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Currently Online");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        chatList.setFont(new java.awt.Font("Helvetica", 0, 12)); // NOI18N
        chatList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(chatList);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Chat");

        messageField.setLocation(new java.awt.Point(1, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(passTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                                            .addComponent(userTextField))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(adressTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)
                                                .addComponent(statusLable, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(connectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(12, 12, 12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(sendButton)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusLable)
                    .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        if (client==null) {
            if ("".equals(adressTextField.getText())
                || "".equals(userTextField.getText()) 
                || "".equals(passTextField.getText())) {
              System.out.println("Not All Text Fields are filled out");
              JOptionPane.showMessageDialog(null, "Please fill out every text field");
              return;
            }
            client = this.createClient();
            if (client != null) {
                JButton b = (JButton) evt.getSource();
                b.setText("Disconnect");
            }
                
            
        } else {
            //disconnect
            client.close();
            
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        if (client == null)
            return;
        String input = messageField.getText();
        
        List v = clientList.getSelectedValuesList();
        
        if (v.isEmpty()) {
            client.send("280"+input);
            txtMessage(input);
            messageField.setText("");
        } else {
            String newMsg = "281";
            String[] destinations = new String[v.size()];
            int i=0;
            for (Object o : v) {
                String name = o.toString().substring(36);
                System.out.println("name: "+name+"/end/");
                String finalName = "";
                for (int j=0; j<name.length(); j++) {
                    if (name.charAt(j) != '<')
                        finalName += name.charAt(j);
                    else
                        break;
                }
                newMsg += finalName + ";";
                destinations[i] = finalName;
                i++;
            }
            newMsg += input;
            client.send(newMsg);
            privateMessage(destinations, input);
            messageField.setText("");
        }  
        
    }//GEN-LAST:event_sendButtonActionPerformed

    private void adressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adressTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adressTextField;
    private javax.swing.JList chatList;
    private javax.swing.JList clientList;
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField messageField;
    private javax.swing.JTextField passTextField;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel statusLable;
    private javax.swing.JTextField userTextField;
    // End of variables declaration//GEN-END:variables
}
