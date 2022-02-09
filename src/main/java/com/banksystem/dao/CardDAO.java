package com.banksystem.dao;

import com.banksystem.dto.CardDTO;
import com.banksystem.exceptions.DAOException;

import java.util.List;

public interface CardDAO {
    Long create(final CardDTO item, final Long clientId) throws DAOException;
    CardDTO read(final Long id) throws DAOException;
    boolean update(final CardDTO item) throws DAOException;
    boolean delete(final Long id) throws DAOException;
    List<CardDTO> readAll(final Long clientId) throws DAOException;
    List<CardDTO> readAllCards() throws DAOException;
}
