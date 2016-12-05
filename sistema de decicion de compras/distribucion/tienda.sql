CREATE DATABASE abarrotes;
use abarrotes;

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellidoPaterno` varchar(100) NOT NULL,
  `apellidoMaterno` varchar(100) DEFAULT NULL,
  `rfc` char(14) NOT NULL,
  `calle` varchar(100) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `numeroInt` varchar(10) NOT NULL,
  `colonia` varchar(100) NOT NULL,
  `municipio` varchar(20) NOT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `cp` int(11) NOT NULL,
  `razonSocial` varchar(120) NOT NULL,
  `pais` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rfc` (`rfc`)
);

CREATE TABLE `personal` (
  `id` int(11) NOT NULL,
  `nombre` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `precioCompra` decimal(6,2) NOT NULL,
  `precio` decimal(6,2) NOT NULL,
  `existencia` decimal(5,2) DEFAULT NULL,
  `iva` decimal(4,2) DEFAULT NULL,
  `descuento` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `venta` (
  `folio` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `cliente` int(11) NOT NULL,
  `subtotal` decimal(8,2) NOT NULL,
  `iva` decimal(6,2) NOT NULL,
  `total` decimal(8,2) NOT NULL,
  `cajero` int(11) NOT NULL,
  `metodoPago` varchar(20) NOT NULL,
  `dineroRecibido` decimal(8,2) NOT NULL,
  `cambio` decimal(8,2) NOT NULL,
  PRIMARY KEY (`folio`),
  KEY `cliente` (`cliente`),
  KEY `cajero` (`cajero`),
  CONSTRAINT `Venta_ibfk_1` FOREIGN KEY (`cliente`) REFERENCES `cliente` (`id`),
  CONSTRAINT `Venta_ibfk_2` FOREIGN KEY (`cajero`) REFERENCES `personal` (`id`)
);

CREATE TABLE `detalleventa` (
  `folio` bigint(20) NOT NULL DEFAULT '0',
  `producto` int(11) NOT NULL DEFAULT '0',
  `precio` decimal(6,2) NOT NULL,
  `cantidad` decimal(5,2) NOT NULL,
  `importe` decimal(7,2) NOT NULL,
  `descuento` decimal(7,2) NOT NULL,
  `iva` decimal(5,2) NOT NULL,
  PRIMARY KEY (`folio`,`producto`),
  KEY `producto` (`producto`),
  CONSTRAINT `DetalleVenta_ibfk_1` FOREIGN KEY (`folio`) REFERENCES `venta` (`folio`) ON DELETE CASCADE,
  CONSTRAINT `DetalleVenta_ibfk_2` FOREIGN KEY (`producto`) REFERENCES `producto` (`id`)
);