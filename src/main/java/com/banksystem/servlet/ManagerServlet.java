package com.banksystem.servlet;

import com.banksystem.dao.ManagerDAO;
import com.banksystem.dao.impl.ManagerDAOImpl;
import com.banksystem.dto.RequestManager;
import com.banksystem.entity.Manager;
import com.banksystem.entity.User;
import com.banksystem.exceptions.DAOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/bank-api/managers/*")
public class ManagerServlet extends HttpServlet {
    public ManagerDAO managerDAO;


    @Override
    public void init() throws ServletException {
        this.managerDAO = new ManagerDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Manager manager = null;
        if (pathInfo != null) {
            String[] parts = pathInfo.split("/");

            if (parts[1].matches("[0-9]+")) {
                Long clientId = Long.parseLong(parts[1]);
                try {
                    manager = managerDAO.read(clientId);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
            PrintWriter out = resp.getWriter();

            if (manager != null) {
                final String jsonClients = new ObjectMapper().writeValueAsString(manager);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(200);
                out.write(jsonClients);
            } else {
                out.write(String.format("There are no client with such id!"));
            }
            return;
        }

        List<Manager> managers = null;
        try {
            managers = managerDAO.readAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        PrintWriter out = resp.getWriter();
        if (managers != null) {
            final String jsonClients = new ObjectMapper().writeValueAsString(managers);
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            out.write(jsonClients);
        } else {
            out.write("There are no managers yet!");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if (req.getContentType().equals("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            RequestManager requestManager = null;
            try {
                requestManager = mapper.readValue(req.getInputStream(), RequestManager.class);
            } catch (JsonParseException ex) {
                out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                return;
            }

            if (requestManager != null) {
                Manager manager = Manager.builder()
                        .withSpecialization(requestManager.getSpecialization())
                        .withFirstName(requestManager.getFirstName())
                        .withLastName(requestManager.getLastName())
                        .withEmail(requestManager.getEmail())
                        .withPassword(requestManager.getPassword())
                        .withRole(User.Role.valueOf(requestManager.getRole()))
                        .withDateOfBirth(requestManager.getDateOfBirth())
                        .build();

                Long result = null;
                Manager insertedManager = null;
                try {
                    if (managerDAO.readAll().stream().anyMatch(m -> m.getEmail().equals(manager.getEmail()))) {
                        out.write(String.format("Manager with such email=%s already exists in DB", manager.getEmail()));
                        return;
                    }
                    result = managerDAO.create(manager);
                    insertedManager = managerDAO.read(result);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                final String jsonManager = new ObjectMapper().writeValueAsString(insertedManager);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(200);
                out.write(jsonManager);
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
            RequestManager requestManager = null;
            try {
                requestManager = mapper.readValue(req.getInputStream(), RequestManager.class);
            } catch (JsonParseException ex) {
                out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                return;
            }

            Manager updatedManager = null;

            if (requestManager != null) {
                Manager manager = Manager.builder()
                        .withId(requestManager.getId())
                        .withFirstName(requestManager.getFirstName())
                        .withLastName(requestManager.getLastName())
                        .withEmail(requestManager.getEmail())
                        .withPassword(requestManager.getPassword())
                        .build();

                try {
                    managerDAO.update(manager);
                    updatedManager = managerDAO.read(requestManager.getId());
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                final String jsonManager = new ObjectMapper().writeValueAsString(updatedManager);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(200);
                out.write(jsonManager);
            }
            return;
        }
        out.write("Something went wrong");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            String[] parts = pathInfo.split("/");

            if (parts[1].matches("[0-9]+")) {
                Long managerId = Long.parseLong(parts[1]);

                try {
                    Manager foundManager = managerDAO.read(managerId);
                    if (foundManager == null) {
                        out.write(String.format("There not client with id=%d", managerId));
                        return;
                    }
                    managerDAO.delete(managerId);
                    out.write(String.format("Manager with id=%d was deleted", managerId));
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        out.write("Something went wrong");
    }
}
