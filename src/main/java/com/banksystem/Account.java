package com.banksystem;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Account {
    private Long id;
    private List<Card> cards;
}
