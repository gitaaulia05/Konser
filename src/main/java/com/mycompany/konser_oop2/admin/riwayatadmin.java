package com.mycompany.konser_oop2.admin;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.konser_oop2.connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class riwayatAdmin extends JFrame {

    private JPanel contentPanel;
    private String id_admin;
    private JScrollPane scrollPane;
    
    public riwayatAdmin(String id_admin) {
        this.id_admin = id_admin; 
        initUi();
        tampilkanData();
       
    }
    
    private void initUi(){
         try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setTitle("Riwayat Admin");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // === Navbar ===
        navbarAdmin navbar = new navbarAdmin(id_admin);
        add(navbar, BorderLayout.NORTH);

         // Panel konten utama
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);

        setSize(800, 600);
        setLocationRelativeTo(null);

       
        tampilkanData(); // Tampilkan data dari DB
        setVisible(true); 
    }

private void tampilkanData() {
    contentPanel.removeAll(); // Bersihkan konten sebelumnya
    
    try (Connection conn = connection.getConnection()) {
        // Query untuk mendapatkan semua konser admin
        String sqlKonser = "SELECT k.id_konser, k.nama_konser, k.nama_band, k.tanggal, k.lokasi, g.genre_konser " +
                          "FROM konser k " +
                          "JOIN genre_konser g ON k.id_genre_konser = g.id_genre " +
                          "WHERE k.id_admin = ? " +
                          "ORDER BY k.tanggal DESC";
        
        PreparedStatement stmtKonser = conn.prepareStatement(sqlKonser);
        stmtKonser.setString(1, id_admin);
        ResultSet rsKonser = stmtKonser.executeQuery();

        while (rsKonser.next()) {
            String idKonser = rsKonser.getString("id_konser");
            String namaKonser = rsKonser.getString("nama_konser");
            String namaBand = rsKonser.getString("nama_band");
            String tanggal = rsKonser.getString("tanggal");
            String lokasi = rsKonser.getString("lokasi");
            String genre = rsKonser.getString("genre_konser");

            // Panel utama untuk setiap konser
            JPanel panelKonser = new JPanel(new BorderLayout());
            panelKonser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            panelKonser.setBackground(Color.WHITE);
            panelKonser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

            // Header konser
            JPanel headerPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            headerPanel.setOpaque(false);
            
            headerPanel.add(new JLabel("🎤 " + namaKonser, JLabel.LEFT) {{
                setFont(getFont().deriveFont(Font.BOLD, 16f));
            }});
            headerPanel.add(new JLabel("🎸 " + namaBand));
            headerPanel.add(new JLabel("📅 " + tanggal + "  |  📍 " + lokasi));
            headerPanel.add(new JLabel("🎶 Genre: " + genre));
            
            panelKonser.add(headerPanel, BorderLayout.NORTH);

            // Panel untuk kategori tiket
            JPanel kategoriPanel = new JPanel();
            kategoriPanel.setLayout(new BoxLayout(kategoriPanel, BoxLayout.Y_AXIS));
            kategoriPanel.setBorder(BorderFactory.createTitledBorder("Kategori Tiket"));
            kategoriPanel.setOpaque(false);

            // Query untuk mendapatkan kategori tiket per konser
            String sqlKategori = "SELECT kt.kategori_konser, dkt.jumlah_tiket, dkt.harga_tiket, " +
                                "COUNT(rp.id_riwayat) AS terjual, " +
                                "COUNT(rp.id_riwayat) * dkt.harga_tiket AS total_pendapatan " +
                                "FROM detail_kategori_tiket dkt " +
                                "JOIN kategori_tiket kt ON dkt.id_kategori_tiket = kt.id_kategori_tiket " +
                                "LEFT JOIN riwayat_pembeli rp ON rp.id_det_tiket = dkt.id_det_tiket " +
                                "WHERE dkt.id_konser = ? " +
                                "GROUP BY dkt.id_det_tiket, kt.kategori_konser, dkt.jumlah_tiket, dkt.harga_tiket";
            
            PreparedStatement stmtKategori = conn.prepareStatement(sqlKategori);
            stmtKategori.setString(1, idKonser);
            ResultSet rsKategori = stmtKategori.executeQuery();

            while (rsKategori.next()) {
                String kategori = rsKategori.getString("kategori_konser");
                int jumlah = rsKategori.getInt("jumlah_tiket");
                int terjual = rsKategori.getInt("terjual");
                double harga = rsKategori.getDouble("harga_tiket");
                double pendapatan = rsKategori.getDouble("total_pendapatan");

                JPanel panelKategori = new JPanel(new GridLayout(0, 2, 10, 5));
                panelKategori.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                panelKategori.setBackground(new Color(240, 240, 240));
                panelKategori.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                panelKategori.add(new JLabel("🎫 " + kategori));
                panelKategori.add(new JLabel(""));
                panelKategori.add(new JLabel("📦 Total: " + jumlah));
                panelKategori.add(new JLabel("✅ Terjual: " + terjual));
                panelKategori.add(new JLabel("💵 Harga: Rp " + String.format("%,.0f", harga)));
                panelKategori.add(new JLabel("📈 Pendapatan: Rp " + String.format("%,.0f", pendapatan)));

                kategoriPanel.add(panelKategori);
                kategoriPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }

            panelKonser.add(kategoriPanel, BorderLayout.CENTER);
            contentPanel.add(panelKonser);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        if (contentPanel.getComponentCount() == 0) {
            contentPanel.add(new JLabel("Tidak ada data konser", JLabel.CENTER));
        }

        contentPanel.revalidate();
        contentPanel.repaint();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, 
            "Gagal mengambil data: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

}
