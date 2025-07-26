/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.pembeli;

import com.mycompany.konser_oop2.connection;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.swing.JOptionPane;

public class bookingKonser extends javax.swing.JFrame {
    
    // Variabel untuk database
    private String id_riwayat;
    private String id_pembeli;
    private String selectedId;  // id_det_tiket
    private String id_konserBook;
    private String id_bank;
    
    private int kursiBooking;
    private String metodePembayaran;
    private String tanggal_transaksi;
    
    private Map<String, String> linkBank = new HashMap<>();
    private Map<String, String> linkKategori = new HashMap<>();
     

    public bookingKonser(String konserId, String id_pembeli) {
        initComponents();
        setTitle("Pesan Tiket Konser");
        pembayaran();
        bookingKonserMain(konserId);
        this.id_pembeli = id_pembeli;
        try {
             Connection conn = connection.getConnection(); // bikin koneksi baru
            String query = "SELECT nama FROM pembeli WHERE id_pembeli = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id_pembeli);
            ResultSet rs = ps.executeQuery();
           if (rs.next()) {
        String namaPembeli = rs.getString("nama");
        usernameInput.setText(namaPembeli);
        usernameBook.setText(namaPembeli); // tampilkan nama di GUI
    }
    rs.close();
    ps.close();
    conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        panelDetailKonser = new javax.swing.JPanel();
        namaKonserLbl = new javax.swing.JLabel();
        judulLbl = new javax.swing.JLabel();
        tanggalLbl = new javax.swing.JLabel();
        jamLbl = new javax.swing.JLabel();
        lokasiLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        berandaLbl = new javax.swing.JLabel();
        usernameBook = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelBooking = new javax.swing.JPanel();
        usernameInput = new javax.swing.JTextField();
        kategoriCombo = new javax.swing.JComboBox<>();
        kursiCombo = new javax.swing.JComboBox<>();
        pembayaranCombo = new javax.swing.JComboBox<>();
        pesananBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        namaKonserLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        namaKonserLbl.setText("ONE OK ROCK");

        judulLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        judulLbl.setText("LINKIN PARK");

        tanggalLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tanggalLbl.setText("Juni 19, 2025");

        jamLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jamLbl.setText("4:00 Pm wib");

        lokasiLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lokasiLbl.setText("Stadion Utama GBK, Jakarta");

        javax.swing.GroupLayout panelDetailKonserLayout = new javax.swing.GroupLayout(panelDetailKonser);
        panelDetailKonser.setLayout(panelDetailKonserLayout);
        panelDetailKonserLayout.setHorizontalGroup(
            panelDetailKonserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetailKonserLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(judulLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetailKonserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lokasiLbl)
                    .addGroup(panelDetailKonserLayout.createSequentialGroup()
                        .addComponent(tanggalLbl)
                        .addGap(52, 52, 52)
                        .addComponent(jamLbl)))
                .addGap(71, 71, 71))
            .addGroup(panelDetailKonserLayout.createSequentialGroup()
                .addGap(282, 282, 282)
                .addComponent(namaKonserLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDetailKonserLayout.setVerticalGroup(
            panelDetailKonserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailKonserLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(namaKonserLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelDetailKonserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(judulLbl)
                    .addComponent(tanggalLbl)
                    .addComponent(jamLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lokasiLbl)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("KONSERKU");

        berandaLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        berandaLbl.setText("BERANDA");
        berandaLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                berandaLblMouseClicked(evt);
            }
        });

        usernameBook.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usernameBook.setText("USERNAME");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("RIWAYAT");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        usernameInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameInput.setText("USERNAME");
        usernameInput.setEnabled(false);
        usernameInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameInputActionPerformed(evt);
            }
        });

        kategoriCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        kategoriCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kategoriComboActionPerformed(evt);
            }
        });

        kursiCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        kursiCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kursiComboActionPerformed(evt);
            }
        });

        pembayaranCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pembayaranComboActionPerformed(evt);
            }
        });

        pesananBtn.setText("Buat Pesanan");
        pesananBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesananBtnActionPerformed(evt);
            }
        });

        backBtn.setText("Kembali");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBookingLayout = new javax.swing.GroupLayout(panelBooking);
        panelBooking.setLayout(panelBookingLayout);
        panelBookingLayout.setHorizontalGroup(
            panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBookingLayout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pesananBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(pembayaranCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kursiCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kategoriCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameInput))
                .addContainerGap(110, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBookingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backBtn)
                .addGap(192, 192, 192))
        );
        panelBookingLayout.setVerticalGroup(
            panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBookingLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(kategoriCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(kursiCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pembayaranCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pesananBtn)
                .addGap(18, 18, 18)
                .addComponent(backBtn)
                .addContainerGap())
        );

        logoutBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutBtn.setText("LOGOUT");
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(panelDetailKonser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addComponent(berandaLbl)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(usernameBook)
                .addGap(34, 34, 34)
                .addComponent(logoutBtn)
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(panelBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(berandaLbl)
                    .addComponent(jLabel3)
                    .addComponent(usernameBook)
                    .addComponent(logoutBtn))
                .addGap(38, 38, 38)
                .addComponent(panelDetailKonser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kategoriComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kategoriComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kategoriComboActionPerformed

    private void kursiComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kursiComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kursiComboActionPerformed

    private void pembayaranComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pembayaranComboActionPerformed
       String selectedName = (String) pembayaranCombo.getSelectedItem();
                if(linkBank.containsKey(selectedName)){
                    String selectedId = linkBank.get(selectedName);
                    id_bank = selectedId;
                }
    }//GEN-LAST:event_pembayaranComboActionPerformed

    private void pembayaran() {
         Connection conn = connection.getConnection();
         String query ="SELECT * from bank";
         pembayaranCombo.removeAllItems();
         linkBank.clear();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String kategori = rs.getString("nama_bank");
                String id_genre = rs.getString("id_bank");
                pembayaranCombo.addItem(kategori);
                linkBank.put(kategori, id_genre);
            }    
            
            if (pembayaranCombo.getItemCount()>0){
                pembayaranCombo.setSelectedIndex(0);
                 String selectedName = (String) pembayaranCombo.getSelectedItem();
                if(linkBank.containsKey(selectedName)){
                    String selectedId = linkBank.get(selectedName);
                    id_bank = selectedId;
                }
            }
            rs.close();
            ps.close();
            conn.close();
        }catch(Exception e){
              e.printStackTrace();
         }
    }
    
    private void pesananBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesananBtnActionPerformed
        id_riwayat = UUID.randomUUID().toString();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
       tanggal_transaksi = LocalDateTime.now().format(formatter);
       
       try {
           Connection conn = connection.getConnection();
           Statement stmt = conn.createStatement();
           String queryIrp = "INSERT INTO riwayat_pembeli " + 
                   "(id_riwayat, id_pembeli, id_det_tiket, id_konser, id_bank kursi) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";
           PreparedStatement irp = conn.prepareStatement(queryIrp);
           
           irp.setString(1, id_riwayat);
           irp.setString(2, id_pembeli);
           irp.setString(3, selectedId);
           irp.setString(4, id_konserBook);
           irp.setString(5, id_bank);
           irp.setInt(6, kursiBooking);
           
         
           int rowsAffected = irp.executeUpdate();
           if(rowsAffected > 0){
                new riwayatPembeli2(id_pembeli).setVisible(true);
                    dispose();
           } else {
               JOptionPane.showMessageDialog(null, "Gagal menyimpan Data", 
                       "Rrror", JOptionPane.ERROR_MESSAGE);
           }
           irp.close();
           conn.close();
       }catch(SQLException e){
           e.printStackTrace();
       }
    }//GEN-LAST:event_pesananBtnActionPerformed

    private void usernameInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameInputActionPerformed
        new beranda(id_pembeli).setVisible(true);
                   dispose();
    }//GEN-LAST:event_usernameInputActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        new beranda(id_pembeli).setVisible(true);
        dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void berandaLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berandaLblMouseClicked
                   new beranda(id_pembeli).setVisible(true);
                   dispose();        
    }//GEN-LAST:event_berandaLblMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        new riwayatPembeli2(id_pembeli).setVisible(true);
                   dispose();  
    }//GEN-LAST:event_jLabel3MouseClicked

    private void logoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseClicked
       new loginPembeli().setVisible(true);
        dispose();
    }//GEN-LAST:event_logoutBtnMouseClicked

    public void bookingKonserMain(String konserId){
        namaKonserLbl.setText("");
        judulLbl.setText("");
        tanggalLbl.setText("");
        jamLbl.setText("");
        lokasiLbl.setText("");
        
        kategoriCombo.removeAllItems();
        kursiCombo.removeAllItems();
        List<String> idKategoriList = new ArrayList<>();
        
         try {
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();
            String query ="SELECT * from konser where id_konser = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, konserId);
            ResultSet rs = ps.executeQuery();
            
            
            if(rs.next()) {
             id_konserBook = rs.getString("id_konser");
             String nama_konser = rs.getString("nama_konser");
             String nama_band = rs.getString("nama_band");
             String tanggal = rs.getString("tanggal");
             String jam = rs.getString("jam");
             String lokasi = rs.getString("lokasi");
            namaKonserLbl.setText(nama_band);
            tanggalLbl.setText(tanggal);
            jamLbl.setText(jam);
            lokasiLbl.setText(lokasi);
            judulLbl.setText(nama_konser);  
            }
            
            String booking ="SELECT d.id_det_tiket, d.jumlah_tiket, d.harga_tiket, d.deksripsi, k.id_genre_konser, kt.kategori_konser, d.id_det_tiket " 
                    + "from konser k " +
                    "JOIN detail_kategori_tiket d on k.id_konser = d.id_konser "
                    +
                    "JOIN kategori_tiket kt on d.id_kategori_tiket = kt.id_kategori_tiket "
                    +
                    "where k.id_konser = ?";
            
            PreparedStatement b = conn.prepareStatement(booking);
            b.setString(1, konserId);
            ResultSet br = b.executeQuery();
            
            while(br.next()){
                String kategori = br.getString("kategori_konser");
                String idKategori = br.getString("id_det_tiket");
                int jumlah_tiket = br.getInt("jumlah_tiket");
                
                 kategoriCombo.addItem(kategori);      
                 idKategoriList.add(idKategori); 
                 
                 kategoriCombo.addActionListener(e -> {
                     int selectedIndex = kategoriCombo.getSelectedIndex();
                     if (selectedIndex >= 0 && selectedIndex < idKategoriList.size()){
                        selectedId = idKategoriList.get(selectedIndex);
                         System.out.println("id yang dipilih : " + selectedId);
                         
                         try {
                            String kursiQuery = "SELECT kursi from riwayat_pembeli WHERE id_det_tiket = ?";
                            PreparedStatement kq = conn.prepareStatement(kursiQuery);
                            kq.setString(1, selectedId);
                            ResultSet kursiRs = kq.executeQuery();
                            
                            Set<Integer> terisi = new HashSet<>();
                            while(kursiRs.next()){
                                terisi.add(kursiRs.getInt("kursi"));
                            }
                            
                            kursiCombo.removeAllItems();
                            
                            for(int i = 1; i<= jumlah_tiket; i++){
                                if(!terisi.contains(i)){
                                    kursiCombo.addItem(String.valueOf(i));
                                }
                            }
                            kursiCombo.addActionListener(e2 -> {
                              updateKursiDipilih();
                            });
                            
                         } catch (SQLException ex){
                             ex.printStackTrace();
                         }
                    }
                 });
            }    
         } catch(Exception e){
              e.printStackTrace();
         }
    }
    
        private void updateKursiDipilih(){
         String selectedKursi = (String) kursiCombo.getSelectedItem();
         if(selectedKursi != null){
             try {
                 kursiBooking = Integer.parseInt(selectedKursi);
                 System.out.println("Kursi yang dipilih " + kursiBooking);
             } catch(NumberFormatException ex){
                 ex.printStackTrace();
             }
         }
    }
    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel berandaLbl;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jamLbl;
    private javax.swing.JLabel judulLbl;
    private javax.swing.JComboBox<String> kategoriCombo;
    private javax.swing.JComboBox<String> kursiCombo;
    private javax.swing.JLabel logoutBtn;
    private javax.swing.JLabel lokasiLbl;
    private javax.swing.JLabel namaKonserLbl;
    private javax.swing.JPanel panelBooking;
    private javax.swing.JPanel panelDetailKonser;
    private javax.swing.JComboBox<String> pembayaranCombo;
    private javax.swing.JButton pesananBtn;
    private javax.swing.JLabel tanggalLbl;
    private javax.swing.JLabel usernameBook;
    private javax.swing.JTextField usernameInput;
    // End of variables declaration//GEN-END:variables
}
