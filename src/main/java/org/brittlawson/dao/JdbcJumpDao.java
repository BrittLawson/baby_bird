package org.brittlawson.dao;

import org.brittlawson.model.Jump;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Jump> getJumpsByDz(int dzId) {
        return null;
    }

    @Override
    public List<Jump> getJumpsByDateRange(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<Jump> getJumpsByLandingAccuracy(int maxDistanceFromTarget) {
        return null;
    }

    @Override
    public List<Jump> getJumpsByExitAltitude(int minAltitude, int maxAltitude) {
        return null;
    }

    @Override
    public List<Jump> getJumpsByFormationSize(int minFormationSize) {
        return null;
    }

    @Override
    public LocalDate getMostRecentJumpDate() {
        return null;
    }

    @Override
    public Jump createJump(Jump jump) {
        return null;
    }

    @Override
    public void updateJump(Jump jump) {

    }

    @Override
    public void deleteJump(int jumpNumber) {

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
