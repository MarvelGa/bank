package com.banksystem;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Card {
   public enum Currency{
      USD, UAH, EUR
   }
   private Long id;
   private String number;
   private Long amount;
   private Currency currencyOfCard;
}
