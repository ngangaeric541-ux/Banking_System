/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package SystemUI;


import static SystemUI.DatabaseCon.con;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.sql.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.Boolean.FALSE;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

/**
 *
 * @author mashmash
 */
public class LandingPage extends JFrame implements ActionListener {

    private JPanel actionPanel;
    private JPanel centerPanel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JLabel balance,currentBal;
    private double currentBalance;
    private JLabel txn1;
    private JLabel txn1Time;
    private JLabel activityTitle;;

    private JLabel txn2;
    private JLabel txn2Time;

    private JLabel txn3;
    private JLabel txn3Time;
    
    private JButton buyBtn,depositBtn,sendBtn;
    private JTextField depositAmountField,sendAmountField,recipientField,airtimeAmountField;
    private JLabel recipientNameLabel;
    private JLabel statusLabel;

    
    //--constructor--//
   LandingPage(){
       super("Home");
       setSize(1280,600);
        setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BorderLayout(5,0));
    topPanel();
   // midPanel();
    sidePanel();
    dashPanel();
    loadBalance();

   
   setVisible(true);
   }
   
   
   
    public static void main(String[] args) {
            // TODO code application logic here
            LandingPage landingPage = new LandingPage();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae){
        dashPanel();
        
       
        
    }
    
    
    
    private void topPanel(){
         JPanel top = new JPanel(new BorderLayout(10,10));
         top.setBackground(new Color(17,52,88));
         top.setPreferredSize(new Dimension(0,70));
         top.setBorder(BorderFactory.createEmptyBorder(8,20,0,20));
                
         //greeting 
         JPanel leftSideTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
         leftSideTop.setBackground(new Color(17,52,88));
         JLabel profile = createProfilePicture("/SystemUI/images/default.jpeg",50);
         JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel,BoxLayout.Y_AXIS));
        userPanel.setOpaque(false);

        JLabel welcome = new JLabel("Welcome back,");
        welcome.setForeground(new Color(215,225,240));
        welcome.setFont(new Font("Arial",Font.PLAIN,12));

        JLabel greeting = new JLabel(CurrentUser.getName()+" "+CurrentUser.getLastName());
        greeting.setForeground(Color.WHITE);
        greeting.setFont(new Font("Arial",Font.BOLD,16));

        userPanel.add(welcome);
        userPanel.add(greeting);

        leftSideTop.add(profile);
        leftSideTop.add(userPanel);
         top.add(leftSideTop,BorderLayout.WEST);
         
         //title
         JLabel title = new JLabel("GFH Bank",SwingConstants.CENTER);
         title.setFont(new Font("Arial",Font.BOLD,23));
         title.setForeground(Color.WHITE);
         top.add(title,BorderLayout.CENTER);//the title IN the panel
         
         //button
         JButton logOut = new JButton("Logout");
         logOut.setPreferredSize(new Dimension(100,40));
         logOut.setBackground(new Color(18,18,18));
         logOut.setForeground( Color.WHITE);
         logOut.setFocusable(false);
         logOut.setCursor( Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         logOut.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent ae){
                 logout();
              }
         });
         logOut.putClientProperty("JButton.buttonType", "roundRect");
         top.add(logOut,BorderLayout.EAST);
    
         add(top,BorderLayout.NORTH);//the panel itself
    }
    
    private void sidePanel() {

    // =========================================================
    //                  SIDEBAR COLORS
    // =========================================================
    Color panelColor = new Color(24,30,42);
    Color titleColor = Color.WHITE;
    Color textColor = new Color(220,220,220);
    Color secondaryColor = new Color(150,150,150);
    Color accentBlue = new Color(17,52,88);
    Color successGreen = new Color(34,197,94);

    JPanel leftPanel = new JPanel();
    leftPanel.setPreferredSize(new Dimension(250, 0));
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.setBackground(panelColor);
    leftPanel.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));

    // =========================================================
    //                  SIDEBAR TITLE
    // =========================================================

    JLabel activityTitle = new JLabel("Recent Activity");
    activityTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
    activityTitle.setForeground(titleColor);

    leftPanel.add(activityTitle);
    leftPanel.add(Box.createVerticalStrut(20));

    // =========================================================
    //              RECENT TRANSACTIONS
    // =========================================================

    txn1 = new JLabel();
    txn1.setForeground(textColor);

    txn1Time = new JLabel();
    txn1Time.setForeground(secondaryColor);

    txn2 = new JLabel();
    txn2.setForeground(textColor);

    txn2Time = new JLabel();
    txn2Time.setForeground(secondaryColor);

    txn3 = new JLabel();
    txn3.setForeground(textColor);

    txn3Time = new JLabel();
    txn3Time.setForeground(secondaryColor);

    // =========================================================
    //                  LAST LOGIN
    // =========================================================

    JLabel loginTitle = new JLabel("Last Login");
    loginTitle.setForeground(titleColor);
    loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JLabel loginDate = new JLabel();
    loginDate.setForeground(textColor);

    String loginTime =
            CurrentUser.getLastLogin()
            .toLocalDateTime()
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));

    String[] parts = loginTime.split(" ",4);

    loginDate.setText(
            "<html>"
            + parts[0] + " " + parts[1] + " " + parts[2]
            + "<br>"
            + parts[3]
            + "</html>");

    // =========================================================
    //                  ACCOUNT NUMBER
    // =========================================================

    JLabel accountTitle = new JLabel("Account Number");
    accountTitle.setForeground(titleColor);
    accountTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JLabel accountNo = new JLabel(CurrentUser.getAccNum());
    accountNo.setForeground(textColor);

    // =========================================================
    //              DOWNLOAD STATEMENT
    // =========================================================

    JButton download = new JButton("Download Statement");
    download.setBackground(accentBlue);
    download.setForeground(Color.WHITE);
    download.putClientProperty("JButton.buttonType","roundRect");

    download.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae){
            accSummary();
        }
    });

    // =========================================================
    //                  SYSTEM STATUS
    // =========================================================

    JLabel statusTitle = new JLabel("System Status");
    statusTitle.setForeground(titleColor);
    statusTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JLabel status = new JLabel("● Session Active");
    status.setForeground(successGreen);

    // =========================================================
    //                  SEPARATORS
    // =========================================================

    JSeparator sep1 = new JSeparator();
    sep1.setForeground(accentBlue);

    JSeparator sep2 = new JSeparator();
    sep2.setForeground(accentBlue);

    JSeparator sep3 = new JSeparator();
    sep3.setForeground(accentBlue);

    JSeparator sep4 = new JSeparator();
    sep4.setForeground(accentBlue);

    // =========================================================
    //                  ADD COMPONENTS
    // =========================================================

    leftPanel.add(txn1);
    leftPanel.add(txn1Time);
    leftPanel.add(Box.createVerticalStrut(10));

    leftPanel.add(txn2);
    leftPanel.add(txn2Time);
    leftPanel.add(Box.createVerticalStrut(10));

    leftPanel.add(txn3);
    leftPanel.add(txn3Time);
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(sep1);
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(loginTitle);
    leftPanel.add(loginDate);
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(sep2);
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(accountTitle);
    leftPanel.add(accountNo);
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(sep3);
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(download);
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(sep4);
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(statusTitle);
    leftPanel.add(status);

    add(leftPanel, BorderLayout.WEST);

    // =========================================================
    //          LOAD RECENT TRANSACTIONS (BACKEND)
    // =========================================================

    loadRecentActivity();

}
    
    private void action(){
        JPanel actionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(actionPanel,gbc);
    
    }
    
    
    
    private void dashPanel() {

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    centerPanel = new JPanel(new GridBagLayout());
    centerPanel.setBackground(new Color(18,18,18));
    centerPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10,10,10,10);
    gbc.anchor = GridBagConstraints.CENTER;

    // ===== AVAILABLE BALANCE =====
    balance = new JLabel("Available Balance : KES 0.00");
    balance.setFont(new Font("Segoe UI",Font.BOLD,32));
    balance.setForeground(Color.WHITE);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    centerPanel.add(balance, gbc);

    JCheckBox hideBalance = new JCheckBox("Hide Balance");
        hideBalance.setBackground(new Color(18,18,18));
        hideBalance.setForeground(new Color(210,210,210));
        hideBalance.setFocusPainted(false);
        hideBalance.setFont(new Font("Segoe UI",Font.PLAIN,13));
    hideBalance.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent ae){
        if(hideBalance.isSelected()){
            balance.setText("***********");
        }else{
            balance.setText("KES " + currentBalance);
        }
    }
});

    gbc.gridy = 1;
    centerPanel.add(hideBalance, gbc);

    // ===== QUICK ACTIONS =====
    JButton send = new JButton("Send Money");
    send.setPreferredSize(new Dimension(170,45));
    send.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            cardLayout.show(cardPanel, "action");
            showSendPanel();
        }
    });

    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 2;
    styleButton(send);
    centerPanel.add(send, gbc);

    JButton request = new JButton("Borrow Money");
    request.setPreferredSize(new Dimension(170,45));
    request.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            showLoan();
        }
    });

    gbc.gridx = 1;
    styleButton(request);
    centerPanel.add(request, gbc);

    JButton pay = new JButton("Deposit");
    pay.setPreferredSize(new Dimension(170,45));
    pay.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            cardLayout.show(cardPanel, "action");
            showDepoPanel();
        }
    });

    gbc.gridx = 0;
    gbc.gridy = 3;
    styleButton(pay);
    centerPanel.add(pay, gbc);

    JButton air = new JButton("Buy Airtime");
    air.setPreferredSize(new Dimension(170,45));
    air.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            cardLayout.show(cardPanel, "action");
            showAirPanel();
        }
    });

    gbc.gridx = 1;
    styleButton(air);
    centerPanel.add(air, gbc);

    // ===== SEPARATOR =====
    JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
    sep.setForeground(new Color(60,60,60));
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    centerPanel.add(sep, gbc);

    gbc.fill = GridBagConstraints.NONE;

    // ===== FINANCIAL SNAPSHOT =====
    JLabel snapshot = new JLabel("Financial Snapshot");
    snapshot.setFont(new Font("Arial", Font.BOLD, 17));
    snapshot.setForeground(Color.WHITE);
    gbc.gridy = 5;
    centerPanel.add(snapshot, gbc);

    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;

    JLabel accountType = new JLabel("Account Type");
    accountType.setForeground(new Color(180,180,180));
    JLabel accountValue = new JLabel(CurrentUser.getAccountType());
    accountValue.setForeground(new Color(70,170,255));
    accountValue.setFont(new Font("Arial", Font.PLAIN, 12));
    gbc.gridx = 0;
    gbc.gridy = 6;
    centerPanel.add(accountType, gbc);

    gbc.gridx = 1;
    centerPanel.add(accountValue, gbc);

    JLabel loanStatus = new JLabel("Loan Status");
    loanStatus.setForeground(new Color(180,180,180));
    JLabel loanValue = new JLabel();
    loanValue.setForeground(new Color(70,170,255));
    loanValue.setFont(new Font("Arial", Font.PLAIN, 12));
    if(CurrentUser.getCurrLoan() == 0.00){
                loanValue.setText("No active loan");
        }else{
                loanValue.setText("KES " + CurrentUser.getStatus());
        }

    gbc.gridx = 0;
    gbc.gridy = 7;
    centerPanel.add(loanStatus, gbc);

    gbc.gridx = 1;
    centerPanel.add(loanValue, gbc);

    JLabel loanLimit = new JLabel("Loan Limit");
    loanLimit.setForeground(new Color(180,180,180));
    JLabel loanLimitValue = new JLabel("KES " + CurrentUser.getLimit());
    loanLimitValue.setForeground(new Color(70,170,255));
    loanLimitValue.setFont(new Font("Arial", Font.PLAIN, 12));
    gbc.gridx = 0;
    gbc.gridy = 8;
    centerPanel.add(loanLimit, gbc);

    gbc.gridx = 1;
    centerPanel.add(loanLimitValue, gbc);

    // ===== ACTION PANEL =====
    actionPanel = new JPanel(new GridBagLayout());

    cardPanel.add(centerPanel, "dashboard");
    cardPanel.add(actionPanel, "action");

    add(cardPanel, BorderLayout.CENTER);
}
    
    
    //load the user balance from database
    private void loadBalance(){

    DatabaseCon.database_connection();

    try{

        String query = "SELECT balance FROM accounts WHERE user_id = ?";

        PreparedStatement psmt = con.prepareStatement(query);
        psmt.setLong(1, CurrentUser.getUserId());

        ResultSet rs = psmt.executeQuery();

        if(rs.next()){
                currentBalance = rs.getDouble("balance");
                balance.setText("KES " + currentBalance);
        }

    }catch(SQLException ex){

        Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);

    }

}
    
 
    
    private void logout(){
        DatabaseCon.database_connection();
       int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?");
       if(option == JOptionPane.YES_OPTION){
           
            try {
                long userId1 = CurrentUser.getUserId();
                String query = "UPDATE sessions SET logout_time = NOW(),is_active = 0 WHERE user_id = ? AND is_active = 1";
                PreparedStatement psmt = con.prepareStatement(query);
                psmt.setLong(1, userId1);
                int rowsAffected = psmt.executeUpdate();
                
                    if(rowsAffected  > 0){
                        JOptionPane.showMessageDialog(this,"Logout successful");
                    }else{
                        JOptionPane.showMessageDialog(this, "An error occured,please try again");
                    }
                
                dispose();
                new LoginPage();
            } catch (SQLException ex) {
                Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else{
          setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
       }
    
    }
    
    
    
 private void showSendPanel(){

    actionPanel.removeAll();
    actionPanel.setLayout(new GridBagLayout());
    actionPanel.setBackground(Color.WHITE);
    actionPanel.setBorder(BorderFactory.createEmptyBorder(35,60,35,60));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5,10,5,10);

    //=========================================================
    // TITLE
    //=========================================================

    JLabel title = new JLabel("<html><u>Send Money</u></html>",SwingConstants.CENTER);
    title.setFont(new Font("Arial",Font.BOLD,28));
    title.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 4;
    gbc.insets = new Insets(5,10,35,10);
    actionPanel.add(title,gbc);

    //=========================================================
    // RECIPIENT ACCOUNT LABEL
    //=========================================================

    JLabel recipientTitle = new JLabel("  Recipient Account");
    recipientTitle.setIcon(FontIcon.of(FontAwesomeSolid.USER, 18));
    recipientTitle.setFont(new Font("Sans Serif",Font.BOLD,10));
    recipientTitle.setForeground(Color.BLACK);
    gbc.gridy = 1;
    gbc.insets = new Insets(5,10,5,10);
    actionPanel.add(recipientTitle,gbc);

    //=========================================================
    // RECIPIENT ACCOUNT FIELD
    //=========================================================

    recipientField = new JTextField(25);
    recipientField.setForeground(Color.black);
    recipientField.setOpaque(false);
    recipientField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(180,180,180)));
    gbc.gridy = 2;
    gbc.insets = new Insets(0,10,20,10);
    actionPanel.add(recipientField,gbc);

  //=========================================================
 // RECIPIENT / STATUS
