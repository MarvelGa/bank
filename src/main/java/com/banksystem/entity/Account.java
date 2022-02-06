package com.banksystem.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {
    private final Long id;
    private final List<Card> cards;
    private final Manager manager;
    private final User user;

    public Account(Builder builder, User user) {
        this.id = builder.id;
        this.cards = builder.cards;
        this.manager = builder.manager;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(cards, account.cards) && Objects.equals(manager, account.manager) && Objects.equals(user, account.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cards, manager, user);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", cards=" + cards +
                ", manager=" + manager +
                ", user=" + user +
                '}';
    }

    public static class Builder {
        private Long id;
        private List<Card> cards;
        private Manager manager;
        private User user;

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

        public Builder withUser (User user){
            this.user = user;
            return this;
        }

        public Account build() {
            return new Account(this, user);
        }
    }
}
