package com.nttdata.apirestcustomers.service;

import com.nttdata.apirestcustomers.model.document.Customer;
import com.nttdata.apirestcustomers.repository.CustomerRepository;
import com.nttdata.apirestcustomers.util.AppUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    private Customer customer1;

    private Customer customer2;

    private List<Customer> listCustomer = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        customer1 = new Customer();
        customer1.setId("001");
        customer1.setName("Empresa A");
        customer1.setCustomerType("B");
        customer1.setAddress("Calle Lejana");
        customer1.setEmail("empresa_a@gmail.com");
        customer1.setPhone("999666999");
        customer1.setNumberDocument("12345678");
        customer1.setDateBirthDay("1990-05-22");


        customer2 = new Customer();
        customer2.setId("002");
        customer2.setName("Pedro");
        customer2.setCustomerType("B");
        customer2.setAddress("Calle Lejana");
        customer2.setEmail("pedro@gmail.com");
        customer2.setPhone("999333999");
        customer2.setNumberDocument("87654321");
        customer2.setDateBirthDay("1990-05-22");

        listCustomer.add(customer1);
        listCustomer.add(customer2);
    }

    @Test
    void create() {
        when(repository.save(any(Customer.class))).thenReturn(Mono.just(customer1));
        assertNotNull(service.create(AppUtils.entityToDto(customer1)));
    }

    @Test
    void listAll() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(listCustomer));
        assertNotNull(service.listAll());
    }

    @Test
    void getById() {
        when(repository.findById("001")).thenReturn(Mono.just(customer1));
        assertNotNull(service.getById("001"));
    }
}