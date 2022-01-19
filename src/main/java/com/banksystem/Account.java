package com.banksystem;

import java.util.List;
import java.util.Objects;

public final class Account {
    private Long id;
    private List<Card> cards;
    private Manager manager;
    private List<Client> clientList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public static Builder builder() {
        return new Account().new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(cards, account.cards) && Objects.equals(manager, account.manager) && Objects.equals(clientList, account.clientList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cards, manager, clientList);
    }

    public class Builder {
        private Builder() {
        }

        public Builder id(Long id) {
            Account.this.id = id;
            return this;
        }

        public Builder cards(List<Card> cards) {
            Account.this.cards = cards;
            return this;
        }

        public Builder manager(Manager manager) {
            Account.this.manager = manager;
            return this;
        }

        public Builder client(List<Client> clientList) {
            Account.this.clientList = clientList;
            return this;
        }

        public Account build() {
            return Account.this;
        }
    }
}
