-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 24, 2025 at 12:27 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_penggilingan`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admins` bigint(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admins`, `nama`, `username`, `password`, `role`) VALUES
(3, 'Fajar', 'fajar', 'admin123', 'admin'),
(4, 'Ferdi', 'ferdi', 'admin123', 'Tester');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id_barang` int(11) NOT NULL,
  `id_supplier` int(11) DEFAULT NULL,
  `nama_bahan` varchar(100) NOT NULL,
  `jenis_bahan` varchar(100) NOT NULL,
  `stok` decimal(10,2) DEFAULT NULL,
  `hargapkg` decimal(15,2) NOT NULL,
  `keterangan` text DEFAULT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`id_barang`, `id_supplier`, `nama_bahan`, `jenis_bahan`, `stok`, `hargapkg`, `keterangan`, `tanggal`) VALUES
(6, 1, 'Mujair', 'Ikan', -14.00, 12000.00, 'Segar', '2025-06-04'),
(7, 2, 'Terigu', 'Tepung', 7.00, 10000.00, 'Baru', '2025-06-06'),
(8, 2, 'Garam', 'Bumbu', 4.91, 11000.00, 'Bagus', '2025-06-04'),
(9, 1, 'Gurame', 'Ikan', -22.00, 25000.00, 'Segar', '2025-06-10'),
(10, 3, 'Lele', 'Ikan', 2.57, 35000.00, 'Segars', '2025-06-11'),
(11, 2, 'tenggiri', 'Ikan', 3.00, 40000.00, 'frozen', '2025-06-10'),
(12, 1, 'tapioka', 'Tepung', -10.00, 20000.00, 'Baru', '2025-06-10'),
(13, 2, 'vanir', 'Tepung', 14.00, 15000.00, 'Baru', '2025-06-10'),
(14, 2, 'gandum', 'Tepung', 20.00, 25000.00, 'import', '2025-06-11'),
(15, 2, 'Sagu', 'Tepung', 20.00, 15000.00, 'baru', '2025-06-10'),
(16, 3, 'Sapu-sapu', 'Ikan', 15.00, 10000.00, 'segar', '2025-06-10'),
(17, 1, 'MSG Sasa', 'Bumbu', 10.00, 20000.00, 'Baru', '2025-06-10'),
(18, 6, 'Merica', 'Bumbu', 16.67, 15000.00, 'baru', '2025-06-10'),
(19, 1, 'Lada', 'Bumbu', 17.14, 14000.00, 'baru', '2025-06-10'),
(20, 3, 'Gula', 'Bumbu', 2.17, 23000.00, 'baru', '2025-06-10'),
(21, 3, 'Lele', 'Ikan', 2.00, 35000.00, 'dsa', '2025-06-22'),
(22, 3, 'Gurame', 'Ikan', 2.00, 25000.00, 'das', '2025-06-22');

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` int(11) NOT NULL,
  `nama_karyawan` varchar(100) NOT NULL,
  `jabatan` enum('Operator Penggilingan Molen','Operator Penggilingan Kasar','Operator Pemotongan Ikan','Admin') DEFAULT NULL,
  `gaji` decimal(15,2) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `keuangan`
--

CREATE TABLE `keuangan` (
  `id_keuangan` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `tipe` enum('Pemasukan Harian','Pengeluaran Harian') NOT NULL,
  `jumlah` decimal(15,2) NOT NULL,
  `id_pembelian` int(11) DEFAULT NULL,
  `id_penjualan` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `keuangan`
--

INSERT INTO `keuangan` (`id_keuangan`, `tanggal`, `tipe`, `jumlah`, `id_pembelian`, `id_penjualan`) VALUES
(3, '2025-06-22', 'Pengeluaran Harian', 50000.00, 22, NULL),
(8, '2025-06-03', 'Pemasukan Harian', 154000.00, NULL, 8),
(10, '2025-06-12', 'Pemasukan Harian', 154000.00, NULL, 10),
(11, '2025-06-19', 'Pemasukan Harian', 154000.00, NULL, 11),
(12, '2025-06-13', 'Pemasukan Harian', 154000.00, NULL, 12),
(13, '2025-06-13', 'Pemasukan Harian', 155000.00, NULL, 13);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(11) NOT NULL,
  `nama_pelanggan` varchar(100) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `perusahaan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama_pelanggan`, `no_hp`, `alamat`, `perusahaan`) VALUES
(2, 'kafe1', '12345', 'bogor', 'ceriamandiri'),
(3, 'Cafe Linglung', '0988089097', 'Condet', 'PT Indo sejahtera'),
(4, 'Cafe Cahaya Ilahi', '0989897878', 'Depok', 'PT Timur Jaya'),
(5, 'Cafe J.F.K', '097676456789', 'Jakarta', 'PT General Motors USD'),
(6, 'Cafe Churchil', '087546578765', 'Jakarta', 'PT Britihs Indie'),
(7, 'cafe Luftwaffe', '0898765789', 'Bali', 'PT Maserschmid');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL,
  `nama_supplier` varchar(100) NOT NULL,
  `alamat` text DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `perusahaan` varchar(100) DEFAULT NULL,
  `keterangan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `alamat`, `no_hp`, `perusahaan`, `keterangan`) VALUES
(1, 'Chelsea, Bumbu', 'Jl. Mawar No.1', '081234567890', 'PT Cucurak Fajar Utama', 'Blm Dibayar wak'),
(2, 'Bagus Bumbu', 'Jl knpo no 1', '09890231', 'Pt. Adi Jaya2', 'dsadasdas'),
(3, 'Fadel Ikan 2', 'Jl. Mawar No.1', '081234567890', 'PT Cucurak Fajar Utama', 'Blm Dibayar wak'),
(6, 'Aulif Tepung', 'PGC Cililitan', '8903183131', 'PT Gunung Mas', 'Lunas'),
(8, 'Dicky Ikan', 'Condet, Jakarta Timur', '09883713131', 'PT Timur Tengah Jaya', 'Lunas');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_pelanggan`
--

CREATE TABLE `transaksi_pelanggan` (
  `id_transaksi` int(11) NOT NULL,
  `id_pelanggan` int(11) NOT NULL,
  `tanggal_transaksi` date NOT NULL,
  `total_transaksi` decimal(15,2) NOT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admins`) USING BTREE,
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`),
  ADD KEY `id_supplier` (`id_supplier`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `keuangan`
--
ALTER TABLE `keuangan`
  ADD PRIMARY KEY (`id_keuangan`),
  ADD KEY `id_pembelians` (`id_pembelian`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Indexes for table `transaksi_pelanggan`
--
ALTER TABLE `transaksi_pelanggan`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_pelanggan` (`id_pelanggan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admins` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `karyawan`
--
ALTER TABLE `karyawan`
  MODIFY `id_karyawan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `keuangan`
--
ALTER TABLE `keuangan`
  MODIFY `id_keuangan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `id_pelanggan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `transaksi_pelanggan`
--
ALTER TABLE `transaksi_pelanggan`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `id_supplier` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `keuangan`
--
ALTER TABLE `keuangan`
  ADD CONSTRAINT `id_pembelians` FOREIGN KEY (`id_pembelian`) REFERENCES `pembelian` (`id_pembelian`);

--
-- Constraints for table `transaksi_pelanggan`
--
ALTER TABLE `transaksi_pelanggan`
  ADD CONSTRAINT `transaksi_pelanggan_ibfk_1` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
