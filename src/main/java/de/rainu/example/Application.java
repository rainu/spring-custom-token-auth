package de.rainu.example;

import de.rainu.example.config.security.AuthProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @author Max Marche (m.marche@tarent.de)
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public org.springframework.security.authentication.AuthenticationProvider createCustomAuthenticationProvider()  {
    return new AuthProvider();
  }


}
