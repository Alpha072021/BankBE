package com.alpha.bankApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BankAppSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTAuthenticationFiler authenticationFiler;

	private static final String[] PUBLIC_URLS = { "/swagger-apis/**", "/swagger-ui/**", "/v3/api-docs/**",
			"/swagger-ui.html", "/employees/login", "/api/version/v1/admins/login", "/api/version/{version}/login",
			"/swagger-ui/index.html" };

	private static final String[] ADMIN_URLS = { "/api/version/{version}/banks", "/api/version/{version}/banks/getAll",
			"/api/version/{version}/banks/bankId/{bankId}", "/api/version/{version}/banks/getAllUnAssigned",
			"/api/version/{version}/banks/bankName/{bankName}", "/api/version/{version}/managingDirectors",
			"/api/version/{version}/managingDirectors/getManagingDirectorById",
			"/api/version/{version}/managingDirectors/getAllManagingDirector",
			"/api/version/{version}/managingDirectors/removeManagingDirector" };

	private static final String[] MANAGING_DIRECTOR_URLS = {
			"/api/version/{version}/managingDirectors/getManagingDirector", "/api/version/{version}/branchs/save",
			"/api/version/{version}/branchs/update", "/api/version/{version}/branchs/getById",
			"/api/version/{version}/branchs/getAllBranch", "/api/version/{version}/branchs/delete",
			"/api/version/{version}/branchs/getAllUnAssigned", "/api/version/{version}/branchManagers/save",
			"/api/version/{version}/branchManagers/getById", "/api/version/{version}/branchManagers/update",
			"/api/version/{version}/branchManagers/delete", "/api/version/{version}/branchManagers/getAll" };

	private static final String[] BRANCH_MANAGER_URLS = { "/api/version/{version}/documents","/api/version/{version}/documents",
			"/api/version/{version}/branchManagers/getBranchManager", "/api/version/{version}/users",
			"/api/version/{version}/users/getById", "/api/version/{version}/users/getAllUsersByBrancId",
			"/api/version/{version}/accounts/saving", "/api/version/{version}/accounts/current",
			"/api/version/{version}/accounts/saving/getAllAccounts/branchId",
			"/api/version/{version}/accounts/saving/getAllAccounts/bankId",
			"/api/version/{version}/accounts/saving/getAccountByAccountNumber",
			"/api/version/{version}/accounts/getAllAccounts/branchId",
			"/api/version/{version}/accounts/getAllAccounts/bankId",
			"/api/version/{version}/accounts/current/getAllAccounts/branchId",
			"/api/version/{version}/accounts/current/getAllAccounts/bankId",
			"/api/version/{version}/accounts/current/getAccountByAccountNumber",
			"/api/version/{version}/accounts/remove" }

	;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().cors().and()
		http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults())
				.sessionManagement(
						sessionManagment -> sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(PUBLIC_URLS)
						.permitAll().requestMatchers(ADMIN_URLS).hasRole("ADMIN")
						.requestMatchers(MANAGING_DIRECTOR_URLS).hasAnyRole("MANAGING_DIRECTOR")
						.requestMatchers(BRANCH_MANAGER_URLS).hasAnyRole("BRANCH_MANAGER").anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authenticationFiler, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling((exception) -> exception.authenticationEntryPoint(authenticationEntryPoint()));
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthEntryPointJwt();
	}

	// to allow CORS from UI for spring security
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				// registry.addMapping("/**").allowedOrigins("HEAD", "GET", "PUT", "POST",
				// "DELETE", "PATCH");
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("GET", "POST", "PUT",
						"DELETE", "OPTIONS", "PATCH", "HEAD");
			}
		};
	}

//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5713"));
//		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS"));
//		configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}

}
