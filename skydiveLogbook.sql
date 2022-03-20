DROP TABLE IF EXISTS jump;
DROP TABLE IF EXISTS canopy;
DROP TABLE IF EXISTS dropzone;


CREATE TABLE dropzone
(
	dz_id SERIAL,
	dz_name VARCHAR(200) NOT NULL,
	city_state VARCHAR(100) NOT NULL,
	country VARCHAR(100) DEFAULT 'USA',

	CONSTRAINT pk_dz_id PRIMARY KEY (dz_id)
);

CREATE TABLE canopy
(
	canopy_id SERIAL,
	platform VARCHAR(100) NOT NULL,
	size_square_feet INTEGER NOT NULL,

	CONSTRAINT pk_canopy_id PRIMARY KEY (canopy_id),
	CONSTRAINT chk_positive_size CHECK (size_square_feet > 0)
);

CREATE TABLE jump
(
	jump_number INTEGER,
	date DATE DEFAULT CURRENT_DATE,
	dz_id INTEGER NOT NULL,
	aircraft VARCHAR(100),
	exit_altitude_in_feet INTEGER NOT NULL,
	deployment_altitude_in_feet INTEGER NOT NULL,
	canopy_id INTEGER NOT NULL,
	distance_from_target_feet INTEGER,
	maneuver VARCHAR(200),
	description TEXT,

	CONSTRAINT pk_jump_number PRIMARY KEY (jump_number),
	CONSTRAINT chk_valid_jump_number CHECK (jump_number > 0),
	CONSTRAINT fk_dz_id FOREIGN KEY (dz_id) REFERENCES dropzone (dz_id),
	CONSTRAINT fk_canopy_id FOREIGN KEY (canopy_id) REFERENCES canopy (canopy_id),
	CONSTRAINT chk_appropriate_altitudes CHECK (deployment_altitude_in_feet <= exit_altitude_in_feet),
	CONSTRAINT chk_appropriate_distance CHECK (distance_from_target_feet >= 0)
);
