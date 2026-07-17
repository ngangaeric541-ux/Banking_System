

package SystemUI;

import static SystemUI.DatabaseCon.con;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


public class CreateAccount extends JFrame implements ActionListener{
    GridBagLayout gbl;
    GridBagConstraints gbc;
    JLabel title, firstName, lastName, idNo, email, kra_pin, phone, createPassword, repeatPassword;
    JCheckBox check1, check2;
    JButton create;
    JTextField $firstField, $secondField, $id, $email, $kra, $phone;
    JPasswordField $createPass, $repeatPass;
    Font titleFont,labelFont,textFont,buttonFont;

    public CreateAccount() {
        super("Account creation");
        setSize(900, 700);
       // setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        addWindowListener(new WindowAdapter(){
          @Override
          public void windowClosing(WindowEvent evt){
          int selected = JOptionPane.showConfirmDialog(CreateAccount.this, "Are you sure you want to exit?", "Warning", JOptionPane.WARNING_MESSAGE);
            if(selected == JOptionPane.YES_OPTION){
              dispose();     
            }else{
              setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
          }
        });
        
        
        gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();
        
        //Fonts for all components
        labelFont = new Font("Times New Roman", Font.PLAIN, 13);
        textFont = new Font("Sans Serif", Font.PLAIN, 13);
        
        // Title Label
        title = new JLabel("Create New Account");
        title.setFont(new Font("Arial", Font.BOLD, 18)); 
        gbc.anchor = GridBagConstraints.NORTH;  
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.gridwidth = 2; 
        gbc.insets = new Insets(10, 10, 10, 10); 
        add(title, gbc);
        
        // First Name Label
        firstName = new JLabel("First Name:");
        firstName.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.gridx = 0; 
        gbc.gridy = 1; 
        gbc.gridwidth = 1; // Set to 1 for individual components
        add(firstName, gbc);
        
        // First name text field
        $firstField = new JTextField(30);
        $firstField.setFont(textFont);
        gbc.gridx = 1; // Placing the text field in the second column (right of label)
        gbc.gridy = 1; 
       // gbc.fill = GridBagConstraints.HORIZONTAL; // stretches text field  horizontally
        add($firstField, gbc);
        
        // last name Label
        lastName = new JLabel("Last Name:");
        lastName.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.gridx = 0; 
        gbc.gridy = 2; 
        gbc.gridwidth = 1; // Set to 1 for individual components
        add(lastName, gbc);
        
        //last name text field
        $secondField = new JTextField(30);
        $secondField.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add($secondField, gbc);
        
        //id label
        idNo = new JLabel("ID number:");
        idNo.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(idNo,gbc);
        
        //id textfield
        $id = new JTextField(30);
        $id.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add($id,gbc);
        
        //email label
        email = new JLabel("E-mail:");
        email.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(email,gbc);
        
        //email textfield
        $email = new JTextField(30);
        $email.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add($email,gbc);
        
        //kra pin label
        kra_pin = new JLabel("KRA Pin:");
        kra_pin.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(kra_pin,gbc);
        
        //kra textfield
        $kra = new JTextField(30);
        $kra.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add($kra,gbc);
                
        //phone no label
        phone = new JLabel("Phone number:");
        phone.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        add(phone,gbc);
                
        //phone no textfield
        $phone = new JTextField(30);
        $phone.setFont(textFont);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add($phone,gbc);
                
        //create password label
        createPassword = new JLabel("Create a new password:");
        createPassword.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        add(createPassword,gbc);
        
        //password creation field
        $createPass = new JPasswordField(30);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add($createPass,gbc);
                        
        //repeat password field
        repeatPassword = new JLabel("Repeat password:");
        repeatPassword.setFont(labelFont);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        add(repeatPassword,gbc);
        
        //confirm password field 
        $repeatPass = new JPasswordField(30);
        gbc.gridx = 1;
        gbc.gridy = 8;
        add($repeatPass,gbc);
        
        //checkboxes
           //#1
        check1 = new JCheckBox("I have read the privacy policy");
        check1.setFont(textFont);
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(check1,gbc);
           //#2
        check2 = new JCheckBox("I agree to the terms and conditions");
        check2.setFont(textFont);
        gbc.gridx = 0;
        gbc.gridy = 10;
        add(check2,gbc);
        
        //creando button
        create = new JButton("Create account");
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        create.addActionListener(this);
        add(create,gbc);
        
