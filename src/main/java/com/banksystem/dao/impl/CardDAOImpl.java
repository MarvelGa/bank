package com.banksystem.dao.impl;

import com.banksystem.dao.CardDAO;
import com.banksystem.entity.Card;
import com.banksystem.exceptions.DAOException;

import java.util.List;

public class CardDAOImpl implements CardDAO {
    private final static String CREATE_CARD ="";
    private final static String GET_CARD_BY_ID ="";
    private final static String UPDATE_CARD ="";
    private final static String DELETE_CARD ="";
    private final static String REAL_ALL_CARDS ="";


    @Override
    public Long create(Card item) throws DAOException {
        return null;
    }

    @Override
    public Card read(Long id) throws DAOException {
        return null;
    }

    @Override
    public boolean update(Card item) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        return false;
    }

    @Override
    public List<Card> readAll() throws DAOException {
        return null;
    }
}
