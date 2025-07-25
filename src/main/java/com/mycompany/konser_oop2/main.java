/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.konser_oop2;

import com.mycompany.konser_oop2.pembeli.beranda;
import utils.ThemeConfig;

/**
 *
 * @author Gita Aulia Hafid
 */
public class main {
     public static void main(String[] args) {
        ThemeConfig.applyTheme(); // pasang tema dulu

        String id_pembeli = null;
        new beranda(id_pembeli).setVisible(true); // ganti sesuai nama form kamu
    }
}
