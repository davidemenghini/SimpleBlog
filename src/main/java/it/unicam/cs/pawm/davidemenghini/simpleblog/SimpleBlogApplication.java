package it.unicam.cs.pawm.davidemenghini.simpleblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@EnableJpaRepositories("it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence")
//@ComponentScan("it.unicam.cs.pawm.davidemenghini.simpleblog.Model.repository")
public class SimpleBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBlogApplication.class, args);
    }

}
