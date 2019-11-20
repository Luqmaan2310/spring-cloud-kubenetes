package za.co.solinta.gatewayserver.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{bcrypt}$2a$10$ZiSRuKu8MSeDQ8VyrEY87OQH7/I6j5WaD7YFHLTb9OuMATI.FAEle") // password
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{bcrypt}$2a$10$rRBA59jEPHJkh/Ie.N1JJ.AEqr11dSYlDfTkFOyugeWZTxMRZ/tmC") // admin
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/organisation-service/books")
                .permitAll().antMatchers("/eureka/**").hasRole("ADMIN")
                .anyRequest().authenticated().and().formLogin().and()
                .logout().permitAll().logoutSuccessUrl("/organisation-service/books")
                .permitAll().and().csrf().disable();
    }
}