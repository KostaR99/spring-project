package com.example.demo.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
 	UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/user/**").hasRole("Korisnik")
				.antMatchers("/admin/**").hasRole("Admin")
				.antMatchers("/").permitAll()
			.and().
			    formLogin()
			    	.loginPage("/login.jsp")
			    	.loginProcessingUrl("/login")
			    	.usernameParameter("username")
			    	.passwordParameter("password")
			    	.defaultSuccessUrl("/auth/pocetna")
			    	.failureForwardUrl("/errors/loginFailed.jsp")
			    	
			.and()
			    .exceptionHandling().accessDeniedPage("/errors/accessDenied.jsp")	 
			.and()
		    	.rememberMe()
		    .and()
		    	.csrf().disable();
	}
}