package com.banksystem;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;
@SuperBuilder
@Data
public class Manager extends User{
    private String specialization;
    private List<Client> clientList;
}
