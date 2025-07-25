/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.admin;

import com.mycompany.konser_oop2.connection;
import com.mycompany.konser_oop2.landingPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class loginAdmin extends javax.swing.JFrame {

    /**
     * Creates new form loginAdmin
     */
    public loginAdmin() {
        initComponents();
        setTitle("Login - Aplikasi Konserku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Panel Kiri (gambar + teks) ===
        JPanel leftPanel = new JPanel() {
            private final Image bgImage = new ImageIcon(getClass().getClassLoader().getResource("com/mycompany/konser_oop2/icon.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(255, 255, 255, 230));
                g.setFont(new Font("SansSerif", Font.BOLD, 20));
                g.drawString("Selamat datang di aplikasi konserku", 20, getHeight() - 40);
            }
        };
        leftPanel.setPreferredSize(new Dimension(400, 500));
        leftPanel.setLayout(null);

        // === Panel Kanan (Login Form) ===
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Login Administrator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 23));
        titleLabel.setForeground(new Color(165, 87, 88));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel emailLabel = new JLabel("Email:");
        rightPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(15);
        rightPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        rightPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passField = new JPasswordField(15);
        rightPanel.add(passField, gbc);

        // Tombol Login
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        rightPanel.add(loginButton, gbc);

        // Tombol Kembali
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton backButton = new JButton("Kembali");
        rightPanel.add(backButton, gbc);
 
        // === Gabungkan Panel Kiri dan Kanan ===
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

          // === event back button ===       
         backButton.addActionListener(e -> {
             new landingPage().setVisible(true);
             dispose();
         });
         
        // === Event Login Button ===
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passField.getPassword());

            if(email.isEmpty() || password.isEmpty()){
           JOptionPane.showMessageDialog(this, "Semua field harus Diisi!");
           return;
       }
            
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
       
        try {
             conn = connection.getConnection();  
             String query = "Select * FROM administrator where email = ?";
             pstmt = conn.prepareStatement(query);
             pstmt.setString(1, email);

             rs = pstmt.executeQuery();
             
             if(rs.next()){
                String passwordDb = rs.getString("password");
                String id_admin = rs.getString("id_admin");
                passwordDb = passwordDb.replaceFirst("^\\$2y\\$", "\\$2a\\$");
                 if(BCrypt.checkpw(password, passwordDb)) {
                      new berandaAdmin(id_admin).setVisible(true);
                    dispose();
                 }else {
                     JOptionPane.showMessageDialog(this, "Password salah!");
                }
             }else {
                     JOptionPane.showMessageDialog(this, "Email tidak ditemukan!");
                }         
        } catch(SQLException er){
             er.printStackTrace();
        }  
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
