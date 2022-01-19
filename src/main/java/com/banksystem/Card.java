package com.banksystem;

import java.util.Objects;

public final class Card {
    public enum Currency {
        USD, UAH, EUR
    }

    private Long id;
    private String number;
    private Long amount;
    private Currency currencyOfCard;
    private Integer expirationDate;

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

    public static Builder builder() {
        return new Card().new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(number, card.number) && Objects.equals(amount, card.amount) && currencyOfCard == card.currencyOfCard && Objects.equals(expirationDate, card.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, amount, currencyOfCard, expirationDate);
    }

    public class Builder {
        private Builder() {
        }

        public Builder id(Long id) {
            Card.this.id = id;
            return this;
        }

        public Builder number(String number) {
            Card.this.number = number;
            return this;
        }

        public Builder amount(Long amount) {
            Card.this.amount = amount;
            return this;
        }

        public Builder currencyOfCard(Currency currencyOfCard) {
            Card.this.currencyOfCard = currencyOfCard;
            return this;
        }

        public Builder expirationDate(Integer expirationDate) {
            Card.this.expirationDate = expirationDate;
            return this;
        }

        public Card build() {
            return Card.this;
        }
    }
}