//=========================================================

    gbc.gridwidth = 1;

    // Recipient label
    JLabel recLbl = new JLabel("Recipient");
    recLbl.setFont(new Font("Sans Serif",Font.BOLD,10));
    recLbl.setForeground(Color.BLACK);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.insets = new Insets(5,10,5,10);
    actionPanel.add(recLbl,gbc);

    // Status label
    JLabel statLbl = new JLabel("Status");
    statLbl.setFont(new Font("Sans Serif",Font.BOLD,10));
    statLbl.setForeground(Color.BLACK);
    gbc.gridx = 1;
    actionPanel.add(statLbl,gbc);

    // Recipient value
    recipientNameLabel = new JLabel("-");
    recipientNameLabel.setFont(new Font("Arial",Font.BOLD,14));
    recipientNameLabel.setForeground(new Color(17,52,88));
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.insets = new Insets(0,10,20,10);
    actionPanel.add(recipientNameLabel,gbc);

    // Status value
    statusLabel = new JLabel("Waiting...");
    statusLabel.setFont(new Font("Sans Serif",Font.BOLD,10));;
    statusLabel.setForeground(new Color(17,52,88));
    gbc.gridx = 1;
    actionPanel.add(statusLabel,gbc);

    //=========================================================
    // AMOUNT LABEL
    //=========================================================

    JLabel amountLbl = new JLabel("  Amount (KES)");
    amountLbl.setIcon(FontIcon.of(FontAwesomeSolid.MONEY_BILL_WAVE, 18));
    amountLbl.setFont(new Font("Sans Serif",Font.BOLD,10));
    amountLbl.setForeground(Color.BLACK);
    gbc.gridx=0;
    gbc.gridy = 5;
    gbc.insets = new Insets(5,10,5,10);
    actionPanel.add(amountLbl,gbc);

    //=========================================================
    // AMOUNT FIELD
    //=========================================================

    sendAmountField = new JTextField(25);
    sendAmountField.setForeground(Color.black);
    sendAmountField.setOpaque(false);
    sendAmountField.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(180,180,180)));
    gbc.gridy = 6;
    gbc.insets = new Insets(0,10,20,10);
    actionPanel.add(sendAmountField,gbc);
    
     //=========================================================
    // AVAILABLE BALANCE LABEL
   //=========================================================

    JLabel balTitle = new JLabel("Available Balance");
    balTitle.setFont(new Font("Arial",Font.BOLD,13));
    balTitle.setForeground(Color.BLACK);
    gbc.gridy = 7;
    gbc.insets = new Insets(5,10,5,10);
    actionPanel.add(balTitle,gbc);

    //=========================================================
   // AVAILABLE BALANCE VALUE
  //=========================================================

    JLabel bal = new JLabel("KES " + CurrentUser.getBalance());
    bal.setFont(new Font("Arial",Font.BOLD,15));
    bal.setForeground(new Color(17,52,88));
    gbc.gridy = 8;
    gbc.insets = new Insets(0,10,30,10);
    actionPanel.add(bal,gbc);

    //=========================================================
    // BUTTON PANEL
    //=========================================================

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
    btnPanel.setBackground(Color.WHITE);
    JButton backBtn = new JButton("Cancel");
    backBtn.setPreferredSize(new Dimension(140,42));
    backBtn.setBackground(new Color(235,235,235));
    backBtn.setForeground(Color.BLACK);
    backBtn.setFocusable(false);
    backBtn.putClientProperty("JButton.buttonType","roundRect");
    backBtn.putClientProperty("JButton.arc",25);
    sendBtn = new JButton("Send Money");
    sendBtn.setPreferredSize(new Dimension(170,42));
    sendBtn.setBackground(new Color(17,52,88));
    sendBtn.setForeground(Color.WHITE);
    sendBtn.setFocusable(false);
    sendBtn.putClientProperty("JButton.buttonType","roundRect");
    sendBtn.putClientProperty("JButton.arc",25);
    btnPanel.add(backBtn);
    btnPanel.add(sendBtn);
    gbc.gridy = 9;
    gbc.insets = new Insets(20,10,10,10);
    actionPanel.add(btnPanel,gbc);

    //=========================================================
    // EVENTS
    //=========================================================

    backBtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae){

            cardLayout.show(cardPanel,"dashboard");

        }

    });

    sendMoneyLogic();

    actionPanel.revalidate();
    actionPanel.repaint();

}



