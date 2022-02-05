package com.banksystem.dao.impl;

import com.banksystem.dao.ManagerDAO;
import com.banksystem.dao.dbmanager.DBManager;
import com.banksystem.entity.Manager;
import com.banksystem.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAOImpl implements ManagerDAO {
    private static final String GET_MANAGERS_BY_CLIENT_ID = "SELECT m.id, " +
            "m.spacialization, " +
            "u.first_name, " +
            "u.last_name " +
            "FROM managers m J" +
            "OIN users u ON u.id=m.user_id WHERE m.id IN (SELECT a.manager_id FROM accounts a JOIN clients c ON a.client_id=c.id AND a.manager_id=m.id AND c.id=?)";


    private static final String INSERT_USER = "INSERT INTO users (first_name, last_name, email, passwords, `role`, birth_day) VALUES ( ?, ?, ?, ?, ?, ? );";
    private static final String INSERT_MANAGER = "INSERT INTO managers (spacialization, user_id) VALUES (?,?);";
    private static final String GET_MANAGER_BY_ID = "SELECT m.id, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.birth_day, " +
            "m.spacialization " +
            "FROM managers m " +
            "JOIN users u ON u.id=m.user_id WHERE m.id=?";

    private static final String GET_ALL_MANAGERS = "SELECT m.id, " +
            "m.spacialization, " +
            "u.first_name, " +
            "u.last_name, " +
            "u.birth_day " +
            "FROM managers m " +
            "JOIN users u ON u.id=m.user_id";

    private static final String UPDATE_MANAGER = "UPDATE users SET first_name=?, last_name=?, email=?, passwords=? WHERE id =(SELECT m.user_id FROM managers m WHERE m.id=?)";
    private static final String DELETE_CLIENT = "DELETE FROM users u WHERE u.id=(SELECT m.user_id FROM managers m WHERE m.id=?)";

    @Override
    public Long create(Manager item) throws DAOException {
        final String insertUser = INSERT_USER;
        final String insertClient = INSERT_MANAGER;
        Long insertedManagerId = -1L;
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
            pstmt.setString(1, item.getSpecialization());
            pstmt.setLong(2, insertedUserId);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                insertedManagerId = rs.getLong(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't create Manager", ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return insertedManagerId;
    }

    @Override
    public Manager read(Long id) throws DAOException {
        final String query = GET_MANAGER_BY_ID;
        Manager manager = null;
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
                manager = Manager.builder()
                        .withId(rs.getLong("id"))
                        .withFirstName(rs.getString("first_name"))
                        .withLastName(rs.getString("last_name"))
                        .withDateOfBirth(rs.getString("birth_day"))
                        .withSpecialization(rs.getString("spacialization"))
                        .build();
            }
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't read Manager", ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return manager;
    }

    @Override
    public boolean update(Manager item) throws DAOException {
        final String query = UPDATE_MANAGER;
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setString(k++, item.getFirstName());
            pstmt.setString(k++, item.getLastName());
            pstmt.setString(k++, item.getEmail());
            pstmt.setString(k++, item.getPassword());
            pstmt.setLong(k, item.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't update Manager", ex);
        } finally {
            DBManager.close(con, pstmt);
        }
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
            throw new DAOException("Can't delete Manager by id", ex);
        } finally {
            DBManager.close(con, psmt);

        }
        return true;
    }

    @Override
    public List<Manager> readAll() throws DAOException {
        final String query = GET_ALL_MANAGERS;
        List<Manager> managers = new ArrayList<>();
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
                Manager client = Manager.builder()
                        .withId(rs.getLong("id"))
                        .withSpecialization(rs.getString("spacialization"))
                        .withFirstName(rs.getString("first_name"))
                        .withLastName(rs.getString("last_name"))
                        .withDateOfBirth(rs.getString("birth_day"))
                        .build();
                managers.add(client);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't read all Managers", ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return managers;
    }

    @Override
    public List<Manager> getAllManagersByClientId(Long clientId) throws DAOException {
        final String query = GET_MANAGERS_BY_CLIENT_ID;
        List<Manager> managers = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, clientId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Manager manager = Manager.builder()
                        .withId(rs.getLong("id"))
                        .withFirstName(rs.getString("spacialization"))
                        .withFirstName(rs.getString("first_name"))
                        .withLastName(rs.getString("last_name"))
                        .build();
                managers.add(manager);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't get Managers by Client's id", ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return managers;
    }
}
