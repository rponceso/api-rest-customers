/**
 * Bean Stores Customer Information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.model.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nttdata.apirestcustomers.validation.TypeCustomerValidation;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    @NotEmpty
    @Size(min = 3)
    @Field(name = "name")
    private String name;

    @NotEmpty
    @Size(min = 3)
    @Field(name = "address")
    private String address;

    @NotEmpty
    @Email
    @Field(name = "email")
    private String email;

    @NotEmpty
    @Field(name = "phone")
    private String phone;

    @NotEmpty
    @Field(name = "numberDocument")
    private String numberDocument;

    @NotEmpty
    @Field(name = "customerType")
    @TypeCustomerValidation
    private String customerType;

    @NotEmpty
    @Field(name = "dateBirthDay")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateBirthDay;
}
