/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.admin;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.konser_oop2.connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class berandaAdmin extends javax.swing.JFrame {

    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private String id_admin;
    private String id_konser;
    
    JButton btnKategori;
    JButton btnKelola;
    public berandaAdmin(String id_admin) {
        this.id_admin = id_admin;
        initUi();
        tampilkanDaftarKonser();
    }
    
    private void initUi() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
         getContentPane().setLayout(new BorderLayout());
      
        // Tambah navbarAdmin
       navbarAdmin navbar = new navbarAdmin(id_admin);
        add(navbar, BorderLayout.NORTH);

        // Panel konten utama
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);
    }
        
        
    private void tampilkanDaftarKonser() {

        try (Connection conn = connection.getConnection()) {
            String query = "SELECT * FROM konser WHERE id_admin = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id_admin);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id_konser = rs.getString("id_konser");
                String CurrentId = rs.getString("id_konser");
                String namaKonser = rs.getString("nama_konser");
                String lokasi = rs.getString("lokasi");
                String tanggal = rs.getString("tanggal");
                String jam = rs.getString("jam");
                
                // Panel utama untuk 1 konser
                JPanel card = new JPanel();
                card.setLayout(new BorderLayout());
                card.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
                card.setBackground(Color.WHITE);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
                card.setPreferredSize(new Dimension(750, 100));
                card.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                // Panel kiri: nama konser dan lokasi
                JLabel kiri = new JLabel("<html><b>" + namaKonser + "</b><br>" + lokasi + "</html>");
                kiri.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                card.add(kiri, BorderLayout.WEST);

                // Panel kanan: tanggal, jam, tombol
                JPanel kanan = new JPanel();
                kanan.setLayout(new BoxLayout(kanan, BoxLayout.Y_AXIS));
                kanan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                kanan.setBackground(Color.WHITE);
                
                JLabel labelTanggalJam = new JLabel("<html>" + tanggal + "<br>" + jam + "</html>");
                labelTanggalJam.setAlignmentX(Component.RIGHT_ALIGNMENT);

                kanan.add(labelTanggalJam);
                kanan.add(Box.createRigidArea(new Dimension(0, 5)));   
                
                if(kategoriKonser(id_konser) == false) {
                btnKategori = new JButton("Tambah Kategori Tiket");
                kanan.add(btnKategori);
                kanan.add(Box.createRigidArea(new Dimension(0, 5)));
                btnKategori.setAlignmentX(Component.RIGHT_ALIGNMENT);
                btnKategori.addActionListener(e -> {
                    new tambahKategori(id_admin,CurrentId).setVisible(true);
                    dispose();
                 });  
                }else{
                btnKelola = new JButton("Kelola");
                btnKelola.setAlignmentX(Component.RIGHT_ALIGNMENT);
                kanan.add(btnKelola);
                kanan.add(Box.createRigidArea(new Dimension(0, 5))); // jarak
                btnKelola.addActionListener(e -> {
                    new detailKonser(id_admin, CurrentId).setVisible(true);
                    dispose();
                 });  
                }

                card.add(kanan, BorderLayout.EAST);

                // Tambahkan ke content panel
                contentPanel.add(card);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
            }
            contentPanel.revalidate();
            contentPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  


    public boolean kategoriKonser (String id_konsers){
         try (Connection conn = connection.getConnection()) {
            String query = "SELECT * FROM detail_kategori_tiket WHERE id_konser = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id_konsers);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 return true;
             }
         }catch (Exception e) {
            e.printStackTrace();
        }
         return false;
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
            java.util.logging.Logger.getLogger(berandaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(berandaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(berandaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(berandaAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new berandaAdmin("cc4e86d9-a947-4a7b-84e3-f4636ee6929e").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
