package com.rest.VaadinUI;

import com.rest.Security.SecuredForIllia;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("illia")
@SecuredForIllia
public class IlliaView extends VerticalLayout {
    H1 label;
    Image illiaPhoto;
    @Autowired
    public IlliaView() {
        label = new H1("Hello, Illia!");
        illiaPhoto = new Image("images/photoillia.jpg", "A flower");
        add(label, illiaPhoto);
        Anchor logout = new Anchor("/logout", "Log out");
        add(logout);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }
}
