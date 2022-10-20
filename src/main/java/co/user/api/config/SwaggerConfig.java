package co.user.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConditionalOnWebApplication
public class SwaggerConfig {

    /*public OpenAPI corporateAPI(){
        return new OpenAPI
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_12)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(metadata())
                .useDefaultResponseMessages(false)
                .genericModelSubstitutes(Optional.class);
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder()
                .title("Corporate User Request Application")
                .description("API Information")
                .version("1.0.0")
                .build();
    }*/
}
