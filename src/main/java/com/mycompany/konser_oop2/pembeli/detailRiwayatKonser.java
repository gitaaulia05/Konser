/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.pembeli;

import com.mycompany.konser_oop2.connection;
import com.formdev.flatlaf.FlatLightLaf;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class detailRiwayatKonser extends javax.swing.JFrame {


    private String var_pembeli;
    private String id_det_tiket;

    private JLabel namaKonserLbl;
    private JLabel namaBandLbl;
    private JLabel tanggalLbl;
    private JLabel jamLbl;
    private JLabel lokasiLbl;
    private JLabel kategoriLbl;
    private JLabel qrLbl;
    private JLabel metodePemLbl;
    private JLabel tanggalTransaksiLbl;
    
    public detailRiwayatKonser(String id_det_tiket, String id_pembeli) {
        this.var_pembeli = id_pembeli;
        this.id_det_tiket = id_det_tiket;

        if (var_pembeli == null || var_pembeli.isEmpty() || id_det_tiket == null || id_det_tiket.isEmpty()) {
            new beranda(id_pembeli).setVisible(true);
            dispose();
            return;
        }

        initUI();
        loadDetail();
        setVisible(true);
    }

    private void initUI() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Detail Riwayat Konser");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // === Navbar ===
        navbar navbar = new navbar(var_pembeli);
        add(navbar, BorderLayout.NORTH);

        // === Panel Detail ===
        JPanel  infoPanel = new JPanel();
         infoPanel.setLayout(new BoxLayout( infoPanel, BoxLayout.Y_AXIS));
         infoPanel.setBackground(Color.WHITE);
         infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
          // Panel kiri (info teks)
        JPanel detailPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        namaKonserLbl = createLabel("", 18, Font.BOLD);
        namaBandLbl = createLabel("", 16, Font.PLAIN);
        tanggalLbl = createLabel("", 14, Font.PLAIN);
        jamLbl = createLabel("", 14, Font.PLAIN);
        lokasiLbl = createLabel("", 14, Font.PLAIN);
        kategoriLbl = createLabel("", 14, Font.PLAIN);
        metodePemLbl = createLabel("", 14, Font.PLAIN);
        tanggalTransaksiLbl =createLabel("", 14, Font.PLAIN);
       
         infoPanel.add(namaKonserLbl);
         infoPanel.add(Box.createVerticalStrut(10));
         infoPanel.add(namaBandLbl);
         infoPanel.add(Box.createVerticalStrut(10));
         infoPanel.add(tanggalLbl);
         infoPanel.add(jamLbl);
         infoPanel.add(lokasiLbl);
         infoPanel.add(kategoriLbl);
         infoPanel.add(Box.createVerticalStrut(20));
         infoPanel.add(metodePemLbl);
         infoPanel.add(Box.createVerticalStrut(20));
         infoPanel.add(tanggalTransaksiLbl);
         infoPanel.add(Box.createVerticalStrut(20));
         
          // Panel kanan (QR Code)
            JPanel qrPanel = new JPanel();
            qrPanel.setBackground(Color.WHITE);
            qrLbl = new JLabel();
            qrPanel.add(qrLbl);

        // Gabungkan info dan QR code
        detailPanel.add(infoPanel, BorderLayout.CENTER);
        detailPanel.add(qrPanel, BorderLayout.EAST);

        add(detailPanel, BorderLayout.CENTER);

        // === Tombol Kembali ===
        JButton backBtn = new JButton("Kembali");
        backBtn.addActionListener(e -> {
            new riwayatPembeli(var_pembeli).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, int size, int style) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", style, size));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private void loadDetail() {
        try {
            Connection conn = connection.getConnection();
            String query = "SELECT rp.id_det_tiket, rp.kursi, dk.harga_tiket, kt.kategori_konser, rp.tanggal_transaksi, " +
                    "k.nama_band, k.nama_konser, k.lokasi, k.jam, k.tanggal, b.nama_bank " +
                    "FROM riwayat_pembeli rp " +
                    "JOIN bank b on rp.id_bank = b.id_bank " + 
                    "JOIN detail_kategori_tiket dk ON rp.id_det_tiket = dk.id_det_tiket " +
                    "JOIN kategori_tiket kt ON dk.id_kategori_tiket = kt.id_kategori_tiket " +
                    "JOIN konser k ON dk.id_konser = k.id_konser " +
                    "WHERE rp.id_pembeli = ? AND rp.id_det_tiket = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, var_pembeli);
            ps.setString(2, id_det_tiket);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                namaKonserLbl.setText("Nama Konser: " + rs.getString("nama_konser"));
                namaBandLbl.setText("Band: " + rs.getString("nama_band"));
                tanggalLbl.setText("Tanggal: " + rs.getString("tanggal"));
                jamLbl.setText("Jam: " + rs.getString("jam"));
                lokasiLbl.setText("Lokasi: " + rs.getString("lokasi"));
                kategoriLbl.setText("Kategori/Kursi: " + rs.getString("kategori_konser") + " / " + rs.getString("kursi"));
                metodePemLbl.setText("Metode Pembayaran: " + rs.getString("nama_bank"));
                tanggalTransaksiLbl.setText("Tanggal Transaksi: " + rs.getString("tanggal_transaksi"));
                generateQRCode(id_det_tiket, qrLbl);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateQRCode(String data, JLabel label) {
        int width = 200;
        int height = 200;
        QRCodeWriter qrWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            label.setIcon(new ImageIcon(qrImage));
        } catch (WriterException e) {
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
