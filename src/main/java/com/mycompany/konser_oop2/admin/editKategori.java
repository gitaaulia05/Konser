/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.admin;

import com.mycompany.konser_oop2.connection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.*;
import java.util.LinkedHashMap;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Gita Aulia Hafid
 */
public class editKategori extends javax.swing.JFrame {

    private String id_admin;
    private String id_kategori;
    private String id_konser;
    
    public editKategori(String id_admin, String id_kategori) {
        this.id_admin = id_admin;
        this.id_kategori = id_kategori;
        setTitle("Update Detail Kategori Tiket ");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // === Navbar ===
        navbarAdmin navbar = new navbarAdmin(id_admin);
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); 
        add(navbar, BorderLayout.NORTH);

        // === Card Detail ===
        JPanel detailCard = createDetailCard();
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(detailCard);
        add(centerPanel, BorderLayout.CENTER);


        setVisible(true); 
    }
    
    private JPanel createDetailCard() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

    JComboBox<String> kategoriCombo = new JComboBox<>();
    LinkedHashMap<String, String> kategoriMap = new LinkedHashMap<>();

    JTextField hargaField = new JTextField();
    JTextField jumlahField = new JTextField();
    JTextArea deskripsiArea = new JTextArea(5, 20);
    
    try (Connection conn = connection.getConnection()) {
        // Ambil semua kategori
        String kategoriSql = "SELECT id_kategori_tiket, kategori_konser FROM kategori_tiket";
        PreparedStatement psKategori = conn.prepareStatement(kategoriSql);
        ResultSet rsKategori = psKategori.executeQuery();
        while (rsKategori.next()) {
            String id = rsKategori.getString("id_kategori_tiket");
            String nama = rsKategori.getString("kategori_konser");
            
            kategoriCombo.addItem(nama);
            kategoriMap.put(nama, id);
        }

        // Ambil data detail berdasarkan id_kategori
        String detailSql = """
            SELECT d.*, k.kategori_konser 
            FROM detail_kategori_tiket d
            JOIN kategori_tiket k ON d.id_kategori_tiket = k.id_kategori_tiket
            WHERE d.id_det_tiket = ?
        """;
        PreparedStatement psDetail = conn.prepareStatement(detailSql);
        psDetail.setString(1, id_kategori);
        ResultSet rs = psDetail.executeQuery();

        if (rs.next()) {
            String kategoriNama = rs.getString("kategori_konser");
            id_konser= rs.getString("id_konser");
            hargaField.setText(String.valueOf(rs.getDouble("harga_tiket")));
            jumlahField.setText(String.valueOf(rs.getInt("jumlah_tiket")));
            deskripsiArea.setText(rs.getString("deskripsi"));

            kategoriCombo.setSelectedItem(kategoriNama);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    panel.add(new JLabel("Kategori Konser"));
    panel.add(kategoriCombo);
    panel.add(new JLabel("Harga Tiket"));
    panel.add(hargaField);
    panel.add(new JLabel("Jumlah Tiket"));
    panel.add(jumlahField);
    panel.add(new JLabel("Deskripsi"));
    panel.add(new JScrollPane(deskripsiArea));

    JButton simpanBtn = new JButton("Simpan Perubahan");
    
    
    simpanBtn.addActionListener(e -> {
        String selectedKategori = (String) kategoriCombo.getSelectedItem();
        String id_kategori_tiket = kategoriMap.get(selectedKategori);
        double harga = Double.parseDouble(hargaField.getText());
        int jumlah = Integer.parseInt(jumlahField.getText());
        String deskripsi = deskripsiArea.getText();

        try (Connection conn = connection.getConnection()) {
            
            String updateSql = """
                UPDATE detail_kategori_tiket
                SET id_kategori_tiket = ?, harga_tiket = ?, jumlah_tiket = ?, deskripsi = ?
                WHERE id_det_tiket = ?
            """;
            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1, id_kategori_tiket);
            ps.setDouble(2, harga);
            ps.setInt(3, jumlah);
            ps.setString(4, deskripsi);
            ps.setString(5, id_kategori);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
            detailKonser detailFrame = new detailKonser(id_admin, id_konser);
                 detailFrame.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data.");
        }
    });

     JButton kembaliBtn = new JButton("kembali");

     kembaliBtn.addActionListener(e -> {
         new detailKonser(id_admin, id_konser).setVisible(true);
           dispose();
     });
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(simpanBtn);
    panel.add(Box.createRigidArea(new Dimension(0, 5))); 
    panel.add(kembaliBtn);

    return panel;
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
