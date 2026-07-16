package SystemUI;

import static SystemUI.DatabaseCon.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrentUser {

    private static long userId;
    private static String email;
    private static String firstName;
    private static String lastName;

    private static long accountId;
    private static String accountNumber;

    private static double balance;
    private static double loanLimit;
    private static double outstandingLoan;

    private static String loanStatus;

    private static Timestamp lastLogin;
    private static String lastIP;

    public static void set(long id,
                           String userEmail,
                           String name,
                           String jina,
                           long accId,
                           String accNumber,
                           double bal,
                           double limit,
                           double loan,
                           String status,
                           Timestamp login,
                           String ip){

        userId = id;
        email = userEmail;
        firstName = name;
        lastName = jina;

        accountId = accId;
        accountNumber = accNumber;

        balance = bal;
        loanLimit = limit;
        outstandingLoan = loan;

        loanStatus = status;

        lastLogin = login;
        lastIP = ip;

    }

    public static long getUserId(){
        return userId;
    }

    public static String getEmail(){
        return email;
    }

    public static String getName(){
        return firstName;
    }

    public static String getLastName(){
        return lastName;
    }

    public static long getAccountId(){
        return accountId;
    }

    public static String getAccNum(){
        return accountNumber;
    }

    public static double getBalance(){
        return balance;
    }

    public static double getLimit(){
        return loanLimit;
    }

    public static double getCurrLoan(){
        return outstandingLoan;
    }

    public static String getStatus(){
        return loanStatus;
    }

    public static Timestamp getLastLogin(){
        return lastLogin;
    }

    public static String getIP(){
        return lastIP;
    }
    
    static void getAllInfo(long userId){

    try{

        DatabaseCon.database_connection();

        String query =
                "SELECT "
                + "u.user_id,"
                + "u.first_name,"
                + "u.last_name,"
                + "u.email,"
                + "a.account_id,"
                + "a.account_number,"
                + "a.balance,"
                + "a.loan_limit,"
                + "a.outstanding_loan,"
                + "s.login_time,"
                + "s.ip_address,"
                + "COALESCE((SELECT loan_status "
                + "FROM loans "
                + "WHERE user_id = u.user_id "
                + "ORDER BY borrowed_at DESC "
                + "LIMIT 1),'NONE') AS loan_status "
                + "FROM users u "
                + "JOIN accounts a "
                + "ON u.user_id = a.user_id "
                + "LEFT JOIN sessions s "
                + "ON u.user_id = s.user_id "
                + "WHERE u.user_id = ? "
                + "ORDER BY s.login_time DESC "
                + "LIMIT 1";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setLong(1,userId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            CurrentUser.set(

                    rs.getLong("user_id"),

                    rs.getString("email"),

                    rs.getString("first_name"),

                    rs.getString("last_name"),

                    rs.getLong("account_id"),

                    rs.getString("account_number"),

                    rs.getDouble("balance"),

                    rs.getDouble("loan_limit"),

                    rs.getDouble("outstanding_loan"),

                    rs.getString("loan_status"),

                    rs.getTimestamp("login_time"),

                    rs.getString("ip_address")

            );

        }

    }catch(SQLException ex){

        Logger.getLogger(LoginPage.class.getName())
                .log(Level.SEVERE,null,ex);

    }

}

}