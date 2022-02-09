package com.banksystem.servlet;

import com.banksystem.dao.AccountDAO;
import com.banksystem.dao.impl.AccountDAOImpl;
import com.banksystem.dto.AccountResponse;
import com.banksystem.dto.RequestAccount;
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

@WebServlet(urlPatterns = "/bank-api/accounts/*")
public class AccountServlet extends HttpServlet {
    public AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        this.accountDAO = new AccountDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        AccountResponse account = null;
        if (pathInfo != null) {
            String[] parts = pathInfo.split("/");

            if (parts[1].matches("[0-9]+")) {
                Long accountId = Long.parseLong(parts[1]);
                try {
                    account = accountDAO.read(accountId);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
            }
            PrintWriter out = resp.getWriter();

            if (account != null) {
                final String jsonClients = new ObjectMapper().writeValueAsString(account);
                resp.setContentType("application/json; charset=UTF-8");
                resp.setStatus(200);
                out.write(jsonClients);
            } else {
                out.write(String.format("There are no an account with such id!"));
            }
            return;
        }

        List<AccountResponse> accounts = null;
        try {
            accounts = accountDAO.readAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        PrintWriter out = resp.getWriter();
        if (accounts != null) {
            final String jsonClients = new ObjectMapper().writeValueAsString(accounts);
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            out.write(jsonClients);
        } else {
            out.write("There are no accounts yet!");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        if (req.getContentType().equals("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            RequestAccount requestAccount = null;
            try {
                requestAccount = mapper.readValue(req.getInputStream(), RequestAccount.class);
            } catch (JsonParseException ex) {
                out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                return;
            }

            if (requestAccount != null) {

                Long result = null;
                AccountResponse insertedAccount = null;
                try {
                    RequestAccount finalRequestAccount = requestAccount;
                    if (accountDAO.readAll().stream().anyMatch(a -> a.getManagerId().equals(finalRequestAccount.getManagerId()) && a.getClientId().equals(finalRequestAccount.getClientId()))) {
                        out.write("Such account already exists in DB");
                        return;
                    }
                    result = accountDAO.create(requestAccount);
                    insertedAccount = accountDAO.read(result);
                } catch (DAOException e) {
                    e.printStackTrace();
                    out.write(e.getMessage());
                    return;
                }
                final String jsonManager = new ObjectMapper().writeValueAsString(insertedAccount);
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
        // changing manager for the client by accountId;

        PrintWriter out = resp.getWriter();
        if (req.getContentType().equals("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            RequestAccount requestAccount = null;
            try {
                requestAccount = mapper.readValue(req.getInputStream(), RequestAccount.class);
            } catch (JsonParseException ex) {
                out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                return;
            }

            AccountResponse accountResponse = null;

            if (requestAccount != null) {

                try {
                    if (accountDAO.read(requestAccount.getId()) == null) {
                        out.write(String.format("There not account with id=%d", requestAccount.getId()));
                        return;
                    }
                    accountDAO.update(requestAccount);
                    accountResponse = accountDAO.read(requestAccount.getId());
                } catch (DAOException e) {
                    e.printStackTrace();
                    out.write(e.getMessage());
                    return;
                }
                final String jsonManager = new ObjectMapper().writeValueAsString(accountResponse);
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
                Long accountId = Long.parseLong(parts[1]);

                try {
                    AccountResponse foundAccount = accountDAO.read(accountId);
                    if (foundAccount == null) {
                        out.write(String.format("There no such account with id=%d", accountId));
                        return;
                    }
                    accountDAO.delete(accountId);
                    out.write(String.format("Account with id=%d was deleted", accountId));
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        out.write("Something went wrong");
    }
}
