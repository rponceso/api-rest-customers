/**
 * Bean Stores AuthorizedSigner Information
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers.model.dto;

import lombok.Data;

@Data
public class AuthorizedSignerDto {
    private String name;
    private String lastname;
    private String numberDocument;
    private String email;
}
