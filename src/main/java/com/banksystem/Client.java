package com.banksystem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public final class Client extends User {
    private List<Account> accountList;

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(accountList, client.accountList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountList);
    }

    public static Builder builder() {
        return new Client().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder accountList(List<Account> accountList) {
            Client.this.accountList = accountList;
            return this;
        }

        public Builder id(Long id) {
            Client.this.setId(id);
            return this;
        }

        public Builder firstName(String firstName) {
            Client.this.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            Client.this.setLastName(lastName);
            return this;
        }

        public Builder email(String email) {
            Client.this.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            Client.this.setPassword(password);
            return this;
        }

        public Builder role(User.Role role) {
            Client.this.setRole(role);
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            Client.this.setDateOfBirth(dateOfBirth);
            return this;
        }

        public Client build() {
            return Client.this;
        }
    }
}
