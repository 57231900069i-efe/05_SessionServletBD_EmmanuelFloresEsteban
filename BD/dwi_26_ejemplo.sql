CREATE DATABASE IF NOT EXISTS `dwi_26_ejemplo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `dwi_26_ejemplo`;

-- Configuración inicial
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
SET NAMES utf8mb4;

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `alumnos`
-- --------------------------------------------------------
CREATE TABLE `alumnos` (
  `NL` int(11) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Paterno` varchar(50) NOT NULL,
  `Materno` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcado de datos para la tabla `alumnos`
INSERT INTO `alumnos` (`NL`, `Nombre`, `Paterno`, `Materno`) VALUES
(1, '1', '1', '1'),
(2, '2', '2', '2'),
(3, '3', '3', '3'),
(11, 'Vicente', 'Tadeo q', 'Gabriel'),
(100, 'Emmanuel', 'Flores', 'Esteban');

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `roles`
-- --------------------------------------------------------
CREATE TABLE `roles` (
  `id_rol` int(11) NOT NULL,
  `nombre_rol` varchar(50) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcado de datos para la tabla `roles`
INSERT INTO `roles` (`id_rol`, `nombre_rol`, `descripcion`) VALUES
(1, 'superusuario', 'Administrador del sistema con todos los privilegios'),
(2, 'usuario', 'Usuario regular con acceso limitado');

-- --------------------------------------------------------
-- Estructura de tabla para la tabla `usuarios`
-- --------------------------------------------------------
CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido1` varchar(100) NOT NULL,
  `apellido2` varchar(100) DEFAULT '',
  `correo` varchar(150) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `estado` enum('activo','rechazado','inactivo') DEFAULT 'inactivo',
  `id_rol` int(11) DEFAULT 2,
  `token_validacion` varchar(255) DEFAULT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcado de datos para la tabla `usuarios`
INSERT INTO `usuarios` (`id_usuario`, `nombre`, `apellido1`, `apellido2`, `correo`, `contrasena`, `estado`, `id_rol`, `token_validacion`, `fecha_registro`) VALUES
(11, 'Emmanuel', 'Flores', 'Esteban', 'darkar16efe@gmail.com', '44752ece9dc279d4d3bfe422a20595795efe805f61d7d21852443cf521a0c1d6', 'rechazado', 2, 'iVkWcd8BY8uXSOOAXL4XSeCOoseWxgzLoANJs6EZReM', '2026-07-03 03:08:15'),
(16, 'Emmanuel', 'Flores', 'Esteban', '57231900069@utrng.edu.mx', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'rechazado', 2, 'rNMst5fLOOtlaCVVVKjP71w3B9DbPq7ORbEFpeTxHEU', '2026-07-04 02:43:22'),
(17, 'Emmanuel', 'Flores Esteban', 'Esteban', '57231900000@utrng.edu.mx', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'inactivo', 2, 'tcJADqW-QQ-sSnVwSYKcQwjyBSQRf-y9YZSiubQ-lgg', '2026-07-04 04:07:03'),
(18, 'Emmanuel', 'Flores', ' Esteban', '5723190006@utrng.edu.mx', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'inactivo', 2, 'nsFiWDqt19E9RqRzV5j45-eXhoKxpt2J79QC4fWka58', '2026-07-04 04:19:58'),
(19, 'Administrador', 'Sistema', 'Principal', 'admin@utchilapa.edu.mx', '3eb3fe66b31e3b4d10fa70b5cad49c7112294af6ae4e476a1c405155d45aa121', 'activo', 1, NULL, '2026-07-04 05:04:58'),
(20, 'Emmanuel', 'Flores ', 'Esteban', '57231900069_i@utrng.edu.mx', 'c775e7b757ede630cd0aa1113bd102661ab38829ca52a6422ab782862f268646', 'activo', 2, '61UMbiEiygm6fwDfwr4Y-aN6fcx32j1tf2ynW4LPHJg', '2026-07-04 05:06:54');

-- --------------------------------------------------------
-- Índices y Claves Primarias
-- --------------------------------------------------------
ALTER TABLE `alumnos`
  ADD PRIMARY KEY (`NL`);

ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_rol`),
  ADD UNIQUE KEY `nombre_rol` (`nombre_rol`);

ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `correo` (`correo`),
  ADD KEY `id_rol` (`id_rol`);

-- --------------------------------------------------------
-- AUTO_INCREMENT
-- --------------------------------------------------------
ALTER TABLE `roles`
  MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

-- --------------------------------------------------------
-- Restricciones (Foreign Keys)
-- --------------------------------------------------------
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`) ON DELETE CASCADE;

-- Finalizar transacción
COMMIT;