

package SystemUI;

import static SystemUI.DatabaseCon.con;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.mindrot.jbcrypt.BCrypt;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
 

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
        setSize(950, 900);
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
        
        
//========================================
// MAIN LAYOUT
//========================================

setLayout(new BorderLayout());

//========================================
// LEFT PANEL
//========================================

JPanel leftPanel = new JPanel(new GridBagLayout());
leftPanel.setPreferredSize(new Dimension(300,950));
leftPanel.setBackground(new Color(17,52,88));
GridBagConstraints left =new GridBagConstraints();

left.gridx = 0;
left.gridy = 0;
left.weightx = 1;
left.weighty = 1;
left.anchor = GridBagConstraints.CENTER;

//========================================
// IMAGE PLACEHOLDER
//========================================

JLabel imageLabel = new JLabel();

imageLabel.setPreferredSize(new Dimension(300,950));

imageLabel.setHorizontalAlignment(SwingConstants.CENTER);


ImageIcon icon = new ImageIcon(getClass().getResource("/SystemUI/images/bluebg.jpg"));

Image img = icon.getImage().getScaledInstance(500,900,Image.SCALE_SMOOTH);

imageLabel.setIcon(new ImageIcon(img));


leftPanel.add(imageLabel,left);

//========================================
// RIGHT PANEL
//========================================

JPanel rightPanel =new JPanel();

rightPanel.setLayout(new GridBagLayout());

rightPanel.setBackground(Color.WHITE);
add(leftPanel,BorderLayout.WEST);
add(rightPanel,BorderLayout.CENTER);

//========================================
// GRIDBAG FOR FORM
//========================================

gbc = new GridBagConstraints();
gbc.fill =GridBagConstraints.HORIZONTAL;
gbc.weightx = 1;

//========================================
// FONTS
//========================================

labelFont = ( new Font("SansSerif", Font.BOLD,10));


//========================================
// TITLE
//========================================

title = new JLabel("<html><u>Create New Account</u></html>");
title.setFont(( new Font("SansSerif", Font.BOLD,26)));
title.setForeground(new Color(17,52,88));
title.setHorizontalAlignment(SwingConstants.CENTER);
gbc.gridx = 0;
gbc.gridy = 0;
gbc.gridwidth = 2;
gbc.insets = new Insets(20,10,30,10);
gbc.anchor = GridBagConstraints.CENTER;

rightPanel.add(title,gbc);

//========================================
// FIRST NAME
//========================================

firstName = new JLabel("  First Name");
firstName.setIcon(FontIcon.of(FontAwesomeSolid.USER, 18));
firstName.setFont(labelFont);
firstName.setForeground(new Color(40,40,40));
gbc.gridx = 0;
gbc.gridy = 1;
gbc.gridwidth = 2;
gbc.anchor = GridBagConstraints.WEST;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(firstName,gbc);

$firstField = new JTextField(25);
$firstField.setFont(textFont);
$firstField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(110,110,110)));
$firstField.setOpaque(true);
$firstField.setBackground(Color.WHITE);
$firstField.setForeground(Color.BLACK);
$firstField.setCaretColor(new Color(17,52,88));
gbc.gridy = 2;
gbc.insets = new Insets(8,60,20,60);
rightPanel.add($firstField,gbc);

//========================================
// LAST NAME
//========================================

lastName = new JLabel("  Last Name");
lastName.setIcon(FontIcon.of(FontAwesomeSolid.USER, 18));
lastName.setFont(labelFont);
lastName.setForeground(new Color(40,40,40));
gbc.gridy = 3;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(lastName,gbc);

$secondField = new JTextField(25);
$secondField.setFont(textFont);
$secondField.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(110,110,110)));
$secondField.setOpaque(true);
$secondField.setBackground(Color.WHITE);
$secondField.setForeground(Color.BLACK);
$secondField.setCaretColor(new Color(17,52,88));
gbc.gridy = 4;
gbc.insets = new Insets(8,60,20,60);;
rightPanel.add($secondField,gbc);

//========================================
// ID NUMBER
//========================================

idNo = new JLabel("  National ID");
idNo.setIcon(FontIcon.of(FontAwesomeSolid.ID_CARD, 18));
idNo.setFont(labelFont);
idNo.setForeground(new Color(40,40,40));
gbc.gridy = 5;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(idNo,gbc);

$id = new JTextField(25);
$id.setFont(textFont);
$id.setBorder(BorderFactory.createMatteBorder( 0,0,1,0,new Color(110,110,110)));
$id.setOpaque(true);
$id.setBackground(Color.WHITE);
$id.setForeground(Color.BLACK);
$id.setCaretColor(new Color(17,52,88));
gbc.gridy = 6;
gbc.insets = new Insets(8,60,20,60);;
rightPanel.add($id,gbc);

//========================================
// EMAIL
//========================================

email = new JLabel("  Email Address");
email.setIcon(FontIcon.of(FontAwesomeSolid.ENVELOPE, 18));
email.setFont(labelFont);
email.setForeground(new Color(40,40,40));
gbc.gridy = 7;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(email,gbc);

