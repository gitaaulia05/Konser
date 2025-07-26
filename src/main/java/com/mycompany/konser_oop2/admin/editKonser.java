/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.admin;

import com.mycompany.konser_oop2.connection;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Gita Aulia Hafid
 */
public class editKonser extends javax.swing.JFrame {
    
    private String id_admin;
    private String id_konser;
    private String id_kategori;
    
    private String nama_konser, nama_band, lokasi, tanggalStr, jamStr;
    private Date tanggal;
    java.sql.Time jam;
    
        private Map<String, String> listKategori = new HashMap<>();
        
    public editKonser(String id_admin, String id_konser) {
        initComponents();
        this.id_admin = id_admin;
        this.id_konser = id_konser;
        kategoriList();
        setTitle("Update Detail Kategori Tiket ");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // === Navbar ===
        navbarAdmin navbar = new navbarAdmin(id_admin);
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

    // Buat field input
    JComboBox<String>  kategoriKonserField= new JComboBox<>(listKategori.keySet().toArray(new String[0]));

     String selectedNamaDefault = (String) kategoriKonserField.getSelectedItem();
     id_kategori = listKategori.get(selectedNamaDefault);
    //comboKategori event klik  
        kategoriKonserField.addActionListener(e -> {
          String selectedNama= (String) kategoriKonserField.getSelectedItem();
          id_kategori = listKategori.get(selectedNama);
      });
        
    JTextField namaKonserField = new JTextField(20);
    JTextField namaBandField = new JTextField(20);
    JTextField lokasiField = new JTextField(20);

    JDateChooser dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("yyyy-MM-dd");

    SpinnerDateModel timeModel = new SpinnerDateModel();
    JSpinner timeSpinner = new JSpinner(timeModel);
    timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

    // Panel helper untuk setiap baris (Label + Input)
    panel.add(createFormRow("Kategori Konser:", kategoriKonserField));
    panel.add(createFormRow("Nama Konser:", namaKonserField));
    panel.add(createFormRow("Nama Band:", namaBandField));
    panel.add(createFormRow("Lokasi:", lokasiField));
    panel.add(createFormRow("Tanggal:", dateChooser));
    panel.add(createFormRow("Jam:", timeSpinner));

    // Ambil data dari DB (isi form)
    loadData(namaKonserField, namaBandField, lokasiField, dateChooser, timeSpinner);

    // Tombol
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.WHITE);
    JButton simpanBtn = new JButton("✅ Simpan Perubahan");
    JButton kembaliBtn = new JButton("⬅ Kembali");

    simpanBtn.addActionListener(e -> {
        nama_konser = namaKonserField.getText();
        nama_band = namaBandField.getText();
        lokasi = lokasiField.getText();
        tanggal = dateChooser.getDate();
        jam = new java.sql.Time(((Date) timeSpinner.getValue()).getTime());

        updateData(id_admin, id_konser, id_kategori, nama_konser, nama_band, tanggal, jam);
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

    // Helper untuk 1 baris form (Label + Field)
    private JPanel createFormRow(String labelText, JComponent inputComponent) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25));
        row.add(label, BorderLayout.WEST);
        row.add(inputComponent, BorderLayout.CENTER);
        return row;
    }


    private void updateData(String id_pemilik, String idKonser , String id_genre, String nama_konser, String nama_band,
        Date tanggal, java.sql.Time jam){
        
                // Ambil waktu sekarang + 1 jam
        Calendar minTime = Calendar.getInstance();
        minTime.add(Calendar.HOUR_OF_DAY, 1);

        // Gabungkan tanggal & jam input
        Calendar inputCal = Calendar.getInstance();
        inputCal.setTime(tanggal);
        Calendar jamCal = Calendar.getInstance();
        jamCal.setTime(jam);
        inputCal.set(Calendar.HOUR_OF_DAY, jamCal.get(Calendar.HOUR_OF_DAY));
        inputCal.set(Calendar.MINUTE, jamCal.get(Calendar.MINUTE));

        // Cek validasi
        if (inputCal.before(minTime)) {
            JOptionPane.showMessageDialog(this, "Waktu konser harus minimal 1 jam dari sekarang!");
            return;
        }
        try {
            Connection conn = connection.getConnection();
            Statement checkId = conn.createStatement();
            String ci = "Select * from konser where id_konser = ? and id_admin =? ";
            PreparedStatement pci = conn.prepareStatement(ci);
            pci.setString(1, idKonser);
            pci.setString(2, id_admin);
            ResultSet rs = pci.executeQuery();
            
            if(rs.next()){
                
                String updateSql = "UPDATE konser SET id_genre_konser =?, nama_konser = ?, nama_band = ?, lokasi =?, tanggal=?, jam=? WHERE id_konser = ? ";
                PreparedStatement psUpdate = conn.prepareStatement(updateSql);
                psUpdate.setString(1, id_genre);
                psUpdate.setString(2, nama_konser);
                psUpdate.setString(3, nama_band);
                psUpdate.setString(4, lokasi);
                psUpdate.setDate(5,  new java.sql.Date(tanggal.getTime()));
                psUpdate.setTime(6, jam);
                psUpdate.setString(7, id_konser);

                int rowsUpdated = psUpdate.executeUpdate();
                if (rowsUpdated > 0) {
                   JOptionPane.showMessageDialog(this, "Berhasil Ubah Data!");
                    new berandaAdmin(id_admin).setVisible(true);
                    dispose();
                }else {
                 JOptionPane.showMessageDialog(this, "Gagal Update data konser!");
                 return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "data konser tidak valid!");
                return;
            }

            
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void loadData(JTextField namaKonserField, JTextField namaBandField,
     JTextField lokasiField, JDateChooser dateChooser, JSpinner timeSpinner) {
    try (Connection conn = connection.getConnection()) {
        String sql = "SELECT * FROM konser WHERE id_konser = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id_konser);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            namaKonserField.setText(rs.getString("nama_konser"));
            namaBandField.setText(rs.getString("nama_band"));
            lokasiField.setText(rs.getString("lokasi"));

            // Tanggal
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date tanggalDate = sdf.parse(rs.getString("tanggal"));
                dateChooser.setDate(tanggalDate);
                dateChooser.setMinSelectableDate(new Date()); // minimal hari ini
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // JAM 
            SimpleDateFormat jamFormat = new SimpleDateFormat("HH:mm");
            try {
                Date jamDate = jamFormat.parse(rs.getString("jam"));
                timeSpinner.setValue(jamDate);

                // Minimal 1 jam dari sekarang
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR_OF_DAY, 1);
                Date minTime = cal.getTime();             
                SpinnerDateModel model = (SpinnerDateModel) timeSpinner.getModel();
               
                
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
     public void kategoriList(){
        listKategori.clear();
         try {
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();
            String kategori ="Select * from genre_konser";
            PreparedStatement kl = conn.prepareStatement(kategori);
            ResultSet rkl = kl.executeQuery();
            
        while(rkl.next()){
            String idKategori = rkl.getString("id_genre");
            String namaKategori = rkl.getString("genre_konser");     
            listKategori.put(namaKategori, idKategori);
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
