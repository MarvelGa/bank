package com.banksystem.entity;

import java.util.List;
import java.util.Objects;

public class Account {
    private final Long id;
    private final List<Card> cards;
    private final Manager manager;

    public Account(Builder builder) {
        this.id = builder.id;
        this.cards = builder.cards;
        this.manager = builder.manager;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Manager getManager() {
        return manager;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(cards, account.cards) &&
                Objects.equals(manager, account.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cards, manager);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", cards=" + cards +
                ", manager=" + manager +
                '}';
    }

    public static class Builder {
        private Long id;
        private List<Card> cards;
        private Manager manager;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withCards(List<Card> cards) {
            this.cards = cards;
            return this;
        }

        public Builder withManager(Manager manager) {
            this.manager = manager;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}
