/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package SystemUI;

import static SystemUI.DatabaseCon.con;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.sql.*;

import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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

    private JLabel txn2;
    private JLabel txn2Time;

    private JLabel txn3;
    private JLabel txn3Time;
    
    private JButton buyBtn,depositBtn,sendBtn;
    private JTextField depositAmountField,sendAmountField,recipientField,airtimeAmountField;

    
    //--constructor--//
   LandingPage(){
       super("Home");
       setSize(1280,600);
        setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BorderLayout(5,5));
    topPanel();
   // midPanel();
    sidePanel();
    dashPanel();
    loadBalance();

   
   setVisible(true);
   }
   
   
   
    public static void main(String[] args) {
        // TODO code application logic here
        new LandingPage();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae){
        dashPanel();
        
       
        
    }
    
    
    
    private void topPanel(){
         JPanel top = new JPanel(new BorderLayout(10,10));
         top.setPreferredSize(new Dimension(0,50));
                
         //greeting 
         JPanel leftSideTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 2,0));
         ImageIcon originalIcon = new ImageIcon("pictures/image.jpg");
         Image scaled = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
         JLabel profile = new JLabel(new ImageIcon(scaled));
         JLabel greeting = new JLabel(CurrentUser.getName()+ " " + CurrentUser.getLastName());
         leftSideTop.add(profile);//add the greeting and pfp to the left
         leftSideTop.add(greeting);
         top.add(leftSideTop,BorderLayout.WEST);
         
         //title
         JLabel title = new JLabel("GFH Bank",SwingConstants.CENTER);
         title.setFont(new Font("Arial",Font.BOLD,23));
         top.add(title,BorderLayout.CENTER);//the title IN the panel
         
         //button
         JButton logOut = new JButton("Logout");
         logOut.setPreferredSize(new Dimension(100,40));
         logOut.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent ae){
                 logout();
              }
         });
         top.add(logOut,BorderLayout.EAST);
         
         //add a separator
         JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
         sep.setPreferredSize(new Dimension(0,2));
         top.add(sep,BorderLayout.SOUTH);
         
         add(top,BorderLayout.NORTH);//the panel itself
    }
    
    
    /*private void midPanel(){
        JPanel middle = new JPanel(new FlowLayout(FlowLayout.LEFT,100,10));
        middle.setPreferredSize(new Dimension(0,70));
        //loop to create buttons
        String[] buttons = {"Deposit","Withdraw","Loan"};
        for(String button : buttons){
            JButton btn = new JButton(button);
            btn.setPreferredSize(new Dimension(150,20));//width,height
            middle.add(btn);
        }
        //create a separator
        JPanel sepPanel = new JPanel(new BorderLayout());
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sepPanel.setPreferredSize(new Dimension(0, 2));
        sepPanel.add(sep, BorderLayout.CENTER);
        middle.add(sepPanel);

        add(middle, BorderLayout.SOUTH);
    }*/
    private void sidePanel() {

    JPanel leftPanel = new JPanel();
    leftPanel.setPreferredSize(new Dimension(250, 0));
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
    leftPanel.setBorder(BorderFactory.createTitledBorder("Recent Activity"));

    // =========================================================
    //              RECENT TRANSACTIONS
    // =========================================================

    txn1 = new JLabel();
    txn1Time = new JLabel();

    txn2 = new JLabel();
    txn2Time = new JLabel();

    txn3 = new JLabel();
    txn3Time = new JLabel();

    // =========================================================
    //                  LAST LOGIN
    // =========================================================
    JLabel loginTitle = new JLabel("Last Login");
    JLabel loginDate = new JLabel();
    String loginTime =
CurrentUser.getLastLogin()
.toLocalDateTime()
.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));

