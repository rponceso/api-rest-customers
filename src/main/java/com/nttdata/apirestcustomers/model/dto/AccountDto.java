/**
 * Bean Stores Account Information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto {
    private String id;
    private AccountTypeDto accountType;
    private String accountNumber;
    private String currency;
    private double amount; //monto
    private double balance;//saldo
    private CustomerDto customer;
    private String state;
    private int maxLimitMovementPerMonth;
    private List<HeadLineDto> headlines;
    private List<AuthorizedSignerDto> authorizedSigners;
}
