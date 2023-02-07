package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Configuration;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig{

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((autz)-> {
            try {
                autz.requestMatchers("/api/private/login")
                            .hasRole("user")
                        .and()
                            .formLogin(x->x.loginPage("/api/private/login"))
                        .authorizeHttpRequests()
                        .requestMatchers("/api/public/**")
                        .permitAll()
                        .and()
                        .csrf(csfr-> csfr.ignoringRequestMatchers("/api/public/**"));
            } catch (Exception e) {
                System.out.println("errore nel bean...");
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        logger.info("so dentro al filtro...");
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SessionHandlerUtil sessionHandlerUtil(){
        return new DefaultLoginHandler();
    }

    @Bean
    public UserSessionChecker checkUserSession(){
        return new DefaultUserCheckerChecker();
    }


    @Bean
    public PostService postService(){
        return new DefaultPostService();
    }
}
