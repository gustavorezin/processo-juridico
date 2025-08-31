package com.gustavo.processo_juridico.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI appOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Processo Jurídico API")
                        .description("API para gestão de processos, partes e ações.")
                        .contact(new Contact().name("Gustavo").email("gustavo.rezin@gmail.com"))
                        );
    }
}
