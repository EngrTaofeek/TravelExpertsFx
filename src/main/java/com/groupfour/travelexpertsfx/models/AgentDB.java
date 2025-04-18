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
                "agt.agencyid, CONCAT(agc.agncyaddress, ',', agc.agncycity, ',', agc.agncyprov, ',', agc.agncycountry), agt.role AS agency " +
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
                    rs.getString(9),
                    rs.getString(10)
            );
            agents.add(Agent);
        }
        return agents;
    }

    public static int addAgent(Agent agent) throws SQLException {
        Connection conn = getConnection();
        int numAffectedRows = 0;
        String sql = "INSERT INTO agents (agtfirstname, agtmiddleinitial, " +
                "agtlastname, agtbusphone, agtemail, agtposition, agencyid, role, status)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, agent.getAgtfirstname());
        stmt.setString(2,agent.getAgtmiddleinitial());
        stmt.setString(3,agent.getAgtlastname());
        stmt.setString(4,agent.getAgtbusphone());
        stmt.setString(5,agent.getAgtemail());
        stmt.setString(6,agent.getAgtposition());
        stmt.setInt(7,agent.getAgencyid());
        stmt.setString(8,agent.getRole());
        stmt.setString(9, "approved");

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
                "agtposition = ?, agencyid = ? ,role = ? " +
                "Where agentid = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, agent.getAgtfirstname());
        stmt.setString(2,agent.getAgtmiddleinitial());
        stmt.setString(3,agent.getAgtlastname());
        stmt.setString(4,agent.getAgtbusphone());
        stmt.setString(5,agent.getAgtemail());
        stmt.setString(6,agent.getAgtposition());
        stmt.setInt(7,agent.getAgencyid());
        stmt.setString(8,agent.getRole());
        stmt.setInt(9,agent.getId());


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

    public static ObservableList<Agent> searchAgents(String searchWord,Integer agencyId) throws SQLException {
        ObservableList<Agent> agents = FXCollections.observableArrayList();
        Agent agent;
        Connection conn = getConnection();
        String SQL = "select agt.agentid, agt.agtfirstname, " +
                "agt.agtmiddleinitial, agt.agtlastname, " +
                "agt.agtbusphone, agt.agtemail, agt.agtposition, " +
                "agt.agencyid, CONCAT(agc.agncyaddress, ',', agc.agncycity, ',', agc.agncyprov, ',', agc.agncycountry), agt.role AS agency " +
                "FROM agents agt JOIN agencies agc ON agt.agencyid = agc.agencyid WHERE " +
                "(agt.agtfirstname ilike '%" + searchWord + "%' " +
                "or agt.agtmiddleinitial ilike '%" + searchWord + "%' " +
                "or agt.agtlastname ilike '%" + searchWord + "%' " +
                "or agt.agtbusphone ilike '%" + searchWord + "%' " +
                "or agt.agtemail ilike '%" + searchWord + "%' " +
                "or agt.agtposition ilike '%" + searchWord + "%') " +
                "AND (? IS NULL OR agt.agencyid = ?)";
        PreparedStatement stmt = conn.prepareStatement(SQL);


        if(agencyId == null) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setNull(2, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(1, agencyId);
            stmt.setInt(2, agencyId);
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
                    rs.getString(9),
                    rs.getString(10)
            );
            agents.add(agent);
        }
        return agents;
    }
}
