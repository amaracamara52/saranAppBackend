package org.sid.saranApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

//    @Autowired
//    BuildProperties pom;

	@Bean
	public OpenAPI swaggerAPI() {
//        return new OpenAPI().info(new Info().title("SIRH Douane Guineé").
//        		description("solution de resources humaines").version("1.0"));
		String securitySchemeName = "Auth JWT";
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(
						new Components().addSecuritySchemes(securitySchemeName,
								new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
										.scheme("bearer").bearerFormat("JWT")))
				.info(new Info().title("SaranApp Backend").description("Systeme d'information des boutique").version("v0.1.0"));
	}

}
