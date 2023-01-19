-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-01-2023 a las 16:17:33
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `vfranco`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `authoritys`
--

CREATE TABLE `authoritys` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `authoritys`
--

INSERT INTO `authoritys` (`id`, `nombre`) VALUES
(1, 'admin'),
(2, 'cliente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--

CREATE TABLE `compra` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` double NOT NULL,
  `producto_id` bigint(20) NOT NULL,
  `factura_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `compra`
--

INSERT INTO `compra` (`id`, `cantidad`, `precio`, `producto_id`, `factura_id`) VALUES
(15, 2, 10, 37, 15),
(16, 2, 155.86, 58, 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime NOT NULL,
  `iva` int(10) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `total_precio` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`id`, `fecha`, `iva`, `usuario_id`, `total_precio`) VALUES
(15, '2023-01-16 13:01:05', 0, 7, 165.86);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(256) NOT NULL,
  `nombre` varchar(256) NOT NULL,
  `precio` double(10,2) NOT NULL,
  `subtipoproducto_id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `images` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `codigo`, `nombre`, `precio`, `subtipoproducto_id`, `cantidad`, `images`) VALUES
(37, 'dsfdsa', 'Aguita con Gas', 5.00, 1, 8, '13r59v59htr8.jpeg'),
(58, '87481564dsaf', 'Aspiradoras de Polvo', 77.93, 13, 70, 'ambientador.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subtipoproducto`
--

CREATE TABLE `subtipoproducto` (
  `id` bigint(20) NOT NULL,
  `tipoproducto_id` bigint(20) NOT NULL,
  `nombre` varchar(256) NOT NULL,
  `codigo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `subtipoproducto`
--

INSERT INTO `subtipoproducto` (`id`, `tipoproducto_id`, `nombre`, `codigo`) VALUES
(1, 95, 'Desodorante', 'DESODORANTE'),
(13, 99, 'Útiles de limpieza LEWI', 'UTILESDELIMPIEZALEWI');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoproducto`
--

CREATE TABLE `tipoproducto` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(256) NOT NULL,
  `codigo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tipoproducto`
--

INSERT INTO `tipoproducto` (`id`, `nombre`, `codigo`) VALUES
(95, 'Productos Químicos', 'PRODUCTOSQUIMICOS'),
(99, 'Complementos de Higuiene', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `dni` varchar(11) NOT NULL,
  `nombre` varchar(256) NOT NULL,
  `apellido` varchar(256) NOT NULL,
  `apellido2` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `token` varchar(256) DEFAULT NULL,
  `authority_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `dni`, `nombre`, `apellido`, `apellido2`, `email`, `username`, `password`, `token`, `authority_id`) VALUES
(6, '54451256156', 'pepe', 'f', 'f', 'buenas@tardes.com', 'cliente1', '82d7eeedb5bbfdaf7b7d0fe6f40a9ce2', NULL, 2),
(7, '54451256156', 'jose', 'r', 'r', 'dios@mio.com', 'admin', 'f38d09938ead31a57eca34d2a0df1c44', NULL, 1),
(8, '54451256156', 'sergi', 'm', 'r', 'aiuda@policia.com', 'cliente', 'f38d09938ead31a57eca34d2a0df1c44', NULL, 2),
(9, '78451542E', 'alex', 'jimenez', 'ramirez', 'pat@example.com', 'alex/cliente', 'f38d09938ead31a57eca34d2a0df1c44', NULL, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `authoritys`
--
ALTER TABLE `authoritys`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_carrito_fk` (`producto_id`),
  ADD KEY `usuario_carrito_fk` (`usuario_id`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `compra_producto_id_fk` (`producto_id`),
  ADD KEY `compra_factura_id_fk` (`factura_id`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `factura_usuario_fk` (`usuario_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_tipoproducto_id_fk` (`subtipoproducto_id`);

--
-- Indices de la tabla `subtipoproducto`
--
ALTER TABLE `subtipoproducto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `subtipo_tipoproductoID_fk` (`tipoproducto_id`);

--
-- Indices de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_authority_id_fk` (`authority_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `authoritys`
--
ALTER TABLE `authoritys`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT de la tabla `subtipoproducto`
--
ALTER TABLE `subtipoproducto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD CONSTRAINT `producto_carrito_fk` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `usuario_carrito_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `compra_factura_id_fk` FOREIGN KEY (`factura_id`) REFERENCES `factura` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `compra_producto_id_fk` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_usuario_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_subtipoproducto_id_fk` FOREIGN KEY (`subtipoproducto_id`) REFERENCES `subtipoproducto` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `subtipoproducto`
--
ALTER TABLE `subtipoproducto`
  ADD CONSTRAINT `subtipo_tipoproductoID_fk` FOREIGN KEY (`tipoproducto_id`) REFERENCES `tipoproducto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_authority_id_fk` FOREIGN KEY (`authority_id`) REFERENCES `authoritys` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
