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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.UUID;

public class pesanKonser extends javax.swing.JFrame {
    
     // Variabel untuk database
    private String id_riwayat;
    private String id_pembeli;
    private String selectedId;  // id_kategori
    private String id_det_tiket;
    private String id_bank;
    private String id_konser;
    private int kursiBooking;
    
    private String metodePembayaran;
    private String tanggal_transaksi;
    
    JTextArea benefitArea;

    
    private Map<String, String> listBank = new HashMap<>();
    
    private Map<String, String> listKategori = new HashMap<>();
    private List<Integer> listKursi = new ArrayList<>();
    private List<String>listDeskripsi =new ArrayList<>(); 
    
    JComboBox<String> kursiKonserCombo ;
    JComboBox<String> pilihanPembayaranCombo;
    
   public pesanKonser(String konserId, String Pembeli) {
    this.id_konser = konserId;
    this.id_pembeli = Pembeli;     
    getKategoriKonser();
    getBank();
    
    setTitle("Detail Konser");
    setSize(800, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    
    // === Navbar ===
    navbar navbar = new navbar(id_pembeli); // pastikan class ini ada
    add(navbar, BorderLayout.NORTH);

    // === Panel yang akan discroll ===
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // agar vertikal
    centerPanel.setBackground(Color.WHITE);

    // Tambahkan komponen ke centerPanel
    JPanel detailCard = createDetailCard();
    centerPanel.add(detailCard);
    buatFormPemesan(centerPanel); // tambahkan form ke panel yang sama

    // === Scroll Pane ===
    JScrollPane scrollPane = new JScrollPane(centerPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getVerticalScrollBar().setUnitIncrement(16); // agar scroll halus

    add(scrollPane, BorderLayout.CENTER); // Tambahkan scrollPane, bukan centerPanel langsung

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
                id_konser = rs.getString("id_konser");
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
    JPanel kiriPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3 baris, 2 kolom
    kiriPanel.setBackground(Color.WHITE);

      
      // konversi list kursi ke string
      String[] kursiItems = listKursi.stream()
                                      .map(Object::toString)
                                      .toArray(String[]::new);
    
      JLabel kategoriLabel = new JLabel("Kategori Konser:");
      JComboBox<String> kategoriKonserCombo = new JComboBox<>(listKategori.keySet().toArray(new String[0]));
      
      JLabel kursiLabel = new JLabel("Pilih Kursi:");
      kursiKonserCombo = new JComboBox<>(kursiItems);
      
      JLabel pembayaranLabel = new JLabel("Metode Pembayaran:");
      pilihanPembayaranCombo = new JComboBox<>(listBank.keySet().toArray(new String[0]));
      
      // set default id
      kategoriKonserCombo.setSelectedIndex(0);
      pilihanPembayaranCombo.setSelectedIndex(0);
      
      
      kiriPanel.add(kategoriLabel);
      kiriPanel.add(kategoriKonserCombo);
      
      kiriPanel.add(kursiLabel);
      kiriPanel.add(kursiKonserCombo);
      
      kiriPanel.add(pembayaranLabel);
      kiriPanel.add(pilihanPembayaranCombo);

      // === Kanan ===
      JPanel kananPanel = new JPanel(new BorderLayout());
      kananPanel.setLayout(new BoxLayout(kananPanel, BoxLayout.Y_AXIS));
      kananPanel.setBackground(Color.WHITE);
      
      // username
     JLabel usernameLbl = new JLabel("Username : " + navbar.getNamaPembeli(id_pembeli)); 
     usernameLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
     usernameLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
     kananPanel.add(usernameLbl);
    
            // TextArea untuk Benefit
        benefitArea = new JTextArea(5, 20);
        benefitArea.setLineWrap(true);
        benefitArea.setWrapStyleWord(true);
        benefitArea.setEditable(false);
        benefitArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        
        
         // Evenet klik Pembayaran 
      pilihanPembayaranCombo.addActionListener(e -> {
          String selectedBank = (String) pilihanPembayaranCombo.getSelectedItem();
          bank(selectedBank); // Fungsi yang dipanggil saat diklik
      });
      
      // === Event Klik kategori konser ===
      kategoriKonserCombo.addActionListener(e -> {
          String selectedKategori = (String) kategoriKonserCombo.getSelectedItem();
          kategoriDipilih(selectedKategori); // Fungsi yang dipanggil saat diklik
          benefitArea.setText(String.join("\n", listDeskripsi));
      });

            // Set default kategori dan bank
      if (!listKategori.isEmpty()) {
          String firstKategori = listKategori.keySet().iterator().next();
          kategoriKonserCombo.setSelectedItem(firstKategori);
          kategoriDipilih(firstKategori); // Panggil agar kursi terisi
          id_det_tiket = listKategori.get(firstKategori);
      }

      if (!listBank.isEmpty()) {
          String firstBank = listBank.keySet().iterator().next();
          pilihanPembayaranCombo.setSelectedItem(firstBank);
          id_bank = listBank.get(firstBank);
      }

      // Ambil kursi pertama setelah kategori dipilih
      if (!listKursi.isEmpty()) {
          kursiBooking = listKursi.get(0); // integer langsung
      }


        // Scroll agar rapi jika panjang
        JScrollPane scrollBenefit = new JScrollPane(benefitArea);
        scrollBenefit.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollBenefit.setBorder(BorderFactory.createTitledBorder("Benefit Kategori Konser"));

        // Tambahkan ke panel
        kananPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        kananPanel.add(scrollBenefit);

      // Gabungkan kiri dan kanan ke grid
      gridPanel.add(kiriPanel);
      gridPanel.add(kananPanel);

      formPanel.add(gridPanel, BorderLayout.CENTER);

      // === Tombol Pesan Tiket ===
      JButton pesanBtn = new JButton("Pesan Tiket");
      pesanBtn.setBackground(Color.RED);
      pesanBtn.setForeground(Color.WHITE);
      pesanBtn.setFocusPainted(false);
      
      pesanBtn.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin membeli tiket konser ini?",
            "Konfirmasi Pembelian",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            simpanData(id_pembeli, id_det_tiket, id_bank, id_konser, kursiBooking);
        }       
      });

