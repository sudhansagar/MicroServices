package com.madhu.springbootadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@Configuration
@SpringBootApplication
public class SpringbootadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootadminApplication.class, args);
	}
	
	/*@Configuration
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
			http.logout().logoutUrl("/logout");
			http.csrf().disable();

			http.authorizeRequests()
	*///		.antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
	//		.permitAll();
	//		http.authorizeRequests().antMatchers("/**").authenticated();
	//		http.httpBasic();
	//		}
//	}
}
