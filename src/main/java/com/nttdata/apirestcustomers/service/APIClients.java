package com.nttdata.apirestcustomers.service;


import com.nttdata.apirestcustomers.model.dto.AccountDto;
import com.nttdata.apirestcustomers.model.dto.CreditCardDto;
import com.nttdata.apirestcustomers.model.dto.CreditDto;
import com.nttdata.apirestcustomers.model.dto.DebitCardDto;
import reactor.core.publisher.Flux;

public interface APIClients {
    Flux<AccountDto> findAccountByCustomer(String customerId);

    Flux<CreditDto> findCreditByCustomer(String customerId);

    Flux<CreditCardDto> findCreditCardsByCustomer(String customerId);

    Flux<DebitCardDto> findDebitCardsByCustomer(String customerId);

}
