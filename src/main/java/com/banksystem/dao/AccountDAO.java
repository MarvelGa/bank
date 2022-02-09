package com.banksystem.dao;

import com.banksystem.dto.AccountResponse;
import com.banksystem.dto.RequestAccount;
import com.banksystem.entity.Account;
import com.banksystem.exceptions.DAOException;

import java.util.List;

public interface AccountDAO{
    Long create(final RequestAccount item) throws DAOException;
    AccountResponse read(final Long id) throws DAOException;
    boolean update(final RequestAccount item) throws DAOException;
    boolean delete(final Long id) throws DAOException;
    List<AccountResponse> readAll() throws DAOException;
}
