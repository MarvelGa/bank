package com.banksystem.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager extends User {
    private final String specialization;
    private final List<Account> accounts;

    protected Manager(ManagerBuilder userBuilder) {
        super(userBuilder);
        this.specialization = userBuilder.specialization;
        this.accounts = userBuilder.accounts;
    }

    public String getSpecialization() {
        return specialization;
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
        Manager manager = (Manager) o;
        return Objects.equals(specialization, manager.specialization) &&
                Objects.equals(accounts, manager.accounts);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "specialization='" + specialization + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialization, accounts);
    }

    public static ManagerBuilder builder() {
        return new ManagerBuilder();
    }

    public static class ManagerBuilder extends UserBuilder<ManagerBuilder> {
        private String specialization;
        private List<Account> accounts;

        public ManagerBuilder() {
        }

        public Manager build() {
            return new Manager(self());
        }

        @Override
        public ManagerBuilder self() {
            return this;
        }

        @Override
        public ManagerBuilder withId(Long id) {
            super.withId(id);
            return self();
        }

        @Override
        public ManagerBuilder withFirstName(String firstName) {
            super.withFirstName(firstName);
            return self();
        }

        @Override
        public ManagerBuilder withLastName(String lastName) {
            super.withLastName(lastName);
            return self();
        }

        @Override
        public ManagerBuilder withEmail(String email) {
            super.withEmail(email);
            return self();
        }

        @Override
        public ManagerBuilder withPassword(String password) {
            super.withPassword(password);
            return self();
        }

        @Override
        public ManagerBuilder withRole(Role role) {
            super.withRole(role);
            return self();
        }

        @Override
        public ManagerBuilder withDateOfBirth(String dateOfBirth) {
            super.withDateOfBirth(dateOfBirth);
            return self();
        }

        public ManagerBuilder withSpecialization(String specialization) {
            this.specialization = specialization;
            return this;
        }

        public ManagerBuilder withAccountList(List<Account> accounts) {
            this.accounts = accounts;
            return this;
        }
    }
}