private void showLoan() {

  new  LoanPage();
  dispose();
}

private void showDepoPanel() {

    actionPanel.removeAll();
    actionPanel.setLayout(new GridBagLayout());
    actionPanel.setBackground(Color.WHITE);
    actionPanel.setBorder(BorderFactory.createEmptyBorder(35,60,35,60));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(8,10,8,10);

    // =========================================================
    //                  PAGE TITLE
    // =========================================================

    JLabel title = new JLabel("Deposit Money",SwingConstants.CENTER);
    title.setFont(new Font("Arial",Font.BOLD,28));
    title.setForeground(new Color(17,52,88));

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(5,10,35,10);

    actionPanel.add(title,gbc);

    // =========================================================
    //          SHOW CURRENT ACCOUNT BALANCE
    // =========================================================
    // Placeholder for now.
    // Later this will read directly from the database.

    currentBal = new JLabel("Available Balance");
    currentBal.setFont(new Font("Arial",Font.BOLD,13));
    currentBal.setForeground(Color.BLACK);
    gbc.gridy = 1;
    gbc.insets = new Insets(5,10,5,10);
    actionPanel.add(currentBal,gbc);
    JLabel currentAmount = new JLabel("KES " + currentBalance);
    currentAmount.setFont(new Font("Arial",Font.BOLD,24));
    currentAmount.setForeground(new Color(17,52,88));
    gbc.gridy = 2;
    gbc.insets = new Insets(0,10,25,10);
    actionPanel.add(currentAmount,gbc);

 
    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;

    // =========================================================
    //                  AMOUNT FIELD
    // =========================================================

    JLabel amount = new JLabel("Amount (KES)");
    amount.setFont(new Font("Arial",Font.BOLD,13));
    amount.setForeground(Color.BLACK);
    gbc.gridx = 0;
    gbc.gridy = 3;
    actionPanel.add(amount,gbc);

    depositAmountField = new JTextField(25);
    depositAmountField.setForeground(Color.BLACK);
    depositAmountField.setFont(new Font("Arial",Font.PLAIN,15));
    depositAmountField.setOpaque(false);
    depositAmountField.setBorder(
    BorderFactory.createMatteBorder(0,0,1,0, new Color(180,180,180)));

    gbc.gridx = 1;

    actionPanel.add(depositAmountField,gbc);

    // =========================================================
    //              QUICK AMOUNT BUTTONS
    // =========================================================

    JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));
    quickPanel.setBackground(Color.WHITE);

    int[] quickAmounts = {100,500,1000,5000};

    for(int amt : quickAmounts){

        JButton btn = new JButton("KES " + amt);

        btn.setBackground(new Color(17,52,88));
        btn.setForeground(Color.WHITE);
        btn.setFocusable(false);
        btn.putClientProperty("JButton.buttonType","roundRect");
        btn.putClientProperty("JButton.arc",25);

        btn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae){

                depositAmountField.setText(String.valueOf(amt));

            }

        });

        quickPanel.add(btn);

    }

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(20,10,25,10);

    actionPanel.add(quickPanel,gbc);

    // =========================================================
    //                  BUTTON PANEL
    // =========================================================

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
    buttonPanel.setBackground(Color.WHITE);

    depositBtn = new JButton("Deposit");
    depositBtn.setPreferredSize(new Dimension(160,42));
    depositBtn.setBackground(new Color(17,52,88));
    depositBtn.setForeground(Color.WHITE);
    depositBtn.setFocusable(false);
    depositBtn.putClientProperty("JButton.buttonType","roundRect");
    depositBtn.putClientProperty("JButton.arc",25);

    JButton backBtn = new JButton("Cancel");
    backBtn.setPreferredSize(new Dimension(140,42));
    backBtn.setBackground(new Color(235,235,235));
    backBtn.setForeground(Color.BLACK);
    backBtn.setFocusable(false);
    backBtn.putClientProperty("JButton.buttonType","roundRect");
    backBtn.putClientProperty("JButton.arc",25);

    buttonPanel.add(backBtn);
    buttonPanel.add(depositBtn);

    gbc.gridy = 5;
    gbc.insets = new Insets(20,10,10,10);

    actionPanel.add(buttonPanel,gbc);

    // =========================================================
    //                  BACK BUTTON
    // =========================================================

    backBtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae){

            cardLayout.show(cardPanel,"dashboard");

        }

    });

    depositMoneyLogic();
    loadBalance();
    

    // Refresh the panel so Swing redraws it.
    actionPanel.revalidate();
    actionPanel.repaint();

}



