package org.brittlawson.dao;

import org.brittlawson.exception.DropzoneNotFoundException;
import org.brittlawson.model.Dropzone;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcDropzoneDao implements DropzoneDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDropzoneDao (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Dropzone getDropzoneByDzId (int dzId) {
        Dropzone dropzone = null;
        String sql = "SELECT dz_id, dz_name, city, state_abbreviation, country " +
                     "FROM dropzone " +
                     "WHERE dz_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, dzId);
        if(results.next()) {
            dropzone = mapRowToDropzone(results);
        }
        return dropzone;
    }

    @Override
    public List<Dropzone> getAllDropzonesJumpedAt() {
        List<Dropzone> dropzones = new ArrayList<>();
        String sql = "SELECT dz_id, dz_name, city, state_abbreviation, country " +
                     "FROM dropzone " +
                     "JOIN jump USING (dz_id) " +
                     "GROUP BY dz_id " +
                     "ORDER BY dz_name;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            dropzones.add(mapRowToDropzone(results));
        }
        return dropzones;
    }

    @Override
    public List<Dropzone> getDropzonesByState(String stateAbbreviation) {
        List<Dropzone> dropzones = new ArrayList<>();
        String sql = "SELECT dz_id, dz_name, city, state_abbreviation, country " +
                "FROM dropzone " +
                "WHERE state_abbreviation = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, stateAbbreviation);
        while (results.next()) {
            dropzones.add(mapRowToDropzone(results));
        }
        return dropzones;
    }

    @Override
    public Dropzone getMostFrequentDropzone() {
        String sql = "SELECT jump.dz_id " +
                     "FROM dropzone " +
                     "JOIN jump USING (dz_id)" +
                     "GROUP BY jump.dz_id " +
                     "ORDER BY COUNT(*) DESC " +
                     "LIMIT 1;";
        Integer dzId = jdbcTemplate.queryForObject(sql, Integer.class);
        if (dzId != null) {
            return getDropzoneByDzId(dzId);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public Dropzone getMostRecentDropzone() {
        Dropzone dropzone = null;
        String sqlJumpNumber = "SELECT MAX(jump_number) " +
                               "FROM jump;";
        Integer mostRecentJumpNumber = jdbcTemplate.queryForObject(sqlJumpNumber, Integer.class);
        String sql = "SELECT dz_id, dz_name, city, state_abbreviation, country " +
                     "FROM dropzone " +
                     "JOIN jump USING (dz_id) " +
                     "WHERE jump_number = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, mostRecentJumpNumber);
        if (results.next()) {
            dropzone = mapRowToDropzone(results);
        }
        return dropzone;
    }

    @Override
    public int getNumberOfJumpsAtGivenDz(int dzId) throws DropzoneNotFoundException {
            String sql = "SELECT COUNT(dz_id) " +
                         "FROM jump " +
                         "WHERE dz_id = ?;";
            Integer jumps = jdbcTemplate.queryForObject(sql, Integer.class, dzId);
            if (jumps == null) {
                throw new NullPointerException();
            } else if (jumps == 0) {
                throw new DropzoneNotFoundException();
            } else {
                return jumps;
            }
    }

    @Override
    public void updateDropzone(Dropzone dropzone) {
        String sql = "UPDATE dropzone " +
                     "SET dz_name = ?, city = ?, state_abbreviation = ?, country = ? " +
                     "WHERE dz_id = ?;";
        jdbcTemplate.update(sql, dropzone.getDzName(), dropzone.getCity(), dropzone.getStateAbbreviation(), dropzone.getCountry(), dropzone.getDzId());
    }

    public void deleteDropzone(int dzId) {
        String sql = "DELETE FROM dropzone WHERE dz_id = ?;";
        jdbcTemplate.update(sql, dzId);
    }

    private Dropzone mapRowToDropzone(SqlRowSet rowSet) {
        Dropzone dropzone = new Dropzone();

        int dzId = rowSet.getInt("dz_id");
        dropzone.setDzId(dzId);

        String dzName = rowSet.getString("dz_name");
        dropzone.setDzName(dzName);

        String city = rowSet.getString("city");
        dropzone.setCity(city);

        String stateAbbreviation = rowSet.getString("state_abbreviation");
        if(stateAbbreviation != null) {
            dropzone.setStateAbbreviation(stateAbbreviation);
        }

        String country = rowSet.getString("country");
        dropzone.setCountry(country);

        return dropzone;
    }
}
