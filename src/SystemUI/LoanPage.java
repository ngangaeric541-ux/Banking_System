package SystemUI;

import static SystemUI.DatabaseCon.con;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoanPage extends JFrame{

    // =========================================================
    //                  COMPONENTS
    // =========================================================

    private JLabel loanStatus;
    private JLabel availableLimit;
    private JLabel outstandingLoan;

    private JTextField amountField;
    private double amt;
    private long accountId;
    private double newLoan;

    private JButton borrowBtn;
    private JButton repayBtn;
    private JButton backBtn;
    private double avlLimit;

    // =========================================================
    //                  CONSTRUCTOR
    // =========================================================

    public LoanPage(){

        setTitle("Loans");
        setSize(700,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        buildUI();

        setVisible(true);

    }
    

    // =========================================================
    //                  BUILD USER INTERFACE
    // =========================================================

    private void buildUI(){

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // =====================================================
        //                  TITLE
        // =====================================================

        JLabel title = new JLabel("Loan Services");
        title.setFont(new Font("Arial",Font.BOLD,26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title,gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // =====================================================
        //                  LOAN STATUS
        // =====================================================

        JLabel statusTitle = new JLabel("Loan Status");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(statusTitle,gbc);
        loanStatus = new JLabel();
        
        if(CurrentUser.getCurrLoan() == 0.00){
                loanStatus.setText("No active loan");
        }else{
                loanStatus.setText(CurrentUser.getStatus());
        }
        loanStatus.setFont(new Font("Arial",Font.BOLD,18));

        gbc.gridx = 1;
        add(loanStatus,gbc);

        // =====================================================
        //              AVAILABLE LOAN LIMIT
        // =====================================================

        JLabel limitTitle = new JLabel("Available Loan Limit");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(limitTitle,gbc);

        availableLimit = new JLabel("KES " + CurrentUser.getLimit());
        availableLimit.setFont(new Font("Arial",Font.BOLD,18));
        gbc.gridx = 1;

        add(availableLimit,gbc);

        // =====================================================
        //              OUTSTANDING LOAN
        // =====================================================

        JLabel outstandingTitle = new JLabel("Outstanding Loan");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(outstandingTitle,gbc);

        outstandingLoan = new JLabel("KES " + CurrentUser.getCurrLoan());
        outstandingLoan.setFont(new Font("Arial",Font.BOLD,18));
        gbc.gridx = 1;
        add(outstandingLoan,gbc);

        // =====================================================
        //                  LOAN AMOUNT
        // =====================================================

        JLabel amount = new JLabel("Amount");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(amount,gbc);
        
        amountField = new JTextField(18);
        amountField.setFont(new Font("Arial",Font.PLAIN,18));
        gbc.gridx = 1;
        add(amountField,gbc);

        // =====================================================
        //                  BUTTONS
        // =====================================================

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));

        borrowBtn = new JButton("Borrow");
        repayBtn = new JButton("Repay");
        backBtn = new JButton("Back");

        borrowBtn.setPreferredSize(new Dimension(120,40));
        repayBtn.setPreferredSize(new Dimension(120,40));
        backBtn.setPreferredSize(new Dimension(120,40));

        buttonPanel.add(borrowBtn);
        buttonPanel.add(repayBtn);
        buttonPanel.add(backBtn);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        add(buttonPanel,gbc);

        // =====================================================
        //                  BACK BUTTON
        // =====================================================

        backBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae){

                dispose();
                new LandingPage();

            }

        });
        
        // =====================================================
        //                  BORROW BUTTON
        // =====================================================
        
        borrowBtn.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae){

                if(amountField.getText().isEmpty() ){
                        JOptionPane.showMessageDialog(LoanPage.this, "Please fill in the field");
                        return;
                }

                amt = Double.parseDouble(amountField.getText());

                if(amt <= 0){
                        JOptionPane.showMessageDialog(LoanPage.this,"Please enter a value greater than 0");
                        return;
                }
                
        borrowDBLogic();
       

        }        
                });
        
        
        // =====================================================
        //                  REPAY BUTTON
        // =====================================================
        repayBtn.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                
                        if(amountField.getText().isEmpty()){
                                JOptionPane.showMessageDialog(LoanPage.this,"Enter an amount.");
                                return;
                       }

                        amt = Double.parseDouble(amountField.getText());

                        if(amt <= 0){
                            JOptionPane.showMessageDialog(LoanPage.this,"Invalid amount.");
                            return;
                        }//end of if
                        
                        repayDBLogic();
                
                }
        
        });//end of repay listener
        
        

    }

        public static void main(String[] args) {
                new LoanPage();
        }
        
        
      private void borrowDBLogic(){

    try{

        DatabaseCon.database_connection();

        con.setAutoCommit(false);

        // =====================================================
        //      GET ACCOUNT DETAILS
        // =====================================================

        String query =
                "SELECT account_id, loan_limit "
                + "FROM accounts "
                + "WHERE user_id = ?";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setLong(1, CurrentUser.getUserId());

        ResultSet rs = ps.executeQuery();

        if(!rs.next()){
            JOptionPane.showMessageDialog(this, "Account not found.");
            return;
        }

        accountId = rs.getLong("account_id");

        avlLimit = rs.getDouble("loan_limit");

        // =====================================================
        //      CHECK LOAN LIMIT
        // =====================================================

        if(amt > avlLimit){
            JOptionPane.showMessageDialog(this, "Loan exceeds available limit.");
            con.rollback();
            return;

        }

        // =====================================================
        //      CREDIT USER ACCOUNT
        // =====================================================

        String credit = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        PreparedStatement ps1 = con.prepareStatement(credit);
        ps1.setDouble(1, amt);
        ps1.setLong(2, accountId);
        ps1.executeUpdate();

        // =====================================================
        //      REDUCE LOAN LIMIT
        // =====================================================

        String updateLoan = "UPDATE accounts SET loan_limit = loan_limit - ?, outstanding_loan = outstanding_loan + ? WHERE account_id = ?";
        PreparedStatement ps2 = con.prepareStatement(updateLoan);
        ps2.setDouble(1, amt);
        ps2.setDouble(2, amt);
        ps2.setLong(3, accountId);
        ps2.executeUpdate();

        // =====================================================
        //      INSERT LOAN
        // =====================================================

        String insertLoan =
                "INSERT INTO loans("
                + "loan_id,"
                + "account_id,"
                + "user_id,"
                + "loan_amount,"
                + "remaining_balance,"
                + "loan_status,"
                + "borrowed_at)"
                + " VALUES("
                + "UUID_SHORT(),"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "'ACTIVE',"
                + "NOW())";

        PreparedStatement ps3 = con.prepareStatement(insertLoan);

        ps3.setLong(1, accountId);
        ps3.setLong(2, CurrentUser.getUserId());
        ps3.setDouble(3, amt);
        ps3.setDouble(4, amt);

        ps3.executeUpdate();

        // =====================================================
        //      RECORD TRANSACTION
        // =====================================================

        String transaction =
                "INSERT INTO transactions("
                + "transaction_id,"
                + "account_id,"
                + "user_id,"
                + "amount,"
                + "transaction_type,"
                + "created_at)"
                + " VALUES(?,?,?,?,?,NOW())";

        PreparedStatement ps4 = con.prepareStatement(transaction);

        ps4.setString(1, LandingPage.generateID());
        ps4.setLong(2, accountId);
        ps4.setLong(3, CurrentUser.getUserId());
        ps4.setDouble(4, amt);
        ps4.setString(5, "Borrow");

        ps4.executeUpdate();

        // =====================================================
        //      SAVE CHANGES
        // =====================================================

        con.commit();
        con.setAutoCommit(true);
        
        CurrentUser.getAllInfo(CurrentUser.getUserId());
        
        refreshLoanInfo();

        JOptionPane.showMessageDialog(this,"Loan approved successfully.");

        amountField.setText("");

    }catch(SQLException ex){

        try{

            con.rollback();
            con.setAutoCommit(true);

        }catch(SQLException ex1){

            Logger.getLogger(LoanPage.class.getName()).log(Level.SEVERE, null, ex1);

        }

        Logger.getLogger(LoanPage.class.getName()).log(Level.SEVERE, null, ex);

        JOptionPane.showMessageDialog(this,"Loan request failed.");

    }

} //borrow logic end
      
      
      private void repayDBLogic(){

    try{

        DatabaseCon.database_connection();

        con.setAutoCommit(false);

        // =====================================================
        //      GET ACCOUNT INFORMATION
        // =====================================================

        String accInfo =
                "SELECT account_id,balance,loan_limit,outstanding_loan "
                + "FROM accounts "
                + "WHERE user_id=?";

        PreparedStatement ps =con.prepareStatement(accInfo);
        ps.setLong(1,CurrentUser.getUserId());
        ResultSet rs = ps.executeQuery();

        if(!rs.next()){

            JOptionPane.showMessageDialog(this, "Account not found.");

            return;

        }

        accountId = rs.getLong("account_id");

        double balance = rs.getDouble("balance");

        double loanLimit = rs.getDouble("loan_limit");

        double outstandingLoan = rs.getDouble("outstanding_loan");

        // =====================================================
        //      VALIDATION
        // =====================================================

        if(balance < amt){
            JOptionPane.showMessageDialog(this, "Insufficient balance.");
            return;
        }

        if(outstandingLoan <= 0){
            JOptionPane.showMessageDialog(this, "No outstanding loan.");
            return;

        }

        if(amt > outstandingLoan){
            JOptionPane.showMessageDialog(this, "Amount exceeds outstanding loan.");
            return;
        }

        // =====================================================
        //      DEDUCT ACCOUNT BALANCE
        // =====================================================

        String deduct =
                "UPDATE accounts "
                + "SET balance = balance - ? "
                + "WHERE account_id=?";

        PreparedStatement ps1 =con.prepareStatement(deduct);

        ps1.setDouble(1,amt);
        ps1.setLong(2,accountId);
        ps1.executeUpdate();

        // =====================================================
        //      UPDATE LOAN VALUES
        // =====================================================

        String updateLoan =
                "UPDATE accounts "
                + "SET loan_limit = loan_limit + ?, "
                + "outstanding_loan = outstanding_loan - ? "
                + "WHERE account_id=?";

        PreparedStatement ps2 = con.prepareStatement(updateLoan);
        ps2.setDouble(1,amt);
        ps2.setDouble(2,amt);
        ps2.setLong(3,accountId);
        ps2.executeUpdate();

        // =====================================================
        //      GET ACTIVE LOANS
        // =====================================================

        String activeLoans =
                "SELECT loan_id,remaining_balance "
                + "FROM loans "
                + "WHERE user_id=? "
                + "AND loan_status='ACTIVE' "
                + "ORDER BY borrowed_at ASC";

        PreparedStatement ps3 = con.prepareStatement(activeLoans);
        ps3.setLong(1,CurrentUser.getUserId());
        ResultSet rs2 = ps3.executeQuery();

        double remaining = amt;

        while(rs2.next() && remaining > 0){
            long loanId = rs2.getLong("loan_id");
            double loanBal = rs2.getDouble("remaining_balance");

            if(remaining >= loanBal){

                String paid =
                        "UPDATE loans "
                        + "SET remaining_balance=0,"
                        + "loan_status='PAID' "
                        + "WHERE loan_id=?";

                PreparedStatement ps4 =con.prepareStatement(paid);
                ps4.setLong(1,loanId);
                ps4.executeUpdate();
                remaining -= loanBal;

            }else{

                String partial =
                        "UPDATE loans "
                        + "SET remaining_balance=? "
                        + "WHERE loan_id=?";

                PreparedStatement ps5 =con.prepareStatement(partial);
                ps5.setDouble(1,loanBal - remaining);
                ps5.setLong(2,loanId);
                ps5.executeUpdate();

                remaining = 0;

            }

        }

        // =====================================================
        //      RECORD TRANSACTION
        // =====================================================

        String txn =
                "INSERT INTO transactions("
                + "transaction_id,"
                + "account_id,"
                + "user_id,"
                + "amount,"
                + "transaction_type,"
                + "created_at)"
                + " VALUES(?,?,?,?,?,NOW())";

        PreparedStatement ps6 =con.prepareStatement(txn);
        ps6.setString(1,LandingPage.generateID());
        ps6.setLong(2,accountId);
        ps6.setLong(3,CurrentUser.getUserId());
        ps6.setDouble(4,amt);
        ps6.setString(5, "REPAY");
        ps6.executeUpdate();
        
        String updateStatus =
        "UPDATE loans "
      + "SET loan_status = 'PAID' "
      + "WHERE user_id = ? "
      + "AND remaining_balance = 0 "
      + "AND loan_status = 'ACTIVE'";

        PreparedStatement ps7 = con.prepareStatement(updateStatus);

        ps7.setLong(1, CurrentUser.getUserId());

        ps7.executeUpdate();
         
        con.commit();

        con.setAutoCommit(true);
        
        CurrentUser.getAllInfo(CurrentUser.getUserId());
        
        refreshLoanInfo();
        
        amountField.setText("");


        JOptionPane.showMessageDialog(this, "Loan repaid successfully.");
        
       
        
        

    }catch(SQLException ex){

        try{

            con.rollback();

            con.setAutoCommit(true);

        }catch(SQLException e){

            Logger.getLogger( LoanPage.class.getName()).log(Level.SEVERE,null,e);

        }

        Logger.getLogger( LoanPage.class.getName()) .log(Level.SEVERE,null,ex);

    }

}//end of repay logic
      
      
       // =====================================================
        //      REFRESH THE USER DETAILS AFTER EVERY TRANSACTION
        // =====================================================
      
      private void refreshLoanInfo() {

    CurrentUser.getAllInfo(CurrentUser.getUserId());

    availableLimit.setText("KES " + CurrentUser.getLimit());

    outstandingLoan.setText("KES " + CurrentUser.getCurrLoan());

    if(CurrentUser.getCurrLoan() == 0){

        loanStatus.setText("No Active Loan");

    }else{

        loanStatus.setText(CurrentUser.getStatus());

    }

}
      
      
     
      
      
        
        
}