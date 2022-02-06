package com.banksystem.controller;

import com.banksystem.dao.CardDAO;
import com.banksystem.dao.impl.CardDAOImpl;
import com.banksystem.dto.CardDTO;
import com.banksystem.exceptions.DAOException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bank-api")
@RestController
public class CardController {
    public CardDAO cardDAO;

    public CardController() {
        this.cardDAO = new CardDAOImpl();
    }

    @GetMapping("/{clientId}/cards")
    public List<CardDTO> getCardsByClientId(@PathVariable Long clientId) {
        List<CardDTO> result = null;
        try {
            result = cardDAO.readAll(clientId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/card/{cardId}")
    public CardDTO getCardById(@PathVariable Long cardId) {
        CardDTO result = null;
        try {
            result = cardDAO.read(cardId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/{clientId}/add-card")
    public CardDTO createCardByClientId(@RequestBody CardDTO request, @PathVariable Long clientId) {
        Long result = null;
        try {
            result = cardDAO.create(request, clientId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return getCardById(result);
    }

    @DeleteMapping("/card/{cardId}")
    public void deleteAccountById(@PathVariable Long cardId) {
        try {
            cardDAO.delete(cardId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/card")
    public CardDTO updateAccount(@RequestBody CardDTO request) {
        CardDTO updated = null;
        try {
            cardDAO.update(request);
            updated = cardDAO.read(request.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return updated;
    }
}