        setVisible(true);
    }

    /*public static void main(String[] args) {
        new CreateAccount();
    }*/
    
    @Override
    public void actionPerformed(ActionEvent ae){
        //retrieve the passwords from the create and repeat fields
        String password1 =new String($createPass.getPassword());
        String password2 =new String($repeatPass.getPassword());
        
         //Listener for the button to work
         if(ae.getSource() == create){
               //check if any of the fields are empty
            if($firstField.getText().isEmpty() || $secondField.getText().isEmpty() ||
               $id.getText().isEmpty() || $email.getText().isEmpty() || $kra.getText().isEmpty() || $phone.getText().isEmpty()) {
                
                  JOptionPane.showMessageDialog(CreateAccount.this,"Fill in all information","ERROR",JOptionPane.ERROR_MESSAGE);
                //nested if to check whether the passwords retrieved match
              }else if(!password1.equals(password2)){  
                
                  JOptionPane.showMessageDialog(CreateAccount.this,"Password mismatch","ERROR",JOptionPane.ERROR_MESSAGE);
                        //nested if to check whether checkboxes are checked
                      }else if(!check1.isSelected() || !check2.isSelected()){
                 
                  JOptionPane.showMessageDialog(CreateAccount.this,"Please ensure you have checked both boxes","ERROR",JOptionPane.ERROR_MESSAGE);
                
                              }
          //if everything is done jump to the saveInfo method
         else {
              
              saveInfo();
              
              } 
         
         }
           
         
    
    }
    
    public void saveInfo(){

    try{

        // =========================================================
        //          GET USER INPUT
        // =========================================================

        String fName = $firstField.getText();
        String lName = $secondField.getText();
        String email = $email.getText();
        String kraPin = $kra.getText();
        String phone = $phone.getText();

        String hashPass = BCrypt.hashpw(
                new String($createPass.getPassword()),
                BCrypt.gensalt());

        // =========================================================
        //          CONNECT TO DATABASE
        // =========================================================

        DatabaseCon.database_connection();
        con.setAutoCommit(false);

        // =========================================================
        //          CREATE USER
        // =========================================================

        String addUser =
                "INSERT INTO users(user_id,first_name,last_name,email,"
                + "phone,password,kra_pin)"
                + " VALUES(UUID_SHORT(),?,?,?,?,?,?)";

        PreparedStatement ps1 = con.prepareStatement(addUser);

        ps1.setString(1,fName);
        ps1.setString(2,lName);
        ps1.setString(3,email);
        ps1.setString(4,phone);
        ps1.setString(5,hashPass);
        ps1.setString(6,kraPin);

        ps1.executeUpdate();

        // =========================================================
        //      GET THE NEW USER ID
        // =========================================================

        String getUser =
                "SELECT user_id FROM users WHERE email = ?";

        PreparedStatement ps2 = con.prepareStatement(getUser);

        ps2.setString(1,email);

        ResultSet rs = ps2.executeQuery();

        if(rs.next()){

            long userId = rs.getLong("user_id");

            // =========================================================
            //          CREATE BANK ACCOUNT
            // =========================================================

            String createAccount =
                        "INSERT INTO accounts(account_id, user_id, account_number, account_type, balance, created_at) "
                      + "VALUES(UUID_SHORT(), ?, UUID_SHORT(), 'SAVINGS', 0.00, NOW())";

            PreparedStatement ps3 =
                    con.prepareStatement(createAccount);

            ps3.setLong(1,userId);

            ps3.executeUpdate();

        }

        // =========================================================
        //          SAVE CHANGES
        // =========================================================

        con.commit();
        con.setAutoCommit(true);

        int option = JOptionPane.showConfirmDialog(
                CreateAccount.this,
                "Account successfully created.\n"
                + "Use your e-mail and password as credentials.",
                "Success",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if(option == JOptionPane.OK_OPTION){

            dispose();
            new LoginPage();

        }

    }catch(SQLException ex){

        try{

            con.rollback();
            con.setAutoCommit(true);

        }catch(SQLException ex1){

            Logger.getLogger(CreateAccount.class.getName())
                    .log(Level.SEVERE,null,ex1);

        }

        Logger.getLogger(CreateAccount.class.getName())
                .log(Level.SEVERE,null,ex);

        JOptionPane.showMessageDialog(
                CreateAccount.this,
                "Account creation failed.",
                "FAILED",
                JOptionPane.ERROR_MESSAGE);

    }

}
}
