package ru.s1uad_dw.OpenFurnAuthService.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("open-furn-auth-service")
                                .version("v 1.0")
                                .contact(
                                    new Contact()
                                            .email("s1uad.dw@gmail.com")
                                            .url("https://t.me/s1uad_dw")
                                )
                );
    }
}