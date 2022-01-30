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

//    public String getSpecialization() {
//        return specialization;
//    }
//
//    public List<Account> getAccountList() {
//        return accountList;
//    }
//
//    public void setAccountList(List<Account> accountList) {
//        this.accountList = accountList;
//    }
//
//    public static Builder builder() {
//        return new Manager().new Builder();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Manager manager = (Manager) o;
//        return Objects.equals(specialization, manager.specialization) && Objects.equals(accountList, manager.accountList);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(specialization, accountList);
//    }
//
//
//    public class Builder {
//        private Builder() {
//        }
//
//        public Builder specialization(String specialization) {
//            Manager.this.specialization = specialization;
//            return this;
//        }
//
//        public Builder accountList(List<Account> accountList) {
//            Manager.this.accountList = accountList;
//            return this;
//        }
//
//        public Builder id(Long id) {
//            Manager.this.setId(id);
//            return this;
//        }
//
//        public Builder firstName(String firstName) {
//            Manager.this.setFirstName(firstName);
//            return this;
//        }
//
//        public Builder lastName(String lastName) {
//            Manager.this.setLastName(lastName);
//            return this;
//        }
//
//        public Builder email(String email) {
//            Manager.this.setEmail(email);
//            return this;
//        }
//
//        public Builder password(String password) {
//            Manager.this.setPassword(password);
//            return this;
//        }
//
//        public Builder role(User.Role role) {
//            Manager.this.setRole(role);
//            return this;
//        }
//
//        public Builder dateOfBirth(LocalDate dateOfBirth) {
//            Manager.this.setDateOfBirth(dateOfBirth);
//            return this;
//        }
//
//        public Manager build() {
//            return Manager.this;
//        }
//
//    }
}