$email = new JTextField(25);
$email.setFont(textFont);
$email.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(110,110,110)));
$email.setOpaque(true);
$email.setBackground(Color.WHITE);
$email.setForeground(Color.BLACK);
$email.setCaretColor(new Color(17,52,88));
gbc.gridy = 8;
gbc.insets = new Insets(8,60,20,60);;
rightPanel.add($email,gbc);

//========================================
// KRA PIN
//========================================

kra_pin = new JLabel("  KRA PIN");
kra_pin.setIcon(FontIcon.of(FontAwesomeSolid.KEY, 18));
kra_pin.setFont(labelFont);
kra_pin.setForeground(new Color(40,40,40));
gbc.gridy = 9;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(kra_pin,gbc);

$kra = new JTextField(25);
$kra.setFont(textFont);
$kra.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(110,110,110)));
$kra.setOpaque(true);
$kra.setBackground(Color.WHITE);
$kra.setForeground(Color.BLACK);
$kra.setCaretColor(new Color(17,52,88));
gbc.gridy = 10;
gbc.insets = new Insets(8,60,20,60);;
rightPanel.add($kra,gbc);

//========================================
// PHONE NUMBER
//========================================

phone = new JLabel("  Mobile Number");
phone.setIcon(FontIcon.of(FontAwesomeSolid.MOBILE_ALT, 18));
phone.setFont(labelFont);
phone.setForeground(new Color(40,40,40));
gbc.gridy = 11;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(phone,gbc);

$phone = new JTextField(25);
$phone.setFont(textFont);
$phone.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(110,110,110)));
$phone.setOpaque(true);
$phone.setBackground(Color.WHITE);
$phone.setForeground(Color.BLACK);
$phone.setCaretColor(new Color(17,52,88));
gbc.gridy = 12;
gbc.insets = new Insets(8,60,20,60);
rightPanel.add($phone,gbc);

//========================================
// PASSWORD
//========================================

createPassword = new JLabel("  Password");
createPassword.setIcon(FontIcon.of(FontAwesomeSolid.LOCK, 18));
createPassword.setFont(labelFont);
createPassword.setForeground(new Color(40,40,40));
gbc.gridy = 13;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(createPassword,gbc);

$createPass = new JPasswordField(25);
$createPass.setFont(textFont);
$createPass.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(110,110,110)));
$createPass.setOpaque(true);
$createPass.setBackground(Color.WHITE);
$createPass.setForeground(Color.BLACK);
$createPass.setCaretColor(new Color(17,52,88));
gbc.gridy = 14;
gbc.insets = new Insets(8,60,20,60);
rightPanel.add($createPass,gbc);

//========================================
// CONFIRM PASSWORD
//========================================

repeatPassword = new JLabel("  Confirm Password");
repeatPassword.setIcon(FontIcon.of(FontAwesomeSolid.LOCK, 18));
repeatPassword.setFont(labelFont);
repeatPassword.setForeground(new Color(40,40,40));
gbc.gridy = 15;
gbc.insets = new Insets(5,60,5,0);
rightPanel.add(repeatPassword,gbc);

$repeatPass = new JPasswordField(25);
$repeatPass.setFont(textFont);
$repeatPass.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(110,110,110)));
$repeatPass.setOpaque(true);
$repeatPass.setBackground(Color.WHITE);
$repeatPass.setForeground(Color.BLACK);
$repeatPass.setCaretColor(new Color(17,52,88));
gbc.gridy = 16;
gbc.insets = new Insets(0,60,22,60);
rightPanel.add($repeatPass,gbc);

//========================================
// CHECK BOXES
//========================================

check1 = new JCheckBox("  I have read the Privacy Policy");
check1.setOpaque(false);
check1.setFont(textFont);
check1.setForeground(new Color(40,40,40));
check1.setFocusable(false);
gbc.gridy = 17;
gbc.insets = new Insets(5,60,5,60);
rightPanel.add(check1, gbc);

check2 = new JCheckBox("I agree to the Terms & Conditions");
check2.setOpaque(false);
check2.setFont(textFont);
check2.setForeground(new Color(40,40,40));
check2.setFocusable(false);
gbc.gridy = 18;
rightPanel.add(check2, gbc);

//========================================
// CREATE ACCOUNT BUTTON
//========================================

create = new JButton("  Create Account");
create.setBackground(new Color(17,52,88));;
    create.setForeground( Color.WHITE);
    create.setFocusable(false);
    create.setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
create.setPreferredSize(new Dimension(280,42));
create.addActionListener(this);
gbc.gridy = 19;
gbc.insets = new Insets(40,60,20,60);
rightPanel.add(create,gbc);

//========================================
// BACK TO LOGIN
//========================================

JLabel back = new JLabel("<html><u>Back to Login</u></html>");
back.setIcon(FontIcon.of(FontAwesomeSolid.ARROW_LEFT, 18));
back.setForeground(new Color(17,52,88));
back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
back.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {

        new LoginPage();

        dispose();

    }
});
gbc.gridy = 20;
gbc.insets = new Insets(0,60,20,60);
gbc.anchor = GridBagConstraints.CENTER;
rightPanel.add(back,gbc);


setLocationRelativeTo(null);
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
