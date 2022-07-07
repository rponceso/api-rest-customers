/**
 * Implementation Interface Service Customer
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.service;

import com.nttdata.apirestcustomers.model.dto.*;
import com.nttdata.apirestcustomers.repository.CustomerRepository;
import com.nttdata.apirestcustomers.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private APIClients apiClients;

    @Override
    public Mono<CustomerDto> create(CustomerDto customerDto) {
        return Mono.just(customerDto).map(AppUtils::dtoToEntity)
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);

    }

    @Override
    public Mono<CustomerDto> update(CustomerDto customerDto) {
        return Mono.just(customerDto).map(AppUtils::dtoToEntity)
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
    }

    @Override
    public Flux<CustomerDto> listAll() {
        return repository.findAll().map(AppUtils::entityToDto);
    }

    @Override
    public Mono<CustomerDto> getById(String id) {
        return repository.findById(id).map(AppUtils::entityToDto);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<CustomerDto> getByNumberDocument(String numberDocument) {
        return repository.findByNumberDocument(numberDocument).map(AppUtils::entityToDto);
    }

    @Override
    public Flux<CustomerDto> getByCustomerType(String customerType) {
        return repository.findByCustomerType(customerType).map(AppUtils::entityToDto);
    }


    public Mono<Map<String, Object>> getCustomerAndProducts(String customerId) {

        Flux<AccountDto> fxAccounts = apiClients.findAccountByCustomer(customerId);
        Flux<CreditDto> fxCredits = apiClients.findCreditByCustomer(customerId);
        Flux<CreditCardDto> fxCreditCards = apiClients.findCreditCardsByCustomer(customerId);
        Flux<DebitCardDto> fxDebitCards = apiClients.findDebitCardsByCustomer(customerId);

        return fxAccounts.collectList()
                .flatMap(lstAccounts -> fxCredits.collectList()
                        .flatMap(lstCredits -> fxCreditCards.collectList()
                                .flatMap(lstCreditCards -> fxDebitCards.collectList()
                                        .flatMap(lstDebitCards -> {
                                            Map<String, Object> resultado = new HashMap<>();
                                            resultado.put("Customer", customerId);
                                            resultado.put("Accounts: ", lstAccounts);
                                            resultado.put("Credits: ", lstCredits);
                                            resultado.put("Credit Cards: ", lstCreditCards);
                                            resultado.put("Debit Cards: ", lstDebitCards);
                                            return Mono.just(resultado);
                                        }))));

    }
}
