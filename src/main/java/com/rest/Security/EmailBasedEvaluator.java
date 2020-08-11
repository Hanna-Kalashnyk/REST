package com.rest.Security;

import com.rest.Repository.PersonRepository;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.NotFoundException;
import org.ilay.Access;
import org.ilay.AccessEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class EmailBasedEvaluator implements AccessEvaluator<SecuredByEmail> {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Access evaluate(Location location, Class navigationTarget, SecuredByEmail annotation) {
        if (SecurityUtils.isUserLoggedIn()) {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final UserDetails principal = (UserDetails) authentication.getPrincipal();
            String loginnedUsername = principal.getUsername();

            if (personRepository.findByEmailIgnoreCase(loginnedUsername) != null) {
                return Access.granted();
            }
        }
        return Access.restricted(NotFoundException.class);
    }
}

