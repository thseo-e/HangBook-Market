package org.spectra.hangbookmarket.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    //Swagger 설정을 구현해주세요.

    @Bean
    public OpenAPI openAPI()
    {
        return new OpenAPI()
            .components(new Components())
            .info(apiInfo());
    }

    private Info apiInfo()
    {
        return new Info()
            .title("Hangbook Market API")
            .description("Hangbook Market Swagger UI 입니다.")
            .version("1.0.0");
    }
}
