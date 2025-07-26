/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.admin;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.konser_oop2.connection;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.*;

/**
 *
 * @author Gita Aulia Hafid
 */
public class tambahKategori extends javax.swing.JFrame {

    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private String id_admin;
    private String id_konser;
    private String id_kategori;
    
    private Map<String, String> listKategori = new HashMap<>();
    
    public tambahKategori(String id_admin, String id_konser) {
         initUi();
         this.id_admin = id_admin;
         this.id_konser = id_konser;        
         kategoriList();
       
    }
    
    private void initUi() {
    try {
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (Exception e) {
        e.printStackTrace();
    }  

    setTitle("Tambah Kategori Tiket");
    setSize(800, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // === Navbar ===
    navbarAdmin navbar = new navbarAdmin(id_admin);
    add(navbar, BorderLayout.NORTH);

    // === Panel Utama (yang akan discroll) ===
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // pastikan vertikal
    centerPanel.setBackground(Color.WHITE);

    // Tambahkan detail card dan form ke panel ini
    JPanel detailCard = createDetailCard();
    centerPanel.add(detailCard);
    buatForm(centerPanel); // form akan ditambahkan ke centerPanel

    // Bungkus centerPanel dengan JScrollPane
    JScrollPane scrollPane = new JScrollPane(centerPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); // opsional, hilangkan border default
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);  // smooth scroll

    add(scrollPane, BorderLayout.CENTER); // tambahkan scrollPane, bukan centerPanel langsung

    setVisible(true);
}
       
    private JPanel createDetailCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                float[] dash = {5f, 5f};
                Stroke dashed = new BasicStroke(2f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
                g2.setColor(Color.GRAY);
                g2.setStroke(dashed);
                g2.drawRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
            }
        };

        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        card.setPreferredSize(new Dimension(700, 200));

        // === Label judul atas
        JLabel namaKonserLbl = new JLabel("Loading...");
        namaKonserLbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        namaKonserLbl.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(namaKonserLbl, BorderLayout.NORTH);

        // === Panel isi detail
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setOpaque(false);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel judulLbl = new JLabel("...");
        judulLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
        judulLbl.setVerticalAlignment(SwingConstants.TOP);
        detailPanel.add(judulLbl, BorderLayout.WEST);

        JPanel kananPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        kananPanel.setOpaque(false);

