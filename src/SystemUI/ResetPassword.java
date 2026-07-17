/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SystemUI;

import static SystemUI.DatabaseCon.con;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;


import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import org.mindrot.jbcrypt.BCrypt;
public class ResetPassword extends JFrame implements ActionListener {
    
   private GridBagLayout gbl;
   private GridBagConstraints gbc;
   private ResultSet rs;
   private JTextField userName,emailField;
   private JButton button,resetPasswordBtn,submit;
   private JPasswordField newPassField, confirmPassField;
   private JLabel username,newPassLabel,confirmPassLabel,title,emailLabel;
   private JCheckBox showPassword;
   private CardLayout cardLayout;
   private JPanel cardPanel;
    
    public ResetPassword() {

    super("Password Recovery");

    setSize(700,500);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    /*=========================================================
                        VERIFY PANEL
    =========================================================*/

    JPanel verifyPanel = new JPanel();

    GridBagLayout verifyLayout = new GridBagLayout();
    verifyPanel.setLayout(verifyLayout);

    GridBagConstraints gbc = new GridBagConstraints();

    gbc.insets = new Insets(15,15,15,15);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    title = new JLabel("Reset Your Password");
    title.setFont(new Font("SansSerif",Font.BOLD,28));
    title.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    verifyPanel.add(title,gbc);

    JLabel subtitle = new JLabel("Recover access to your account securely.");
    subtitle.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridy = 1;
    verifyPanel.add(subtitle,gbc);
    
    gbc.gridwidth = 1;
    
    emailLabel = new JLabel("Registered Email");
    gbc.gridx = 0;
    gbc.gridy = 2;
    verifyPanel.add(emailLabel,gbc);

    emailField = new JTextField(25);
    gbc.gridx = 1;
    verifyPanel.add(emailField,gbc);

    submit = new JButton("Verify Email");
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    verifyPanel.add(submit,gbc);
    submit.addActionListener(this);

    /*=========================================================
                    PASSWORD PANEL
    =========================================================*/

    JPanel passwordPanel = new JPanel();

    GridBagLayout passwordLayout = new GridBagLayout();
    passwordPanel.setLayout(passwordLayout);

    GridBagConstraints gbc2 = new GridBagConstraints();

    gbc2.insets = new Insets(15,15,15,15);
    gbc2.fill = GridBagConstraints.HORIZONTAL;

    JLabel passwordTitle =new JLabel("Create New Password");
    passwordTitle.setFont( new Font( "SansSerif", Font.BOLD, 24));
    passwordTitle.setHorizontalAlignment(SwingConstants.CENTER);
    gbc2.gridx = 0;
    gbc2.gridy = 0;
    gbc2.gridwidth = 3;
    passwordPanel.add(passwordTitle,gbc2);
    
    gbc2.gridwidth = 1;

    newPassLabel =new JLabel("New Password");
    gbc2.gridx = 0;
    gbc2.gridy = 1;
    passwordPanel.add(newPassLabel,gbc2);

    newPassField =new JPasswordField(22);
    gbc2.gridx = 1;
    passwordPanel.add(newPassField,gbc2);
    
    confirmPassLabel = new JLabel("Confirm Password");
    gbc2.gridx = 0;
    gbc2.gridy = 2;
    passwordPanel.add(confirmPassLabel,gbc2);

    confirmPassField =new JPasswordField(22);
    gbc2.gridx = 1;
    passwordPanel.add(confirmPassField,gbc2);

    showPassword = new JCheckBox("Show Passwords");
    gbc2.gridx = 1;
    gbc2.gridy = 3;
    passwordPanel.add(showPassword,gbc2);

    resetPasswordBtn =new JButton("Update Password");
    gbc2.gridx = 0;
    gbc2.gridy = 4;
    gbc2.gridwidth = 2;
    passwordPanel.add(resetPasswordBtn,gbc2);

    /*=========================================================
                        CARDS
    =========================================================*/

    cardPanel.add(verifyPanel,"verify");
    cardPanel.add(passwordPanel,"password");

    add(cardPanel);

    cardLayout.show(cardPanel,"verify");

    setVisible(true);

}

    
    
    
    @Override
    public void actionPerformed(ActionEvent ae){
        
        try {
            checkDetails();
        } catch (SQLException ex) {
            Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }//end of method
    
    
    
    void checkDetails() throws SQLException{

    String user = emailField.getText();
    PreparedStatement ps = null;
    if(user.isEmpty()){

        JOptionPane.showMessageDialog(this, "Please enter your registered email.");

    }else{

        try{

            DatabaseCon.database_connection();

            String sql = "SELECT * FROM users WHERE email = ?";

            ps = con.prepareStatement(sql);

            ps.setString(1,user);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                makeVisible();

            }else{

                JOptionPane.showMessageDialog(this, "Email address not found.");

            }

        }catch(SQLException ex){

            Logger.getLogger( ResetPassword.class.getName()).log(Level.SEVERE,null,ex);

        }finally{

            if(ps != null){

                ps.close();

            }

        }

    }

}
    
   public void makeVisible(){

    cardLayout.show(cardPanel,"password");

    resaveDetails();

}
    
private void resaveDetails(){

    // Prevent duplicate listeners
    for(ActionListener al : resetPasswordBtn.getActionListeners()){
        resetPasswordBtn.removeActionListener(al);
    }

    resetPasswordBtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e){

            String newPass =new String(newPassField.getPassword());
            String repeatPass = new String(confirmPassField.getPassword());
            String email = emailField.getText();

            if(newPass.isEmpty() || repeatPass.isEmpty()){

                JOptionPane.showMessageDialog( ResetPassword.this, "Please fill in both fields.");

            }else{

                if(newPass.equals(repeatPass)){

                    String updatedPass = BCrypt.hashpw( newPass, BCrypt.gensalt());

                    try{

                        DatabaseCon.database_connection();

                        String query = "UPDATE users " + "SET password=? " + "WHERE email=?";

                        PreparedStatement update = con.prepareStatement(query);

                        update.setString(1,updatedPass);

                        update.setString(2,email);

                        int rows =
                                update.executeUpdate();

                        if(rows > 0){

                            JOptionPane.showMessageDialog( ResetPassword.this, "Password updated successfully.");

                            dispose();

                            new LoginPage();

                        }else{

                            JOptionPane.showMessageDialog( ResetPassword.this, "Unable to update password.");

                        }

                    }catch(SQLException ex){

                        Logger.getLogger( ResetPassword.class.getName()) .log(Level.SEVERE,null,ex);

                    }

                }else{

                    JOptionPane.showMessageDialog( ResetPassword.this,"Passwords do not match.");

                }

            }

        }

    });

    //======================================================
    // SHOW PASSWORD
    //======================================================

    for(ActionListener al : showPassword.getActionListeners()){
        showPassword.removeActionListener(al);
    }

    char defaultEcho = newPassField.getEchoChar();

    char confirmEcho = confirmPassField.getEchoChar();

    showPassword.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent event){

            boolean show = showPassword.isSelected();

            newPassField.setEchoChar(show ? (char)0 : defaultEcho);

            confirmPassField.setEchoChar( show ? (char)0 : confirmEcho);

        }

    });

}
    
        public static void main(String[] args) {
                new ResetPassword();
        }

}//end of code
    
    
