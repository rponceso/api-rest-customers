package com.nttdata.apirestcustomers.service;

import com.nttdata.apirestcustomers.events.CustomerCreatedEvent;
import com.nttdata.apirestcustomers.events.Event;
import com.nttdata.apirestcustomers.events.EventType;
import com.nttdata.apirestcustomers.model.document.Customer;
import com.nttdata.apirestcustomers.model.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class CustomerEventsService {

    @Autowired
    private KafkaTemplate<String, Event<?>> producer;

    @Value("${topic.customer.name:customers}")
    private String topicCustomer;

    public void publish(CustomerDto customerDto) {

        CustomerCreatedEvent created = new CustomerCreatedEvent();
        created.setData(customerDto);
        created.setId(UUID.randomUUID().toString());
        created.setType(EventType.CREATED);
        created.setDate(new Date());

        this.producer.send(topicCustomer, created);
    }


}
