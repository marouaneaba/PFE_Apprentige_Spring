package com.lille1.PFE;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/dashbord", true)
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .csrf().disable();
    }
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select nom, password, enabled from personne where nom=?")
               .authoritiesByUsernameQuery(
                     "select nom, role from personne where nom=?");
//                .usersByUsernameQuery(
//                        "select username, password, enabled from users where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, role from user_roles where username=?");
    }
  /*
    @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth
              .inMemoryAuthentication()
              .withUser("user").password("password").roles("USER");
	}
    */
    
}
