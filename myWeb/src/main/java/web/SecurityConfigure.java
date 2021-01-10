package web;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {
	
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
			.antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
			.antMatchers("/**").permitAll();
		
		http.headers().frameOptions().sameOrigin();
		
		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login/process")
			.defaultSuccessUrl("/")
				.successHandler((req, res, exp) -> {
					res.sendRedirect("/");
				})
				.failureHandler((req, res, exp) -> { // Failure handler invoked after authentication failure
					String errMsg = "";
					if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
						errMsg = "Invalid username or password.";
					} else {
						errMsg = "Unknown error - " + exp.getMessage();
					}
					req.getSession().setAttribute("message", errMsg);
					res.sendRedirect("/login");
				});

		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.invalidateHttpSession(true);
	}
}
