/**
 * Bean Stores Customer Information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDto {
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String numberDocument;
    private String customerType;
    private LocalDate dateBirthDay;
}
