package com.banksystem.controller;

import com.banksystem.dao.ManagerDAO;
import com.banksystem.dao.impl.ManagerDAOImpl;
import com.banksystem.dto.RequestManager;
import com.banksystem.entity.Manager;
import com.banksystem.entity.User;
import com.banksystem.exceptions.DAOException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bank-api")
@RestController
public class ManagerController {
    public ManagerDAO managerDAO;

    public ManagerController() {
        this.managerDAO = new ManagerDAOImpl();
    }

        @GetMapping("/{clientId}/managers")
    public List<Manager> getManagersByClientId(@PathVariable Long clientId) {
        List<Manager> result = null;
        try {
            result = managerDAO.getAllManagersByClientId(clientId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/managers")
    public List<Manager> getManagers() {
        List<Manager> result = null;
        try {
            result = managerDAO.readAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/manager/{managerId}")
    public Manager getManagerById(@PathVariable Long managerId) {
        Manager result = null;
        try {
            result = managerDAO.read(managerId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/add/manager")
    public  Manager createManager(@RequestBody RequestManager request) {
        Manager manager = Manager.builder()
                .withSpecialization(request.getSpecialization())
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .withRole(User.Role.valueOf(request.getRole()))
                .withDateOfBirth(request.getDateOfBirth())
                .build();

        Long result = null;
        try {
            result = managerDAO.create(manager);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return getManagerById(result);
    }

    @DeleteMapping("/manager/{managerId}")
    public void deleteManagerById(@PathVariable Long managerId) {
        try {
            managerDAO.delete(managerId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/manager")
    public Manager updateClient(@RequestBody RequestManager request) {
        Manager updated = null;

        Manager manager = Manager.builder()
                .withId(request.getId())
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .build();

        try {
            managerDAO.update(manager);
            updated = managerDAO.read(request.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return updated;
    }
}
