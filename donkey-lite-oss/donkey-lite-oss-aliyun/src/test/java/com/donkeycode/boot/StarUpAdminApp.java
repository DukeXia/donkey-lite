package com.donkeycode.boot;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author donkey
 */
@EnableScheduling
@EnableAutoConfiguration
@EnableCaching
@ComponentScan
public class StarUpAdminApp {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(StarUpAdminApp.class, args);
    }

}