        JLabel jamTanggal = new JLabel("...");
        JLabel lokasiLbl = new JLabel("...");
        jamTanggal.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lokasiLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));

        kananPanel.add(jamTanggal);
        kananPanel.add(lokasiLbl);
        detailPanel.add(kananPanel, BorderLayout.EAST);

        card.add(detailPanel, BorderLayout.CENTER);

        // === Query DB
        try {
            Connection conn = connection.getConnection();
            String query = "SELECT * FROM konser WHERE id_konser = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id_konser);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String id_konserBook = rs.getString("id_konser");
                String nama_konser = rs.getString("nama_konser");
                String nama_band = rs.getString("nama_band");
                String tanggal = rs.getString("tanggal");
                String jam = rs.getString("jam");
                String lokasi = rs.getString("lokasi");

                // Set ke komponen
                namaKonserLbl.setText(nama_band);
                jamTanggal.setText(jam + ", " + tanggal);
                lokasiLbl.setText(lokasi);
                judulLbl.setText(nama_konser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }
    
    public void buatForm(JPanel parentPanel){
         parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
    parentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Jumlah Tiket
    JLabel labelJumlah = new JLabel("Jumlah Tiket:");
    JSpinner spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

    // Harga Tiket
    JLabel labelHarga = new JLabel("Harga Tiket:");
    JTextField hargaField = new JTextField();

    // Deskripsi
    JLabel labelDeskripsi = new JLabel("Deskripsi:");
    JTextArea deskripsiArea = new JTextArea(3, 20);
    deskripsiArea.setLineWrap(true);
    deskripsiArea.setWrapStyleWord(true);
    JScrollPane scrollDeskripsi = new JScrollPane(deskripsiArea);

    // Kategori Tiket (ComboBox)
    JLabel labelKategori = new JLabel("Kategori Tiket:");
    JComboBox<String> comboKategori = new JComboBox<>(listKategori.keySet().toArray(new String[0]));
    
    String selectedNamaDefault = (String) comboKategori.getSelectedItem();
    id_kategori = listKategori.get(selectedNamaDefault);
    //comboKategori event klik  
        comboKategori.addActionListener(e -> {
          String selectedNama= (String) comboKategori.getSelectedItem();
          id_kategori = listKategori.get(selectedNama);
      });
    // Tombol
    JButton btnSimpan = new JButton("Simpan");
    JButton btnKembali = new JButton("Kembali");

    // Tambahkan ke panel
    parentPanel.add(labelJumlah);
    parentPanel.add(spinnerJumlah);
    parentPanel.add(Box.createVerticalStrut(10));

    parentPanel.add(labelHarga);
    parentPanel.add(hargaField);
    parentPanel.add(Box.createVerticalStrut(10));

    parentPanel.add(labelDeskripsi);
    parentPanel.add(scrollDeskripsi);
    parentPanel.add(Box.createVerticalStrut(10));

    parentPanel.add(labelKategori);
    parentPanel.add(comboKategori);
    parentPanel.add(Box.createVerticalStrut(15));

    parentPanel.add(btnSimpan);
    parentPanel.add(Box.createVerticalStrut(5));
    parentPanel.add(btnKembali);

    // Fungsi tombol Simpan
    btnSimpan.addActionListener(e -> {
        int jumlah = (int) spinnerJumlah.getValue();
        double harga;
        try {
            harga = Double.parseDouble(hargaField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parentPanel, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String deskripsi = deskripsiArea.getText();
        String idKategori = id_kategori;
        simpanData(jumlah, harga, deskripsi, idKategori);
    });

    // Fungsi tombol Kembali
    btnKembali.addActionListener(e -> {
        new detailKonser(id_admin, id_konser).setVisible(true); // sesuaikan id_admin & id_konser sesuai konteks kamu
        SwingUtilities.getWindowAncestor(parentPanel).dispose();
    });
    }
    
    public void kategoriList(){
        listKategori.clear();
         try {
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();
            String kategori ="Select * from kategori_tiket";
            PreparedStatement kl = conn.prepareStatement(kategori);
            ResultSet rkl = kl.executeQuery();
            
        while(rkl.next()){
            String idKategori = rkl.getString("id_kategori_tiket");
            String namaKategori = rkl.getString("kategori_konser");     
            listKategori.put(namaKategori, idKategori);
        }
         }catch(Exception e){
             e.printStackTrace( );
         }
    }

    public void simpanData(int jumlahTiket, double harga_tiket, String deskripsi, String id_kategori){
        try{
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();
            System.out.println(id_kategori);
            String id_det_tiket = UUID.randomUUID().toString();
            System.out.println(id_kategori);
            String kategori ="INSERT INTO detail_kategori_tiket "+
                    "(id_det_tiket, id_kategori_tiket, id_konser, jumlah_tiket, harga_tiket, deskripsi)" + 
                    "VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement kl = conn.prepareStatement(kategori);
             kl.setString(1, id_det_tiket);
             kl.setString(2, id_kategori);
             kl.setString(3, id_konser);
             kl.setInt(4, jumlahTiket);
             kl.setDouble(5, harga_tiket);
             kl.setString(6, deskripsi);
            int rowsAffected = kl.executeUpdate();
                 if(rowsAffected > 0) {
                      JOptionPane.showMessageDialog(null, "Kategori Tiket Berhasil Ditambahkan!");
                      new detailKonser(id_admin, id_konser).setVisible(true);
                    dispose();
                 } else {
                      JOptionPane.showMessageDialog(null, "Register gagal", 
                        "Rrror", JOptionPane.ERROR_MESSAGE);
                 }
            
        }catch(Exception e){
            e.printStackTrace( );
        }
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
