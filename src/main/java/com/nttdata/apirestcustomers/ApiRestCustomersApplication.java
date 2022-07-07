/**
 * Main Class
 *
 * @author Renato Ponce
 * @version 1.0
 * @since 2022-06-24
 */

package com.nttdata.apirestcustomers;

import com.nttdata.apirestcustomers.feignclients.AccountFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableFeignClients(basePackageClasses=com.nttdata.apirestcustomers.feignclients.AccountFeignClient.class)
//@EnableFeignClients(clients = {AccountFeignClient.class})
@EnableEurekaClient
@SpringBootApplication
public class ApiRestCustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRestCustomersApplication.class, args);
    }

}
