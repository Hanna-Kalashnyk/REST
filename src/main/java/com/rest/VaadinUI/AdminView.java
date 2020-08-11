package com.rest.VaadinUI;

//import com.rest.Entity.Role;

import com.rest.Security.SecuredByRole;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

//@ActiveProfiles("release")
@PageTitle("Admin view")
//@Secured(Role.ADMIN)
@Route(value = AdminView.ROUTE)
//@Secured("ROLE_ADMIN")
@SecuredByRole("ROLE_ADMIN")
public class AdminView extends VerticalLayout {
    public static final String ROUTE = "admin";
    NativeButton adminButton;

    //   RouterLink linkCreate;
    @Autowired
    public AdminView() {

        add(new Label("Hello, Admin! You can control Persons through the button below."));
        adminButton = new NativeButton("Persons control");
        //  adminButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

//        adminButton.addClickListener(e -> {
//            new RouterLink(null, MainView.class);
//        });


        //     final RouterLink home = new RouterLink("MainView", MainView.class);
        //   add(home);

        //   linkCreate = new RouterLink("Manage Persons roll on", MainView.class);
        //add(linkCreate);


        adminButton.addClickListener(e ->
                adminButton.getUI().ifPresent(ui ->
                        ui.navigate("list"))
        );
        add(adminButton);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }
}

//@Route("admin")
//@Secured("ROLE_Admin") //
//public class AdminView extends VerticalLayout {
//   // public static final String ROUTE = "admin";
//    Label label;
//
//    @Autowired
//    public AdminView() {
//        label = new Label("Looks like you are admin!");
//        add(label);
//    }
//
//}