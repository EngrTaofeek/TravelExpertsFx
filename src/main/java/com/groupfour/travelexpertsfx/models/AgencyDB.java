package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @Author DarylWxc
 * @Date 2/22/2025
 * @Description Agency DB
 */
public class AgencyDB {
    public static Connection getConnection() {
        String url = DbConfig.getProperty("url");
        String user = DbConfig.getProperty("user");
        String password = DbConfig.getProperty("password");

        Connection conn;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database", e);
        }
        return conn;
    }

    public static ObservableList<Agency> getAgencies() throws SQLException {
        ObservableList<Agency> agencies = FXCollections.observableArrayList();
        Agency agency;
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from agencies");
        while (rs.next()) {
            agency = new Agency(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)
            );
            agencies.add(agency);
        }
        return agencies;
    }

    public static Integer addAgency(Agency agency) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "INSERT INTO agencies (agncyaddress, agncycity, " +
                "agncyprov, agncypostal, agncycountry, agncyphone, agncyfax)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, agency.getAgencyaddress());
        stmt.setString(2,agency.getAgencycity());
        stmt.setString(3,agency.getAgencyprov());
        stmt.setString(4,agency.getAgencypostal());
        stmt.setString(5,agency.getAgencycountry());
        stmt.setString(6,agency.getAgencyphone());
        stmt.setString(7,agency.getAgencyfax());

        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }

    public static Integer updateAgency(Agency agency) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "Update agencies " +
                "SET agncyaddress = ?, " +
                "agncycity = ?, " +
                "agncyprov = ?, agncypostal = ?, agncycountry = ?, " +
                "agncyphone = ?, agncyfax = ? " +
                "Where agencyid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, agency.getAgencyaddress());
        stmt.setString(2,agency.getAgencycity());
        stmt.setString(3,agency.getAgencyprov());
        stmt.setString(4,agency.getAgencypostal());
        stmt.setString(5,agency.getAgencycountry());
        stmt.setString(6,agency.getAgencyphone());
        stmt.setString(7,agency.getAgencyfax());
        stmt.setInt(8,agency.getId());

        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }

    public static int deleteAgency(int agencyId) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "DELETE FROM agencies WHERE agencyid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,agencyId);
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

    public static ObservableList<Agency> searchAgenciesByString(String searchWord) throws SQLException {
        ObservableList<Agency> agencies = FXCollections.observableArrayList();
        Agency agency;
        Connection conn = getConnection();

        String SQL = "SELECT * FROM agencies WHERE " +
                "agncyaddress ilike '%" + searchWord + "%' " +
                "or agncycity  ilike '%" + searchWord + "%' " +
                "or agncyprov  ilike '%" + searchWord + "%'" +
                "or agncypostal  ilike '%" + searchWord + "%'" +
                "or agncycountry  ilike '%" + searchWord + "%'" +
                "or agncyphone  ilike '%" + searchWord + "%'" +
                "or agncyfax  ilike '%" + searchWord + "%'";
        PreparedStatement stmt = conn.prepareStatement(SQL);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            agency = new Agency(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8)
            );
            agencies.add(agency);
        }
        return agencies;
    }
}
