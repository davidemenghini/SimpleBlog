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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

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
                        .authorizeHttpRequests()
                        .requestMatchers("/api/public/**")
                        .permitAll()
                        .and()
                        .csrf(csfr-> csfr.ignoringRequestMatchers("/api/public/**"))
                        ;//.cors(cors -> cors.configurationSource(this.corsConfigurationSource()));
            } catch (Exception e) {
                System.out.println("errore nel bean...");
                logger.error(e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000/"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
