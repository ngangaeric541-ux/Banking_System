/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package SystemUI;

import static SystemUI.DatabaseCon.con;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class LoginPage extends JFrame implements ActionListener {

        private JLabel titleLabel, uName, uPass, createAcc, resetPass;
        private JTextField userField;
        private JPasswordField passField;
        private JButton login;
        private JPanel leftPanel;
        private JPanel rightPanel;
        private JLabel logoLabel;
        
        
        
    public LoginPage() {

    super("Login");

    frameSetup();

    buildUI();

    addListeners();

    setVisible(true);

        }

    
    private void frameSetup(){

    setSize(700,500);

    setLocationRelativeTo(null);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    addWindowListener(new WindowAdapter(){

        @Override
        public void windowClosing(WindowEvent e){

            int choice =
                    JOptionPane.showConfirmDialog(

                            LoginPage.this,

                            "Are you sure you want to exit?",

                            "Confirm Exit",

                            JOptionPane.YES_NO_OPTION);

            if(choice == JOptionPane.YES_OPTION){

                dispose();

                try{

                    con.close();

                }catch(SQLException ex){

                    Logger.getLogger(
                            LoginPage.class.getName())
                            .log(Level.SEVERE,
                                    null,
                                    ex);

                }

            }

        }

    });

}
    
    private void buildUI(){

    setLayout(new BorderLayout());

    leftPanel = new JPanel();
    leftPanel.setPreferredSize(new Dimension(320,550));
    leftPanel.setBackground(new Color(17,52,88));
    leftPanel.setLayout(new GridBagLayout());

    rightPanel = new JPanel();
    rightPanel.setLayout(new GridBagLayout());

    add(leftPanel,BorderLayout.WEST);
    add(rightPanel,BorderLayout.CENTER);

    //========================================
    // LEFT PANEL
    //========================================

    GridBagConstraints left = new GridBagConstraints();
    left.gridx = 0;
    left.anchor = GridBagConstraints.CENTER;
    left.insets = new Insets(10,10,10,10);

    //========================================
// LOGO
//========================================

    ImageIcon logo =new ImageIcon(getClass().getResource("/SystemUI/images/logo.png")); //
    Image scaledLogo =logo.getImage().getScaledInstance( 170,170,Image.SCALE_SMOOTH);
    logoLabel =new JLabel(new ImageIcon(scaledLogo));
    logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    left.gridy = 0;
    leftPanel.add(logoLabel,left);

    JLabel slogan =new JLabel("Secure Digital Banking");
    slogan.setForeground(new Color(220,220,220));
    slogan.setFont(new Font("SansSerif",Font.PLAIN,16));
    left.gridy = 2;
    leftPanel.add(slogan,left);
    
    JLabel quote =new JLabel( "Fast • ️Secure • Reliable");
    quote.setForeground(Color.WHITE);
    quote.setFont( new Font( "SansSerif",Font.ITALIC, 14));
    left.gridy = 3;
    leftPanel.add(quote,left);
    
    JLabel version =new JLabel("Version 1.0");
    version.setForeground(Color.LIGHT_GRAY);
    left.insets =new Insets(90,10,10,10);
    left.gridy = 4;
    leftPanel.add(version,left);

    //========================================
    // RIGHT PANEL
    //========================================

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 1;
    gbc.gridwidth = 2;
    rightPanel.add( Box.createVerticalGlue(),gbc);
    

    titleLabel = new JLabel("Welcome Back");
    titleLabel.setFont( new Font("SansSerif", Font.BOLD,26));
    titleLabel.setHorizontalAlignment( SwingConstants.CENTER);
    gbc.gridy = 1;
    gbc.weighty = 0;
    gbc.insets = new Insets(20,  10,  30,  10);
    rightPanel.add(titleLabel, gbc);

    uName = new JLabel(" Email");
    uName.setIcon(FontIcon.of(FontAwesomeSolid.USER, 18));
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(10,10,3,10);
    rightPanel.add(uName, gbc);

    userField = new JTextField(20);
    userField.setPreferredSize(new Dimension( 280, 36));
    userField.setBorder( BorderFactory.createMatteBorder( 0, 0, 1, 0,new Color(180,180,180)));
    userField.setOpaque(false);
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(4,10,15,10);
    rightPanel.add(userField, gbc);

    uPass = new JLabel(" Password");
    uPass.setIcon(FontIcon.of(FontAwesomeSolid.LOCK, 18));
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    rightPanel.add(uPass, gbc);
    
    passField = new JPasswordField(20);
    passField.setBorder( BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180,180,180)));
    passField.setOpaque(false);
    passField.setPreferredSize( new Dimension(280, 36));
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(4,10,15,10);
    rightPanel.add(passField, gbc);
    
        //========================================
    // LOGIN BUTTON
    //========================================

    login = new JButton("Login");
    login.setPreferredSize( new Dimension(280,42));
    login.setBackground(new Color(17,52,88));
    login.setForeground( Color.WHITE);
    login.setFocusable(false);
    login.setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    gbc.gridx = 0;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(25,10,10,10);
    rightPanel.add(login,gbc);

    //========================================
    // CREATE ACCOUNT
    //========================================

    createAcc =new JLabel("<html><u>Sign up</u></html>");
    createAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    gbc.gridy = 7;
    gbc.insets =new Insets(12, 10, 5, 10);
    rightPanel.add(createAcc,gbc);

    //========================================
    // RESET PASSWORD
    //========================================

    resetPass = new JLabel("<html><u>Forgot password?</u></html>");
    resetPass.setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    gbc.gridy = 8;
    gbc.insets = new Insets( 5, 10,  15, 10);
    rightPanel.add( resetPass, gbc);

    //========================================
    // BOTTOM SPACER
    //========================================

    gbc.gridy = 9;
    gbc.weighty = 1;
    rightPanel.add(Box.createVerticalGlue(), gbc);

}
    
    
    private void addListeners(){

    login.addActionListener(this);
    login.addMouseListener(new MouseAdapter(){

    @Override
    public void mouseEntered(MouseEvent e){

        login.setBackground(
                new Color(25,118,210));

    }

    @Override
    public void mouseExited(MouseEvent e){

        login.setBackground(
                new Color(17,52,88));

    }

});

    createAcc.addMouseListener(new MouseAdapter(){

        @Override
        public void mouseClicked(MouseEvent e){

            new CreateAccount();

            dispose();

        }

    });

    resetPass.addMouseListener(new MouseAdapter(){

        @Override
        public void mouseClicked(MouseEvent e){

            new ResetPassword();

            dispose();

        }

    });

}
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        login();
    }
    
    

    private void login() {
        DatabaseCon.database_connection();

        if (userField.getText().isEmpty() || passField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement("SELECT u.user_id,a.account_id,u.email,u.password,u.first_name,u.last_name FROM users u JOIN accounts a ON u.user_id = a.user_id WHERE u.email = ?");
            ps.setString(1, userField.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String enteredPass = new String(passField.getPassword());
                Long id = rs.getLong("user_id");
                long accountId = rs.getLong("account_id");
                String name = rs.getString("first_name");
                String lName = rs.getString("last_name");

                if (BCrypt.checkpw(enteredPass, storedHash)) {
                    
                    String deviceInfo = System.getProperty("os.name") + " " + System.getProperty("os.version");
                    String ipAddress = "unknown";

                    try {
                        InetAddress localHost = InetAddress.getLocalHost();
                        ipAddress = localHost.getHostAddress();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    
                    String save = "INSERT INTO sessions(user_id,ip_address,device_info) values (?, ?, ?)";
                    
                    PreparedStatement psmt = con.prepareStatement(save);
                    
                    psmt.setLong(1, id);
                    psmt.setString(2, ipAddress);
                    psmt.setString(3, deviceInfo);
                    
                    int rowsAffected = psmt.executeUpdate();
                    
                        if(rowsAffected > 0){

                                CurrentUser.getAllInfo(id);

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Welcome back " + name);

                                new LandingPage();

                                dispose();

                            }else{

                                JOptionPane.showMessageDialog(
                                        this,
                                        "An error occurred.");

}
                    
                   
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid password");
                }
            } else {
                JOptionPane.showMessageDialog(this, "User not found!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
  
}


