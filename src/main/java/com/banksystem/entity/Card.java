package com.banksystem.entity;

import java.util.Objects;

public class Card {
    public enum Currency {
        USD, UAH, EUR
    }

    private final Long id;
    private final String number;
    private final Long amount;
    private final Currency currencyOfCard;
    private final Integer expirationDate;

    public Card(Builder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.amount = builder.amount;
        this.currencyOfCard = builder.currencyOfCard;
        this.expirationDate = builder.expirationDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Long getAmount() {
        return amount;
    }

    public Currency getCurrencyOfCard() {
        return currencyOfCard;
    }

    public Integer getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return Objects.equals(id, card.id) &&
                Objects.equals(number, card.number) &&
                Objects.equals(amount, card.amount) &&
                currencyOfCard == card.currencyOfCard &&
                Objects.equals(expirationDate, card.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, amount, currencyOfCard, expirationDate);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", amount=" + amount +
                ", currencyOfCard=" + currencyOfCard +
                ", expirationDate=" + expirationDate +
                '}';
    }

    public static class Builder {
        private Long id;
        private String number;
        private Long amount;
        private Currency currencyOfCard;
        private Integer expirationDate;

        public Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder withAmount(Long amount) {
            this.amount = amount;
            return this;
        }

        public Builder withCurrencyOfCard(Currency currencyOfCard) {
            this.currencyOfCard = currencyOfCard;
            return this;
        }

        public Builder withExpirationDate(Integer expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }

}
