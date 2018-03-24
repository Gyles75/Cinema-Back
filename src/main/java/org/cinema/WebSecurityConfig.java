package org.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors().and()
			.csrf().disable()
			.authorizeRequests().antMatchers("/api/v1/**").permitAll()
			.antMatchers("/protected/**").authenticated()
			.and()
			.formLogin()//.loginPage("/login").loginProcessingUrl("/api/v1/user/login")
			.defaultSuccessUrl("/api/v1/user/successful").failureUrl("/api/v1/user/failure")
			.and()
			.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(this.sessionRegistry)
		;
	}
}
