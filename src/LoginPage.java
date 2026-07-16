/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package SystemUI;

import static SystemUI.DatabaseCon.con;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.mindrot.jbcrypt.BCrypt;

public class LoginPage extends JFrame implements ActionListener {

    private JLabel titleLabel, uName, uPass, createAcc, resetPass;
    private JTextField userField;
    private JPasswordField passField;

    public LoginPage() {
        super("Login");

        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbl);

        // Window close confirmation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog( LoginPage.this,"Are you sure you want to exit?","Confirm Exit",JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // ---------- TOP SPACER (centering) ----------
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        add(Box.createVerticalGlue(), gbc);

        // ---------- TITLE ----------
        titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(20, 10, 30, 10);
        add(titleLabel, gbc);

        // ---------- USERNAME ----------
        uName = new JLabel("Email");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 20, 10, 10);
        add(uName, gbc);

        userField = new JTextField(20);
        userField.setPreferredSize(new Dimension(280, 36));

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 20);
        add(userField, gbc);

        // ---------- PASSWORD ----------
        uPass = new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 20, 10, 10);
        add(uPass, gbc);

        passField = new JPasswordField(20);
        passField.setPreferredSize(new Dimension(280, 36));

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 20);
        add(passField, gbc);

        // ---------- LOGIN BUTTON ----------
        javax.swing.JButton login = new javax.swing.JButton("Login");
        login.setPreferredSize(new Dimension(120, 36));
        login.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(login, gbc);

        // ---------- SIGN UP ----------
        createAcc = new JLabel("<html><u>Sign up</u></html>");
        createAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 5, 10);
        add(createAcc, gbc);
        createAcc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CreateAccount();
                dispose();
            }
        });

        // ---------- FORGOT PASSWORD ----------
        resetPass = new JLabel("<html><u>Forgot password?</u></html>");
        resetPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        add(resetPass, gbc);
        // ---------- BOTTOM SPACER ----------
        gbc.gridy = 7;
        gbc.weighty = 1;
        add(Box.createVerticalGlue(), gbc);
        resetPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ResetPassword();
                dispose();
            }
        });

        setVisible(true);
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
    
///////////////////
///get the user info
//////////////////
    
    
    
    
     
    
    
    
    

  
}


