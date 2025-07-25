/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.pembeli;

import com.mycompany.konser_oop2.connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class pesanKonser extends javax.swing.JFrame {
    
     // Variabel untuk database
    private String id_riwayat;
    private String id_pembeli;
    private String selectedId;  // id_det_tiket
    private String id_konserBook;
    private String id_bank;
    private String id_konser;
    
    private int kursiBooking;
    private String metodePembayaran;
    private String tanggal_transaksi;

    
    private Map<String, String> linkBank = new HashMap<>();
    private Map<String, String> listKategori = new HashMap<>();
    private Map<String, String> listKursi = new HashMap<>();
    
       JComboBox<String> kursiKonserCombo ;
    public pesanKonser(String konserId , String Pembeli) {
        this.id_konser = konserId;
        this.id_pembeli = Pembeli;     
        getKategoriKonser();
        
        setTitle("Detail Konser");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // === Navbar ===
        navbar navbar = new navbar(id_pembeli); // pastikan class ini ada
        add(navbar, BorderLayout.NORTH);

        // === Card Detail ===
        JPanel detailCard = createDetailCard();
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(detailCard);
        add(centerPanel, BorderLayout.CENTER);
        
        buatFormPemesan(centerPanel); 
        add(centerPanel, BorderLayout.CENTER);

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
                id_konserBook = rs.getString("id_konser");
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
    
     private void buatFormPemesan(JPanel parentPanel) {
      // === Panel Utama ===
      JPanel formPanel = new JPanel();
      formPanel.setLayout(new BorderLayout());
      formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
      formPanel.setBackground(Color.WHITE);

      // === Grid Panel (2 kolom) ===
      JPanel gridPanel = new JPanel(new GridLayout(1, 2, 20, 0));
      gridPanel.setBackground(Color.WHITE);

      // === Kiri ===
      JPanel kiriPanel = new JPanel();
      kiriPanel.setLayout(new GridLayout(3, 1, 10, 10));
      kiriPanel.setBackground(Color.WHITE);
    
      JComboBox<String> kategoriKonserCombo = new JComboBox<>(listKategori.keySet().toArray(new String[0]));
      kursiKonserCombo = new JComboBox<>(listKursi.keySet().toArray(new String[0]));
      JComboBox<String> pilihanPembayaranCombo = new JComboBox<>(new String[]{"Transfer Bank", "OVO", "Gopay"});

      // === Event Klik kategori konser ===
      kategoriKonserCombo.addActionListener(e -> {
          String selectedKategori = (String) kategoriKonserCombo.getSelectedItem();
          kategoriDipilih(selectedKategori); // Fungsi yang dipanggil saat diklik
      });

      kiriPanel.add(kategoriKonserCombo);
      kiriPanel.add(kursiKonserCombo);
      kiriPanel.add(pilihanPembayaranCombo);

      // === Kanan ===
      JPanel kananPanel = new JPanel(new BorderLayout());
      kananPanel.setBackground(Color.WHITE);
      JLabel usernameLbl = new JLabel("Username: " + id_pembeli); 
      usernameLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
      kananPanel.add(usernameLbl, BorderLayout.NORTH);

      // Gabungkan kiri dan kanan ke grid
      gridPanel.add(kiriPanel);
      gridPanel.add(kananPanel);

      formPanel.add(gridPanel, BorderLayout.CENTER);

      // === Tombol Pesan Tiket ===
      JButton pesanBtn = new JButton("Pesan Tiket");
      pesanBtn.setBackground(Color.RED);
      pesanBtn.setForeground(Color.WHITE);
      pesanBtn.setFocusPainted(false);

      // Panel untuk tombol + label kembali
      JPanel bawahPanel = new JPanel(new BorderLayout());
      bawahPanel.setBackground(Color.WHITE);
      bawahPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
      bawahPanel.add(pesanBtn, BorderLayout.NORTH);

      // Label Kembali
      JLabel kembaliLbl = new JLabel("Kembali");
      kembaliLbl.setForeground(Color.BLUE.darker());
      kembaliLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      kembaliLbl.setHorizontalAlignment(SwingConstants.CENTER);

      // Event klik kembali
      kembaliLbl.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              dispose(); // misalnya: menutup halaman saat kembali
              // atau bisa ganti ke halaman sebelumnya
          }
      });

      bawahPanel.add(kembaliLbl, BorderLayout.SOUTH);

      formPanel.add(bawahPanel, BorderLayout.SOUTH);

      // Tambahkan ke panel utama window
      parentPanel.add(formPanel, BorderLayout.SOUTH);
  }


   private void getKategoriKonser() {
    listKategori.clear();
    try {
      Connection conn = connection.getConnection();
      Statement stmt = conn.createStatement();
         String booking ="SELECT d.id_det_tiket, d.id_kategori_tiket, d.jumlah_tiket, d.harga_tiket, d.deskripsi, k.id_genre_konser, kt.kategori_konser, d.id_det_tiket " 
                    + "from konser k " +
                    "JOIN detail_kategori_tiket d on k.id_konser = d.id_konser "
                    +
                    "JOIN kategori_tiket kt on d.id_kategori_tiket = kt.id_kategori_tiket "
                    +
                    "where k.id_konser = ?";
       PreparedStatement bo = conn.prepareStatement(booking);
       bo.setString(1, id_konser);
       ResultSet br = bo.executeQuery();
       
        while(br.next()){
            String idKategori = br.getString("id_kategori_tiket");
            String namaKategori = br.getString("kategori_konser");     
            listKategori.put(namaKategori, idKategori);
        }
        
    }catch(Exception e){
              e.printStackTrace();
         }
}
   
   public void kategoriDipilih(String selectedKategori){      
                if(listKategori.containsKey(selectedKategori)){
                    String selectedId = listKategori.get(selectedKategori);
                    selectedId = selectedId;
                    System.out.println(selectedId);
                }
       
       listKursi.clear();
       
       try {
        Connection conn = connection.getConnection();
        Statement stmt = conn.createStatement();
        
         String kategoriDipilih = "SELECT d.id_det_tiket, d.jumlah_tiket, d.harga_tiket, d.deskripsi, k.id_genre_konser, kt.kategori_konser, d.id_det_tiket " 
                    + "from konser k " +
                    "JOIN detail_kategori_tiket d on k.id_konser = d.id_konser "
                    +
                    "JOIN kategori_tiket kt on d.id_kategori_tiket = kt.id_kategori_tiket "
                    +
                    "where k.id_konser = ?";
       PreparedStatement kp = conn.prepareStatement(kategoriDipilih);
       kp.setString(1, id_konser);
       ResultSet br = kp.executeQuery();
       while(br.next()){
                
       }
       } catch(Exception e){
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

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(pesanKonser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pesanKonser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pesanKonser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pesanKonser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new pesanKonser("5773476a-064f-4bb3-b518-5747e6917dc0", "9e99d231-4a94-4c1e-aa87-0d9b672cff6a").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
