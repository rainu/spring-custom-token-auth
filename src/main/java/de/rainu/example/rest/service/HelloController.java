package de.rainu.example.rest.service;

import de.rainu.example.config.security.annotation.IsAdmin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Max Marche (m.marche@tarent.de)
 */
@RestController
public class HelloController {

    //TODO: For session looks here http://docs.spring.io/spring-session/docs/current/reference/html5/#httpsession-rest

    @IsAdmin
    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello World!";
    }

}