private void showAirPanel(){

    actionPanel.removeAll();
    actionPanel.setLayout(new GridBagLayout());
    actionPanel.setBackground(Color.WHITE);
    actionPanel.setBorder(BorderFactory.createEmptyBorder(35,60,35,60));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(8,10,8,10);

    //=========================================================
    // TITLE
    //=========================================================

    JLabel title = new JLabel("Buy Airtime",SwingConstants.CENTER);
    title.setFont(new Font("Arial",Font.BOLD,28));
    title.setForeground(new Color(17,52,88));

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(5,10,35,10);

    actionPanel.add(title,gbc);

    //=========================================================
    // AVAILABLE BALANCE
    //=========================================================

    JLabel available = new JLabel("Available Balance");
    available.setFont(new Font("Arial",Font.BOLD,13));
    available.setForeground(Color.BLACK);

    gbc.gridy = 1;
    gbc.insets = new Insets(5,10,5,10);

    actionPanel.add(available,gbc);

    currentBal = new JLabel("KES " + currentBalance);
    currentBal.setFont(new Font("Arial",Font.BOLD,24));
    currentBal.setForeground(new Color(17,52,88));

    gbc.gridy = 2;
    gbc.insets = new Insets(0,10,25,10);

    actionPanel.add(currentBal,gbc);

    // =========================================================
    //                  AMOUNT FIELD
    // =========================================================

    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;

    JLabel amount = new JLabel("Amount (KES)");
    amount.setFont(new Font("Arial",Font.BOLD,13));
    amount.setForeground(Color.BLACK);

    gbc.gridx = 0;
    gbc.gridy = 3;

    actionPanel.add(amount,gbc);

    airtimeAmountField = new JTextField(25);
    airtimeAmountField.setFont(new Font("Arial",Font.PLAIN,15));
    airtimeAmountField.setForeground(Color.BLACK);
    airtimeAmountField.setOpaque(false);
    airtimeAmountField.setBorder(
            BorderFactory.createMatteBorder(
                    0,0,1,0,
                    new Color(180,180,180)));

    gbc.gridx = 1;

    actionPanel.add(airtimeAmountField,gbc);

    // =========================================================
    //              QUICK AMOUNT BUTTONS
    // =========================================================

    JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));
