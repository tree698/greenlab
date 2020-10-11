package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import web.service.UserService;

@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/pub/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/**").permitAll();
		
		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login/process")
			.defaultSuccessUrl("/");

		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.invalidateHttpSession(true);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(getApplicationContext().getBean(UserDetailsService.class)).passwordEncoder(passwordEncoder());
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	/*
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	*/
}
