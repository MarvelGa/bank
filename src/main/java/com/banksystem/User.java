package com.banksystem;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Data
public class User {
   public enum Role{
       ADMIN, CLIENT, MANAGER
   }
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private LocalDate dateOfBirth;
}
