
package com.mycompany.konser_oop2;

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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.swing.JOptionPane;


/**
 *
 * @author Gita Aulia Hafid
 */
public class detailKonser extends javax.swing.JFrame {

    private String defaultNamaKonserLbl;
    private String defaultJudulLbl;
    private String defaultTanggalLbl;
    private String defaultJamLbl;
    private String defaultLokasiLbl;
    
    // nilai buat insert ke database
    private String id_riwayat;
    private String id_pembeli;
        // id_det_tiket
    private String selectedId;
    private String id_konserBook;
    
    private int kursiBooking;
    private String metodePembayaran;
    private String tanggal_transaksi;

    public detailKonser(String konserId) {
        initComponents();
        detailBooking(konserId);
        
    }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        berandaLbl = new javax.swing.JLabel();
        riwayatLbl = new javax.swing.JLabel();
        usernameLbl = new javax.swing.JLabel();
        panelDetKon = new javax.swing.JPanel();
        namaKonserLbl = new javax.swing.JLabel();
        judulLbl = new javax.swing.JLabel();
        tanggalLbl = new javax.swing.JLabel();
        jamLbl = new javax.swing.JLabel();
        lokasiLbl = new javax.swing.JLabel();
        panelBooking = new javax.swing.JPanel();
        usernameBook = new javax.swing.JTextField();
        kategoriCombo = new javax.swing.JComboBox<>();
        kursiCombo = new javax.swing.JComboBox<>();
        PembayaranCombo = new javax.swing.JComboBox<>();
        pesananBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Konserku");

        berandaLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        berandaLbl.setText("Beranda");

        riwayatLbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        riwayatLbl.setText("Riwayat");

        panelDetKon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        namaKonserLbl.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        namaKonserLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        namaKonserLbl.setText("One ok rock");

        judulLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        judulLbl.setText("Linkin Park");

        tanggalLbl.setText("Juni 19, 2025");

        jamLbl.setText("4.00 WIB");

        lokasiLbl.setText("Stadion utama gbl, jakarta");

        javax.swing.GroupLayout panelDetKonLayout = new javax.swing.GroupLayout(panelDetKon);
        panelDetKon.setLayout(panelDetKonLayout);
        panelDetKonLayout.setHorizontalGroup(
            panelDetKonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetKonLayout.createSequentialGroup()
                .addGroup(panelDetKonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelDetKonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(namaKonserLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDetKonLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(judulLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addGroup(panelDetKonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lokasiLbl)
                            .addGroup(panelDetKonLayout.createSequentialGroup()
                                .addComponent(tanggalLbl)
                                .addGap(27, 27, 27)
                                .addComponent(jamLbl)))))
                .addGap(44, 44, 44))
        );
        panelDetKonLayout.setVerticalGroup(
            panelDetKonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetKonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(namaKonserLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetKonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(judulLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tanggalLbl)
                    .addComponent(jamLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lokasiLbl)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        usernameBook.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameBook.setText("Nama User");
        usernameBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameBookActionPerformed(evt);
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

        PembayaranCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BCA", "MANDIRI", "SHOPEE PAY", "GOPAY" }));
        PembayaranCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PembayaranComboActionPerformed(evt);
            }
        });

        pesananBtn.setText("Buat Pesanan");
        pesananBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesananBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBookingLayout = new javax.swing.GroupLayout(panelBooking);
        panelBooking.setLayout(panelBookingLayout);
        panelBookingLayout.setHorizontalGroup(
            panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBookingLayout.createSequentialGroup()
                .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBookingLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(kategoriCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(usernameBook)
                            .addComponent(kursiCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PembayaranCombo, 0, 242, Short.MAX_VALUE)))
                    .addGroup(panelBookingLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(pesananBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelBookingLayout.setVerticalGroup(
            panelBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBookingLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(usernameBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(kategoriCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(kursiCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PembayaranCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pesananBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32)
                        .addComponent(berandaLbl)
                        .addGap(45, 45, 45)
                        .addComponent(riwayatLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(usernameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(39, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelDetKon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(usernameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(berandaLbl)
                            .addComponent(riwayatLbl))))
                .addGap(34, 34, 34)
                .addComponent(panelDetKon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameBookActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameBookActionPerformed

    private void kategoriComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kategoriComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kategoriComboActionPerformed

    private void kursiComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kursiComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kursiComboActionPerformed

    private void PembayaranComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PembayaranComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PembayaranComboActionPerformed

    private void pesananBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesananBtnActionPerformed
       id_riwayat = UUID.randomUUID().toString();
       id_pembeli = "2007cf89-dd68-450c-9c32-268ae43764fd";
       
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
       tanggal_transaksi = LocalDateTime.now().format(formatter);
       String metodePembayaran = "BCA";
       
       try {
           Connection conn = connection.getConnection();
           Statement stmt = conn.createStatement();
           String queryIrp = "INSERT INTO riwayat_pembeli " + 
                   "(id_riwayat, id_pembeli, id_det_tiket, id_konser, kursi, metode_pembayaran) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";
           PreparedStatement irp = conn.prepareStatement(queryIrp);
           
           irp.setString(1, id_riwayat);
           irp.setString(2, id_pembeli);
           irp.setString(3, selectedId);
           irp.setString(4, id_konserBook);
           irp.setInt(5, kursiBooking);
           irp.setString(6, metodePembayaran);
          // irp.setString(7, tanggal_transaksi);
           
           int rowsAffected = irp.executeUpdate();
           if(rowsAffected > 0){
                new riwayatPembeli().setVisible(true);
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
 
    public void detailBooking(String konserId){
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
                    "where k.id_konser = ? ";
            
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

    public static void main(String args[]) {
      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> PembayaranCombo;
    private javax.swing.JLabel berandaLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jamLbl;
    private javax.swing.JLabel judulLbl;
    private javax.swing.JComboBox<String> kategoriCombo;
    private javax.swing.JComboBox<String> kursiCombo;
    private javax.swing.JLabel lokasiLbl;
    private javax.swing.JLabel namaKonserLbl;
    private javax.swing.JPanel panelBooking;
    private javax.swing.JPanel panelDetKon;
    private javax.swing.JButton pesananBtn;
    private javax.swing.JLabel riwayatLbl;
    private javax.swing.JLabel tanggalLbl;
    private javax.swing.JTextField usernameBook;
    private javax.swing.JLabel usernameLbl;
    // End of variables declaration//GEN-END:variables
}
