
package com.mycompany.konser_oop2.pembeli;
import com.mycompany.konser_oop2.connection;
import com.mycompany.konser_oop2.pembeli.bookingKonser;
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
import com.mycompany.konser_oop2.connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.mycompany.konser_oop2.pembeli.loginPembeli; // Diimpor oleh ind


public class beranda extends javax.swing.JFrame {

    private JPanel panelGenre;
    private JTabbedPane tabs;
    
    private JPanel panelDetBooking;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private String id_pembeli;
    
    public beranda(String id_pembeli) {
        this.id_pembeli = id_pembeli;
        initComponents();
        initUI();
        loadGenreButtons();
    }
    
  public void initUI() {
    try {
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (Exception e) {
        e.printStackTrace();
    }

    setTitle("Aplikasi Konser");
    setSize(800, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null); // penting agar bisa atur posisi manual dengan setBounds

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);
    panelDetBooking = new JPanel();

    // ========= PANEL HEADER ATAS (tabs + tombol Login) =========
    JPanel headerPanel = new JPanel(null); // pakai null layout
    headerPanel.setBounds(0, 0, 800, 50); // lebar penuh, tinggi 50
    add(headerPanel); // tambahkan ke frame

    // ======= TABS (Rock / Pop) =======
    tabs = new JTabbedPane();
    tabs.setBounds(10, 10, 600, 30); // letak tab (kiri)
    tabs.addChangeListener(e -> {
        int selectedIndex = tabs.getSelectedIndex();
        if (selectedIndex != -1) {
            String genre = tabs.getTitleAt(selectedIndex);
            filterGenre(genre);
        }
    });
    headerPanel.add(tabs); // masukkan ke panel header

    // ======= TOMBOL LOGIN =======
    JButton btnLogin = new JButton("Login/Daftar");
    btnLogin.setBounds(660, 20, 120, 30); // letak tombol sejajar tab
    btnLogin.addActionListener(e -> {
        new loginPembeli().setVisible(true);
        dispose();
    });
    headerPanel.add(btnLogin);

    // ========= PANEL UTAMA =========
    mainPanel.setBounds(0, 50, 800, 410); // di bawah header
    add(mainPanel); // tambahkan ke frame

    mainPanel.add(tabs, "Beranda");
    mainPanel.add(panelDetBooking, "Detail Booking");
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
                   if(id_pembeli == null || id_pembeli.isEmpty()  ){
                        new loginPembeli().setVisible(true);
                        setVisible(false);
                   } else {
                   new bookingKonser(konserId, id_pembeli).setVisible(true);
                    dispose();
                   }
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
