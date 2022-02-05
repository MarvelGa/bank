package com.banksystem.dao;

import com.banksystem.dto.AccountResponse;
import com.banksystem.entity.Account;
import com.banksystem.exceptions.DAOException;

import java.util.List;

public interface AccountDAO{
    Long create(final Account item) throws DAOException;
    AccountResponse read(final Long id) throws DAOException;
    boolean update(final Account item) throws DAOException;
    boolean delete(final Long id) throws DAOException;
    List<AccountResponse> readAll() throws DAOException;
}
