/* 
 * Name: ValiSign
 * Project: ValiSign
 * Version: 0.2.0
 * Description: This class is the starting point 
 * of the application which initializes springboot and swagger implementations
 * Created Date: 2023-08-08
 * Created By:    Siddish Gollapelli
 * Modified Date: 2023-08-11
 * Modified By:   Siddish Gollapelli
 * 
 */
package com.ideabytes;

import static springfox.documentation.builders.PathSelectors.regex;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.ideabytes.service.implementations.DatabaseServicesImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import springfox.documentation.PathProvider;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="API Documentation",version="1.0",description = "API Documentation for Vali Sign Project"))
//@OpenAPIDefinition(info = @Info(title = "API Documentation", version="1.0", description = "API Documentation for Vali Sign"))
@EnableScheduling
@Configuration
@EnableWebMvc
@ComponentScan
@EnableAutoConfiguration
public class ValiSign extends SpringBootServletInitializer {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Value("${app.Name}")
	private String appName;

	@Value("${spring.profiles.active}")
	private String activeEnv;

	@Value("${domainUrl}")
	private String domainUrl;
	private static ConfigurableApplicationContext context;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ValiSign.class);
	}

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = SpringApplication.run(ValiSign.class, args);
		context = ctx;
		System.out.println("Server ON");
		DatabaseServicesImpl dsi=new DatabaseServicesImpl();
		try {
		dsi.getUsersForDevice(1);
		}
		catch(Exception e) {
		
		}

	}

	@Bean
	public Docket openApivalisign() {
		return new Docket(DocumentationType.OAS_30).groupName("valisign-api-all").apiInfo(apiInfo()).select()
				.paths(valisignPaths()).build();
	}

	@Bean
	public Docket openApivalisignUser() {

		System.out.println("openapi user cpath: " + contextPath);

		return new Docket(DocumentationType.OAS_30)
				// .servers(new Server("local", "https://127.0.0.1:8080/valisign", "local
				// server"))

				.pathProvider(new PathProvider() {
					@Override
					public String getOperationPath(String operationPath) {
						// System.out.println("opath1: " + operationPath);
						String extra_tomcat_path = "/" + appName + "_" + activeEnv;// //valisign_dev /Tracer_dev
						// System.out.println("extra_tomcat_path: " + extra_tomcat_path);
						String opath = operationPath.replace(extra_tomcat_path, StringUtils.EMPTY); // only for dev
						// System.out.println("opath2: " + opath);
						return opath;
					}

					@Override
					public String getResourceListingPath(String groupName, String apiDeclaration) {
						// TODO Auto-generated method stub
						return null;
					}

				}).groupName("valisign-api-user").apiInfo(apiInfo()).securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey())).select().paths(valisignUserPaths()).build();
	}

	@Bean
	public Docket openApivalisignAdmin() {
		return new Docket(DocumentationType.OAS_30)
				// .servers(new Server("local", "https://127.0.0.1:8080/valisign", "local
				// server"))
				.groupName("valisign-api-admin").apiInfo(apiInfo()).select().paths(valisignAdminPaths()).build();
	}

	private Predicate<String> valisignPaths() {
		return regex(".*/api/vs/user.*")
				.or(regex(".*/api/vs/files.*").or(regex(".*/api/vs/admin.*")).or(regex(".*/api/vs/auth.*")));
	}

	private Predicate<String> valisignUserPaths() {
		// return regex(".*/api/user.*").or(regex(".*/api/files.*"));
		return regex(".*/api/vs/user.*");
	}

	private Predicate<String> valisignAdminPaths() {
		return regex(".*/api/vs/admin.*").or(regex(".*/api/vs/files.*"));
	}

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private ApiKey apiKey() {
		return new ApiKey("Bearer", AUTHORIZATION_HEADER, "header");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("valisign - Server API")
				.description("API explorer for the this application of valisign ")
				.termsOfServiceUrl("https://commonresources.valisign.com").contact(new Contact("valisign", "", ""))
				.license("Apache License Version 2.0").licenseUrl("https://commonresources.valisign.com/license")
				.version("2.0").build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}
	
	
	 
	

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//	    return new WebMvcConfigurer() {
//	        @Override
//	        public void addCorsMappings(CorsRegistry registry) {
//	            registry.addMapping("/**")
//	                .allowedOriginPatterns(".*")
//	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//	                .allowedHeaders("*")
//	                .allowCredentials(true);
//	        }
//	    };
//	}


//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin(domainUrl);
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//		config.setAllowedOrigins(Arrays.asList(domainUrl));
//		config.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//	                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
//	                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
//		config.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
//	                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}
//	
	
	
//	 @Bean
//	    public CorsFilter corsFilter() {
//	        CorsConfiguration corsConfiguration = new CorsConfiguration();
//	        corsConfiguration.setAllowCredentials(true);
//	        corsConfiguration.addAllowedOrigin("*"); 
//	        corsConfiguration.addAllowedHeader("*"); 
//	        corsConfiguration.addAllowedMethod("*"); 
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        source.registerCorsConfiguration("/**", corsConfiguration);
//	        return new CorsFilter(source);
//	    }
	

//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
//                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
//        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
//                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
	
//	 @Bean
//	    public CorsFilter corsFilter() {
//	        CorsConfiguration corsConfiguration = new CorsConfiguration();
//	        corsConfiguration.setAllowCredentials(true);
//	        corsConfiguration.setAllowedOrigins(Arrays.asList("/**"));
//	        corsConfiguration.addAllowedOrigin("/**");
//	        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
//	        corsConfiguration.setExposedHeaders(Arrays.asList("*"));
//	        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
//	        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//	        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//	        return new CorsFilter(urlBasedCorsConfigurationSource);
//	    }

//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList(domainUrl));
//		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Content-Length",
//				"X-Requested-With", "x-client-id", "x-client-secret", "language"));
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
//
//	@Bean
//	public CorsFilter corsFilter() {
//		return new CorsFilter(corsConfigurationSource());
//	}

}
