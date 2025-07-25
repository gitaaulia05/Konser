    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.pembeli;

import com.mycompany.konser_oop2.connection;
import com.mycompany.konser_oop2.pembeli.cardRiwayatPanel;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

/**
 *
 * @author Gita Aulia Hafid
 */
public class riwayatPembeli2 extends javax.swing.JFrame {

    private String var_pembeli;
    
    public riwayatPembeli2(String id_pembeli) {
        this.var_pembeli = id_pembeli;
        initComponents();
        setTitle("Riwayat Pembeli");
        containerRiwayat.setLayout(new BoxLayout(containerRiwayat, BoxLayout.Y_AXIS));
      
        if(var_pembeli == null || var_pembeli.isEmpty()){
             new beranda(id_pembeli).setVisible(true);
            dispose();
        } else {
                try {
                     Connection conn = connection.getConnection(); // bikin koneksi baru
                    String query = "SELECT nama FROM pembeli WHERE id_pembeli = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, id_pembeli);
                    ResultSet rs = ps.executeQuery();
                   if (rs.next()) {
                String namaPembeli = rs.getString("nama");
                usernameBook.setText(namaPembeli);
                usernameBook.setText(namaPembeli); // tampilkan nama di GUI
            }   riwayat(var_pembeli);
            rs.close();
            ps.close();
            conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }
    }
    
    public void riwayat(String var_pembeli){
        containerRiwayat.removeAll();
        
        try {
            Connection conn = connection.getConnection();
            String query = "SELECT rp.id_det_tiket, rp.kursi, rp.tanggal_transaksi, rp.metode_pembayaran, dk.harga_tiket, kt.kategori_konser, k.nama_band, k.nama_konser, k.lokasi, k.jam, k.tanggal " +
                    "FROM riwayat_pembeli rp " + "JOIN detail_kategori_tiket dk on rp.id_det_tiket = dk.id_det_tiket " 
                    + "JOIN kategori_tiket kt on dk.id_kategori_tiket = kt.id_kategori_tiket "
                    + "JOIN konser k on dk.id_konser = k.id_konser "                 
                    + "where rp.id_pembeli = ? ";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, var_pembeli);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String namaKonser = rs.getString("nama_konser");
                String namaBand = rs.getString("nama_band");
                String tanggal = rs.getString("tanggal");
                String jam = rs.getString("jam");
                String lokasi = rs.getString("lokasi");
                String kursi = rs.getString("kursi");
                String kategori = rs.getString("kategori_konser");
                String id_detRiwayat = rs.getString("id_det_tiket");
               
                cardRiwayatPanel card = new cardRiwayatPanel(id_detRiwayat, namaKonser, namaBand, tanggal, jam, lokasi, kursi, kategori);
                card.addMouseListener(new MouseAdapter(){
                     public void mouseClicked(MouseEvent e) {
                    new detailRiwayatKonser2(card.getIdTiket(), var_pembeli).setVisible(true);
                    dispose();
                     }
                });
                containerRiwayat.add(card);
                containerRiwayat.add(Box.createRigidArea(new Dimension(0,10)));
            }
            containerRiwayat.revalidate();
            containerRiwayat.repaint();
        } catch(SQLException e) {
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

        containerScroll = new javax.swing.JScrollPane();
        containerRiwayat = new javax.swing.JPanel();
        usernameBook = new javax.swing.JLabel();
        logoutBtn1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        berandaLbl2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout containerRiwayatLayout = new javax.swing.GroupLayout(containerRiwayat);
        containerRiwayat.setLayout(containerRiwayatLayout);
        containerRiwayatLayout.setHorizontalGroup(
            containerRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );
        containerRiwayatLayout.setVerticalGroup(
            containerRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );

        containerScroll.setViewportView(containerRiwayat);

        usernameBook.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usernameBook.setText("USERNAME");

        logoutBtn1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logoutBtn1.setText("LOGOUT");
        logoutBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtn1MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("RIWAYAT");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("KONSERKU");

        berandaLbl2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        berandaLbl2.setText("BERANDA");
        berandaLbl2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                berandaLbl2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(37, 37, 37)
                .addComponent(berandaLbl2)
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(usernameBook)
                .addGap(34, 34, 34)
                .addComponent(logoutBtn1))
            .addGroup(layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(containerScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(berandaLbl2)
                    .addComponent(jLabel6)
                    .addComponent(usernameBook)
                    .addComponent(logoutBtn1))
                .addGap(40, 40, 40)
                .addComponent(containerScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtn1MouseClicked
        new loginPembeli().setVisible(true);
        dispose();
    }//GEN-LAST:event_logoutBtn1MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        new riwayatPembeli2(var_pembeli).setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void berandaLbl2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_berandaLbl2MouseClicked
        new beranda(var_pembeli).setVisible(true);
        dispose();
    }//GEN-LAST:event_berandaLbl2MouseClicked

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel berandaLbl2;
    private javax.swing.JPanel containerRiwayat;
    private javax.swing.JScrollPane containerScroll;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel logoutBtn1;
    private javax.swing.JLabel usernameBook;
    // End of variables declaration//GEN-END:variables
}
