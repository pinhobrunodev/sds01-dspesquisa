package com.devsuperior.dspesquisa.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class OpenAPIConfig {
	@Bean // Considerar ela para subir
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
		.info(new Info()
		.title(" BACKEND DSPesquisa - Semana DevSuperior 1.0 ")
		.version("1.0")
		.termsOfService("http://swagger.io/terms")
		.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
