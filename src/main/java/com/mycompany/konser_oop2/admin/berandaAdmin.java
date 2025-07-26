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
       
       
        // Panel atas untuk tombol
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(Color.WHITE);

        // Buat tombol "Tambah Data"
        JButton tambahBtn = new JButton("Tambah Data");
        tambahBtn.setBackground(Color.WHITE);
        tambahBtn.setFocusPainted(false);
        tambahBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        tambahBtn.setPreferredSize(new Dimension(120, 35));

        // Event klik
        tambahBtn.addActionListener(e -> {
            new tambahKonser(id_admin).setVisible(true); // Arahkan ke form tambah konser
            dispose();
        });

        topPanel.add(tambahBtn);
        add(topPanel, BorderLayout.SOUTH); // atau BorderLayout.NORTH jika mau di atas scroll


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
    contentPanel.removeAll(); // pastikan kosong sebelum isi ulang
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBackground(Color.WHITE);

    try (Connection conn = connection.getConnection()) {
        String query = "SELECT * FROM konser WHERE id_admin = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, id_admin);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String CurrentId = rs.getString("id_konser");
            String namaKonser = rs.getString("nama_konser");
            String lokasi = rs.getString("lokasi");
            String tanggal = rs.getString("tanggal");
            String jam = rs.getString("jam");

            // Panel utama untuk 1 konser
            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            card.setPreferredSize(new Dimension(750, 100));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Panel kiri: Nama konser + Lokasi
            JLabel kiri = new JLabel("<html><b>" + namaKonser + "</b><br>" + lokasi + "</html>");
            kiri.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            card.add(kiri, BorderLayout.WEST);

            // Panel kanan
            JPanel kanan = new JPanel();
            kanan.setLayout(new BoxLayout(kanan, BoxLayout.Y_AXIS));
            kanan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            kanan.setBackground(Color.WHITE);

            // Tanggal dan Jam
            JLabel labelTanggalJam = new JLabel("<html>" + tanggal + "<br>" + jam + "</html>");
            labelTanggalJam.setAlignmentX(Component.RIGHT_ALIGNMENT);
            kanan.add(labelTanggalJam);
            kanan.add(Box.createRigidArea(new Dimension(0, 5)));

            // Tombol kategori atau kelola
            if (!kategoriKonser(CurrentId)) {
                JButton btnKategori = new JButton("Tambah Kategori Tiket");
                btnKategori.setAlignmentX(Component.RIGHT_ALIGNMENT);
                kanan.add(btnKategori);
                kanan.add(Box.createRigidArea(new Dimension(0, 5)));
                btnKategori.addActionListener(e -> {
                    new tambahKategori(id_admin, CurrentId).setVisible(true);
                    dispose();
                });
            } else {
                JButton btnKelola = new JButton("Kelola");
                btnKelola.setAlignmentX(Component.RIGHT_ALIGNMENT);
                kanan.add(btnKelola);
                kanan.add(Box.createRigidArea(new Dimension(0, 5)));
                btnKelola.addActionListener(e -> {
                    new detailKonser(id_admin, CurrentId).setVisible(true);
                    dispose();
                });
            }

            // Panel untuk tombol Edit dan Hapus sejajar
            JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            panelTombol.setBackground(Color.WHITE);

            JButton btnEdit = new JButton("✏️ Edit Konser");
            JButton btnHapus = new JButton("🗑️ Hapus");
            btnHapus.setForeground(Color.RED);

            panelTombol.add(btnEdit);
            panelTombol.add(btnHapus);
            kanan.add(panelTombol);

            // Event Edit
            btnEdit.addActionListener(e -> {
                new editKonser(id_admin, CurrentId).setVisible(true);
                dispose();
            });

            // Event Hapus
            btnHapus.addActionListener(e -> {
                int konfirmasi = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus konser ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    try (Connection connHapus = connection.getConnection()) {
                        String hapusQuery = "DELETE FROM konser WHERE id_konser = ?";
                        PreparedStatement ps = connHapus.prepareStatement(hapusQuery);
                        ps.setString(1, CurrentId);
                        int rowsAffected = ps.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Konser berhasil dihapus!");
                            tampilkanDaftarKonser(); // refresh tampilan
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Gagal menghapus konser.");
                    }
                }
            });

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
