package com.banksystem.controller;

import com.banksystem.dao.AccountDAO;
import com.banksystem.dao.impl.AccountDAOImpl;
import com.banksystem.dto.AccountResponse;
import com.banksystem.dto.RequestAccount;
import com.banksystem.exceptions.DAOException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bank-api")
@RestController
public class AccountController {
    public AccountDAO accountDAO;

    public AccountController() {
        this.accountDAO = new AccountDAOImpl();
    }

    @GetMapping("/accounts")
    public List<AccountResponse> getAccounts() {
        List<AccountResponse> result = null;
        try {
            result = accountDAO.readAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/account/{accountId}")
    public AccountResponse getAccountById(@PathVariable Long accountId) {
        AccountResponse result = null;
        try {
            result = accountDAO.read(accountId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/add/account")
    public AccountResponse createAccount(@RequestBody RequestAccount request) {
        Long result = null;
        try {
            result = accountDAO.create(request);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return getAccountById(result);
    }

    @DeleteMapping("/account/{accountId}")
    public void deleteAccountById(@PathVariable Long accountId) {
        try {
            accountDAO.delete(accountId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/account")
    public AccountResponse updateAccount(@RequestBody RequestAccount request) {
        AccountResponse updated = null;
        try {
            accountDAO.update(request);
            updated = accountDAO.read(request.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return updated;
    }
}
