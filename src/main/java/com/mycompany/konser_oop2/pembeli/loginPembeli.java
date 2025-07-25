/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.pembeli;
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

public class loginPembeli extends javax.swing.JFrame {

    private String id_pembeli = null;
    public loginPembeli() {
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
        JLabel titleLabel = new JLabel("Login Pembeli", SwingConstants.CENTER);
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

        // Label Register (Belum punya akun? Daftar sekarang)
        gbc.gridy++;
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setBackground(rightPanel.getBackground());

        JLabel textLabel = new JLabel("Belum punya akun?");
        textLabel.setForeground(Color.GRAY);

        JLabel registerLabel = new JLabel("Daftar sekarang");
        registerLabel.setForeground(new Color(192, 57, 43));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // register di klik
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new registerPembeli().setVisible(true);
                setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setFont(registerLabel.getFont().deriveFont(Font.BOLD));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setFont(registerLabel.getFont().deriveFont(Font.PLAIN));
            }
        });

        registerPanel.add(textLabel);
        registerPanel.add(registerLabel);
        rightPanel.add(registerPanel, gbc);

        // === Gabungkan Panel Kiri dan Kanan ===
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        
        // === event back button ===       
         backButton.addActionListener(e -> {
             new beranda(id_pembeli).setVisible(true);
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
             String query = "Select * FROM pembeli where email = ?";
             pstmt = conn.prepareStatement(query);
             pstmt.setString(1, email);
           
             rs = pstmt.executeQuery();
             
             if(rs.next()){
                String id_pembeli = rs.getString("id_pembeli");
                String passwordDb = rs.getString("password");
                
                if(BCrypt.checkpw(password, passwordDb)) {
                  new beranda(id_pembeli).setVisible(true);
                   dispose();
                } else {
                     JOptionPane.showMessageDialog(this, "Password salah!");
                }
             } else {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(loginPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginPembeli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginPembeli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
