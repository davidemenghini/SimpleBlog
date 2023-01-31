package it.unicam.cs.pawm.davidemenghini.simpleblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence")
//@ComponentScan("it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository")
public class SimpleBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBlogApplication.class, args);
    }

}
