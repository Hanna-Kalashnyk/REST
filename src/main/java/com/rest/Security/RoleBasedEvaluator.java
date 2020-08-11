package com.rest.Security;

import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.NotFoundException;
import org.ilay.Access;
import org.ilay.AccessEvaluator;

public class RoleBasedEvaluator implements AccessEvaluator<SecuredByRole> {
    @Override
    public Access evaluate(Location location, Class navigationTarget, SecuredByRole annotation) {
        if (!SecurityUtils.isAccessGranted(navigationTarget, annotation)) {
            if (SecurityUtils.isUserLoggedIn()) {
                return Access.restricted(NotFoundException.class);
            } else {
                return Access.restricted(com.rest.VaadinUI.LoginView.ROUTE);
            }
        }

        return Access.granted();
    }
}