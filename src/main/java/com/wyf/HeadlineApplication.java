package com.wyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class HeadlineApplication extends SpringBootServletInitializer {
public class HeadlineApplication {
    /*
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HeadlineApplication.class);
    }*/

	public static void main(String[] args) {
		SpringApplication.run(HeadlineApplication.class, args);
	}

}
