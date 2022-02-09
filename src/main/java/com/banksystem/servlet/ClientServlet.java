package com.banksystem.servlet;

import com.banksystem.dao.ClientDAO;
import com.banksystem.dao.impl.ClientDAOImpl;
import com.banksystem.dto.RequestClient;
import com.banksystem.entity.Client;
import com.banksystem.entity.User;
import com.banksystem.exceptions.DAOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/bank-api/clients/*")
public class ClientServlet extends HttpServlet {
    private ClientDAO userDAO;

    @Override
    public void init() throws ServletException {
        this.userDAO = new ClientDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Client client = null;
        if (pathInfo != null) {
            String[] parts = pathInfo.split("/");

            if (parts[1].matches("[0-9]+")) {
                Long clientId = Long.parseLong(parts[1]);
                try {
                    client = userDAO.read(clientId);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
            PrintWriter out = resp.getWriter();

            if (client != null) {
                final String jsonClients = new ObjectMapper().writeValueAsString(client);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(200);
                out.write(jsonClients);
            } else {
                out.write(String.format("There are no client with such id!"));
            }
            return;
        }

        List<Client> clients = null;
        try {
            clients = userDAO.readAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        PrintWriter out = resp.getWriter();
        if (clients != null) {
            final String jsonClients = new ObjectMapper().writeValueAsString(clients);
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            out.write(jsonClients);
        } else {
            out.write("There are no clients yet!");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if (req.getContentType().equals("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            RequestClient requestClient = null;
            try {
                requestClient = mapper.readValue(req.getInputStream(), RequestClient.class);
            } catch (JsonParseException ex) {
                out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                return;
            }

            if (requestClient != null) {
                Client client = Client.builder()
                        .withFirstName(requestClient.getFirstName())
                        .withLastName(requestClient.getLastName())
                        .withEmail(requestClient.getEmail())
                        .withPassword(requestClient.getPassword())
                        .withRole(User.Role.valueOf(requestClient.getRole()))
                        .withDateOfBirth(requestClient.getDateOfBirth())
                        .build();

                Long result = null;
                Client insertedClient = null;
                try {
                    if (userDAO.readAll().stream().anyMatch(c -> c.getEmail().equals(client.getEmail()))) {
                        out.write(String.format("Client with such email=%s already exists in DB", client.getEmail()));
                        return;
                    }
                    result = userDAO.create(client);
                    insertedClient = userDAO.read(result);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                final String jsonClients = new ObjectMapper().writeValueAsString(insertedClient);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(200);
                out.write(jsonClients);
            }
            return;
        }
        out.write("Something went wrong");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        if (req.getContentType().equals("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            RequestClient requestClient = null;
            try {
                requestClient = mapper.readValue(req.getInputStream(), RequestClient.class);
            } catch (JsonParseException ex) {
                out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                return;
            }

            Client updatedClient = null;

            if (requestClient != null) {
                Client client = Client.builder()
                        .withId(requestClient.getId())
                        .withFirstName(requestClient.getFirstName())
                        .withLastName(requestClient.getLastName())
                        .withEmail(requestClient.getEmail())
                        .withPassword(requestClient.getPassword())
                        .build();

                try {
                    userDAO.update(client);
                    updatedClient = userDAO.read(requestClient.getId());
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                final String jsonClients = new ObjectMapper().writeValueAsString(updatedClient);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(200);
                out.write(jsonClients);
            }
            return;
        }
        out.write("Something went wrong");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();
        Client client = null;
        if (pathInfo != null) {
            String[] parts = pathInfo.split("/");

            if (parts[1].matches("[0-9]+")) {
                Long clientId = Long.parseLong(parts[1]);

                try {
                    Client foundClient = userDAO.read(clientId);
                    if (foundClient == null) {
                        out.write(String.format("There not client with id=%d", clientId));
                        return;
                    }
                    userDAO.delete(clientId);
                    out.write(String.format("Client with id=%d was deleted", clientId));
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        out.write("Something went wrong");
    }
}