String[] parts = loginTime.split(" ", 4);

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
    JLabel accountNo = new JLabel(CurrentUser.getAccNum());

    // =========================================================
    //              DOWNLOAD STATEMENT
    // =========================================================

    JButton download = new JButton("Download Statement");

    // =========================================================
    //                  SYSTEM STATUS
    // =========================================================

    JLabel statusTitle = new JLabel("System Status");
    JLabel status = new JLabel("● Session Active");

    leftPanel.add(txn1);
    leftPanel.add(txn1Time);
    leftPanel.add(Box.createVerticalStrut(10));

    leftPanel.add(txn2);
    leftPanel.add(txn2Time);
    leftPanel.add(Box.createVerticalStrut(10));

    leftPanel.add(txn3);
    leftPanel.add(txn3Time);
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(new JSeparator());
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(loginTitle);
    leftPanel.add(loginDate);
    //time here
    
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(new JSeparator());
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(accountTitle);
    leftPanel.add(accountNo);
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(new JSeparator());
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(download);
    leftPanel.add(Box.createVerticalStrut(20));

    leftPanel.add(new JSeparator());
    leftPanel.add(Box.createVerticalStrut(15));

    leftPanel.add(statusTitle);
    leftPanel.add(status);

    add(leftPanel, BorderLayout.WEST);

    // =========================================================
    //          LOAD RECENT TRANSACTIONS
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
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10,10,10,10);
    gbc.anchor = GridBagConstraints.CENTER;

    // ===== AVAILABLE BALANCE =====
    balance = new JLabel("Available Balance : KES 0.00");
    balance.setFont(new Font("Arial", Font.BOLD, 26));

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    centerPanel.add(balance, gbc);

    JCheckBox hideBalance = new JCheckBox("Hide Balance");
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
    centerPanel.add(air, gbc);

    // ===== SEPARATOR =====
    JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);

    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    centerPanel.add(sep, gbc);

    gbc.fill = GridBagConstraints.NONE;

    // ===== FINANCIAL SNAPSHOT =====
    JLabel snapshot = new JLabel("Financial Snapshot");
    snapshot.setFont(new Font("Arial", Font.BOLD, 18));

    gbc.gridy = 5;
    centerPanel.add(snapshot, gbc);

    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;

    JLabel accountType = new JLabel("Account Type");
    JLabel accountValue = new JLabel("Savings Account");

    gbc.gridx = 0;
    gbc.gridy = 6;
    centerPanel.add(accountType, gbc);

    gbc.gridx = 1;
    centerPanel.add(accountValue, gbc);

    JLabel loanStatus = new JLabel("Loan Status");
    JLabel loanValue = new JLabel("No Active Loan");

    gbc.gridx = 0;
    gbc.gridy = 7;
    centerPanel.add(loanStatus, gbc);

    gbc.gridx = 1;
    centerPanel.add(loanValue, gbc);

    JLabel loanLimit = new JLabel("Loan Limit");
    JLabel loanLimitValue = new JLabel("KES 50,000");

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
    
    
    
    private void showSendPanel() {

    actionPanel.removeAll();
    actionPanel.setLayout(new GridBagLayout());
    actionPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // ===== TITLE =====
    JLabel title = new JLabel("Send Money");
    title.setFont(new Font("Arial", Font.BOLD, 20));

    gbc.gridx = 0; gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    actionPanel.add(title, gbc);

    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;

    // ===== RECIPIENT ACCOUNT =====
    gbc.gridx = 0; gbc.gridy = 1;
    actionPanel.add(new JLabel("Recipient Account:"), gbc);

    recipientField = new JTextField(18);
    gbc.gridx = 1;
    actionPanel.add(recipientField, gbc);

    // ===== AMOUNT =====
    gbc.gridx = 0; gbc.gridy = 2;
    actionPanel.add(new JLabel("Amount:"), gbc);

    sendAmountField = new JTextField(18);
    gbc.gridx = 1;
    actionPanel.add(sendAmountField, gbc);
    
    // ===== DATE =====
    gbc.gridx = 0; gbc.gridy = 4;
    actionPanel.add(new JLabel("Date:"), gbc);

    JTextField dateField = new JTextField(18);
    dateField.setText(java.time.LocalDate.now().toString());
    dateField.setEditable(false); // read-only
    gbc.gridx = 1;
    actionPanel.add(dateField, gbc);

    // ===== BUTTONS =====
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    sendBtn = new JButton("Send");
    JButton backBtn = new JButton("Back");

    // Simple navigation only
    backBtn.addActionListener(e -> cardLayout.show(cardPanel, "dashboard"));

    buttonPanel.add(sendBtn);
    buttonPanel.add(backBtn);

    gbc.gridx = 0; gbc.gridy = 5;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    actionPanel.add(buttonPanel, gbc);
    
    sendMoneyLogic();

    actionPanel.revalidate();
    actionPanel.repaint();
}



private void showLoan() {

  new  LoanPage();
  dispose();
}

