/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SystemUI;

import static SystemUI.DatabaseCon.con;
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
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import org.mindrot.jbcrypt.BCrypt;
public class ResetPassword extends JFrame implements ActionListener {
    
    GridBagLayout gbl;
    GridBagConstraints gbc;
    ResultSet rs;
    JTextField userName,emailField;
    JButton button,resetPasswordBtn,submit;
    JPasswordField newPassField, confirmPassField;
    JLabel username,newPassLabel,confirmPassLabel,title,emailLabel;
    JCheckBox showPassword;
    
    public ResetPassword() {

    super("Reset Password");
    setSize(600, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    GridBagLayout gbl = new GridBagLayout();
    setLayout(gbl);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    /* ===== TITLE ===== */
    title = new JLabel("<html><u>Password Reset Page</u></html>");
    title.setFont(new Font("SansSerif", Font.BOLD, 20));
    title.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    add(title, gbc);

    gbc.gridwidth = 1;

    /* ===== EMAIL ===== */
    emailLabel = new JLabel("Email:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    add(emailLabel, gbc);
    
    emailField = new JTextField(25);
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    add(emailField, gbc);

    gbc.gridwidth = 1;

    submit = new JButton("Submit");
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    submit.addActionListener(this );
    add(submit, gbc);

    /* ===== NEW PASSWORD ===== */
    newPassLabel = new JLabel("New Password:");
    gbc.gridx = 0;
    gbc.gridy = 3;
    add(newPassLabel, gbc);
    
    newPassField = new JPasswordField(20);
    gbc.gridx = 1;
    gbc.gridy = 3;
    add(newPassField, gbc);

    /* ===== CONFIRM PASSWORD ===== */
    confirmPassLabel = new JLabel("Confirm Password:");
    gbc.gridx = 0;
    gbc.gridy = 4;
    add(confirmPassLabel, gbc);
    
    confirmPassField = new JPasswordField(20);
    gbc.gridx = 1;
    gbc.gridy = 4;
    add(confirmPassField, gbc);

    /* ===== SHOW PASSWORD ===== */
    showPassword = new JCheckBox("Show password");
    showPassword.setToolTipText("Show password");
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.WEST;
    add(showPassword, gbc);
    

    /* ===== RESET BUTTON ===== */
    resetPasswordBtn = new JButton("Reset Password");
    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.CENTER;
    add(resetPasswordBtn, gbc);

    /* ===== INITIAL VISIBILITY ===== */
    newPassLabel.setVisible(false);
    newPassField.setVisible(false);
    confirmPassLabel.setVisible(false);
    confirmPassField.setVisible(false);
    showPassword.setVisible(false);
    resetPasswordBtn.setVisible(false);

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
        //convert the TextField into string
        String user = emailField.getText();
        PreparedStatement ps = null;
        
        if(user.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter a valid email address!");
        }else{
            try {
                DatabaseCon.database_connection();
                
                String sql = "SELECT * FROM users WHERE email = ?";
                
                ps = con.prepareStatement(sql);
                ps.setString(1, user);
                
                ResultSet rs = ps.executeQuery();
                
                    if(rs.next()){
                       //true
                    makeVisible();
                    }else{
                       //false
                       JOptionPane.showMessageDialog(this, "E-Mail not found,please enter a valid email!");
                    }//end of checking method
                
               } catch (SQLException ex) {
                Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, null, ex);
                 } finally {
                    ps.close();
                   }
         
        }//end of if block
    }//end of checkDetails method
    
    public void makeVisible() {
    //make the other fields invisible for submit button    
    submit.setVisible(false);
    emailField.setEditable(false);
    //make new fields visible
    newPassLabel.setVisible(true);
    newPassField.setVisible(true);
    confirmPassLabel.setVisible(true);
    confirmPassField.setVisible(true);
    showPassword.setVisible(true);
    resetPasswordBtn.setVisible(true);

    revalidate();
    repaint();
    resaveDetails();
}
//end of resaveDetails method
    
    private void resaveDetails(){
        
        //make the reset button work
        resetPasswordBtn.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
              //covert password to readable strings 
              String newPass = new String(newPassField.getText());
              String repeatPass = new String(confirmPassField.getText());
              String email = emailField.getText();
              
        if(newPass.isEmpty() || repeatPass.isEmpty()){
            JOptionPane.showMessageDialog(ResetPassword.this, "Please fill in both fields");
        }else{
            if(newPass.equals(repeatPass))
                {
                  
                  //re-encrypt password 
                  
                  String updatedPass = BCrypt.hashpw(new String(newPassField.getPassword()), BCrypt.gensalt());
                  try {
                      //block of true code
                      //database connecton and saving
                      DatabaseCon.database_connection();
                      
                      String query = "UPDATE users SET password=? WHERE email=?";
                      
                      PreparedStatement update = con.prepareStatement(query);
                      
                      update.setString(1, updatedPass);
                      
                      update.setString(2, email);
                      
                      update.executeUpdate();
                      
                      JOptionPane.showMessageDialog(null, "Password reset successful");
                      
                      dispose();
                      
                      new LoginPage();
                  
                  } catch (SQLException ex) {
                      Logger.getLogger(ResetPassword.class.getName()).log(Level.SEVERE, null, ex);
                  }
                }else{
                  //false
                  JOptionPane.showMessageDialog(ResetPassword.this,"Password Mismatch!");
              }
            }
              
           }
        });//end of listener
        
        //add a listener to make show password button fully functional for both fields 
        char confirmEcho = confirmPassField.getEchoChar();
        char defaultEcho = newPassField.getEchoChar();
        showPassword.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        boolean show = showPassword.isSelected();
        newPassField.setEchoChar(show ? (char) 0 : defaultEcho);
        confirmPassField.setEchoChar(showPassword.isSelected() ? (char) 0 : confirmEcho);
    }
});

    
    }

}//end of code
    
    
