--Skydiver: id, USPA member #, USPA license, license type, # jumps (? or maybe just method to get in code)
--Logbook: jump #, DZ id, canopy id, aircraft, freefall time(?), exit altitude, deploy altitude, landing distance from target, maneuver, description
--DZ: id, name, USPA?
--address: id, DZ id, city, state, country
--Canopy: id, model, size

DROP TABLE IF EXISTS jump;

CREATE TABLE jump
(
	jump_number INTEGER,
	date DATE NOT NULL,
	dropzone VARCHAR(200),
	aircraft VARCHAR(100),
	exit_altitude INTEGER,
	deployment_altitude INTEGER,
	canopy VARCHAR(100),
	maneuver VARCHAR(200),
	description TEXT,
	
	CONSTRAINT pk_jump_number PRIMARY KEY (jump_number),
	CONSTRAINT chk_appropriate_altitudes CHECK (deployment_altitude <= exit_altitude)
);