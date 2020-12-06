package com.casual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages={"com.casual"})
public class InvoicingManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoicingManagementApplication.class, args);
    }

}
