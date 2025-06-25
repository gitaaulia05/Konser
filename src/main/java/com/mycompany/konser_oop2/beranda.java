
package com.mycompany.konser_oop2;
import com.mycompany.konser_oop2.connection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.formdev.flatlaf.FlatLightLaf;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class beranda extends javax.swing.JFrame {

    private JPanel panelGenre;
    private JTabbedPane tabs;
    
    private JPanel panelDetBooking;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public beranda() {
        initComponents();
        initUI();
        loadGenreButtons();
    }
    
    public void initUI(){
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        setTitle("Aplikasi Konser");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        tabs = new JTabbedPane();
        
        tabs.addChangeListener(e -> {
           int selectedIndex = tabs.getSelectedIndex();
           
           if(selectedIndex != -1){
               String genre = tabs.getTitleAt(selectedIndex);
               filterGenre(genre);
           }
        });
        panelGenre = new JPanel();
        panelGenre.setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
        
        panelDetBooking = new JPanel();
        
        mainPanel.add(tabs, "Beranda");
        mainPanel.add(panelDetBooking, "Detail Booking");
        
       getContentPane().setLayout(new BorderLayout());
       getContentPane().add(mainPanel, BorderLayout.CENTER);
    }
    
    public void loadGenreButtons(){
   
        try{
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SeLECT genre_konser from genre_konser");
            tabs.removeAll();
            
            while(rs.next()) {
                String genre = rs.getString("genre_konser");
                JPanel panelJenis = new JPanel();
                panelJenis.setLayout(new BoxLayout(panelJenis, BoxLayout.Y_AXIS));
                    panelJenis.setPreferredSize(new Dimension(700, 500));
                    
                JScrollPane scroll = new JScrollPane(panelJenis);
                tabs.addTab(genre, scroll);
            }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, "gagal load genre");
        } 
    }
    
      public void filterGenre(String genre){
        try {
         
            JScrollPane selectedScroll = (JScrollPane) tabs.getSelectedComponent();
            JPanel currentPanel = (JPanel) selectedScroll.getViewport().getView();
            currentPanel.removeAll();
            
            currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.Y_AXIS));
            
            Connection conn = connection.getConnection();
            Statement stmt = conn.createStatement();
            String query = "Select k.nama_konser, k.lokasi, k.jam, k.tanggal, k.id_konser " + 
                    "from konser k " + "JOIN genre_konser g on k.id_genre_konser = g.id_genre "
                    + "where g.genre_konser = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, genre);
            ResultSet rs = ps.executeQuery();
           while (rs.next()) {
                String id_konser = rs.getString("id_konser");
                String nama_konser = rs.getString("nama_konser");
                String alamat = rs.getString("lokasi");
                String jam = rs.getString("jam");
                String tanggal = rs.getString("tanggal");
                
                
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(245, 245, 245));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));

   
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);

            JLabel lblNama = new JLabel(nama_konser);
            lblNama.setFont(new Font("SansSerif", Font.BOLD, 12));

            JLabel lblLokasi = new JLabel(alamat);
            lblLokasi.setFont(new Font("SansSerif", Font.PLAIN, 11));

            leftPanel.add(lblNama);
            leftPanel.add(lblLokasi);

            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            rightPanel.setOpaque(false);
            rightPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JLabel lblJam = new JLabel(jam);
            lblJam.setFont(new Font("SansSerif", Font.PLAIN, 11));
            lblJam.setHorizontalAlignment(SwingConstants.RIGHT);

            JLabel lblTanggal = new JLabel(tanggal);
            lblTanggal.setFont(new Font("SansSerif", Font.PLAIN, 11));
            lblTanggal.setHorizontalAlignment(SwingConstants.RIGHT);
            
            JButton btnRsvp = new JButton("Booking Tiket");
            btnRsvp.setFont(new Font("SansSerif", Font.PLAIN, 11));
            btnRsvp.setHorizontalAlignment(SwingConstants.RIGHT);
            btnRsvp.putClientProperty("id", id_konser);
            
            btnRsvp.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent e){
                   JButton clickedButton = (JButton) e.getSource();
                   String konserId = (String) clickedButton.getClientProperty("id");
                   detailBooking(konserId);
               } 
            });

            rightPanel.add(lblJam);
            rightPanel.add(lblTanggal);
            rightPanel.add(btnRsvp);

        card.add(leftPanel, BorderLayout.WEST);
        card.add(rightPanel, BorderLayout.EAST);

        currentPanel.add(card);
        currentPanel.add(Box.createVerticalStrut(15)); 
    }
        } catch(Exception e){
              e.printStackTrace();
        }
    }
      
     public void detailBooking(String konserId){
         
         try{
                 panelDetBooking.removeAll();
        panelDetBooking.setLayout(new BorderLayout());

        // Panel Card (mirip col-7 Bootstrap)
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createTitledBorder("Form Pemesanan Tiket"));
        cardPanel.setPreferredSize(new Dimension(500, 300));

        // Komponen input
        JTextField txtNama = new JTextField();
        JComboBox<String> cmbKategori = new JComboBox<>();
        JComboBox<String> cmbKursi = new JComboBox<>();
        JComboBox<String> cmbPembayaran = new JComboBox<>(new String[] {
            "Transfer Bank", "E-Wallet", "Kartu Kredit"
        });
        JLabel lblHarga = new JLabel("Harga: -");

        // Map penyimpanan data dari DB
        Map<String, Integer> hargaMap = new HashMap<>();
        Map<String, Integer> kursiMap = new HashMap<>();
        Set<Integer> kursiTerpakai = new HashSet<>();
        int jumlahTiket = 0;
        
        // Query kategori tiket dan jumlah kursi
        Connection conn = connection.getConnection();
        String query ="Select k.nama_konser, k.lokasi, k.jam, k.tanggal, k.id_konser, kt.kategori_konser, d.harga_tiket, d.jumlah_tiket, p.kursi " + 
                    "from konser k " + "JOIN detail_kategori_tiket d on k.id_konser = d.id_konser " + "jOIN kategori_tiket kt on d.id_kategori_tiket = kt.id_kategori_tiket "
                    +"JOIN riwayat_pembeli on rp d.id_det_tiket = rp.id_det_tiket " + "where d.id_konser = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, konserId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String kategori = rs.getString("kategori_konser");
            int harga = rs.getInt("harga_tiket");
            int jumlahKursi = rs.getInt("jumlah_tiket");
            int kursi = rs.getInt("kursi");
            
            if(kursi !=0){
                kursiTerpakai.add(kursi);
            }

            hargaMap.put(kategori, harga);
            kursiMap.put(kategori, jumlahKursi);

            cmbKategori.addItem(kategori);
        }

        // Listener kategori: update harga dan kursi
        cmbKategori.addActionListener(e -> {
            String selected = (String) cmbKategori.getSelectedItem();
            Integer harga = hargaMap.get(selected);
            Integer maxKursi = kursiMap.get(selected);

            lblHarga.setText(harga != null ? "Harga: Rp " + harga : "Harga: -");

            cmbKursi.removeAllItems();
            if (maxKursi != null) {
                for (int i = 1; i <= maxKursi; i++) {
                    if(!)
                    cmbKursi.addItem(String.valueOf(i));
                }
            }
        });

        // Label dan komponen ditambahkan ke panel
        cardPanel.add(new JLabel("Nama Pemesan:"));
        txtNama.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cardPanel.add(txtNama);

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(new JLabel("Kategori Tiket:"));
        cardPanel.add(cmbKategori);

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(new JLabel("Jumlah Kursi:"));
        cardPanel.add(cmbKursi);

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(new JLabel("Metode Pembayaran:"));
        cardPanel.add(cmbPembayaran);

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(lblHarga);

        // Tombol simpan
        JButton btnSimpan = new JButton("Simpan Pembayaran");
        btnSimpan.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(btnSimpan);

        // Tombol kembali
        JButton btnBack = new JButton("Kembali");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Beranda"));

        // Tambahkan ke panel utama
        panelDetBooking.add(cardPanel, BorderLayout.CENTER);
        panelDetBooking.add(btnBack, BorderLayout.SOUTH);

        panelDetBooking.revalidate();
        panelDetBooking.repaint();
        cardLayout.show(mainPanel, "Detail Booking");

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
        setTitle("jPanel1");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(beranda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new beranda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
