package com.e_commerce.Entry1.configurations;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationConfiguration {
	
	private final MyUserDetailService userDetailsService;

	public ApplicationConfiguration(MyUserDetailService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		try {

//			http.csrf(customizer -> customizer.disable());
//			http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
////			http.formLogin(Customizer.withDefaults());
//			http.httpBasic(Customizer.withDefaults());
//			http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//			return http.build();

			return http.csrf(customizer -> customizer.disable())
					.authorizeHttpRequests(request -> request.anyRequest().authenticated())
//					.formLogin(Customizer.withDefaults())
					.httpBasic(Customizer.withDefaults())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}


}
