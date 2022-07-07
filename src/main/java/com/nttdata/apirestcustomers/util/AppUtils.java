package com.nttdata.apirestcustomers.util;

import com.nttdata.apirestcustomers.model.dto.CustomerDto;
import com.nttdata.apirestcustomers.model.document.Customer;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static CustomerDto entityToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }

    public static Customer dtoToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }
}
