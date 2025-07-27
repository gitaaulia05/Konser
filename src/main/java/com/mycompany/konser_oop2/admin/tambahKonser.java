/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.admin;

import com.mycompany.konser_oop2.connection;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import static java.time.LocalTime.now;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.*;

/**
 *
 * @author Gita Aulia Hafid
 */
public class tambahKonser extends javax.swing.JFrame {

    private String id_admin;
    private String id_kategori;
    private String nama_konser, nama_band, lokasi;
    private Date tanggal;
    private java.sql.Time jam, jamRaw;
    private Map<String, String> listKategori = new LinkedHashMap<>();
    Date now = new Date(); 

    
    public tambahKonser(String id_admin) {
        this.id_admin = id_admin;

        kategoriList(); // ambil data kategori dari DB

        setTitle("Tambah Konser Baru");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === Navbar ===
        navbarAdmin navbar = new navbarAdmin(id_admin);
        add(navbar, BorderLayout.NORTH);

        // === Card Form ===
        JPanel formPanel = createFormPanel();
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);   
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Dropdown kategori
        JComboBox<String> kategoriCombo = new JComboBox<>(listKategori.keySet().toArray(new String[0]));
        if (!listKategori.isEmpty()) {
            String defaultNama = (String) kategoriCombo.getSelectedItem();
            id_kategori = listKategori.get(defaultNama);
        }
        kategoriCombo.addActionListener(e -> {
            String selectedNama = (String) kategoriCombo.getSelectedItem();
            id_kategori = listKategori.get(selectedNama);
        });

        JTextField namaKonserField = new JTextField(20);
        JTextField namaBandField = new JTextField(20);
        JTextField lokasiField = new JTextField(20);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setMinSelectableDate(new Date()); // tidak bisa pilih hari kemarin

        SpinnerDateModel timeModel = new SpinnerDateModel();
        JSpinner timeSpinner = new JSpinner(timeModel);
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

      

            // Tambahkan ke panel
            panel.add(createFormRow("Kategori Konser:", kategoriCombo));
            panel.add(createFormRow("Nama Konser:", namaKonserField));
            panel.add(createFormRow("Nama Band:", namaBandField));
            panel.add(createFormRow("Lokasi:", lokasiField));
            panel.add(createFormRow("Tanggal:", dateChooser));
            panel.add(createFormRow("Jam:", timeSpinner));

            // Tombol
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.WHITE);
            JButton simpanBtn = new JButton("✅ Simpan");
            JButton kembaliBtn = new JButton("⬅ Kembali");

            simpanBtn.addActionListener(e -> {
                nama_konser = namaKonserField.getText();
                nama_band = namaBandField.getText();
                lokasi = lokasiField.getText();
                tanggal = dateChooser.getDate();
                jam = new java.sql.Time(((Date) timeSpinner.getValue()).getTime());

           Calendar selectedDateTime = Calendar.getInstance();
          selectedDateTime.setTime(tanggal); // tanggal dari JDateChooser

          Calendar selectedTimeCal = Calendar.getInstance();
          selectedTimeCal.setTime((Date) timeSpinner.getValue()); // waktu dari spinner

          // Set jam dan menit ke tanggal
          selectedDateTime.set(Calendar.HOUR_OF_DAY, selectedTimeCal.get(Calendar.HOUR_OF_DAY));
          selectedDateTime.set(Calendar.MINUTE, selectedTimeCal.get(Calendar.MINUTE));

          // Ambil Date final
          Date finalSelectedDateTime = selectedDateTime.getTime();

          // Sekarang bandingkan
          if (finalSelectedDateTime.before(now)) {
              JOptionPane.showMessageDialog(this, 
                  "Jam Tidak Boleh Waktu Lampau", 
                  "Error", 
                  JOptionPane.ERROR_MESSAGE);
              return;
          }

            if (nama_konser.isEmpty() || nama_band.isEmpty() || lokasi.isEmpty() || tanggal == null) {
                JOptionPane.showMessageDialog(this, "Lengkapi semua field!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            

            tambahData(id_admin, id_kategori, nama_konser, nama_band, lokasi, tanggal, jam);
        });

        kembaliBtn.addActionListener(e -> {
            new berandaAdmin(id_admin).setVisible(true);
            dispose();
        });

        buttonPanel.add(simpanBtn);
        buttonPanel.add(kembaliBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createFormRow(String labelText, JComponent inputComponent) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25));
        row.add(label, BorderLayout.WEST);
        row.add(inputComponent, BorderLayout.CENTER);
        return row;
    }

    private void tambahData(String id_admin, String id_kategori, String nama_konser, String nama_band,
                            String lokasi, Date tanggal, java.sql.Time jam) {
        try (Connection conn = connection.getConnection()) {
            String sql = "INSERT INTO konser (id_konser, id_admin, id_genre_konser, nama_konser, nama_band, lokasi, tanggal, jam) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, id_admin);
            ps.setString(3, id_kategori);
            ps.setString(4, nama_konser);
            ps.setString(5, nama_band);
            ps.setString(6, lokasi);
            ps.setDate(7, new java.sql.Date(tanggal.getTime()));
            ps.setTime(8, jam);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "✅ Konser berhasil ditambahkan!");
                new berandaAdmin(id_admin).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Gagal menambahkan konser!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void kategoriList() {
        listKategori.clear();
        try (Connection conn = connection.getConnection()) {
            String sql = "SELECT * FROM genre_konser";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String idKategori = rs.getString("id_genre");
                String namaKategori = rs.getString("genre_konser");
                listKategori.put(namaKategori, idKategori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
