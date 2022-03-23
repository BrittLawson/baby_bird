package org.brittlawson.dao;

import org.brittlawson.model.Canopy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCanopyDao implements CanopyDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCanopyDao(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Canopy> getAllCanopiesJumped() {
        List<Canopy> canopies = new ArrayList<>();
        String sql = "SELECT canopy_id, platform, size_square_feet " +
                     "FROM canopy " +
                     "ORDER BY size_square_feet;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            canopies.add(mapRowToCanopy(results));
        }
        return canopies;
    }

    @Override
    public List<Canopy> getCanopiesBySizeRange(int minSize, int maxSize) {
        List<Canopy> canopies = new ArrayList<>();
        String sql = "SELECT canopy_id, platform, size_square_feet " +
                     "FROM canopy " +
                     "WHERE size_square_feet BETWEEN ? AND ? " +
                     "ORDER BY size_square_feet;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, minSize, maxSize);
        while (results.next()) {
            canopies.add(mapRowToCanopy(results));
        }
        return canopies;
    }

    @Override
    public Canopy getMostRecentCanopyJumped() {
        Canopy canopy = null;
        String sqlJumpNumber = "SELECT MAX(jump_number) " +
                               "FROM jump;";
        Integer mostRecentJumpNumber = jdbcTemplate.queryForObject(sqlJumpNumber, Integer.class);
        String sql = "SELECT canopy_id, platform, size_square_feet " +
                "FROM canopy " +
                "JOIN jump USING (canopy_id) " +
                "WHERE jump_number = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, mostRecentJumpNumber);
        if (results.next()) {
            canopy = mapRowToCanopy(results);
        }
        return canopy;
    }

    @Override
    public void updateCanopy(Canopy canopy) {
        String sql = "UPDATE canopy " +
                     "SET platform = ?, size_square_feet = ? " +
                     "WHERE canopy_id = ?;";
        jdbcTemplate.update(sql, canopy.getPlatform(), canopy.getSizeSquareFeet(), canopy.getCanopyId());
    }

    public void deleteCanopy(int canopy_id) {
        String sql = "DELETE FROM canopy WHERE canopy_id = ?;";
        jdbcTemplate.update(sql, canopy_id);
    }

    private Canopy mapRowToCanopy(SqlRowSet rowSet) {
        Canopy canopy = new Canopy();

        canopy.setCanopyId(rowSet.getInt("canopy_id"));
        canopy.setPlatform(rowSet.getString("platform"));
        canopy.setSizeSquareFeet(rowSet.getInt("size_square_feet"));

        return canopy;
    }
}
