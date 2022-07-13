package com.nttdata.apirestcustomers;

import com.nttdata.apirestcustomers.controller.CustomerController;
import com.nttdata.apirestcustomers.model.dto.CustomerDto;
import com.nttdata.apirestcustomers.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private CustomerService service;

    @Test
    public void addCustomerTest() {
        CustomerDto customerDto = new CustomerDto("001", "Xavier", "Av Javier Prado 1035", "javier@hotmail.com", "999666333", "45678955", "P", "20/10/1990");
        Mono<CustomerDto> customerDtoMono = Mono.just(customerDto);
        when(service.create(customerDto)).thenReturn(customerDtoMono);

        webTestClient.post()
                .uri("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(customerDtoMono, CustomerDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.customer.name").isNotEmpty()
                .jsonPath("$.customer.name").isEqualTo("Xavier");

    }


    @Test
    public void getCustomersTest() {
        Flux<CustomerDto> customerDtoFlux = Flux.just(new CustomerDto("001", "Xavier", "Av Javier Prado 1035", "javier@hotmail.com", "999666333", "45678955", "P", "20/10/1990"),
                new CustomerDto("002", "Charles", "Av Lima 1035", "charles@hotmail.com", "999111333", "15995122", "P", "10/05/1987"));
        when(service.listAll()).thenReturn(customerDtoFlux);

        Flux<CustomerDto> responseBody = webTestClient.get().uri("/api/customers")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(CustomerDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new CustomerDto("001", "Xavier", "Av Javier Prado 1035", "javier@hotmail.com", "999666333", "45678955", "P", "20/10/1990"))
                .expectNext(new CustomerDto("002", "Charles", "Av Lima 1035", "charles@hotmail.com", "999111333", "15995122", "P", "10/05/1987"))
                .verifyComplete();

    }

    @Test
    public void getCustomerTest() {
        Mono<CustomerDto> customerDtoMono = Mono.just(
                new CustomerDto("002", "Charles", "Av Lima 1035", "charles@hotmail.com", "999111333", "15995122", "P", "10/05/1987"));
        when(service.getById(any())).thenReturn(customerDtoMono);

        Flux<CustomerDto> responseBody = webTestClient.get().uri("/api/customers/002")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(CustomerDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(c -> c.getName().equals("Charles"))
                .verifyComplete();

    }

    @Test
    public void updateCustomerTest() {
        CustomerDto customerDto = new CustomerDto("001", "Javier", "Av Javier Prado 1035", "javier@hotmail.com", "999666333", "45678955", "P", "20/10/1990");
        Mono<CustomerDto> customerDtoMono = Mono.just(customerDto);
        when(service.update(customerDto)).thenReturn(customerDtoMono);

/*        return webTestClient.put()
                .uri("/api/customers/001")
                .body(customerDtoMono, CustomerDto.class)
                .exchange();*/

/*        webTestClient.put()
                .uri("/api/customers/{id}", "001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(customerDtoMono, CustomerDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Javier");*/

    }

    @Test
    public void deleteProductTest() {
        webTestClient.delete()
                .uri("/api/customers/{id}", "001")
                .exchange()
                .expectStatus().isOk();
    }
}
