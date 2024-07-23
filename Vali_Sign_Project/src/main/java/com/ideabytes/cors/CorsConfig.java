package com.ideabytes.cors;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.addAllowedOrigin("http://localhost:10000");
//		config.addAllowedOriginPattern("http://localhost:*");
//		config.addAllowedOriginPattern("http://localhost:4200");
//		config.addAllowedOriginPattern("http://*:*");
//		config.addAllowedOriginPattern("*://*:*");
//		config.addAllowedOriginPattern("https://*:*");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");

		config.addAllowedHeader("Content-Type");
		config.addAllowedHeader("Authorization");
		config.addAllowedHeader("Access-Control-Allow-Headers");

		config.setAllowCredentials(true);
//		config.setAllowedOrigins(List.of("http://localhost:4200"));
		config.setAllowedOrigins(List.of("http://localhost:4200", "https://localhost:4200", "https://dev1.valisign.aitestpro.com","https://dev1.valisign.aitestpro.com/","http://localhost:10000"));
//		config.setAllowedOriginPatterns(List.of("http://localhost:*", "https://localhost:4200", "https://dev1.valisign.aitestpro.com","https://*.*.*.*/"));
		
		
		config.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter((CorsConfigurationSource) source);
	}
}
