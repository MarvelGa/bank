package com.banksystem.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Client extends User {
    private final List<Account> accounts;

    protected Client(ClientBuilder userBuilder) {
        super (userBuilder);
        this.accounts = userBuilder.accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(accounts, client.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts);
    }

    @Override
    public String toString() {
        return "Client{" +
                "accounts=" + accounts +
                '}';
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public static class ClientBuilder extends UserBuilder<ClientBuilder> {
        private List<Account> accounts;

        public ClientBuilder(){
        }

        public Client build() {
            return new Client(self());
        }

        @Override
        public ClientBuilder self() {
            return this;
        }

        @Override
        public ClientBuilder withId(Long id) {
            super.withId(id);
            return self();
        }

        @Override
        public ClientBuilder withFirstName(String firstName) {
            super.withFirstName(firstName);
            return self();
        }

        @Override
        public ClientBuilder withLastName(String lastName) {
            super.withLastName(lastName);
            return self();
        }

        @Override
        public ClientBuilder withEmail(String email) {
            super.withEmail(email);
            return self();
        }

        @Override
        public ClientBuilder withPassword(String password) {
            super.withPassword(password);
            return self();
        }

        @Override
        public ClientBuilder withRole(Role role) {
            super.withRole(role);
            return self();
        }

        @Override
        public ClientBuilder withDateOfBirth(LocalDate dateOfBirth) {
            super.withDateOfBirth(dateOfBirth);
            return self();
        }
        public ClientBuilder withAccountList(List<Account> lists) {
            this.accounts = lists;
            return this;
        }
    }
}