private void showDepoPanel() {

    // Remove anything currently inside the action panel
    actionPanel.removeAll();

    // Use GridBagLayout just like the rest of the application
    actionPanel.setLayout(new GridBagLayout());
    actionPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

    // GridBagLayout controller
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15,10,15,10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // =========================================================
    //                  PAGE TITLE
    // =========================================================

    JLabel title = new JLabel("Deposit Money");
    title.setFont(new Font("Arial", Font.BOLD, 24));

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    actionPanel.add(title, gbc);

    // =========================================================
    //          SHOW CURRENT ACCOUNT BALANCE
    // =========================================================
    // Placeholder for now.
    // Later this will read directly from the database.

    currentBal = new JLabel("Available Balance");
    currentBal.setFont(new Font("Arial", Font.BOLD, 15));

    gbc.gridy = 1;
    actionPanel.add(currentBal, gbc);

    JLabel currentAmount = new JLabel("KES " + currentBalance);
    currentAmount.setFont(new Font("Arial", Font.BOLD, 22));

    gbc.gridy = 2;
    actionPanel.add(currentAmount, gbc);

    // Reset GridBag settings for labels below
    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;

    // =========================================================
    //                  AMOUNT FIELD
    // =========================================================

    JLabel amount = new JLabel("Amount (KES)");

    gbc.gridx = 0;
    gbc.gridy = 3;
    actionPanel.add(amount, gbc);

    depositAmountField = new JTextField(20);
    depositAmountField.setFont(new Font("Arial", Font.PLAIN, 18));

    gbc.gridx = 1;
    actionPanel.add(depositAmountField, gbc);

    // =========================================================
    //              QUICK AMOUNT BUTTONS
    // =========================================================
    // Clicking any button automatically fills the amount box.

    JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));

    int[] quickAmounts = {100,500,1000,5000};

    for(int amt : quickAmounts){

        JButton btn = new JButton("KES " + amt);

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
    actionPanel.add(quickPanel, gbc);

    // =========================================================
    //                  BUTTON PANEL
    // =========================================================
    // Deposit is the main action.
    // Back returns to the dashboard.

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

    depositBtn = new JButton("Deposit");
    depositBtn.setPreferredSize(new Dimension(140,40));

    JButton backBtn = new JButton("Back");
    backBtn.setPreferredSize(new Dimension(140,40));

    buttonPanel.add(depositBtn);
    buttonPanel.add(backBtn);

    gbc.gridy = 5;
    actionPanel.add(buttonPanel, gbc);

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
    // 1. Validate the amount.
    // 2. Update the user's balance.
    // 3. Record the transaction.
    // 4. Commit if everything succeeds.
    // 5. Refresh the displayed balance.

    

    // Refresh the panel so Swing redraws it.
    actionPanel.revalidate();
    actionPanel.repaint();

}



private void showAirPanel(){

    actionPanel.removeAll();
    actionPanel.setLayout(new GridBagLayout());
    actionPanel.setBorder(BorderFactory.createEmptyBorder(30,50,30,50));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15,10,15,10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // =========================================================
    //                      TITLE
    // =========================================================

    JLabel title = new JLabel("Buy Airtime");
    title.setFont(new Font("Arial", Font.BOLD, 24));

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    actionPanel.add(title, gbc);

    // =========================================================
    //              AVAILABLE BALANCE
    // =========================================================

    JLabel available = new JLabel("Available Balance");
    available.setFont(new Font("Arial", Font.BOLD, 15));

    gbc.gridy = 1;
    actionPanel.add(available, gbc);

    JLabel currentBal = new JLabel("KES " + currentBalance);
    currentBal.setFont(new Font("Arial", Font.BOLD, 22));

    gbc.gridy = 2;
    actionPanel.add(currentBal, gbc);

    // =========================================================
    //                  AMOUNT FIELD
    // =========================================================

    gbc.gridwidth = 1;
    gbc.anchor = GridBagConstraints.WEST;

    JLabel amount = new JLabel("Amount (KES)");

    gbc.gridx = 0;
    gbc.gridy = 3;
    actionPanel.add(amount, gbc);

    airtimeAmountField = new JTextField(20);
    airtimeAmountField.setFont(new Font("Arial", Font.PLAIN, 18));

    gbc.gridx = 1;
    actionPanel.add(airtimeAmountField, gbc);

    // =========================================================
    //              QUICK AMOUNT BUTTONS
    // =========================================================

    JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,5));

    int[] quickAmounts = {10,20,50,100};

    for(int amt : quickAmounts){

        JButton btn = new JButton("KES " + amt);

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
    actionPanel.add(quickPanel, gbc);

    // =========================================================
    //                  BUTTONS
    // =========================================================

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

    buyBtn = new JButton("Buy Airtime");
    buyBtn.setPreferredSize(new Dimension(150,40));

    JButton backBtn = new JButton("Back");
    backBtn.setPreferredSize(new Dimension(150,40));

    buttonPanel.add(buyBtn);
    buttonPanel.add(backBtn);

    gbc.gridy = 5;
    actionPanel.add(buttonPanel, gbc);

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

            String recipientQuery = "SELECT account_id,user_id " + "FROM accounts " + "WHERE account_number = ?";

            PreparedStatement ps1 = con.prepareStatement(recipientQuery);

            ps1.setLong(1,Long.parseLong(recipientField.getText()));

            ResultSet rs = ps1.executeQuery();

            if(!rs.next()){

                JOptionPane.showMessageDialog(
                        LandingPage.this,
                        "Recipient account not found.");

                return;

            }

            long recipientAccountId =
                    rs.getLong("account_id");

            long recipientUserId =
                    rs.getLong("user_id");

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
    

}