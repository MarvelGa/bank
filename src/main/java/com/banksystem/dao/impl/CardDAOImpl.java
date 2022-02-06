package com.banksystem.dao.impl;

import com.banksystem.dao.CardDAO;
import com.banksystem.dao.dbmanager.DBManager;
import com.banksystem.dto.CardDTO;
import com.banksystem.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAOImpl implements CardDAO {
    private final static String CREATE_CARD = "INSERT INTO cards (`number`, amount, currency, account_id, expiration_date) VALUES (?,?,?,?,?)";

    private final static String GET_CARDS_BY_CLIENT_ID = "SELECT cr.id, " +
            "cr.number, " +
            "cr.amount, " +
            "cr.currency, " +
            "cr.expiration_date " +
            "FROM cards cr " +
            "JOIN accounts a ON cr.account_id=a.id " +
            "JOIN clients c ON a.client_id=c.id WHERE c.id=?;";

    private final static String GET_CARD_BY_ID = "SELECT cr.id, cr.number, cr.amount, cr.currency, cr.expiration_date FROM cards cr WHERE cr.id=?";

    private final static String UPDATE_CARD = "UPDATE cards SET expiration_date=? WHERE id=?";

    private final static String DELETE_CARD = "DELETE FROM cards WHERE id=?";

    @Override
    public Long create(CardDTO item, Long clientId) throws DAOException {
        final String query = CREATE_CARD;
        Long insertedCard = -1L;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getNumber());
            pstmt.setLong(2, item.getAmount());
            pstmt.setString(3, String.valueOf(item.getCurrency()));
            pstmt.setLong(4, clientId);
            pstmt.setDate(5, Date.valueOf(item.getExpirationDate()));
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                insertedCard = rs.getLong(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't create Card", ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return insertedCard;
    }

    @Override
    public CardDTO read(Long id) throws DAOException {
        final String query = GET_CARD_BY_ID;
        CardDTO card = null;
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
                card = new CardDTO();
                card.setId(rs.getLong("id"));
                card.setNumber(rs.getString("number"));
                card.setAmount(rs.getLong("amount"));
                card.setCurrency(rs.getString("currency"));
                card.setExpirationDate(rs.getString("expiration_date"));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't read Card", ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return card;
    }

    @Override
    public boolean update(CardDTO item) throws DAOException {
        final String query = UPDATE_CARD;
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, item.getExpirationDate());
            pstmt.setLong(2, item.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't update Card", ex);
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
        final String query = DELETE_CARD;
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
            throw new DAOException("Can't delete the Card by id", ex);
        } finally {
            DBManager.close(con, ptsmt);
        }
        return true;
    }

    @Override
    public List<CardDTO> readAll(Long clientId) throws DAOException {
        final String query = GET_CARDS_BY_CLIENT_ID;
        List<CardDTO> cards = new ArrayList<>();
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setLong(1, clientId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CardDTO card = new CardDTO();
                card.setId(rs.getLong("id"));
                card.setNumber(rs.getString("number"));
                card.setAmount(rs.getLong("amount"));
                card.setCurrency(rs.getString("currency"));
                card.setExpirationDate(rs.getString("expiration_date"));
                cards.add(card);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            throw new DAOException("Can't get Cards by clientId", ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cards;
    }
}
