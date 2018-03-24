package org.cinema;

import java.util.Arrays;

import javax.servlet.http.HttpSessionEvent;

import org.cinema.actor.domain.Actor;
import org.cinema.annotations.ActorBuilder;
import org.cinema.security.domain.CustomUserDetails;
import org.cinema.security.service.UserService;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CinemaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}
	
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserService userService) throws Exception {
		builder
			.userDetailsService(username -> new CustomUserDetails(userService.getByUsername(username)))
			.passwordEncoder(this.passwordEncoder())
		;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher() {
			
			@Override
			public void sessionCreated(HttpSessionEvent event) {
				super.sessionCreated(event);
				event.getSession().setMaxInactiveInterval(440);
				log.info("** Session created ** " + event.getSession().getId());
			}
			
			@Override
			public void sessionDestroyed(HttpSessionEvent event) {
				super.sessionDestroyed(event);
				log.info("** Session destroyed **");
			}
		};
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
	
	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Actor actor(InjectionPoint ip) {
		ActorBuilder actorBuilder	= AnnotationUtils.getAnnotation(ip.getAnnotatedElement(), ActorBuilder.class);
		Integer id 					= actorBuilder.id();
		String nom					= actorBuilder.nom();
		String prenom				= actorBuilder.prenom();
		
		return new Actor(id, nom, prenom);
	}
}
