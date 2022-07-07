/**
 * Repository that stores customer information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.repository;

import com.nttdata.apirestcustomers.model.document.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    Mono<Customer> findByNumberDocument(String numberDocument);

    Flux<Customer> findByCustomerType(String customerType);
}
