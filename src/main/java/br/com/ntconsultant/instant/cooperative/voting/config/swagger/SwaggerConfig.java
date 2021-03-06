package br.com.ntconsultant.instant.cooperative.voting.config.swagger;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Daniel Santos
 */
@Configuration
public class SwaggerConfig {

    private static final String VERSION_API = "/v1";
    private static final String PATH_URI_PAUTAS = "/pautas";
    private static final String PATH_URI_VOTERS = "/votes";
    private static final String PATH_URI_EVENTS_STREAM = "/events-stream";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")))
                .info(new Info()
                        .title(" API RestFULL - Pauta & Sessions Voting.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Daniel Santos")
                                .url("https://github.com/NecoDan")
                                .email("neco.daniel@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                );
    }

    @Bean
    public GroupedOpenApi pautasOpenApi() {
        String[] paths = { VERSION_API + PATH_URI_PAUTAS + "/**" };
        return GroupedOpenApi.builder().group("pautas").pathsToMatch(paths).build();
    }

    @Bean
    public GroupedOpenApi votersOpenApi() {
        String[] paths = { VERSION_API + PATH_URI_VOTERS + "/**" };
        return GroupedOpenApi.builder().group("votes").pathsToMatch(paths).build();
    }

    @Bean
    public GroupedOpenApi eventsStreamOpenApi() {
        String[] paths = { VERSION_API + PATH_URI_EVENTS_STREAM + "/**" };
        return GroupedOpenApi.builder().group("stream-events").pathsToMatch(paths).build();
    }
}

