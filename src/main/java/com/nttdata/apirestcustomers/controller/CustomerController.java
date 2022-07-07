/**
 * Controller that receives the requests
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.controller;

import com.nttdata.apirestcustomers.model.document.Customer;
import com.nttdata.apirestcustomers.model.dto.CustomerDto;
import com.nttdata.apirestcustomers.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CircuitBreakerFactory cbFactory;

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<CustomerDto>>> list() {
        Flux<CustomerDto> fxCustomers = service.listAll();

        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxCustomers));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> getForId(@PathVariable("id") String id) {
        logger.info("Se obtendra el cliente por Id");
        return cbFactory
                .create("customers")
                .run(() -> service.getById(id) //Mono<Customer>->Mono<ResponseEntity<Customer>>
                        .map(p -> ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(p)
                        )); //Mono<ResponseEntity<Customer>>
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> register(@RequestBody Mono<CustomerDto> monoCustomer, final ServerHttpRequest req) {
        //201 | localhost:8080/customers/123

        Map<String, Object> respuesta = new HashMap<String, Object>();

        return monoCustomer.flatMap(cust -> {
            return service.create(cust)
                    .map(c -> {
                        if (cust.equals("P") || cust.equals("B")) {
                            logger.info("Customer created successfully");
                            respuesta.put("customer", c);
                            respuesta.put("message", "Customer created successfully");
                            respuesta.put("timestamp", new Date());
                            return ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(c.getId())))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(respuesta);

                        } else {
                            logger.info("Customer is not type Personal or Business");
                            respuesta.put("message", "Customer is not type Personal or Business");
                            respuesta.put("timestamp", new Date());
                            return ResponseEntity.badRequest()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(respuesta);
                        }

                    });
        }).onErrorResume(t -> {
            return Mono.just(t).cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(Flux::fromIterable)
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        respuesta.put("errors", list);
                        respuesta.put("timestamp", new Date());
                        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
                        return Mono.just(ResponseEntity.badRequest().body(respuesta));
                    });

        });
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> update(@PathVariable("id") String id, @Valid @RequestBody Mono<CustomerDto> monoCustomer) {

        Mono<CustomerDto> monoBD = service.getById(id);

        Map<String, Object> respuesta = new HashMap<String, Object>();

        return monoBD
                .zipWith(monoCustomer, (bd, c) -> {
                    bd.setId(id);
                    bd.setName(c.getName());
                    bd.setAddress(c.getAddress());
                    bd.setEmail(c.getEmail());
                    bd.setPhone(c.getPhone());
                    bd.setNumberDocument(c.getNumberDocument());
                    bd.setCustomerType(c.getCustomerType());
                    return bd;
                })
                .flatMap(service::update) //bd->service.update(bd)
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return service.getById(id)
                .flatMap(c -> {
                    return service.deleteById(c.getId())
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
                })
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/numberDocument/{numberDocument}")
    public Mono<ResponseEntity<CustomerDto>> getByNumberDocument(@PathVariable("numberDocument") String numberDocument) {
        return service.getByNumberDocument(numberDocument) //Mono<Customer>->Mono<ResponseEntity<Customer>>
                .map(c -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c)
                ); //Mono<ResponseEntity<Customer>>
    }

    @GetMapping("/customerType/{customerType}")
    public Mono<ResponseEntity<Flux<CustomerDto>>> getByCustomerType(@PathVariable("customerType") String customerType) {

        Flux<CustomerDto> fxCustomers = service.getByCustomerType(customerType);

        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fxCustomers));

    }

    private Mono<ResponseEntity<?>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return Mono.just(ResponseEntity.badRequest().body(errores));
    }

    @GetMapping("/products/{customerId}")
    public Mono<ResponseEntity<Map<String, Object>>> getProductsByCustomer(@PathVariable("customerId") String customerId) {
        return service.getCustomerAndProducts(customerId)
                .map(r -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(r)
                );
    }
}