quickPanel.setBackground(Color.WHITE);

int[] quickAmounts = {10,20,50,100};

for(int amt : quickAmounts){

    JButton btn = new JButton("KES " + amt);

    btn.setBackground(new Color(17,52,88));
    btn.setForeground(Color.WHITE);
    btn.setFocusable(false);
    btn.putClientProperty("JButton.buttonType","roundRect");
    btn.putClientProperty("JButton.arc",25);

    btn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae){

            airtimeAmountField.setText(String.valueOf(amt));

        }

    });

    quickPanel.add(btn);

    }

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(20,10,25,10);

    actionPanel.add(quickPanel,gbc);

    // =========================================================
    //                  BUTTONS
    // =========================================================

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
    buttonPanel.setBackground(Color.WHITE);

    buyBtn = new JButton("Buy Airtime");
    buyBtn.setPreferredSize(new Dimension(170,42));
    buyBtn.setBackground(new Color(17,52,88));
    buyBtn.setForeground(Color.WHITE);
    buyBtn.setFocusable(false);
    buyBtn.putClientProperty("JButton.buttonType","roundRect");
    buyBtn.putClientProperty("JButton.arc",25);

    JButton backBtn = new JButton("Cancel");
    backBtn.setPreferredSize(new Dimension(140,42));
    backBtn.setBackground(new Color(235,235,235));
    backBtn.setForeground(Color.BLACK);
    backBtn.setFocusable(false);
    backBtn.putClientProperty("JButton.buttonType","roundRect");
    backBtn.putClientProperty("JButton.arc",25);

    buttonPanel.add(backBtn);
    buttonPanel.add(buyBtn);

    gbc.gridy = 5;
    gbc.insets = new Insets(20,10,10,10);

    actionPanel.add(buttonPanel,gbc);

    // =========================================================
    //                  BACK BUTTON
    // =========================================================

    backBtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae){

            cardLayout.show(cardPanel, "dashboard");
            
        }

    });
    
    buyAirtimeLogic();

    actionPanel.revalidate();
    actionPanel.repaint();

}

