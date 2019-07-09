package com.custodio.resthouse.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class RestHouseProductApi
{
    public static void main(final String... args)
    {
        SpringApplication.run(RestHouseProductApi.class, args);
    }
}