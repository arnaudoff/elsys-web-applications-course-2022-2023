package org.elsys_bg.beans_hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class PoliticianBeansApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliticianBeansApplication.class, args);
	}
}
