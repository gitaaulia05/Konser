-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2025 at 06:35 AM
-- Server version: 10.4.32-MariaDB-log
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `konser`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrator`
--

CREATE TABLE `administrator` (
  `id_admin` varchar(150) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `administrator`
--

INSERT INTO `administrator` (`id_admin`, `nama`, `email`, `password`, `created_at`) VALUES
('cc4e86d9-a947-4a7b-84e3-f4636ee6929e', 'bebe', 'bebe@gmail.com', 'hahaha', '2025-06-22 14:37:27');

-- --------------------------------------------------------

--
-- Table structure for table `detail_kategori_tiket`
--

CREATE TABLE `detail_kategori_tiket` (
  `id_det_tiket` varchar(150) NOT NULL,
  `id_kategori_tiket` varchar(150) NOT NULL,
  `id_konser` varchar(150) NOT NULL,
  `jumlah_tiket` int(11) NOT NULL,
  `harga_tiket` double NOT NULL,
  `deksripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `detail_kategori_tiket`
--

INSERT INTO `detail_kategori_tiket` (`id_det_tiket`, `id_kategori_tiket`, `id_konser`, `jumlah_tiket`, `harga_tiket`, `deksripsi`) VALUES
('2eb0b02c-5d1b-4832-b3ec-9c3806568633', 'b1c5713a-a3f4-44bf-9297-fed377c12d93', '5773476a-064f-4bb3-b518-5747e6917dc0', 10, 2000, 'dhjahdj'),
('2eb0b02c-5d1b-4832-b3ec-9c380656863u', 'b1c5713a-a3f4-44bf-9297-fed377c12d98', '5773476a-064f-4bb3-b518-5747e6917dc0', 20, 1000, 'ahahha');

-- --------------------------------------------------------

--
-- Table structure for table `genre_konser`
--

