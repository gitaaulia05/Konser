package com.mycompany.konser_oop2.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class listKonser extends JFrame {

    private JLabel idLbl;
    private JButton tambahBtn;

    public listKonser(String id_admin) {
        initComponents();
    }

    private void initComponents() {
        idLbl = new JLabel();
        tambahBtn = new JButton();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Daftar Konser");
        setSize(600, 300);
        setLocationRelativeTo(null);

        idLbl.setText("Admin");

        tambahBtn.setText("Tambahkan Konser");
        tambahBtn.addActionListener(e -> {
            new tambahKonser().setVisible(true); // ← buka form
            dispose();
        });

        JPanel panelKonser = new JPanel(new BorderLayout());
        panelKonser.setPreferredSize(new Dimension(500, 100));
        panelKonser.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        panelKonser.setBackground(Color.WHITE);

        JPanel panelKiri = new JPanel();
        panelKiri.setPreferredSize(new Dimension(60, 60));
        panelKiri.setBackground(Color.WHITE);
        panelKiri.add(new JLabel("🎤"));

        JPanel panelTengah = new JPanel();
        panelTengah.setLayout(new BoxLayout(panelTengah, BoxLayout.Y_AXIS));
        panelTengah.setBackground(Color.WHITE);
        panelTengah.add(new JLabel("One Ok Rock"));
        panelTengah.add(new JLabel("Stadion Utama GBK, Jakarta"));

        JPanel panelKanan = new JPanel();
        panelKanan.setLayout(new BoxLayout(panelKanan, BoxLayout.Y_AXIS));
        panelKanan.setBackground(Color.WHITE);

        JLabel labelTanggal = new JLabel("Juni 19, 2025");
        JLabel labelJam = new JLabel("4:00 PM WIB");
        JButton kelolaBtn = new JButton("Kelola");
       kelolaBtn.addActionListener((ActionEvent e) -> {
    new riwayatadmin().setVisible(true); // masuk ke riwayatadmin
    dispose(); // jika ingin menutup halaman list konser
       });


        panelKanan.add(labelTanggal);
        panelKanan.add(labelJam);
        panelKanan.add(kelolaBtn);

        panelKonser.add(panelKiri, BorderLayout.WEST);
        panelKonser.add(panelTengah, BorderLayout.CENTER);
        panelKonser.add(panelKanan, BorderLayout.EAST);

        JPanel panelUtama = new JPanel();
        panelUtama.setLayout(new BoxLayout(panelUtama, BoxLayout.Y_AXIS));
        panelUtama.setBackground(Color.LIGHT_GRAY);
        panelUtama.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelUtama.add(idLbl);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 10)));
        panelUtama.add(tambahBtn);
        panelUtama.add(Box.createRigidArea(new Dimension(0, 20)));
        panelUtama.add(panelKonser);

        getContentPane().add(panelUtama);
    }
}
