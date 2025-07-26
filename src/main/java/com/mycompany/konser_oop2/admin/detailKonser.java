/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.konser_oop2.admin;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.konser_oop2.connection;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Stroke;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Gita Aulia Hafid
 */
public class detailKonser extends javax.swing.JFrame {

    private String id_admin = "cc4e86d9-a947-4a7b-84e3-f4636ee6929e";
    private String id_konser;
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    
    public detailKonser(String id_admin, String id_konser) {
        initUi();
        this.id_admin = id_admin;
        this.id_konser = id_konser;
        
         setTitle("Detail Konser");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // === Navbar ===
        navbarAdmin navbar = new navbarAdmin(id_admin);
        add(navbar, BorderLayout.NORTH);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.setBackground(Color.WHITE);
    
    
        // === Card Detail ===
        JPanel detailCard = createDetailCard();
        detailCard.setAlignmentX(LEFT_ALIGNMENT); // optional, biar rapi kiri
        wrapperPanel.add(detailCard);
        wrapperPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton btnTambahKategori = new JButton("Tambah Kategori Tiket");
        btnTambahKategori.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnTambahKategori.setBackground(new Color(220, 53, 69)); // warna merah mirip Bootstrap danger
        btnTambahKategori.setForeground(Color.WHITE);
        btnTambahKategori.setFocusPainted(false);
        btnTambahKategori.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnTambahKategori.addActionListener(e -> {
            new tambahKategori(id_admin, id_konser).setVisible(true);
            dispose();
        });
        
        wrapperPanel.add(Box.createRigidArea(new Dimension(0, 10))); // jarak atas
        wrapperPanel.add(btnTambahKategori);

        // Tambahkan panel daftar kategori
        JPanel kategoriPanel = createKategoriListPanel();
        kategoriPanel.setAlignmentX(LEFT_ALIGNMENT);
        wrapperPanel.add(kategoriPanel);

        scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // scroll lebih halus
        add(scrollPane, BorderLayout.CENTER);
       

        setVisible(true); 
    }
    
    private void initUi() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                String id_konserBook = rs.getString("id_konser");
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
    
     
   private JPanel createKategoriListPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));

    try (Connection conn = connection.getConnection()) {
        String sql = """
            SELECT d.id_det_tiket, k.kategori_konser, d.harga_tiket, d.jumlah_tiket
            FROM detail_kategori_tiket d
            JOIN kategori_tiket k ON d.id_kategori_tiket = k.id_kategori_tiket
            WHERE d.id_konser = ?
        """;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id_konser);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String namaKategori = rs.getString("kategori_konser");
            double harga = rs.getDouble("harga_tiket");
            int kuota = rs.getInt("jumlah_tiket");
            String id_kategori = rs.getString("id_det_tiket");

            JPanel itemPanel = new JPanel(new GridLayout(1, 3));
            itemPanel.setMaximumSize(new Dimension(700, 30));
            itemPanel.setBackground(new Color(245, 245, 245));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            JLabel labelInfo = new JLabel(namaKategori + " | Harga: Rp" + harga + " | Kuota: " + kuota);
            itemPanel.add(labelInfo, BorderLayout.WEST);
            
                        // Panel tombol
            JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            tombolPanel.setOpaque(false);

            JButton editBtn = new JButton("✏️");
            editBtn.setPreferredSize(new Dimension(45, 25));
            editBtn.setToolTipText("Edit kategori");
            editBtn.addActionListener(e -> {
                // Buka jendela/tab edit
                new editKategori(id_admin, id_kategori).setVisible(true);
                dispose();
            });

            JButton deleteBtn = new JButton("🗑️");
            deleteBtn.setPreferredSize(new Dimension(45, 25));
            deleteBtn.setToolTipText("Hapus kategori");
            deleteBtn.addActionListener(e -> {
                int konfirmasi = JOptionPane.showConfirmDialog(panel,
                        "Yakin ingin menghapus kategori ini?" , "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION);
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    hapusKategori(id_kategori);
                }
            });

            tombolPanel.add(editBtn);
            tombolPanel.add(deleteBtn);
            itemPanel.add(tombolPanel, BorderLayout.EAST);

            panel.add(itemPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    } catch (Exception e) {
        e.printStackTrace();
        panel.add(new JLabel("Gagal memuat data kategori."));
    }

    return panel;
}

   private void loadUI() {
    getContentPane().removeAll();

    JPanel wrapperPanel = new JPanel();
    wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
    wrapperPanel.setBackground(Color.WHITE);

    JPanel detailCard = createDetailCard();
    detailCard.setAlignmentX(LEFT_ALIGNMENT);
    wrapperPanel.add(detailCard);
    wrapperPanel.add(Box.createRigidArea(new Dimension(0, 20)));

    JButton btnTambahKategori = new JButton("Tambah Kategori Tiket");
    btnTambahKategori.setAlignmentX(Component.LEFT_ALIGNMENT);
    btnTambahKategori.setBackground(new Color(220, 53, 69));
    btnTambahKategori.setForeground(Color.WHITE);
    btnTambahKategori.setFocusPainted(false);
    btnTambahKategori.setCursor(new Cursor(Cursor.HAND_CURSOR));

    btnTambahKategori.addActionListener(e -> {
        new tambahKategori(id_admin, id_konser).setVisible(true);
        dispose();
    });

    wrapperPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    wrapperPanel.add(btnTambahKategori);

    JPanel kategoriPanel = createKategoriListPanel();
    kategoriPanel.setAlignmentX(LEFT_ALIGNMENT);
    wrapperPanel.add(kategoriPanel);

    scrollPane = new JScrollPane(wrapperPanel);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    add(scrollPane, BorderLayout.CENTER);

    revalidate();
    repaint();
}

   
   private void hapusKategori(String idKategori) {
    try (Connection conn = connection.getConnection()) {
       String checkQ = "Select id_det_tiket FROM riwayat_pembeli WHERE id_det_tiket = ?";
        PreparedStatement pc = conn.prepareStatement(checkQ);
        pc.setString(1, idKategori);
        ResultSet rc = pc.executeQuery();
        if(rc.next())
        {
            JOptionPane.showMessageDialog(null, "Kategori ini tidak bisa dihapus karena sudah ada pembeli");
            return;
        } 
        String deleteQuery = "DELETE FROM detail_kategori_tiket WHERE id_det_tiket = ?";
        PreparedStatement ps = conn.prepareStatement(deleteQuery);
        ps.setString(1, idKategori);
          ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Kategori berhasil dihapus.");

        
        // Refresh panel setelah hapus
        getContentPane().removeAll();
        revalidate();
        repaint();
        loadUI(); // buat metode ini untuk memanggil ulang semua panel

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menghapus kategori.");
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
