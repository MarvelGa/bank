package com.banksystem.dao.impl;

import com.banksystem.dao.ClientDAO;
import com.banksystem.dao.dbmanager.DBManager;
import com.banksystem.entity.Client;
import com.banksystem.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private static final String GET_CLIENTS_BY_MANAGER = "SELECT c.id, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.birth_day FROM clients c " +
            "JOIN users u ON u.id=c.user_id " +
            "WHERE c.id IN (SELECT a.client_id FROM accounts a JOIN managers m ON a.manager_id=m.id AND a.client_id=c.id AND m.id=?);";

    private static final String GET_ALL_CLIENTS = "SELECT c.id, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.birth_day " +
            "FROM clients c " +
            "JOIN users u ON u.id=c.user_id";

    private static final String GET_CLIENT_BY_ID = "SELECT c.id, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.birth_day " +
            "FROM clients c " +
            "JOIN users u ON u.id=c.user_id WHERE c.id=?";

    private static final String INSERT_USER = "INSERT INTO users (first_name, last_name, email, passwords, `role`, birth_day) VALUES ( ?, ?, ?, ?, ?, ? );";
    private static final String INSERT_CLIENT = "INSERT INTO clients (user_id) VALUES (?);";
    private static final String DELETE_CLIENT = "DELETE FROM users u WHERE u.id=(SELECT c.user_id FROM clients c WHERE c.id=?)";

    @Override
    public List<Client> getAllClientsByManagerId(Long managerId) throws DAOException {
        final String query = GET_CLIENTS_BY_MANAGER;
        List<Client> clients = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, managerId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Client client = Client.builder()
                        .withId(rs.getLong("id"))
                        .withFirstName(rs.getString("first_name"))
                        .withLastName(rs.getString("last_name"))
                        .withDateOfBirth(rs.getString("birth_day"))
                        .build();
                clients.add(client);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return clients;
    }

    @Override
    public Long create(Client item) throws DAOException {
        final String insertUser = INSERT_USER;
        final String insertClient = INSERT_CLIENT;
        Long insertedClientId = -1L;
        Long insertedUserId = -1L;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            con.setAutoCommit(false);

            pstmt = con.prepareStatement(insertUser, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getFirstName());
            pstmt.setString(2, item.getLastName());
            pstmt.setString(3, item.getEmail());
            pstmt.setString(4, item.getPassword());
            pstmt.setString(5, String.valueOf(item.getRole()));
            pstmt.setDate(6, Date.valueOf(item.getDateOfBirth()));
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                insertedUserId = rs.getLong(1);
            }

            pstmt = con.prepareStatement(insertClient, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, insertedUserId);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                insertedClientId = rs.getLong(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            ex.printStackTrace();
        }
        return insertedClientId;
    }

    @Override
    public Client read(Long id) throws DAOException {
        final String query = GET_CLIENT_BY_ID;
        Client client = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                client = Client.builder()
                        .withId(rs.getLong("id"))
                        .withFirstName(rs.getString("first_name"))
                        .withLastName(rs.getString("last_name"))
                        .withDateOfBirth(rs.getString("birth_day"))
                        .build();
            }
        } catch (SQLException ex) {
            DBManager.rollback(con);
            ex.printStackTrace();
        }
        return client;
    }

    @Override
    public boolean update(Client item) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        if (id <= 0) {
            return false;
        }
        final String queryClient = DELETE_CLIENT;
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(queryClient);
            psmt.setLong(1, id);
            psmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    @Override
    public List<Client> readAll() throws DAOException {
        final String query = GET_ALL_CLIENTS;
        List<Client> clients = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                Client client = Client.builder()
                        .withId(rs.getLong("id"))
                        .withFirstName(rs.getString("first_name"))
                        .withLastName(rs.getString("last_name"))
                        .withDateOfBirth(rs.getString("birth_day"))
                        .build();
                clients.add(client);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return clients;
    }
}
