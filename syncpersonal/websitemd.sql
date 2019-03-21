-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-03-2019 a las 19:58:30
-- Versión del servidor: 10.1.36-MariaDB
-- Versión de PHP: 7.0.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mcapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `activity`
--

CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `aspect_id` int(11) DEFAULT NULL,
  `facility_id` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `responsible_id` int(11) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aspect`
--

CREATE TABLE `aspect` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `aproval_procentage` int(11) DEFAULT NULL,
  `evaluation_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluation`
--

CREATE TABLE `evaluation` (
  `id` int(11) NOT NULL,
  `done` datetime DEFAULT NULL,
  `facility_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluation_question`
--

CREATE TABLE `evaluation_question` (
  `evaluation_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Estructura de tabla para la tabla `response_evaluation`
--
CREATE TABLE `response_evaluation`(
  `id` int(11) NOT NULL,
  `id_evaluation` int(11) NOT NULL,
  `assessment` float(11) NOT NULL,
  `facility_id` int(11) NOT NULL,
  `aspect_id` int(11) NOT NULL

)ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Estructura de tabla para la tabla `facility`
--

CREATE TABLE `facility` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `radius` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facility_service`
--

CREATE TABLE `facility_service` (
  `facility_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `surname` varchar(20) DEFAULT NULL,
  `rut` varchar(20) NOT NULL,
  `email` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `created` date NOT NULL,
  `state` int(11) NOT NULL,
  `facility_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`id`, `name`, `surname`, `rut`, `email`, `phone`, `created`, `state`, `facility_id`) VALUES
(11, 'Aurelio', 'Campos', '10.456.234-1', '', '', '0000-00-00', 1, 113),
(10, 'Alfredo', 'Bastias', '17.907.123-1', '', '', '0000-00-00', 1, 113),
(4, 'Jose', 'Acevedo', '18.426.203-6', '', '4654654', '0000-00-00', 1, 3),
(3, 'Juan', 'Perez', '18.456.203-6', '', '4654654', '0000-00-00', 1, 3),
(9, 'Diego', 'Campus', '18.673.765-1', '', '', '0000-00-00', 1, 0),
(2, 'Diego', 'Matus', '19.007.996-1', '', '46465', '0000-00-00', 1, 2),
(5, 'Dylan', 'Tero', '19.299.356-8', '', 'a46456', '0000-00-00', 0, 1),
(1, 'Ariel', 'Cornejo', '19.299.833-6', 'asdas', '465464', '0000-00-00', 1, 2),
(6, 'Benjamin', 'Sanhueza', '19.626.586-5', '', '64654654', '0000-00-00', 0, 1),
(7, 'Felipe', 'Ureta', '19.741.223-9', '', '64654650', '0000-00-00', 1, 4),
(8, 'Diego', 'NuÃ±ez', '19.987.546-9', '', '5446', '0000-00-00', 1, 4),
(9, 'Diego', 'Campos', '19876123-1', '', '', '0000-00-00', 1, 113);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `point`
--

CREATE TABLE `point` (
  `id` int(10) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `question`
--

CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `description` varchar(80) DEFAULT NULL,
  `aproval_porcentage` int(11) DEFAULT NULL,
  `type` varchar(50) NOT NULL,
  `aspect_id` int(11) NOT NULL,
  `point_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `report`
--

CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `activity_id` int(11) DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `service`
--

