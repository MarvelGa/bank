package com.banksystem.dao;

import com.banksystem.entity.Manager;
import com.banksystem.exceptions.DAOException;

import java.util.List;

public interface ManagerDAO extends BaseDAO<Manager> {
    List<Manager> getAllManagersByClientId(Long clientId) throws DAOException;
}
