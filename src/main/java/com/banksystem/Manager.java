package com.banksystem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class Manager extends User {
    private String specialization;
    private List<Account> accountList;

    public String getSpecialization() {
        return specialization;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public static Builder builder() {
        return new Manager().new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return Objects.equals(specialization, manager.specialization) && Objects.equals(accountList, manager.accountList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialization, accountList);
    }


    public class Builder {
        private Builder() {
        }

        public Builder specialization(String specialization) {
            Manager.this.specialization = specialization;
            return this;
        }

        public Builder accountList(List<Account> accountList) {
            Manager.this.accountList = accountList;
            return this;
        }

        public Builder id(Long id) {
            Manager.this.setId(id);
            return this;
        }

        public Builder firstName(String firstName) {
            Manager.this.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            Manager.this.setLastName(lastName);
            return this;
        }

        public Builder email(String email) {
            Manager.this.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            Manager.this.setPassword(password);
            return this;
        }

        public Builder role(User.Role role) {
            Manager.this.setRole(role);
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            Manager.this.setDateOfBirth(dateOfBirth);
            return this;
        }

        public Manager build() {
            return Manager.this;
        }

    }
}
