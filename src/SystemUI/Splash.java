package SystemUI;

import SystemUI.DatabaseCon;
import SystemUI.LoginPage;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class Splash extends JWindow {

    private JProgressBar progressBar;

    public Splash() {
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window on screen
        setLayout(new BorderLayout());

        // --- IMAGE ---
        ImageIcon original = new ImageIcon("pictures/bank.jpeg");
        Image scaled = original.getImage().getScaledInstance(500, 370, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaled));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imgLabel, BorderLayout.CENTER);

        // --- PROGRESS BAR ---
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(500, 30)); // Set a preferred height for the progress bar
        add(progressBar, BorderLayout.SOUTH);

        setVisible(true);

        loadApplication();
    }

    private void loadApplication() {
        new Thread(() -> {
            try {
                // Step 1: simulate loading configs
                for (int i = 0; i <= 30; i += 10) {
                    progressBar.setValue(i);
                    Thread.sleep(200);
                }

                // Step 2: connect to DB
                progressBar.setString("Connecting to Database...");
                try {
                    DatabaseCon.database_connection();
                    progressBar.setValue(60);
                    Thread.sleep(300);
                } catch (Exception e) {
                    progressBar.setString("DB Connection Failed!");
                    Thread.sleep(1000);
                    System.exit(0);
                }

                // Step 3: final UI setup
                progressBar.setString("Starting UI...");
                for (int i = 60; i <= 100; i += 10) {
                    progressBar.setValue(i);
                    Thread.sleep(200);
                }

            } catch (InterruptedException ignored) {}

            SwingUtilities.invokeLater(() -> {
                dispose();
                new LoginPage();
            });
        }).start();
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        new Splash();
    }
}