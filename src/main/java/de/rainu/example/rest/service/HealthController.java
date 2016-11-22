package de.rainu.example.rest.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Max Marche (m.marche@tarent.de)
 */
@RestController
public class HealthController {

	 @RequestMapping(path = "/health", method = RequestMethod.GET)
	 public ResponseEntity health() {
		  return ResponseEntity.status(HttpStatus.OK).build();
	 }
}
