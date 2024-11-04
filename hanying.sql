-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 17, 2023 at 06:27 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hanying`
--

-- --------------------------------------------------------

--
-- Table structure for table `category_item`
--

CREATE TABLE `category_item` (
  `catname` varchar(50) NOT NULL,
  `catpict` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category_item`
--

INSERT INTO `category_item` (`catname`, `catpict`) VALUES
('Steam', 'cat_1'),
('Fried', 'cat_2'),
('Soup', 'cat_3'),
('Noodles', 'cat_4'),
('Drinks', 'cat_5');

-- --------------------------------------------------------

--
-- Table structure for table `menu_item`
--

CREATE TABLE `menu_item` (
  `foodname` varchar(50) NOT NULL,
  `foodpic` varchar(50) NOT NULL,
  `fooddesc` varchar(255) NOT NULL,
  `foodcat` varchar(50) NOT NULL,
  `foodprice` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_item`
--

INSERT INTO `menu_item` (`foodname`, `foodpic`, `fooddesc`, `foodcat`, `foodprice`) VALUES
('Hakau Udang', 'hakauudang', '-', 'Steam', 2.75),
('Kuah Ayam Talas', 'kuahayamtalas', '-', 'Soup', 0.8),
('Mie Polos', 'miepolos', '-', 'Noodles', 0.75),
('Mantau Goreng', 'mantaugoreng', '-', 'Fried', 0.6),
('Es Teh Tawar', 'estehtawar', '-', 'Drinks', 0.25);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `custname` varchar(50) NOT NULL,
  `custemail` varchar(50) NOT NULL,
  `custphone` varchar(50) NOT NULL,
  `role` varchar(10) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `custname`, `custemail`, `custphone`, `role`, `password`) VALUES
('chrstdvano', 'Christopher Abie Diaz Doviano', 'christopher.abie@student.umn.ac.id', '0625779967703', 'admin', '12345'),
('julius.calvin', 'Julius Calvin Saputra', 'julius.calvin@student.umn.ac.id', '6278389957293', 'user', '12345'),
('ray.p', 'Ray Anthony Pranoto', 'ray.anthony@student.umn.ac.id', '6281326458686', 'user', '12345');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
