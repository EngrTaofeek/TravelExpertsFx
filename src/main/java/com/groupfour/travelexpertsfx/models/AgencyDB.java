package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

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

    public static ObservableList<Agency> searchAgencies(String address, String city, String prov, String postal, String country, String phone, String fax) throws SQLException {
        ObservableList<Agency> agencies = FXCollections.observableArrayList();
        Agency agency;
        Connection conn = getConnection();

        String SQL = "SELECT * FROM agencies WHERE " +
                "(? IS NULL OR ? = '' OR LOWER(agncyaddress) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agncycity) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agncyprov) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agncypostal) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agncycountry) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agncyphone) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agncyfax) LIKE LOWER(?)) ";
        PreparedStatement stmt = conn.prepareStatement(SQL);

        stmt.setString(1, address.isEmpty() ? null : address);
        stmt.setString(2, address.isEmpty() ? null : address);
        stmt.setString(3, "%" + address + "%");

        stmt.setString(4, city.isEmpty() ? null : city);
        stmt.setString(5, city.isEmpty() ? null : city);
        stmt.setString(6, "%" + city + "%");

        stmt.setString(7, prov.isEmpty() ? null : prov);
        stmt.setString(8, prov.isEmpty() ? null : prov);
        stmt.setString(9, "%" + prov + "%");

        stmt.setString(10, postal.isEmpty() ? null : postal);
        stmt.setString(11, postal.isEmpty() ? null : postal);
        stmt.setString(12, "%" + postal + "%");

        stmt.setString(13, country.isEmpty() ? null : country);
        stmt.setString(14, country.isEmpty() ? null : country);
        stmt.setString(15, "%" + country + "%");

        stmt.setString(16, phone.isEmpty() ? null : phone);
        stmt.setString(17, phone.isEmpty() ? null : phone);
        stmt.setString(18, "%" + phone + "%");

        stmt.setString(19, fax.isEmpty() ? null : fax);
        stmt.setString(20, fax.isEmpty() ? null : fax);
        stmt.setString(21, "%" + fax + "%");

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
