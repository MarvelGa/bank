package com.banksystem.dao;

import com.banksystem.exceptions.DAOException;

import java.util.List;

public interface BaseDAO<T> {
    Long create(final T item) throws DAOException;
    T read(final Long id) throws DAOException;
    boolean update(final T item) throws DAOException;
    boolean delete(final Long id) throws DAOException;
    List<T> readAll() throws DAOException;
}
