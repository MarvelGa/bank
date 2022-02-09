package com.banksystem.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class User {

    public enum Role {
        ADMIN, CLIENT, MANAGER
    }

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Role role;
    private final String dateOfBirth;

    public User(UserBuilder<? extends UserBuilder> userUserBuilder) {
        this.id = userUserBuilder.id;
        this.firstName = userUserBuilder.firstName;
        this.lastName = userUserBuilder.lastName;
        this.email = userUserBuilder.email;
        this.password = userUserBuilder.password;
        this.role = userUserBuilder.role;
        this.dateOfBirth = userUserBuilder.dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(dateOfBirth, user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, role, dateOfBirth);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public static class UserBuilder<SELF extends UserBuilder<SELF>> {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Role role;
        private String dateOfBirth;

        protected UserBuilder() {
        }

        @SuppressWarnings("unchecked")
        public SELF self() {
            return (SELF) this;
        }

        public SELF withId(Long id) {
            this.id = id;
            return self();
        }

        public SELF withFirstName(String firstName) {
            this.firstName = firstName;
            return self();
        }


        public SELF withLastName(String lastName) {
            this.lastName = lastName;
            return self();
        }


        public SELF withEmail(String email) {
            this.email = email;
            return self();
        }

        public SELF withPassword(String password) {
            this.password = password;
            return self();
        }

        public SELF withRole(Role role) {
            this.role = role;
            return self();
        }


        public SELF withDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return self();
        }
    }
}