//method to generate transactionID and recents automatically
    public static String generateID(){
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            
            StringBuilder transID = new StringBuilder();
            
            Random ran = new Random();
            
            for(int i = 0; i < 9; i++){
                    transID.append(chars.charAt(ran.nextInt(chars.length())));
            }
            
            return transID.toString();
            
    }
    
    
    private void loadRecentActivity(){

    try{

        DatabaseCon.database_connection();

        String query =
                "SELECT transaction_type, amount, created_at "
                + "FROM transactions "
                + "WHERE user_id = ? "
                + "ORDER BY created_at DESC "
                + "LIMIT 3";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setLong(1, CurrentUser.getUserId());

        ResultSet rs = ps.executeQuery();

        JLabel[] transactions = {txn1, txn2, txn3};
        JLabel[] dates = {txn1Time, txn2Time, txn3Time};

        int i = 0;

        while(rs.next()){

            String type = rs.getString("transaction_type");
            double amount = rs.getDouble("amount");
            Timestamp created = rs.getTimestamp("created_at");

            String sign = "+";

            if(type.equalsIgnoreCase("WITHDRAW")
                    || type.equalsIgnoreCase("AIRTIME")
                    || type.equalsIgnoreCase("TRANSFER SENT")){

                sign = "-";

            }

            transactions[i].setText(type + "      " + sign + "KES " + amount);
            dates[i].setText(created.toString());

            i++;

        }

        while(i < 3){

            transactions[i].setText("No Activity");
            dates[i].setText("");

            i++;

        }

    }catch(SQLException ex){

        Logger.getLogger(LandingPage.class.getName())
                .log(Level.SEVERE, null, ex);

    }

}
    
    //Back-end logic for buttons and displaying info
    // =========================================================
    //              BUY AIRTIME LOGIC
    // =========================================================
    private void buyAirtimeLogic(){
            buyBtn.addActionListener(new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent ae){

            // =========================================================
            //          VALIDATE INPUT
            // =========================================================

            if(airtimeAmountField.getText().isEmpty()){

                JOptionPane.showMessageDialog(LandingPage.this,
                        "Please enter an amount.");

                return;

            }

            double amtOut;

            try{

                amtOut = Double.parseDouble(airtimeAmountField.getText());

            }catch(NumberFormatException ex){

                JOptionPane.showMessageDialog(LandingPage.this,
                        "Enter a valid amount.");

                return;

            }

            if(amtOut <= 0){

                JOptionPane.showMessageDialog(LandingPage.this,
                        "Amount must be greater than zero.");

                return;

            }

            // =========================================================
            //      ENSURE SUFFICIENT BALANCE
            // =========================================================

            if(currentBalance < amtOut){

                JOptionPane.showMessageDialog(LandingPage.this,
                        "Insufficient balance.");

                return;

            }

            try{

                DatabaseCon.database_connection();

                con.setAutoCommit(false);

                // =========================================================
                //      DEDUCT AIRTIME AMOUNT
                // =========================================================

                String updateBalance =
                        "UPDATE accounts SET balance = balance - ? WHERE user_id = ?";

                PreparedStatement ps1 = con.prepareStatement(updateBalance);

                ps1.setDouble(1, amtOut);
                ps1.setLong(2, CurrentUser.getUserId());

                ps1.executeUpdate();

                // =========================================================
                //      RECORD TRANSACTION
                // =========================================================

                String insertTransaction =
                        "INSERT INTO transactions(transaction_id,account_id,user_id, amount, transaction_type) "
                        + "VALUES(?, ?, ?, ?, ?)";

                PreparedStatement ps2 = con.prepareStatement(insertTransaction);
                
                ps2.setString(1,  generateID());
                ps2.setLong(2,  CurrentUser.getAccountId());
                ps2.setLong(3, CurrentUser.getUserId());
                ps2.setDouble(4, amtOut);
                ps2.setString(5, "AIRTIME");

                ps2.executeUpdate();

                // =========================================================
                //      SAVE CHANGES
                // =========================================================
            
                con.commit();
                con.setAutoCommit(true);
                
                loadBalance();
                loadRecentActivity();

                currentBal.setText("KES " + currentBalance);

                JOptionPane.showMessageDialog(LandingPage.this,
                        "Airtime purchase successful.");

                airtimeAmountField.setText("");

                cardLayout.show(cardPanel, "dashboard");

            }catch(SQLException ex){

                try{

                    con.rollback();
                    con.setAutoCommit(true);

                }catch(SQLException ex1){

                    Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex1);

                }

                Logger.getLogger(LandingPage.class.getName()).log(Level.SEVERE, null, ex);

                JOptionPane.showMessageDialog(LandingPage.this,
                        "Purchase failed.");

            }

        }

    });
    
    
    
    }//end of buy airtime method
    
    
    // =========================================================
    //                  DEPOSIT LOGIC
    // =========================================================
    public void depositMoneyLogic(){
    depositBtn.addActionListener(new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent ae){

        // =========================================================
        //          VALIDATE INPUT
        // =========================================================

        if(depositAmountField.getText().isEmpty()){

            JOptionPane.showMessageDialog(LandingPage.this,
                    "Please enter an amount.");

            return;

        }

        double amtIn;

        try{

            amtIn = Double.parseDouble(depositAmountField.getText());

        }catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(LandingPage.this,
                    "Enter a valid amount.");

            return;

        }

        if(amtIn <= 0){

            JOptionPane.showMessageDialog(LandingPage.this,
                    "Amount must be greater than zero.");

            return;

        }

        try{

            DatabaseCon.database_connection();

            con.setAutoCommit(false);

            // =========================================================
            //          UPDATE ACCOUNT BALANCE
            // =========================================================

            String updateBalance =
                    "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";

            PreparedStatement ps1 = con.prepareStatement(updateBalance);

            ps1.setDouble(1, amtIn);
            ps1.setLong(2, CurrentUser.getUserId());

            ps1.executeUpdate();

            // =========================================================
            //          RECORD TRANSACTION
            // =========================================================

            String insertTransaction = "INSERT INTO transactions(transaction_id,account_id,user_id, amount, transaction_type) " + "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement ps2 = con.prepareStatement(insertTransaction);
            
            ps2.setString(1, generateID());
            ps2.setLong(2, CurrentUser.getAccountId());
            ps2.setLong(3, CurrentUser.getUserId());
            ps2.setDouble(4, amtIn);
            ps2.setString(5, "DEPOSIT");

            ps2.executeUpdate();

            // =========================================================
            //          SAVE CHANGES
            // =========================================================

            
            con.commit();
            con.setAutoCommit(true);

            loadBalance();
            loadRecentActivity();
            
            currentBal.setText("KES " + currentBalance);

            JOptionPane.showMessageDialog(LandingPage.this,
                    "Deposit successful.");

            depositAmountField.setText("");

            cardLayout.show(cardPanel, "dashboard");

        }catch(SQLException ex){

            try{

                con.rollback();
                con.setAutoCommit(true);

            }catch(SQLException ex1){

                Logger.getLogger(LandingPage.class.getName())
                        .log(Level.SEVERE, null, ex1);

            }

            Logger.getLogger(LandingPage.class.getName())
                    .log(Level.SEVERE, null, ex);

            JOptionPane.showMessageDialog(LandingPage.this,
                    "Deposit failed.");

        }

    }

 });
    }//end of deposit logic


    
