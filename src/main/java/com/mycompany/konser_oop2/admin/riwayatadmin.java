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
    try (Connection conn = connection.getConnection()) {
        String sql = """
            SELECT 
                k.nama_konser,
                k.tanggal,
                k.lokasi,
                g.genre_konser,
                kt.kategori_konser,
                dkt.jumlah_tiket,
                dkt.harga_tiket,
                COUNT(rp.id_riwayat) AS terjual,
                COUNT(rp.id_riwayat) * dkt.harga_tiket AS total_pendapatan
            FROM konser k
            JOIN genre_konser g ON k.id_genre_konser = g.id_genre
            JOIN detail_kategori_tiket dkt ON k.id_konser = dkt.id_konser
            JOIN kategori_tiket kt ON kt.id_kategori_tiket = dkt.id_kategori_tiket
            LEFT JOIN riwayat_pembeli rp ON rp.id_det_tiket = dkt.id_det_tiket
            WHERE k.id_admin = ?
            GROUP BY 
                k.id_konser, 
                dkt.id_det_tiket,
                k.nama_konser,
                k.tanggal,
                k.lokasi,
                g.genre_konser,
                kt.kategori_konser,
                dkt.jumlah_tiket,
                dkt.harga_tiket;
        """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id_admin);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String namaKonser = rs.getString("nama_konser");
            String tanggal = rs.getString("tanggal");
            String lokasi = rs.getString("lokasi");
            String genre = rs.getString("genre_konser");
            String kategori = rs.getString("kategori_konser");
            int jumlah = rs.getInt("jumlah_tiket");
            int terjual = rs.getInt("terjual");
            double harga = rs.getDouble("harga_tiket");
            double pendapatan = rs.getDouble("total_pendapatan");

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            panel.setBackground(Color.WHITE);
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

            JPanel innerPanel = new JPanel(new GridLayout(0, 2, 10, 5));
            innerPanel.setOpaque(false); // biar ikut warna putih

            innerPanel.add(new JLabel("🎤 Nama Konser: " + namaKonser));
            innerPanel.add(new JLabel("📅 Tanggal: " + tanggal));
            innerPanel.add(new JLabel("📍 Lokasi: " + lokasi));
            innerPanel.add(new JLabel("🎶 Genre: " + genre));
            innerPanel.add(new JLabel("🎫 Kategori Tiket: " + kategori));
            innerPanel.add(new JLabel("📦 Jumlah Tiket: " + jumlah));
            innerPanel.add(new JLabel("✅ Terjual: " + terjual));
            innerPanel.add(new JLabel("💵 Harga: Rp " + String.format("%,.0f", harga)));
            innerPanel.add(new JLabel("📈 Total Pendapatan: Rp " + String.format("%,.0f", pendapatan)));

            panel.add(innerPanel, BorderLayout.CENTER);

            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            contentPanel.add(panel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
    }
}

}
