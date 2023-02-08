-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 08-02-2023 a las 16:31:01
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

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
  `nombre` varchar(256) COLLATE utf8_unicode_ci NOT NULL
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
(17, 2, 57.98, 60, 16),
(18, 2, 53.52, 61, 16),
(19, 1, 61.79, 62, 16),
(20, 2, 123.58, 62, 17),
(21, 1, 16.66, 63, 17),
(22, 2, 53.52, 61, 17),
(23, 2, 117.32, 67, 18),
(24, 1, 58.66, 67, 19),
(25, 1, 61.79, 62, 20),
(26, 2, 53.52, 61, 20),
(27, 1, 28.99, 60, 20),
(28, 1, 44.27, 69, 21),
(29, 1, 9.1, 68, 21),
(30, 2, 70.1558, 60, 22),
(31, 1, 32.3796, 61, 22),
(32, 1, 74.7659, 62, 22),
(33, 2, 62.038599999999995, 60, 23),
(34, 2, 57.266400000000004, 61, 23),
(35, 3, 198.3459, 62, 23),
(36, 1, 17.8262, 63, 23),
(37, 1, 35.0779, 60, 24),
(38, 1, 32.3796, 61, 24),
(39, 1, 74.7659, 62, 24);

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
(15, '2023-01-16 13:01:05', 0, 7, 165.86),
(16, '2023-01-23 17:00:06', 0, 7, 173.29),
(17, '2023-01-23 18:36:36', 0, 7, 193.76000000000002),
(18, '2023-01-23 20:07:06', 0, 7, 117.32),
(19, '2023-01-23 20:07:20', 0, 7, 58.66),
(20, '2023-01-27 16:39:12', 0, 7, 144.3),
(21, '2023-01-27 16:57:38', 0, 7, 53.370000000000005),
(22, '2023-01-30 18:38:33', 21, 7, 177.30130000000003),
(23, '2023-01-30 18:42:56', 7, 9, 335.47709999999995),
(24, '2023-01-30 19:01:43', 21, 7, 142.22340000000003);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `nombre` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `precio` double(10,2) NOT NULL,
  `subtipoproducto_id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `images` varchar(256) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `codigo`, `nombre`, `precio`, `subtipoproducto_id`, `cantidad`, `images`) VALUES
(60, '154541fdsafd', 'Ambientadores y desodorantes', 28.99, 1, 19, 'panuelo.jpg'),
(61, '87481564dsaf', 'Útiles de Limpieza LEWI', 26.76, 1, 55, 'ambientador.jpg'),
(62, '154541fdsafd', 'Ambientadores y desodorantes', 61.79, 1, 43, 'desengrasante.jpg'),
(63, 'fdsafdsa5454', 'Celulosa Industrial', 16.66, 14, 13, 'opubwnpvjue.jpeg'),
(64, '154541fdsafd', 'Útiles de Limpieza LEWI', 22.02, 1, 14, 'panuelo.jpg'),
(65, '87481564dsaf', 'Celulosa Industrial', 90.82, 1, 20, 'desengrasante.jpg'),
(66, '87481564dsaf', 'Ambientadores y desodorantes', 13.53, 1, 68, 'opubwnpvjue.jpeg'),
(67, 'fdsafdsa5484848854', 'Aspiradoras de Polvo', 58.66, 1, 91, 'panuelo.jpg'),
(68, 'fdsafdsa5484848854', 'Random1', 9.10, 14, 90, 'opubwnpvjue.jpeg'),
(69, '154541fdsafd', 'Random2', 44.27, 14, 23, 'panuelo.jpg'),
(71, '45725fdsafdsfd', 'Agua con Gas', 2.20, 1, 5, 'mci4gf12rfc.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subtipoproducto`
--

CREATE TABLE `subtipoproducto` (
  `id` bigint(20) NOT NULL,
  `tipoproducto_id` bigint(20) NOT NULL,
  `nombre` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `codigo` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `subtipoproducto`
--

INSERT INTO `subtipoproducto` (`id`, `tipoproducto_id`, `nombre`, `codigo`) VALUES
(1, 95, 'Desodorante', 'DESODORANTE'),
(14, 95, 'Inyección ', 'UTILESDELIMPIEZALEWI');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoproducto`
--

CREATE TABLE `tipoproducto` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `codigo` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tipoproducto`
--

INSERT INTO `tipoproducto` (`id`, `nombre`, `codigo`) VALUES
(95, 'Productos Químicos', 'PRODUCTOSQUIMICOS'),
(112, 'Celulosas y textiles', 'CELULOSAYTEXTILES');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `dni` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `nombre` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `apellido` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `apellido2` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `token` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `authority_id` bigint(20) NOT NULL,
  `verification_code` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `newpassword_code` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `dni`, `nombre`, `apellido`, `apellido2`, `email`, `username`, `password`, `token`, `authority_id`, `verification_code`, `newpassword_code`) VALUES
(6, '54451256156', 'pepe', 'f', 'f', 'buenas@tardes.com', 'cliente1', '82d7eeedb5bbfdaf7b7d0fe6f40a9ce2', NULL, 2, '', ''),
(7, '54451256156', 'jose', 'r', 'r', 'dios@mio.com', 'admin', 'f38d09938ead31a57eca34d2a0df1c44', NULL, 1, '', ''),
(8, '54451256156', 'sergi', 'm', 'r', 'josedavidrosalesrios0310', 'cliente', 'f38d09938ead31a57eca34d2a0df1c44', NULL, 2, '', ''),
(9, '78451542E', 'alex', 'jimenez', 'ramirez', 'josedavidrosalesrios0310', 'alex/cliente', 'f38d09938ead31a57eca34d2a0df1c44', NULL, 2, '', ''),
(15, '12345678E', 'Iris', 'Suay', NULL, 'josedavidrosalesrios0310@gmail.com', 'Iris', 'f38d09938ead31a57eca34d2a0df1c44', NULL, 2, '963047259233779150', NULL);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;

--
-- AUTO_INCREMENT de la tabla `subtipoproducto`
--
ALTER TABLE `subtipoproducto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `tipoproducto`
--
ALTER TABLE `tipoproducto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=113;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

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
