package com.banksystem.dao;

import com.banksystem.entity.Manager;

import java.util.List;

public interface ManagerDAO {
    List<Manager> getAllManagersByClientId(Long managerId);
    List<Manager> getAllManagers();
    Manager getById(Long managerId);
    Manager addClient(Manager manager);
    Manager updateClient(Manager manager);
    void deleteManagerById(Long managerId);
}
