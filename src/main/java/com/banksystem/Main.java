package com.banksystem;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Card card1 = Card.builder()
                .id(1L)
                .number("102020")
                .amount(20000L)
                .currencyOfCard(Card.Currency.UAH)
                .build();

        Card card2 = Card.builder()
                .id(2L)
                .number("5456815")
                .amount(10000L)
                .currencyOfCard(Card.Currency.EUR)
                .build();

        Card card3 = Card.builder()
                .id(3L)
                .number("8265454")
                .amount(19000L)
                .currencyOfCard(Card.Currency.USD)
                .build();

        Account account1 = Account.builder()
                .id(1L)
                .cards(List.of(card1, card2))
                .build();

        Account account2 = Account.builder()
                .id(1L)
                .cards(List.of(card3))
                .build();

        Client client1 = Client.builder()
                .id(1L)
                .firstName("Petro")
                .lastName("Petrenko")
                .email("petro@gmail.com")
                .password("petro123")
                .role(User.Role.CLIENT)
                .account(account1)
                .dateOfBirth(LocalDate.of(1980, Month.JANUARY, 1))
                .build();

        Client client2 = Client.builder()
                .id(2L)
                .firstName("Vasiliy")
                .lastName("Stepanenko")
                .email("stepanenko@gmail.com")
                .password("stepanenko123")
                .role(User.Role.CLIENT)
                .account(account2)
                .dateOfBirth(LocalDate.of(1985, Month.FEBRUARY, 1))
                .build();

        Manager manager1 = Manager.builder()
                .id(5L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("bob@gmail.com")
                .password("bob123")
                .role(User.Role.MANAGER)
                .specialization("Manager Of Mortgage")
                .dateOfBirth(LocalDate.of(1990, Month.JANUARY, 1))
                .clientList(List.of(client1))
                .build();

        Manager manager2 = Manager.builder()
                .id(6L)
                .firstName("Sofia")
                .lastName("Stepanova")
                .email("stepanova@gmail.com")
                .password("stepanova123")
                .role(User.Role.MANAGER)
                .specialization("Manager Of Credits")
                .dateOfBirth(LocalDate.of(1992, Month.JANUARY, 10))
                .clientList(List.of(client1, client2))
                .build();

        client1.setManagerList(List.of(manager1, manager2));
        client2.setManagerList(List.of(manager2));
    }
}
