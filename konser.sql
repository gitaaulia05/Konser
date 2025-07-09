/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 8.0.30 : Database - konser
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`konser` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `konser`;

/*Table structure for table `administrator` */

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `id_admin` varchar(150) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `administrator` */

insert  into `administrator`(`id_admin`,`nama`,`email`,`password`,`created_at`) values 
('cc4e86d9-a947-4a7b-84e3-f4636ee6929e','bebe','bebe@gmail.com','hahaha','2025-06-22 21:37:27');

/*Table structure for table `detail_kategori_tiket` */

DROP TABLE IF EXISTS `detail_kategori_tiket`;

CREATE TABLE `detail_kategori_tiket` (
  `id_det_tiket` varchar(150) NOT NULL,
  `id_kategori_tiket` varchar(150) NOT NULL,
  `id_konser` varchar(150) NOT NULL,
  `jumlah_tiket` int NOT NULL,
  `harga_tiket` double NOT NULL,
  `deksripsi` text NOT NULL,
  PRIMARY KEY (`id_det_tiket`),
  KEY `id_konser` (`id_konser`),
  KEY `id_kategori_tiket` (`id_kategori_tiket`),
  CONSTRAINT `detail_kategori_tiket_ibfk_1` FOREIGN KEY (`id_konser`) REFERENCES `konser` (`id_konser`),
  CONSTRAINT `detail_kategori_tiket_ibfk_2` FOREIGN KEY (`id_kategori_tiket`) REFERENCES `kategori_tiket` (`id_kategori_tiket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `detail_kategori_tiket` */

insert  into `detail_kategori_tiket`(`id_det_tiket`,`id_kategori_tiket`,`id_konser`,`jumlah_tiket`,`harga_tiket`,`deksripsi`) values 
('2eb0b02c-5d1b-4832-b3ec-9c3806568633','b1c5713a-a3f4-44bf-9297-fed377c12d93','5773476a-064f-4bb3-b518-5747e6917dc0',10,2000,'dhjahdj'),
('2eb0b02c-5d1b-4832-b3ec-9c380656863u','b1c5713a-a3f4-44bf-9297-fed377c12d98','5773476a-064f-4bb3-b518-5747e6917dc0',20,1000,'ahahha');

/*Table structure for table `genre_konser` */

DROP TABLE IF EXISTS `genre_konser`;

CREATE TABLE `genre_konser` (
  `id_genre` varchar(150) NOT NULL,
  `genre_konser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id_genre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `genre_konser` */

insert  into `genre_konser`(`id_genre`,`genre_konser`) values 
('38653931-ed47-47e1-ad83-a2f2bd2ef3a2','Rock'),
('487015f6-71fe-4c98-acdf-1f47af3c1661','Pop');

/*Table structure for table `kategori_tiket` */

DROP TABLE IF EXISTS `kategori_tiket`;

CREATE TABLE `kategori_tiket` (
  `id_kategori_tiket` varchar(150) NOT NULL,
  `kategori_konser` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id_kategori_tiket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `kategori_tiket` */

insert  into `kategori_tiket`(`id_kategori_tiket`,`kategori_konser`,`created_at`) values 
('b1c5713a-a3f4-44bf-9297-fed377c12d93','VIP','2025-06-22 21:38:13'),
('b1c5713a-a3f4-44bf-9297-fed377c12d98','Regular','2025-06-24 23:02:34');

/*Table structure for table `konser` */

DROP TABLE IF EXISTS `konser`;

CREATE TABLE `konser` (
  `id_konser` varchar(150) NOT NULL,
  `id_admin` varchar(150) NOT NULL,
  `id_genre_konser` varchar(150) NOT NULL,
  `nama_konser` varchar(100) NOT NULL,
  `nama_band` varchar(50) NOT NULL,
  `lokasi` varchar(150) NOT NULL,
  `tanggal` date DEFAULT NULL,
  `jam` time NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id_konser`),
  KEY `id_admin` (`id_admin`),
  KEY `id_genre_konser` (`id_genre_konser`),
  CONSTRAINT `konser_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `administrator` (`id_admin`),
  CONSTRAINT `konser_ibfk_2` FOREIGN KEY (`id_genre_konser`) REFERENCES `genre_konser` (`id_genre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `konser` */

insert  into `konser`(`id_konser`,`id_admin`,`id_genre_konser`,`nama_konser`,`nama_band`,`lokasi`,`tanggal`,`jam`,`created_at`) values 
('5773476a-064f-4bb3-b518-5747e6917dc0','cc4e86d9-a947-4a7b-84e3-f4636ee6929e','38653931-ed47-47e1-ad83-a2f2bd2ef3a2','latina','One Ok Rock','Jakarta','2025-06-22','00:00:12','2025-06-22 21:55:29');

/*Table structure for table `pembeli` */

DROP TABLE IF EXISTS `pembeli`;

CREATE TABLE `pembeli` (
  `id_pembeli` varchar(150) NOT NULL,
  `nama` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id_pembeli`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pembeli` */

insert  into `pembeli`(`id_pembeli`,`nama`,`email`,`password`,`created_at`) values 
('2007cf89-dd68-450c-9c32-268ae43764fd','lala','lala@gmail.com','hahaha','2025-06-22 21:36:19');

/*Table structure for table `riwayat_admin` */

DROP TABLE IF EXISTS `riwayat_admin`;

CREATE TABLE `riwayat_admin` (
  `id_riwayat_admin` varchar(150) NOT NULL,
  `id_admin` varchar(150) NOT NULL,
  `id_konser` varchar(150) NOT NULL,
  `jumlah_kursi_terjual` int NOT NULL,
  PRIMARY KEY (`id_riwayat_admin`),
  KEY `id_admin` (`id_admin`),
  KEY `id_konser` (`id_konser`),
  CONSTRAINT `riwayat_admin_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `administrator` (`id_admin`),
  CONSTRAINT `riwayat_admin_ibfk_2` FOREIGN KEY (`id_konser`) REFERENCES `konser` (`id_konser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `riwayat_admin` */

/*Table structure for table `riwayat_pembeli` */

DROP TABLE IF EXISTS `riwayat_pembeli`;

CREATE TABLE `riwayat_pembeli` (
  `id_riwayat` varchar(150) NOT NULL,
  `id_pembeli` varchar(150) NOT NULL,
  `id_det_tiket` varchar(150) NOT NULL,
  `id_konser` varchar(150) NOT NULL,
  `kursi` char(4) NOT NULL,
  `metode_pembayaran` varchar(100) NOT NULL,
  `tanggal_transaksi` timestamp NOT NULL,
  PRIMARY KEY (`id_riwayat`),
  KEY `id_pembeli` (`id_pembeli`),
  KEY `id_konser` (`id_konser`),
  KEY `id_det_tiket` (`id_det_tiket`),
  CONSTRAINT `riwayat_pembeli_ibfk_1` FOREIGN KEY (`id_pembeli`) REFERENCES `pembeli` (`id_pembeli`),
  CONSTRAINT `riwayat_pembeli_ibfk_2` FOREIGN KEY (`id_konser`) REFERENCES `konser` (`id_konser`),
  CONSTRAINT `riwayat_pembeli_ibfk_3` FOREIGN KEY (`id_det_tiket`) REFERENCES `detail_kategori_tiket` (`id_det_tiket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `riwayat_pembeli` */

insert  into `riwayat_pembeli`(`id_riwayat`,`id_pembeli`,`id_det_tiket`,`id_konser`,`kursi`,`metode_pembayaran`,`tanggal_transaksi`) values 
('2eb0b02c-5d1b-4832-b3ec-9c3806568787','2007cf89-dd68-450c-9c32-268ae43764fd','2eb0b02c-5d1b-4832-b3ec-9c3806568633','5773476a-064f-4bb3-b518-5747e6917dc0','4','haha','2025-06-24 23:08:21');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