      // Panel untuk tombol + label kembali
      JPanel bawahPanel = new JPanel(new BorderLayout());
      
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
              new beranda(id_pembeli).setVisible(true);
              dispose();
            
          }
      });

      bawahPanel.add(kembaliLbl, BorderLayout.SOUTH);

      formPanel.add(bawahPanel, BorderLayout.SOUTH);

      // Tambahkan ke panel utama window
      parentPanel.add(formPanel, BorderLayout.SOUTH);
  }


   private void getKategoriKonser() {
    listKategori.clear();
    listDeskripsi.clear();
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
            String iddetTiket = br.getString("id_det_tiket");
            String namaKategori = br.getString("kategori_konser");
            String deskripsidb = br.getString("deskripsi");
            listDeskripsi.add(deskripsidb);
            listKategori.put(namaKategori, iddetTiket);
        }
         br.close();
        bo.close();
        conn.close();
    }catch(Exception e){
              e.printStackTrace();
         }
}
   
   private void getBank(){
        listBank.clear();
    try {
       Connection conn = connection.getConnection();
       Statement stmt = conn.createStatement();
       String bank ="Select * from bank";
       PreparedStatement ba = conn.prepareStatement(bank);
       ResultSet rba = ba.executeQuery();
       
        while(rba.next()){
            String idBank = rba.getString("id_bank");
            String namaBank = rba.getString("nama_bank");     
            listBank.put(namaBank, idBank);
        }    
    }catch(Exception e){
              e.printStackTrace();
         }
       
   }
   
   public void kategoriDipilih(String selectedKategori){      
        if(listKategori.containsKey(selectedKategori)){
            selectedId = listKategori.get(selectedKategori);         
        }

       listKursi.clear();    
       listDeskripsi.clear();
       try {
        Connection conn = connection.getConnection();
        Statement stmt = conn.createStatement();
        
            String kategoriDipilih = "SELECT d.id_det_tiket, d.jumlah_tiket, d.harga_tiket, d.deskripsi, rp.kursi, " +
                                 "k.id_genre_konser " +
                                 "FROM konser k " +
                                 "JOIN detail_kategori_tiket d ON k.id_konser = d.id_konser " +
                                 "LEFT JOIN riwayat_pembeli rp ON d.id_det_tiket = rp.id_det_tiket " +
                                 "WHERE k.id_konser = ? AND d.id_det_tiket = ?";  
        
        PreparedStatement kp = conn.prepareStatement(kategoriDipilih);
        kp.setString(1, id_konser);
        kp.setString(2, selectedId);
       ResultSet br = kp.executeQuery();
       
       List<Integer> kursiTerpakai = new ArrayList<>();
       
       int jumlahTiket =0;
       while(br.next()){  
           jumlahTiket = br.getInt("jumlah_tiket");
           int kursi = br.getInt("kursi");
           String deskripsidb = br.getString("deskripsi");
           listDeskripsi.add(deskripsidb);
            if (!br.wasNull()) {
                kursiTerpakai.add(kursi);
            }
       }
       
       for (int i =1; i<=jumlahTiket; i++){
          int kursi = i;
           if(!kursiTerpakai.contains(kursi)){
               listKursi.add(kursi);
           }
       }
       
       String[] kursiArray = listKursi.stream()
                                       .map(Object::toString)
                                       .toArray(String[]::new);
        kursiKonserCombo.setModel(new DefaultComboBoxModel<>(kursiArray));
      
        
       } catch(Exception e){
           e.printStackTrace();
       }
       
   }

   public void bank(String selectedBank){
       if(listBank.containsKey(selectedBank)){
           String idBank = listBank.get(selectedBank);
           id_bank = idBank;
        }
   }
    

   public void simpanData(String idPembeli, String iddettiket, String idbank, String idkonser, int kursibooking){
       id_riwayat = UUID.randomUUID().toString();
        try {
           Connection conn = connection.getConnection();
           Statement stmt = conn.createStatement();
           String queryp = "INSERT INTO riwayat_pembeli " + 
                   "(id_riwayat, id_pembeli, id_det_tiket, id_konser, id_bank, kursi) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";
           PreparedStatement psq = conn.prepareStatement(queryp);
           
           psq.setString(1, id_riwayat);
           psq.setString(2, idPembeli);
           psq.setString(3, iddettiket);
           psq.setString(4, id_konser);
           psq.setString(5, idbank);
           psq.setInt(6, kursibooking);
          
          int rowsAffected = psq.executeUpdate();
           if(rowsAffected > 0){
                 JOptionPane.showMessageDialog(null, "Berhasil Membeli Tiket!");
                new riwayatPembeli(id_pembeli).setVisible(true);
                    dispose();
           } else {
               JOptionPane.showMessageDialog(null, "Gagal menyimpan Data", 
                       "Error", JOptionPane.ERROR_MESSAGE);
           }
           psq.close();
           conn.close();
       }catch(SQLException e){
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
