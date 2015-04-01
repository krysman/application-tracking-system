package com.saprykin.surveyapp.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.saprykin.surveyapp")
public class AppConfig {

    /*
    this class is empty and only reason for it’s existence is @ComponentScan which provides beans auto-detection facility
     */

}
