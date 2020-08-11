package com.rest.VaadinUI;

import com.rest.Repository.PersonRepository;
import com.rest.Security.SecuredByRole;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

//@ActiveProfiles("release")
@PageTitle("User view")
//@Secured(Role.USER)
@Route(value = UserView.ROUTE)
@SecuredByRole("ROLE_USER")

public class UserView extends VerticalLayout {
    public static final String ROUTE = "";
    private Long loginnedPersonId;
    private String profileURL;
    private Button button;

    @Autowired
    PersonRepository personRepository;


    @Autowired
    public UserView(PersonRepository personRepository) {
        this.personRepository = personRepository;
        add(new H1(" Hello, User! "));

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails principal = (UserDetails) authentication.getPrincipal();
        String loginnedUsername = principal.getUsername();

        if (personRepository.findByEmailIgnoreCase(loginnedUsername) == null) {
            add(new Span("U've entered by common username. "));
            add(new Span("So You can logout and try to login again with your email as username - for profile data accessing"));
        }
        if (personRepository.findByEmailIgnoreCase(loginnedUsername) != null) {
            add(new Span("As U've entered by your email - You can tap button below and review your data.)"));

            loginnedPersonId = (personRepository.findByEmailIgnoreCase(loginnedUsername)).getId();
            profileURL = "managePerson/" + loginnedPersonId;

            button = new Button("Profile", event -> UI.getCurrent().navigate(profileURL));
            button.getElement().getThemeList().add("primary");
            add(button);
        }
        Anchor logout = new Anchor("/logout", "Log out");
        add(logout);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

    }
}