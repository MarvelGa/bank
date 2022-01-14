package com.banksystem;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Data
@SuperBuilder
public class Client extends User{
    private Account account;
    private List<Manager> managerList;
}
