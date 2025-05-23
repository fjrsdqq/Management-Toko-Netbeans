-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 23 Bulan Mei 2025 pada 08.15
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

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
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id_admins` bigint(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id_admins`, `nama`, `username`, `password`, `role`) VALUES
(3, 'Fajar', 'fajar', 'admin123', 'admin'),
(4, 'Ferdi', 'ferdi', 'admin123', 'Tester');

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `id_barang` int(11) NOT NULL,
  `nama_bahan` varchar(100) NOT NULL,
  `jenis_bahan` varchar(100) NOT NULL,
  `jumlah_stock` int(11) NOT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`id_barang`, `nama_bahan`, `jenis_bahan`, `jumlah_stock`, `keterangan`) VALUES
(1, 'Tepung Terigu', 'Bahan Pokok', 100, 'Kualitas Premium'),
(2, 'Gula Pasir', 'Bahan Pokok', 50, 'Dari supplier A');

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `id_karyawan` int(11) NOT NULL,
  `nama_karyawan` varchar(100) NOT NULL,
  `jabatan` varchar(100) NOT NULL,
  `gaji` decimal(15,2) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`id_karyawan`, `nama_karyawan`, `jabatan`, `gaji`, `no_hp`, `alamat`) VALUES
(1, 'Budi', 'Kasir', 3500000.00, '082234567890', 'Jl. Kenanga No.2');

-- --------------------------------------------------------

--
-- Struktur dari tabel `keuangan`
--

CREATE TABLE `keuangan` (
  `id_keuangan` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `tipe` enum('Pemasukan Harian','Pengeluaran Harian') NOT NULL,
  `keterangan` text DEFAULT NULL,
  `jumlah` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `keuangan`
--

INSERT INTO `keuangan` (`id_keuangan`, `tanggal`, `tipe`, `keterangan`, `jumlah`) VALUES
(4, '2025-05-08', 'Pemasukan Harian', 'vfdcv', 21321.00),
(5, '2025-05-08', '', 'pengluaran harian', 22222.00),
(6, '2025-05-08', 'Pemasukan Harian', 'pengluaran harian', 111.00),
(8, '2025-05-08', 'Pemasukan Harian', 'ff', 22.00),
(9, '2025-05-16', 'Pemasukan Harian', 'vfdcv', 222.00),
(11, '2025-05-17', 'Pemasukan Harian', 'pengluaran harian', 1234.00),
(12, '2025-05-16', 'Pemasukan Harian', 'qwerty', 1234567890.00),
(14, '2025-05-24', 'Pemasukan Harian', 'ancbcd', 0.00),
(15, '2025-05-09', 'Pemasukan Harian', 'aaaaaaaa', 1234.00),
(18, '2025-05-09', 'Pemasukan Harian', 'dfsdsfsd', 12332432433.00),
(19, '2025-05-10', '', 'aaaaaaaaaaaaa', 32411111.00),
(20, '2025-05-10', 'Pemasukan Harian', 'ddddddddd', 222222.00),
(21, '2025-05-10', '', 'eeeee', 222222222.00),
(22, '2025-05-17', '', 'aaaaasss', 1111122.00),
(23, '2025-05-03', '', 'aaaa', 12333.00),
(24, '2025-05-17', '', 'aqqqq', 11111111.00),
(26, '2025-05-10', '', 'aaaaaaaa', 1111111.00),
(27, '2025-05-10', 'Pemasukan Harian', '1111122', 11111111.00),
(28, '2025-05-03', '', 'aaaaaaa', 11111111.00),
(30, '2025-05-24', '', 'ada', 2000.00),
(31, '2025-05-17', 'Pemasukan Harian', '11133', 0.00),
(32, '2025-05-03', 'Pemasukan Harian', '224', 0.00),
(33, '2025-05-03', '', 'ee', 11.00),
(34, '2025-05-17', 'Pengeluaran Harian', 'ww', 3333.00),
(35, '2025-05-10', 'Pemasukan Harian', 'ww', 222.00),
(36, '2025-05-08', 'Pemasukan Harian', 'ff', 22.00),
(37, '2025-05-17', 'Pengeluaran Harian', 'ww', 22.00),
(38, '2025-05-14', 'Pemasukan Harian', 'bakso tahu', 2000000.00);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(11) NOT NULL,
  `nama_pelanggan` varchar(100) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `perusahaan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama_pelanggan`, `no_hp`, `alamat`, `perusahaan`) VALUES
(2, 'kafe1', '12345', 'bogor', 'ceriamandiri'),
(3, '3', '6757', 'hjgj', 'gkjh');

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL,
  `nama_supplier` varchar(100) NOT NULL,
  `alamat` text DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `alamat`, `no_hp`, `email`) VALUES
(1, 'Supplier A', 'Jl. Mawar No.1', '081234567890', 'suppliera@gmail.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_pelanggan`
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
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admins`) USING BTREE,
  ADD UNIQUE KEY `username` (`username`);

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`);

--
-- Indeks untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id_karyawan`);

--
-- Indeks untuk tabel `keuangan`
--
ALTER TABLE `keuangan`
  ADD PRIMARY KEY (`id_keuangan`);

--
-- Indeks untuk tabel `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- Indeks untuk tabel `transaksi_pelanggan`
--
ALTER TABLE `transaksi_pelanggan`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_pelanggan` (`id_pelanggan`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id_admins` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `barang`
--
ALTER TABLE `barang`
  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  MODIFY `id_karyawan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `keuangan`
--
ALTER TABLE `keuangan`
  MODIFY `id_keuangan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT untuk tabel `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `id_pelanggan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `transaksi_pelanggan`
--
ALTER TABLE `transaksi_pelanggan`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `transaksi_pelanggan`
--
ALTER TABLE `transaksi_pelanggan`
  ADD CONSTRAINT `transaksi_pelanggan_ibfk_1` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
