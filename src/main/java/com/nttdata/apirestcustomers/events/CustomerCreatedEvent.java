package com.nttdata.apirestcustomers.events;

import com.nttdata.apirestcustomers.model.dto.CustomerDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCreatedEvent extends Event<CustomerDto> {

}
