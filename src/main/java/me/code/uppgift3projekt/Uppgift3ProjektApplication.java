package me.code.uppgift3projekt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Uppgift3ProjektApplication {

    public static void main(String[] args) {
        SpringApplication.run(Uppgift3ProjektApplication.class, args);
    }

}
