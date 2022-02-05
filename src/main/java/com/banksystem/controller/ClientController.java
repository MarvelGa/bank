package com.banksystem.controller;

import com.banksystem.dao.ClientDAO;
import com.banksystem.dao.impl.ClientDAOImpl;
import com.banksystem.dto.RequestClient;
import com.banksystem.entity.Client;
import com.banksystem.entity.User;
import com.banksystem.exceptions.DAOException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bank-api")
@RestController
public class ClientController {
    public ClientDAO userDAO;

    public ClientController() {
        this.userDAO = new ClientDAOImpl();
    }

    @GetMapping("/{managerId}/clients")
    public List<Client> getClientsByManagerId(@PathVariable Long managerId) {
        List<Client> result = null;
        try {
            result = userDAO.getAllClientsByManagerId(managerId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/clients")
    public List<Client> getClients() {
        List<Client> result = null;
        try {
            result = userDAO.readAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/client/{clientId}")
    public Client getClientById(@PathVariable Long clientId) {
        Client result = null;
        try {
            result = userDAO.read(clientId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/add/client")
    public Client createClient(@RequestBody RequestClient request) {
        Client client = Client.builder()
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .withRole(User.Role.valueOf(request.getRole()))
                .withDateOfBirth(request.getDateOfBirth())
                .build();

        Long result = null;
        try {
            result = userDAO.create(client);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return getClientById(result);
    }

    @DeleteMapping("/client/{clientId}")
    public void deleteClientById(@PathVariable Long clientId) {
        try {
            userDAO.delete(clientId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/client")
    public Client updateClient(@RequestBody RequestClient request) {
        Client updated = null;

        Client client = Client.builder()
                .withId(request.getId())
                .withFirstName(request.getFirstName())
                .withLastName(request.getLastName())
                .withEmail(request.getEmail())
                .withPassword(request.getPassword())
                .build();

        try {
            userDAO.update(client);
            updated = userDAO.read(request.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return updated;
    }
}
