package com.banksystem.dao.impl;

import com.banksystem.dao.AccountDAO;
import com.banksystem.dao.dbmanager.DBManager;
import com.banksystem.dto.AccountResponse;
import com.banksystem.dto.RequestAccount;
import com.banksystem.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    private static final String CREATE_ACCOUNT = "INSERT INTO accounts (manager_id, client_id) VALUES (?,?)";

    private static final String GET_ACCOUNT_BY_ID = "SELECT a.id AS AccountId, " +
            "c.id As ClientId, " +
            "m.id As ManagerId, " +
            "u.first_name AS ClientFirstName, " +
            "u.last_name AS ClientLastName, " +
            "(select u.last_name from users u JOIN managers m ON m.user_id=u.id JOIN accounts ac ON ac.manager_id=m.id AND ac.id=a.id) AS ManagerLastName  " +
            "FROM accounts a " +
            "JOIN managers m ON m.id=a.manager_id " +
            "JOIN clients c ON c.id=a.client_id " +
            "JOIN users u ON u.id=c.user_id " +
            "WHERE a.id=?;";

    private static final String UPDATE_ACCOUNT = "UPDATE accounts SET manager_id=? WHERE id=?;";

    private static final String DELETE_ACCOUNT = "DELETE FROM accounts a WHERE a.id=?";

    private static final String GET_ALL_ACCOUNTS = "SELECT a.id AS AccountId, " +
            "c.id As ClientId, " +
            "m.id As ManagerId, " +
            "u.first_name AS ClientFirstName, " +
            "u.last_name AS ClientLastName, " +
            "(select u.last_name from users u JOIN managers m ON m.user_id=u.id JOIN accounts ac ON ac.manager_id=m.id AND ac.id=a.id) AS ManagerLastName  " +
            "FROM accounts a " +
            "JOIN managers m ON m.id=a.manager_id " +
            "JOIN clients c ON c.id=a.client_id " +
            "JOIN users u ON u.id=c.user_id; ";

    @Override
    public Long create(RequestAccount item) throws DAOException {
        final String query = CREATE_ACCOUNT;
        DBManager dbm;
        PreparedStatement pstmt = null;
        Long insertedAccount = -1L;
        Connection con = null;
        ResultSet rs = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, item.getManagerId());
            pstmt.setLong(2, item.getClientId());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                insertedAccount = rs.getLong(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException(String.format("Can't create Account by managerId=%d and userId=%d", item.getManagerId(), item.getClientId()), ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return insertedAccount;
    }

    @Override
    public AccountResponse read(Long id) throws DAOException {
        final String query = GET_ACCOUNT_BY_ID;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AccountResponse accountResponse = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accountResponse = new AccountResponse();
                accountResponse.setAccountId(rs.getLong("AccountId"));
                accountResponse.setClientId(rs.getLong("ClientId"));
                accountResponse.setManagerId(rs.getLong("ManagerId"));
                accountResponse.setClientFirstName(rs.getString("ClientFirstName"));
                accountResponse.setClientLastName(rs.getString("ClientLastName"));
                accountResponse.setManagerLastName(rs.getString("ManagerLastName"));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't read the Account", ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return accountResponse;
    }

    @Override
    public boolean update(RequestAccount item) throws DAOException {
        final String query = UPDATE_ACCOUNT;
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, item.getManagerId());
            pstmt.setLong(2, item.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't update Client", ex);
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
        final String query = DELETE_ACCOUNT;
        DBManager dbm;
        Connection con = null;
        PreparedStatement ptsmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            ptsmt = con.prepareStatement(query);
            ptsmt.setLong(1, id);
            ptsmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't delete the Account by id", ex);
        } finally {
            DBManager.close(con, ptsmt);
        }
        return true;
    }

    @Override
    public List<AccountResponse> readAll() throws DAOException {
        final String query = GET_ALL_ACCOUNTS;
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AccountResponse> accountResponses = new ArrayList<>();
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                AccountResponse accountResponse = new AccountResponse();
                accountResponse.setAccountId(rs.getLong("AccountId"));
                accountResponse.setClientId(rs.getLong("ClientId"));
                accountResponse.setManagerId(rs.getLong("ManagerId"));
                accountResponse.setClientFirstName(rs.getString("ClientFirstName"));
                accountResponse.setClientLastName(rs.getString("ClientLastName"));
                accountResponse.setManagerLastName(rs.getString("ManagerLastName"));
                accountResponses.add(accountResponse);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't read all Accounts", ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return accountResponses;
    }
}
