package org.brittlawson.dao;

import org.brittlawson.model.Jump;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JdbcJumpDao implements JumpDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcJumpDao (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //need to implement all methods
    
    @Override
    public Jump getJump(int jumpNumber) {
        Jump jump = null;
        String sql = "SELECT jump_number, date, dz_id, aircraft, exit_altitude_in_feet, deployment_altitude_in_feet, canopy_id, distance_from_target_feet, number_in_formation, maneuver, description " +
                     "FROM jump " +
                     "WHERE jump_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, jumpNumber);
        if(results.next()) {
            jump = mapRowToJump(results);
        }
        return jump;
    }

    @Override
    public List<Jump> getAllJumps() {
        List<Jump> jumps = new ArrayList<>();
        String sql = "SELECT * " +
                     "FROM jump;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            jumps.add(mapRowToJump(results));
        }
        return jumps;
    }

    @Override
    public int getTotalNumberJumps() {
        String sql = "SELECT MAX(jump_number) " +
                               "FROM jump;";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public List<Jump> getJumpsByDz(int dzId) {
        List<Jump> jumps = new ArrayList<>();
        String sql = "SELECT jump_number, date, dz_id, aircraft, exit_altitude_in_feet, deployment_altitude_in_feet, canopy_id, distance_from_target_feet, number_in_formation, maneuver, description " +
                     "FROM jump " +
                     "WHERE dz_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, dzId);
        while (results.next()) {
            jumps.add(mapRowToJump(results));
        }
        return jumps;
    }

    @Override
    public List<Jump> getJumpsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Jump> jumps = new ArrayList<>();
        String sql = "SELECT jump_number, date, dz_id, aircraft, exit_altitude_in_feet, deployment_altitude_in_feet, canopy_id, distance_from_target_feet, number_in_formation, maneuver, description " +
                     "FROM jump " +
                     "WHERE date BETWEEN ? AND ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, startDate, endDate);
        while (results.next()) {
            jumps.add(mapRowToJump(results));
        }
        return jumps;
    }

    @Override
    public List<Jump> getJumpsByLandingAccuracy(int maxDistanceFromTarget) {
        List<Jump> jumps = new ArrayList<>();
        String sql = "SELECT jump_number, date, dz_id, aircraft, exit_altitude_in_feet, deployment_altitude_in_feet, canopy_id, distance_from_target_feet, number_in_formation, maneuver, description " +
                     "FROM jump " +
                     "WHERE distance_from_target_feet <= ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, maxDistanceFromTarget);
        while (results.next()) {
            jumps.add(mapRowToJump(results));
        }
        return jumps;
    }

    @Override
    public List<Jump> getJumpsByExitAltitude(int minAltitude, int maxAltitude) {
        List<Jump> jumps = new ArrayList<>();
        String sql = "SELECT jump_number, date, dz_id, aircraft, exit_altitude_in_feet, deployment_altitude_in_feet, canopy_id, distance_from_target_feet, number_in_formation, maneuver, description " +
                     "FROM jump " +
                     "WHERE exit_altitude_in_feet BETWEEN ? AND ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, minAltitude, maxAltitude);
        while (results.next()) {
            jumps.add(mapRowToJump(results));
        }
        return jumps;
    }

    @Override
    public List<Jump> getJumpsByFormationSize(int minFormationSize) {
        List<Jump> jumps = new ArrayList<>();
        String sql = "SELECT jump_number, date, dz_id, aircraft, exit_altitude_in_feet, deployment_altitude_in_feet, canopy_id, distance_from_target_feet, number_in_formation, maneuver, description " +
                     "FROM jump " +
                     "WHERE number_in_formation >= ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, minFormationSize);
        while (results.next()) {
            jumps.add(mapRowToJump(results));
        }
        return jumps;
    }

    @Override
    public Jump getMostRecentJump() {
        String sql = "SELECT MAX(jump_number) " +
                     "FROM jump;";
        Integer mostRecentJumpNumber = jdbcTemplate.queryForObject(sql, Integer.class);
        if (mostRecentJumpNumber != null) {
            return getJump(mostRecentJumpNumber);
        } else throw new NullPointerException();
    }

    @Override
    public Jump createJump(Jump jump) {
        String sql = "INSERT INTO jump (jump_number, date, dz_id, aircraft, exit_altitude_in_feet, deployment_altitude_in_feet, canopy_id, distance_from_target_feet, number_in_formation, maneuver, description) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING jump_number;";
        Integer newJumpNumber = jdbcTemplate.queryForObject(sql, Integer.class, jump.getJumpNumber(), jump.getDate(), jump.getDzId(), jump.getAircraft(), jump.getExitAltitudeInFeet(), jump.getDeploymentAltitudeInFeet(), jump.getCanopyId(), jump.getDistanceFromTargetFeet(), jump.getNumberInFormation(), jump.getManeuver(), jump.getDescription());
        if(newJumpNumber != null) {
            return getJump(newJumpNumber);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void updateJump(Jump jump) {
        String sql = "UPDATE jump " +
                     "SET date = ?, " +
                     "    dz_id = ?, " +
                     "    aircraft = ?, " +
                     "    exit_altitude_in_feet = ?, " +
                     "    deployment_altitude_in_feet = ?, " +
                     "    canopy_id = ?, " +
                     "    distance_from_target_feet = ?, " +
                     "    number_in_formation = ?, " +
                     "    maneuver = ?, " +
                     "    description = ? " +
                     "WHERE jump_number = ?;";
        jdbcTemplate.update(sql, jump.getDate(), jump.getDzId(), jump.getAircraft(), jump.getExitAltitudeInFeet(), jump.getDeploymentAltitudeInFeet(),
                jump.getCanopyId(), jump.getDistanceFromTargetFeet(), jump.getNumberInFormation(), jump.getManeuver(), jump.getDescription(), jump.getJumpNumber());
    }

    public void deleteJump(int jumpNumber) {
        String sql = "DELETE FROM jump WHERE jump_number = ?;";
        jdbcTemplate.update(sql, jumpNumber);
    }

    private Jump mapRowToJump(SqlRowSet rowSet) {
        Jump jump = new Jump();

        jump.setJumpNumber(rowSet.getInt("jump_number"));

        Date date = rowSet.getDate("date");
        if(date != null) {
            jump.setDate(date.toLocalDate());
        }

        jump.setDzId(rowSet.getInt("dz_id"));

        String aircraft = rowSet.getString("aircraft");
        if(aircraft != null) {
            jump.setAircraft(aircraft);
        }

        jump.setExitAltitudeInFeet(rowSet.getInt("exit_altitude_in_feet"));

        jump.setDeploymentAltitudeInFeet(rowSet.getInt("deployment_altitude_in_feet"));

        jump.setCanopyId(rowSet.getInt("canopy_id"));

        jump.setDistanceFromTargetFeet(rowSet.getInt("distance_from_target_feet"));

        jump.setNumberInFormation(rowSet.getInt("number_in_formation"));

        String maneuver = rowSet.getString("maneuver");
        if(maneuver != null) {
            jump.setManeuver(maneuver);
        }

        String description = rowSet.getString("description");
        if(description != null) {
            jump.setDescription(description);
        }

        return jump;
    }
}
