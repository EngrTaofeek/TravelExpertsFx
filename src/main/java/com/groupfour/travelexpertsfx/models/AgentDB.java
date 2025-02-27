package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * @Author DarylWxc
 * @Date 2/23/2025
 * @Description Agent DB
 */
public class AgentDB {
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

    public static ObservableList<Agent> getAgents() throws SQLException {
        ObservableList<Agent> agents = FXCollections.observableArrayList();
        Agent Agent;
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        String sql = "select agt.agentid, agt.agtfirstname, " +
                "agt.agtmiddleinitial, agt.agtlastname, " +
                "agt.agtbusphone, agt.agtemail, agt.agtposition, " +
                "agt.agencyid, CONCAT(agc.agncyaddress, ',', agc.agncycity, ',', agc.agncyprov, ',', agc.agncycountry) AS agency " +
                "FROM agents agt JOIN agencies agc ON agt.agencyid = agc.agencyid";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Agent = new Agent(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getInt(8),
                    rs.getString(9)
            );
            agents.add(Agent);
        }
        return agents;
    }

    public static int addAgent(Agent agent) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "INSERT INTO agents (agtfirstname, agtmiddleinitial, " +
                "agtlastname, agtbusphone, agtemail, agtposition, agencyid)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, agent.getAgtfirstname());
        stmt.setString(2,agent.getAgtmiddleinitial());
        stmt.setString(3,agent.getAgtlastname());
        stmt.setString(4,agent.getAgtbusphone());
        stmt.setString(5,agent.getAgtemail());
        stmt.setString(6,agent.getAgtposition());
        stmt.setInt(7,agent.getAgencyid());

        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }

    public static int updateAgent(Agent agent) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "Update agents " +
                "SET agtfirstname = ?, " +
                "agtmiddleinitial = ?, " +
                "agtlastname = ?, agtbusphone = ?, agtemail = ?, " +
                "agtposition = ?, agencyid = ? " +
                "Where agentid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, agent.getAgtfirstname());
        stmt.setString(2,agent.getAgtmiddleinitial());
        stmt.setString(3,agent.getAgtlastname());
        stmt.setString(4,agent.getAgtbusphone());
        stmt.setString(5,agent.getAgtemail());
        stmt.setString(6,agent.getAgtposition());
        stmt.setInt(7,agent.getAgencyid());
        stmt.setInt(8,agent.getId());

        numAffectedRows = stmt.executeUpdate();
        return numAffectedRows;
    }

    public static int deleteAgent(int agentId) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "DELETE FROM agents WHERE agentid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,agentId);
        numAffectedRows = stmt.executeUpdate();
        conn.close();
        return numAffectedRows;
    }

    public static ObservableList<Agent> searchAgents(String firstname, String middleInitial, String lastname, String phone,String email, String position, Integer agencyId) throws SQLException {
        ObservableList<Agent> agents = FXCollections.observableArrayList();
        Agent agent;
        Connection conn = getConnection();
        String SQL = "select agt.agentid, agt.agtfirstname, " +
                "agt.agtmiddleinitial, agt.agtlastname, " +
                "agt.agtbusphone, agt.agtemail, agt.agtposition, " +
                "agt.agencyid, CONCAT(agc.agncyaddress, ',', agc.agncycity, ',', agc.agncyprov, ',', agc.agncycountry) AS agency " +
                "FROM agents agt JOIN agencies agc ON agt.agencyid = agc.agencyid WHERE " +
                "(? IS NULL OR ? = '' OR LOWER(agt.agtfirstname) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agt.agtmiddleinitial) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agt.agtlastname) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agt.agtbusphone) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agt.agtemail) LIKE LOWER(?)) " +
                "AND (? IS NULL OR ? = '' OR LOWER(agt.agtposition) LIKE LOWER(?)) " +
                "AND (? IS NULL OR agt.agencyid = ?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);

        stmt.setString(1, firstname.isEmpty() ? null : firstname);
        stmt.setString(2, firstname.isEmpty() ? null : firstname);
        stmt.setString(3, "%" + firstname + "%");

        stmt.setString(4, middleInitial.isEmpty() ? null : middleInitial);
        stmt.setString(5, middleInitial.isEmpty() ? null : middleInitial);
        stmt.setString(6, "%" + middleInitial + "%");

        stmt.setString(7, lastname.isEmpty() ? null : lastname);
        stmt.setString(8, lastname.isEmpty() ? null : lastname);
        stmt.setString(9, "%" + lastname + "%");

        stmt.setString(10, phone.isEmpty() ? null : phone);
        stmt.setString(11, phone.isEmpty() ? null : phone);
        stmt.setString(12, "%" + phone + "%");

        stmt.setString(13, email.isEmpty() ? null : email);
        stmt.setString(14, email.isEmpty() ? null : email);
        stmt.setString(15, "%" + email + "%");

        stmt.setString(16, position.isEmpty() ? null : position);
        stmt.setString(17, position.isEmpty() ? null : position);
        stmt.setString(18, "%" + position + "%");

        if(agencyId == null) {
            stmt.setNull(19, Types.INTEGER);
            stmt.setNull(20, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(19, agencyId);
            stmt.setInt(20, agencyId);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            agent = new Agent(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getInt(8),
                    rs.getString(9)
            );
            agents.add(agent);
        }
        return agents;
    }
}