// =========================================================
//              SEND MONEY LOGIC
// =========================================================
    
    
    private void sendMoneyLogic(){
    sendBtn.addActionListener(new ActionListener(){

    @Override
    public void actionPerformed(ActionEvent ae){

        // =====================================================
        //          VALIDATE INPUT
        // =====================================================

        if(recipientField.getText().isEmpty()|| sendAmountField.getText().isEmpty()){

            JOptionPane.showMessageDialog(
                    LandingPage.this,
                    "Fill in all fields.");

            return;

        }

        double amtOut;

        try{

            amtOut = Double.parseDouble(sendAmountField.getText());

        }catch(NumberFormatException ex){

            JOptionPane.showMessageDialog(
                    LandingPage.this,
                    "Enter a valid amount.");

            return;

        }

        if(amtOut <= 0){

            JOptionPane.showMessageDialog(
                    LandingPage.this,
                    "Amount must be greater than zero.");

            return;

        }

        try{

            DatabaseCon.database_connection();

            con.setAutoCommit(false);

            // =====================================================
            //      FIND RECIPIENT ACCOUNT
            // =====================================================

            String recipientQuery =
                "SELECT a.account_id, " +
                "a.user_id, " +
                "u.first_name, " +
                "u.last_name " +
                "FROM accounts a " +
                "JOIN users u " +
                "ON a.user_id = u.user_id " +
                "WHERE a.account_number = ?";

            PreparedStatement ps1 = con.prepareStatement(recipientQuery);

            ps1.setLong(1,Long.parseLong(recipientField.getText()));

            ResultSet rs = ps1.executeQuery();

            if(!rs.next()){

                recipientNameLabel.setText("-");
                statusLabel.setText("❌ Not Found");

                return;

            }

            long recipientAccountId =
                    rs.getLong("account_id");

            long recipientUserId =
                    rs.getLong("user_id");
            
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");

            recipientNameLabel.setText(firstName + " " + lastName);
            statusLabel.setText("✔ Verified");

            // =====================================================
            //      PREVENT SELF TRANSFER
            // =====================================================

            if(recipientUserId
                    == CurrentUser.getUserId()){

                JOptionPane.showMessageDialog(
                        LandingPage.this,
                        "You cannot send money to yourself.");

                return;

            }

            // =====================================================
            //      CHECK BALANCE
            // =====================================================

            if(currentBalance < amtOut){

                JOptionPane.showMessageDialog(
                        LandingPage.this,
                        "Insufficient balance.");

                return;

            }

            // =====================================================
            //      DEDUCT SENDER
            // =====================================================

            String deduct =
                    "UPDATE accounts "
                    + "SET balance = balance - ? "
                    + "WHERE account_id = ?";

            PreparedStatement ps2 = con.prepareStatement(deduct);

            ps2.setDouble(1,amtOut);
            ps2.setLong(2, CurrentUser.getAccountId());

            ps2.executeUpdate();

            // =====================================================
            //      CREDIT RECEIVER
            // =====================================================

            String credit =
                    "UPDATE accounts "
                    + "SET balance = balance + ? "
                    + "WHERE account_id = ?";

            PreparedStatement ps3 =
                    con.prepareStatement(credit);

            ps3.setDouble(1,amtOut);
            ps3.setLong(2,recipientAccountId);

            ps3.executeUpdate();

            // =====================================================
            //      SENDER TRANSACTION
            // =====================================================

            String senderTxn ="INSERT INTO transactions(transaction_id,account_id,user_id,amount,recipient_account_id,transaction_type)"
                    + "VALUES(?,?,?,?,?,?)";

            PreparedStatement ps4 =
                    con.prepareStatement(senderTxn);
            
            ps4.setString(1,
                    generateID());

            ps4.setLong(2,
                    CurrentUser.getAccountId());
            
            ps4.setLong(3,
                    CurrentUser.getUserId());

            ps4.setDouble(4,
                    amtOut);
            
            ps4.setLong(5,
                    recipientAccountId);
            
            ps4.setString(6,
                    "TRANSFER SENT");

            ps4.executeUpdate();

            // =====================================================
            //      RECEIVER TRANSACTION
            // =====================================================

                                String receiverTxn =
                    "INSERT INTO transactions(transaction_id,account_id,user_id,amount,recipient_account_id,transaction_type)"
                    + " VALUES(?,?,?,?,?,?)";

                    PreparedStatement ps5 =
                            con.prepareStatement(receiverTxn);

                    ps5.setString(1, generateID());

                    ps5.setLong(2, recipientAccountId);

                    ps5.setLong(3, recipientUserId);

                    ps5.setDouble(4, amtOut);

                    ps5.setLong(5, CurrentUser.getAccountId());

                    ps5.setString(6, "TRANSFER RECEIVED");

                    ps5.executeUpdate();

            // =====================================================
            //      SAVE CHANGES
            // =====================================================

            con.commit();
            con.setAutoCommit(true);

            loadBalance();
            loadRecentActivity();

            JOptionPane.showMessageDialog(
                    LandingPage.this,
                    "Transfer successful.");

            sendAmountField.setText("");
            recipientField.setText("");

            cardLayout.show(cardPanel,
                    "dashboard");

        }catch(SQLException ex){

            try{

                con.rollback();
                con.setAutoCommit(true);

            }catch(SQLException ex1){

                Logger.getLogger(
                        LandingPage.class.getName())
                        .log(Level.SEVERE,
                                null,
                                ex1);

            }

            Logger.getLogger(
                    LandingPage.class.getName())
                    .log(Level.SEVERE,
                            null,
                            ex);

            JOptionPane.showMessageDialog(
                    LandingPage.this,
                    "Transfer failed.");

        }

    }

});
    }//end of send logic