CREATE TABLE `genre_konser` (
  `id_genre` varchar(150) NOT NULL,
  `genre_konser` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `genre_konser`
--

INSERT INTO `genre_konser` (`id_genre`, `genre_konser`) VALUES
('38653931-ed47-47e1-ad83-a2f2bd2ef3a2', 'Rock'),
('487015f6-71fe-4c98-acdf-1f47af3c1661', 'Pop');

-- --------------------------------------------------------

--
-- Table structure for table `kategori_tiket`
--

CREATE TABLE `kategori_tiket` (
  `id_kategori_tiket` varchar(150) NOT NULL,
  `kategori_konser` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategori_tiket`
--

INSERT INTO `kategori_tiket` (`id_kategori_tiket`, `kategori_konser`, `created_at`) VALUES
('b1c5713a-a3f4-44bf-9297-fed377c12d93', 'VIP', '2025-06-22 14:38:13'),
('b1c5713a-a3f4-44bf-9297-fed377c12d98', 'Regular', '2025-06-24 16:02:34');

-- --------------------------------------------------------

--
-- Table structure for table `konser`
--

CREATE TABLE `konser` (
  `id_konser` varchar(150) NOT NULL,
  `id_admin` varchar(150) NOT NULL,
  `id_genre_konser` varchar(150) NOT NULL,
  `nama_konser` varchar(100) NOT NULL,
  `nama_band` varchar(50) NOT NULL,
  `lokasi` varchar(150) NOT NULL,
  `tanggal` date DEFAULT NULL,
  `jam` time NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konser`
--

INSERT INTO `konser` (`id_konser`, `id_admin`, `id_genre_konser`, `nama_konser`, `nama_band`, `lokasi`, `tanggal`, `jam`, `created_at`) VALUES
('5773476a-064f-4bb3-b518-5747e6917dc0', 'cc4e86d9-a947-4a7b-84e3-f4636ee6929e', '38653931-ed47-47e1-ad83-a2f2bd2ef3a2', 'latina', 'One Ok Rock', 'Jakarta', '2025-06-22', '00:00:12', '2025-06-22 14:55:29');

-- --------------------------------------------------------

--
-- Table structure for table `pembeli`
--

CREATE TABLE `pembeli` (
  `id_pembeli` varchar(150) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pembeli`
--

INSERT INTO `pembeli` (`id_pembeli`, `nama`, `email`, `password`, `created_at`) VALUES
('2007cf89-dd68-450c-9c32-268ae43764fd', 'lala', 'lala@gmail.com', 'hahaha', '2025-06-22 14:36:19');

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_admin`
--

CREATE TABLE `riwayat_admin` (
  `id_riwayat_admin` varchar(150) NOT NULL,
  `id_admin` varchar(150) NOT NULL,
  `id_konser` varchar(150) NOT NULL,
  `jumlah_kursi_terjual` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_pembeli`
--

CREATE TABLE `riwayat_pembeli` (
  `id_riwayat` varchar(150) NOT NULL,
  `id_pembeli` varchar(150) NOT NULL,
  `id_det_tiket` varchar(150) NOT NULL,
  `id_konser` varchar(150) NOT NULL,
  `kursi` char(4) NOT NULL,
  `metode_pembayaran` varchar(100) NOT NULL,
  `tanggal_transaksi` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `riwayat_pembeli`
--

INSERT INTO `riwayat_pembeli` (`id_riwayat`, `id_pembeli`, `id_det_tiket`, `id_konser`, `kursi`, `metode_pembayaran`, `tanggal_transaksi`) VALUES
('2eb0b02c-5d1b-4832-b3ec-9c3806568787', '2007cf89-dd68-450c-9c32-268ae43764fd', '2eb0b02c-5d1b-4832-b3ec-9c3806568633', '5773476a-064f-4bb3-b518-5747e6917dc0', '4', 'haha', '2025-06-24 16:08:21');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrator`
--
ALTER TABLE `administrator`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indexes for table `detail_kategori_tiket`
--
ALTER TABLE `detail_kategori_tiket`
  ADD PRIMARY KEY (`id_det_tiket`),
  ADD KEY `id_konser` (`id_konser`),
  ADD KEY `id_kategori_tiket` (`id_kategori_tiket`);

--
-- Indexes for table `genre_konser`
--
ALTER TABLE `genre_konser`
  ADD PRIMARY KEY (`id_genre`);

--
-- Indexes for table `kategori_tiket`
--
ALTER TABLE `kategori_tiket`
  ADD PRIMARY KEY (`id_kategori_tiket`);

--
-- Indexes for table `konser`
--
ALTER TABLE `konser`
  ADD PRIMARY KEY (`id_konser`),
  ADD KEY `id_admin` (`id_admin`),
  ADD KEY `id_genre_konser` (`id_genre_konser`);

--
-- Indexes for table `pembeli`
--
ALTER TABLE `pembeli`
  ADD PRIMARY KEY (`id_pembeli`);

--
-- Indexes for table `riwayat_admin`
--
ALTER TABLE `riwayat_admin`
  ADD PRIMARY KEY (`id_riwayat_admin`),
  ADD KEY `id_admin` (`id_admin`),
  ADD KEY `id_konser` (`id_konser`);

--
-- Indexes for table `riwayat_pembeli`
--
ALTER TABLE `riwayat_pembeli`
  ADD PRIMARY KEY (`id_riwayat`),
  ADD KEY `id_pembeli` (`id_pembeli`),
  ADD KEY `id_konser` (`id_konser`),
  ADD KEY `id_det_tiket` (`id_det_tiket`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_kategori_tiket`
--
ALTER TABLE `detail_kategori_tiket`
  ADD CONSTRAINT `detail_kategori_tiket_ibfk_1` FOREIGN KEY (`id_konser`) REFERENCES `konser` (`id_konser`),
  ADD CONSTRAINT `detail_kategori_tiket_ibfk_2` FOREIGN KEY (`id_kategori_tiket`) REFERENCES `kategori_tiket` (`id_kategori_tiket`);

--
-- Constraints for table `konser`
--
ALTER TABLE `konser`
  ADD CONSTRAINT `konser_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `administrator` (`id_admin`),
  ADD CONSTRAINT `konser_ibfk_2` FOREIGN KEY (`id_genre_konser`) REFERENCES `genre_konser` (`id_genre`);

--
-- Constraints for table `riwayat_admin`
--
ALTER TABLE `riwayat_admin`
  ADD CONSTRAINT `riwayat_admin_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `administrator` (`id_admin`),
  ADD CONSTRAINT `riwayat_admin_ibfk_2` FOREIGN KEY (`id_konser`) REFERENCES `konser` (`id_konser`);

--
-- Constraints for table `riwayat_pembeli`
--
ALTER TABLE `riwayat_pembeli`
  ADD CONSTRAINT `riwayat_pembeli_ibfk_1` FOREIGN KEY (`id_pembeli`) REFERENCES `pembeli` (`id_pembeli`),
  ADD CONSTRAINT `riwayat_pembeli_ibfk_2` FOREIGN KEY (`id_konser`) REFERENCES `konser` (`id_konser`),
  ADD CONSTRAINT `riwayat_pembeli_ibfk_3` FOREIGN KEY (`id_det_tiket`) REFERENCES `detail_kategori_tiket` (`id_det_tiket`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