CREATE TABLE `service` (
  `id` int(10) NOT NULL,
  `code` varchar(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `identifier` varchar(16) NOT NULL,
  `icon` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subservice`
--

CREATE TABLE `subservice` (
  `id` int(10) NOT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `service_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `summary`
--

CREATE TABLE `summary` (
  `id` int(11) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `created` date NOT NULL,
  `facility_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `aspect_id` int(11) DEFAULT NULL,
  `facility_id` int(11) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `rut` varchar(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `password`, `created`, `rut`, `email`, `phone`) VALUES
(1, 'Oliver', 'Atom13', '12345', '0000-00-00 00:00:00', '18.701.567-', 'o.atom@gmail.com', '912345678');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visit`
--

CREATE TABLE `visit` (
  `id` int(11) NOT NULL,
  `facility_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `enter` datetime DEFAULT NULL,
  `finalice` datetime DEFAULT NULL,
  `comment` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `aspect_id` (`aspect_id`),
  ADD KEY `facility_id` (`facility_id`),
  ADD KEY `creator_id` (`creator_id`),
  ADD KEY `responsible_id` (`responsible_id`);

--
-- Indices de la tabla `aspect`
--
ALTER TABLE `aspect`
  ADD PRIMARY KEY (`id`),
  ADD KEY `evaluation_id` (`evaluation_id`);

--
-- Indices de la tabla `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `evaluation_question`
--
ALTER TABLE `evaluation_question`
  ADD PRIMARY KEY (`evaluation_id`,`question_id`),
  ADD KEY `question_id` (`question_id`);

--
-- Indices de la tabla `facility`
--
ALTER TABLE `facility`
  ADD PRIMARY KEY (`id`),
  ADD KEY `facility_ibfk_1` (`user_id`);

--
-- Indices de la tabla `facility_service`
--
ALTER TABLE `facility_service`
  ADD PRIMARY KEY (`facility_id`,`service_id`),
  ADD KEY `facility_id` (`facility_id`),
  ADD KEY `facility_service_ibfk_2` (`service_id`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`rut`) USING BTREE,
  ADD KEY `facility_id` (`facility_id`);

--
-- Indices de la tabla `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `activity_id` (`activity_id`),
  ADD KEY `task_id` (`task_id`);

--
-- Indices de la tabla `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `subservice`
--
ALTER TABLE `subservice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `service_key_1` (`service_id`);

--
-- Indices de la tabla `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `aspect_id` (`aspect_id`),
  ADD KEY `facility_id` (`facility_id`),
  ADD KEY `creator_id` (`creator_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `visit`
--
ALTER TABLE `visit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `facility_id` (`facility_id`);

ALTER TABLE `response_evaluation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_evaluation` (`id_evaluation`),
  ADD KEY `facility_id` (`facility_id`),
  ADD KEY `aspect_id` (`aspect_id`);
--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `service`
--
ALTER TABLE `service`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `subservice`
--
ALTER TABLE `subservice`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;


ALTER TABLE `response_evaluation`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`aspect_id`) REFERENCES `aspect` (`id`),
  ADD CONSTRAINT `activity_ibfk_2` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`),
  ADD CONSTRAINT `activity_ibfk_3` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `activity_ibfk_4` FOREIGN KEY (`responsible_id`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `aspect`
--
ALTER TABLE `aspect`
  ADD CONSTRAINT `aspect_ibfk_1` FOREIGN KEY (`evaluation_id`) REFERENCES `evaluation` (`id`);

--
-- Filtros para la tabla `evaluation_question`
--
ALTER TABLE `evaluation_question`
  ADD CONSTRAINT `evaluation_question_ibfk_1` FOREIGN KEY (`evaluation_id`) REFERENCES `evaluation` (`id`),
  ADD CONSTRAINT `evaluation_question_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

--
-- Filtros para la tabla `facility_service`
--
ALTER TABLE `facility_service`
  ADD CONSTRAINT `facility_service_ibfk_1` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`),
  ADD CONSTRAINT `facility_service_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`);

--
-- Filtros para la tabla `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  ADD CONSTRAINT `report_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

--
-- Filtros para la tabla `subservice`
--
ALTER TABLE `subservice`
  ADD CONSTRAINT `service_key_1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`);

--
-- Filtros para la tabla `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`aspect_id`) REFERENCES `aspect` (`id`),
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`),
  ADD CONSTRAINT `task_ibfk_3` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`);

ALTER TABLE `response_evaluation`
  ADD CONSTRAINT `response_evaluation_ibfk_1` FOREIGN KEY (`id_evaluation`) REFERENCES `evaluation` (`id`),
  ADD CONSTRAINT `response_evaluation_ibfk_2` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`),
  ADD CONSTRAINT `response_evaluation_ibfk_3` FOREIGN KEY (`aspect_id`) REFERENCES `aspect` (`id`);
--
-- Filtros para la tabla `visit`
--
ALTER TABLE `visit`
  ADD CONSTRAINT `visit_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `visit_ibfk_2` FOREIGN KEY (`facility_id`) REFERENCES `facility` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;