private void accSummary(){
        //Generate the account summary using PDFBox jar file
         try{

        DatabaseCon.database_connection();

        String sql =
                "SELECT transaction_id,"
                + "transaction_type,"
                + "amount,"
                + "created_at "
                + "FROM transactions "
                + "WHERE account_id=? "
                + "ORDER BY created_at DESC";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setLong(1,
                CurrentUser.getAccountId());

        ResultSet rs =
                ps.executeQuery();

        // ============================================
        // CREATE PDF
        // ============================================
        
        //surpress warnings
        java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.SEVERE);

        PDDocument document =
                new PDDocument();

        PDPage page =
                new PDPage();

        document.addPage(page);

        PDPageContentStream content =
                new PDPageContentStream(document,page);

        content.beginText();

        content.setFont(
                new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD),
                18
            );

        content.newLineAtOffset(50,750);

        content.showText("GFH Bank");

        content.newLineAtOffset(0,-25);

        content.setFont(
                new PDType1Font(Standard14Fonts.FontName.HELVETICA),
                12
            );

        content.showText(
                "Customer : "
                + CurrentUser.getName()
                + " "
                + CurrentUser.getLastName());

        content.newLineAtOffset(0,-18);

        content.showText(
                "Account No : "
                + CurrentUser.getAccNum());

        content.newLineAtOffset(0,-18);

        content.showText(
                "Account Type : "
                + CurrentUser.getAccountType());

        content.newLineAtOffset(0,-18);

        content.showText(
                "Balance : KES "
                + CurrentUser.getBalance());

        content.newLineAtOffset(0,-35);

        content.setFont(
                new PDType1Font(Standard14Fonts.FontName.HELVETICA),
                12
            );

        content.showText(
                "Transactions");

        content.newLineAtOffset(0,-20);

        content.setFont(
                new PDType1Font(Standard14Fonts.FontName.COURIER),
                10
            );;

        // ============================================
        // LOOP THROUGH TRANSACTIONS
        // ============================================

        while(rs.next()){

            String line =
                    rs.getString("transaction_id")
                    + " | "
                    + rs.getString("transaction_type")
                    + " | KES "
                    + rs.getDouble("amount")
                    + " | "
                    + rs.getTimestamp("created_at");

            content.showText(line);

            content.newLineAtOffset(0,-15);

        }

        content.endText();

        content.close();

        JFileChooser chooser =
        new JFileChooser();

        chooser.setSelectedFile(
                new File("Statement.pdf"));
        int option =
                chooser.showSaveDialog(this);

        if(option == JFileChooser.APPROVE_OPTION){

            document.save(
                    chooser.getSelectedFile());

            JOptionPane.showMessageDialog(
                    this,
                    "Statement downloaded successfully.");

        }

        document.close();

    }catch(Exception ex){

        Logger.getLogger(
                LandingPage.class.getName())
                .log(Level.SEVERE,
                        null,
                        ex);

    }



}

private void styleButton(JButton button){

    button.setBackground(new Color(28,74,122));
    button.setForeground(Color.WHITE);

    button.setFocusable(false);

    button.putClientProperty("JButton.buttonType", "borderless");
    button.putClientProperty("JComponent.minimumHeight", 42);

    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
}

    
private JLabel createProfilePicture(String path,int size){

    Image image = new ImageIcon(path).getImage();

    JLabel profile = new JLabel(){

        @Override
        protected void paintComponent(Graphics g){

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setClip(new java.awt.geom.Ellipse2D.Double(0,0,size,size));

            g2.drawImage(image,0,0,size,size,null);

            g2.dispose();
        }

    };

    profile.setPreferredSize(new Dimension(size,size));

    return profile;
}

}