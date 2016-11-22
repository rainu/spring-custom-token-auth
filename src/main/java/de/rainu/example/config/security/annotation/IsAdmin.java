package de.rainu.example.config.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * @author Max Marche (m.marche@tarent.de)
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('ADMIN')")
public @interface IsAdmin {
}
