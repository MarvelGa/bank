package com.banksystem.dao;

import com.banksystem.entity.Client;
import com.banksystem.exceptions.DAOException;

import java.util.List;

public interface ClientDAO extends BaseDAO<Client>{
    List<Client> getAllClientsByManagerId(Long managerId) throws DAOException;
}
