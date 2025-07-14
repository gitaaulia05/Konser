package com.mycompany.konser_oop2.admin;

import com.mycompany.konser_oop2.connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class riwayatadmin extends JFrame {

    private JPanel contentPanel;

    public riwayatadmin() {
        setTitle("Riwayat Admin");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        tampilkanData(); // Tampilkan data dari DB
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
                    COALESCE(SUM(rp.jumlah_beli), 0) AS terjual,
                    COALESCE(SUM(rp.jumlah_beli) * dkt.harga_tiket, 0) AS total_pendapatan
                FROM konser k
                JOIN genre_konser g ON k.id_genre_konser = g.id_genre
                JOIN detail_kategori_tiket dkt ON k.id_konser = dkt.id_konser
                JOIN kategori_tiket kt ON kt.id_kategori_tiket = dkt.id_kategori_tiket
                LEFT JOIN riwayat_pembeli rp ON rp.id_det_tiket = dkt.id_det_tiket
                GROUP BY k.id_konser, dkt.id_det_tiket
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
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

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                panel.setBackground(new Color(245, 245, 245));
                panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
                panel.setAlignmentX(Component.LEFT_ALIGNMENT);

                panel.add(new JLabel("Nama Konser: " + namaKonser));
                panel.add(new JLabel("Tanggal: " + tanggal));
                panel.add(new JLabel("Stadion: " + lokasi));
                panel.add(new JLabel("Genre: " + genre));
                panel.add(new JLabel("Kategori Tiket: " + kategori));
                panel.add(new JLabel("Jumlah Tiket: " + jumlah));
                panel.add(new JLabel("Terjual: " + terjual));
                panel.add(new JLabel("Harga Tiket: Rp " + String.format("%,.0f", harga)));
                panel.add(new JLabel("Total Pendapatan: Rp " + String.format("%,.0f", pendapatan)));

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
