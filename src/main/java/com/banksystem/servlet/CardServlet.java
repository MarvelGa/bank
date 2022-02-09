package com.banksystem.servlet;

import com.banksystem.dao.CardDAO;
import com.banksystem.dao.impl.CardDAOImpl;
import com.banksystem.dto.CardDTO;

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

@WebServlet(urlPatterns = "/bank-api/cards/*")
public class CardServlet extends HttpServlet {
    public CardDAO cardDAO;

    @Override
    public void init() throws ServletException {
        this.cardDAO = new CardDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();
        CardDTO card = null;
        if (pathInfo != null) {
            String[] parts = pathInfo.split("/");
            if (!parts[1].equals("clientId")) {

                if (parts[1].matches("[0-9]+")) {
                    Long cardId = Long.parseLong(parts[1]);
                    try {
                        card = cardDAO.read(cardId);
                    } catch (DAOException e) {
                        e.printStackTrace();
                    }
                }

                if (card != null) {
                    final String jsonClients = new ObjectMapper().writeValueAsString(card);
                    resp.setContentType("application/json; charset=UTF-8");
                    resp.setStatus(200);
                    out.write(jsonClients);
                    return;
                } else {
                    out.write(String.format("There are no an card with such id!"));

                }
                return;
            } else {
                if (parts[2].matches("[0-9]+")) {
                    Long clientId = Long.parseLong(parts[2]);

                    List<CardDTO> cards = null;
                    try {
                        cards = cardDAO.readAll(clientId);
                    } catch (DAOException e) {
                        e.printStackTrace();
                    }
                    if (cards != null) {
                        final String jsonClients = new ObjectMapper().writeValueAsString(cards);
                        resp.setContentType("application/json; charset=UTF-8");
                        resp.setStatus(200);
                        out.write(jsonClients);
                        return;
                    } else {
                        out.write("There are no accounts yet!");
                        return;
                    }
                }
            }
        }
        try {
            final String jsonClients = new ObjectMapper().writeValueAsString(cardDAO.readAllCards());
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(200);
            out.write(jsonClients);
        } catch (DAOException e) {
            e.printStackTrace();
            out.write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();
        CardDTO card = null;
        if (pathInfo != null) {
            String[] parts = pathInfo.split("/");

            if (parts[1].matches("[0-9]+")) {
                Long accountId = Long.parseLong(parts[1]);

                if (req.getContentType().equals("application/json")) {
                    ObjectMapper mapper = new ObjectMapper();
                    CardDTO requestCard = null;
                    try {
                        requestCard = mapper.readValue(req.getInputStream(), CardDTO.class);
                    } catch (JsonParseException ex) {
                        out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                        return;
                    }

                    if (requestCard != null) {

                        Long result = null;
                        CardDTO cardDTO = null;
                        try {
                            var ss = cardDAO.readAllCards();
                            CardDTO finalRequestCard = requestCard;
                            if (cardDAO.readAllCards().stream().anyMatch(c -> c.getNumber().equals(finalRequestCard.getNumber()))) {
                                out.write("The card with such number already exists in DB");
                                return;
                            }
                            result = cardDAO.create(requestCard, accountId);
                            cardDTO = cardDAO.read(result);
                        } catch (DAOException e) {
                            e.printStackTrace();
                            out.write(e.getMessage());
                            return;
                        }
                        final String jsonManager = new ObjectMapper().writeValueAsString(cardDTO);
                        resp.setContentType("application/json; charset=UTF-8");
                        resp.setStatus(200);
                        out.write(jsonManager);
                    }
                    return;
                }
            }
        }
        out.write("Something went wrong");
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // changing data expiration by cardId;

        PrintWriter out = resp.getWriter();
        if (req.getContentType().equals("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            CardDTO cardRequest = null;
            try {
                cardRequest = mapper.readValue(req.getInputStream(), CardDTO.class);
            } catch (JsonParseException ex) {
                out.write("Problem with parsing JSON. Pls, check out the requestBody!");
                return;
            }

            CardDTO cardDTO = null;

            if (cardRequest != null) {

                try {
                    if (cardDAO.read(cardRequest.getId()) == null) {
                        out.write(String.format("There not card with id=%d", cardRequest.getId()));
                        return;
                    }
                    cardDAO.update(cardRequest);
                    cardDTO = cardDAO.read(cardRequest.getId());
                } catch (DAOException e) {
                    e.printStackTrace();
                    out.write(e.getMessage());
                    return;
                }
                final String jsonManager = new ObjectMapper().writeValueAsString(cardDTO);
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
                Long cardId = Long.parseLong(parts[1]);

                try {
                    CardDTO foundCard = cardDAO.read(cardId);
                    if (foundCard == null) {
                        out.write(String.format("There no such card with id=%d", cardId));
                        return;
                    }
                    cardDAO.delete(cardId);
                    out.write(String.format("Card with id=%d was deleted", cardId));
                } catch (DAOException e) {
                    e.printStackTrace();
                    out.write(e.getMessage());
                    return;
                }
                return;
            }
        }
        out.write("Something went wrong");
    }
}
