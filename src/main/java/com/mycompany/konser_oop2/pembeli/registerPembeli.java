/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.pembeli;
import com.mycompany.konser_oop2.connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

public class registerPembeli extends javax.swing.JFrame {

    private String id_pembeli;
    private String hashPass;
    
    public registerPembeli() {
       setTitle("Daftar Akun Pembeli- Aplikasi Konserku");
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
        JLabel titleLabel = new JLabel("Daftar Akun Pembeli", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 23));
        titleLabel.setForeground(new Color(165, 87, 88));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(titleLabel, gbc);
        
         // Nama
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel namaLabel = new JLabel("Nama :");
        rightPanel.add(namaLabel, gbc);

        gbc.gridx = 1;
        JTextField namaField = new JTextField(15);
        rightPanel.add(namaField, gbc);
        

        // Email
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
        JButton regsiterButton = new JButton("Daftar Akun");
        rightPanel.add(regsiterButton, gbc);
     
        // === Gabungkan Panel Kiri dan Kanan ===
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // === Event Login Button ===
        regsiterButton.addActionListener(e -> {
            String nama = namaField.getText();
            String email = emailField.getText();
            String password = new String(passField.getPassword());
            
            if(email.isEmpty() || password.isEmpty() || nama.isEmpty()){
                JOptionPane.showMessageDialog(this, "Semua field harus Diisi!");
                return;
            }
         
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
          
        try {
           conn = connection.getConnection();     
            if(conn == null || conn.isClosed()) {
             JOptionPane.showMessageDialog(this, "Koneksi database gagal!", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         } 
          String query = """
                          SELECT * FROM pembeli WHERE nama = ? OR email = ?
                             UNION
                             SELECT * FROM administrator WHERE nama = ? OR email = ?
                         """;
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, nama);
          pstmt.setString(2, email);
          pstmt.setString(3, nama);
          pstmt.setString(4, email);
          
          rs = pstmt.executeQuery();
          if(rs.next()) {
              String storeEmail = rs.getString("email");
              String storeUsername = rs.getString("nama");
              if(storeEmail.equals(email)){
                  JOptionPane.showMessageDialog(this, "Email Sudah terdaftar");
                  return;
              }
              if(storeUsername.equals(nama)){
                   JOptionPane.showMessageDialog(this, "Username Sudah terdaftar");
                   return;
              }
            }
          
                String queryStore = "INSERT INTO pembeli " + "(id_pembeli, nama, email, password) "
                        +"VALUES (?, ?, ?, ?)";
                ps = conn.prepareStatement(queryStore);
                id_pembeli = UUID.randomUUID().toString();
                hashPass =  BCrypt.hashpw(password, BCrypt.gensalt());
                ps.setString(1, id_pembeli);
                ps.setString(2, nama);
                ps.setString(3, email);
                ps.setString(4, hashPass);
                
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected > 0) {
                     JOptionPane.showMessageDialog(null, "Register Berhasil Silahkan Masuk Dengan Akun Anda!");
                     new loginPembeli().setVisible(true);
                   dispose();
                } else {
                     JOptionPane.showMessageDialog(null, "Register gagal", 
                       "Rrror", JOptionPane.ERROR_MESSAGE);
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
