DROP TABLE IF EXISTS `summary`;


CREATE TABLE `summary` (
  `id` int(11) NOT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `created` date NOT NULL,
  `facility_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `summary`
  ADD PRIMARY KEY (`id`),
  ADD KEY `facility_id` (`facility_id`);



CREATE TABLE `question`(
	`id` int(11) NOT NULL,
	`description` varchar(100) NOT NULL,
	`aproval_porcentage` float(10) NOT NULL,
	`type` varchar(10) NOT NULL,
	`aspect_id` int(11) NOT NULL,
	`point_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `question`
	ADD PRIMARY KEY (`id`),
	ADD KEY `aspect_id` (`aspect_id`),
	ADD KEY `point_id` (`point_id`);




CREATE TABLE `point`(
	`id` int(10) NOT NULL,
	`name` varchar(20) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `point`
	ADD PRIMARY KEY (`id`);



CREATE TABLE `evaluation`(
	`id` int (10) NOT NULL,
	`done` varchar(10) NOT NULL,
	`facility_id` int(10) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `evaluation`
	ADD PRIMARY KEY (`id`),
	ADD KEY `facility_id`(`facility_id`);