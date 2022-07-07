package com.nttdata.apirestcustomers.service;

import com.nttdata.apirestcustomers.model.dto.AccountDto;
import com.nttdata.apirestcustomers.model.dto.CreditCardDto;
import com.nttdata.apirestcustomers.model.dto.CreditDto;
import com.nttdata.apirestcustomers.model.dto.DebitCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Service
public class APIClientsImpl implements APIClients {

    @Autowired
    private WebClient webClient;

    @Value("${config.base.enpoint.accounts}")
    private String urlAccounts;

    @Value("${config.base.enpoint.credits}")
    private String urlCredits;

    @Value("${config.base.enpoint.creditcards}")
    private String urlCreditCards;

    @Value("${config.base.enpoint.debitcards}")
    private String urlDebitCards;

    @Override
    public Flux<AccountDto> findAccountByCustomer(String customerId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", customerId);
        return webClient.get().uri(urlAccounts + "/customer/{id}", params).accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(AccountDto.class));
    }

    @Override
    public Flux<CreditDto> findCreditByCustomer(String customerId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", customerId);
        return webClient.get().uri(urlCredits + "/customer/{id}", params).accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(CreditDto.class));
    }

    @Override
    public Flux<CreditCardDto> findCreditCardsByCustomer(String customerId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", customerId);
        return webClient.get().uri(urlCreditCards + "/customer/{id}", params).accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(CreditCardDto.class));
    }

    @Override
    public Flux<DebitCardDto> findDebitCardsByCustomer(String customerId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", customerId);
        return webClient.get().uri(urlDebitCards + "/customer/{id}", params).accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(DebitCardDto.class));
    }
}
