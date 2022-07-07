/**
 * Interface Service Customer
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.service;

import com.nttdata.apirestcustomers.model.dto.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface CustomerService {

    Mono<CustomerDto> create(CustomerDto customerDto);

    Mono<CustomerDto> update(CustomerDto customerDto);

    Flux<CustomerDto> listAll();

    Mono<CustomerDto> getById(String id);

    Mono<Void> deleteById(String id);

    Mono<CustomerDto> getByNumberDocument(String numberDocument);

    Flux<CustomerDto> getByCustomerType(String customerType);

    Mono<Map<String, Object>> getCustomerAndProducts(String customerId);

}
