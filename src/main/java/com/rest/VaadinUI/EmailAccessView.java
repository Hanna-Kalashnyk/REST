package com.rest.VaadinUI;

import com.rest.Security.SecuredByEmail;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = EmailAccessView.ROUTE)
@SecuredByEmail
public class EmailAccessView extends VerticalLayout {
    public static final String ROUTE = "emailview";
    Button userButton;

    EmailAccessView() {
        add(new Span(" Hello, User! You can control your data through button below."));
        userButton = new Button("User control");
        userButton.getElement().getThemeList().add("primary");
        //   userButton.addClickListener( e -> UI.getCurrent().navigate(ManagePerson.class, EmailBasedEvaluator.lastLoginnedPersonId));
        Anchor logout = new Anchor("/logout", "Log out");

        add(userButton, logout);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

    }
}