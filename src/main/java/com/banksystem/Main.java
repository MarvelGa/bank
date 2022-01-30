package com.banksystem;


import com.banksystem.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Card card1 = Card.builder()
                .withId(1L)
                .withNumber("102020")
                .withAmount(20000L)
                .withCurrencyOfCard(Card.Currency.UAH)
                .withExpirationDate(012023)
                .build();

        Card card2 = Card.builder()
                .withId(2L)
                .withNumber("5456815")
                .withAmount(10000L)
                .withCurrencyOfCard(Card.Currency.EUR)
                .withExpirationDate(012023)
                .build();

        Card card3 = Card.builder()
                .withId(3L)
                .withNumber("8265454")
                .withAmount(19000L)
                .withCurrencyOfCard(Card.Currency.USD)
                .withExpirationDate(012023)
                .build();


        Account account1 = Account.builder()
                .withId(1L)
                .withCards(List.of(card1, card2))
                .build();

        Account account2 = Account.builder()
                .withId(1L)
                .withCards(List.of(card3))
                .build();

        Client client1 = Client.builder()
                .withId(1L)
                .withFirstName("Petro")
                .withLastName("Petrenko")
                .withEmail("petro@gmail.com")
                .withPassword("petro123")
                .withRole(User.Role.CLIENT)
                .withDateOfBirth((LocalDate.of(1980, Month.JANUARY, 1)).toString())
                .withAccountList(List.of(account1))
                .build();

        Client client2 = Client.builder()
                .withId(2L)
                .withFirstName("Vasiliy")
                .withLastName("Stepanenko")
                .withEmail("stepanenko@gmail.com")
                .withPassword("stepanenko123")
                .withRole(User.Role.CLIENT)
                .withDateOfBirth((LocalDate.of(1985, Month.FEBRUARY, 1)).toString())
                .withAccountList(List.of(account2))
                .build();

        Manager manager1 = Manager.builder()
                .withId(5L)
                .withFirstName("Ivan")
                .withLastName("Ivanov")
                .withEmail("bob@gmail.com")
                .withPassword("bob123")
                .withRole(User.Role.MANAGER)
                .withSpecialization("Manager Of Mortgage")
                .withDateOfBirth((LocalDate.of(1990, Month.JANUARY, 1)).toString())
                .withAccountList(List.of(account1))
                .build();

        Manager manager2 = Manager.builder()
                .withId(6L)
                .withFirstName("Sofia")
                .withLastName("Stepanova")
                .withEmail("stepanova@gmail.com")
                .withPassword("stepanova123")
                .withRole(User.Role.MANAGER)
                .withSpecialization("Manager Of Credits")
                .withDateOfBirth((LocalDate.of(1992, Month.JANUARY, 10)).toString())
                .withAccountList(List.of(account2))
                .build();

        System.out.println(client1.getId());

        String jsonClient = objectMapper.writeValueAsString(client1);
        System.out.println(jsonClient);
    }
}
