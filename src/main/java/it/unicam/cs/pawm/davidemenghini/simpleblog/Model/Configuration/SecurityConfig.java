package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Configuration;

import it.unicam.cs.pawm.davidemenghini.simpleblog.Model.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
//@EnableWebSecurity
public class SecurityConfig{

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);





    //@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(List.of("Set-Cookie"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SessionHandlerUtil sessionHandlerUtil(){
        return new DefaultLoginHandler();
    }

    @Bean
    public UserSessionChecker checkUserSession(){
        return new DefaultUserChecker();
    }


    @Bean
    public PostService postService(){
        return new DefaultPostService();
    }

    @Bean
    public CommentService commentService(){return new DefaultCommentService();}


    @Bean
    public DefaultUserService userService(){
        return new DefaultUserService();
    }
}
