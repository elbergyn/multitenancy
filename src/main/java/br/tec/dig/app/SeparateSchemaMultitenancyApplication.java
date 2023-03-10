package br.tec.dig.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"br.tec.dig.multitenancy.separate.schema", "br.tec.dig.app"})
@EntityScan(basePackages={"br.tec.dig.multitenancy.separate.schema", "br.tec.dig.app"})
@EnableJpaRepositories(basePackages= {"br.tec.dig.multitenancy.separate.schema", "br.tec.dig.app"})
public class SeparateSchemaMultitenancyApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeparateSchemaMultitenancyApplication.class, args);
	}
}
