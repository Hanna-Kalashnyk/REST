package com.rest.VaadinUI;

import com.rest.Entity.MyUserCash;
import com.rest.Security.CustomRequestCache;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = LoginView.ROUTE)
@PageTitle("Login")
@NpmPackage(value = "@polymer/iron-form", version = "3.0.1")
@JsModule("@polymer/iron-form/iron-form.js")
@CssImport("./styles/center-style.css")
public class LoginView extends VerticalLayout {
    public static final String ROUTE = "login";

    @Autowired
    public LoginView(MyUserCash userCash, AuthenticationManager authenticationManager,
                     CustomRequestCache requestCache) {

        H3 Ilayinfo = new H3("Ilay Secured Vaadin");
        Ilayinfo.setClassName("title");

        Label labelInfo1 = new Label("The password is the same");
        labelInfo1.addClassName("green");
        Label labelInfo2 = new Label("for admin, user, illia, herring@gmail.com, malcolm@gmail.com and jackson@gmail.com");

        TextField userNameTextField = new TextField();
        userNameTextField.getElement().setAttribute("name", "username");
        userNameTextField.setLabel("Username");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.getElement().setAttribute("name", "password");


        if (userCash.getUsername() != null) userNameTextField.setValue(userCash.getUsername());
        else {
            userNameTextField.setPlaceholder("Enter username");
            userNameTextField.setAutofocus(true);
        }

        if (userCash.getPassword() != null) passwordField.setValue(userCash.getPassword());
        else {
            passwordField.setPlaceholder("Enter password");
        }

        Button loginButton = new Button("Login");
        loginButton.setId("submitbutton");
        loginButton.getElement().getThemeList().add("primary");

        Checkbox rememberMe = new Checkbox("remember me", false);
        rememberMe.getElement().setAttribute("name", "rememberme");
        rememberMe.setValue(userCash.rememberme);

        loginButton.addClickListener(e -> {
            try {
                if (!rememberMe.getValue()) {
                    userCash.setUsername(null);
                    userCash.setPassword(null);
                    userCash.rememberme = false;
                }
                if (rememberMe.getValue()) {
                    userCash.setUsername(userNameTextField.getValue());
                    userCash.setPassword(passwordField.getValue());
                    userCash.rememberme = true;

                }
                final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userNameTextField.getValue(), passwordField.getValue()));
                // if authentication was successful we will update the security context and redirect to the page requested first
                if (authentication != null) {

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //Access to view by role
                    if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ROLE_ADMIN"))) {
                        UI.getCurrent().navigate(AdminView.class);
                    } else {
                        if (((UserDetails) authentication.getPrincipal()).getUsername().equals("illia")) {
                            UI.getCurrent().navigate(IlliaView.class);
                        } else {
                            UI.getCurrent().navigate(requestCache.resolveRedirectUrl());
                        }
                    }
                }
            } catch (AuthenticationException ex) {
                Notification.show("Invalid credentials (for demo use: admin/password)");
            }
        });
        loginButton.addClickShortcut(Key.ENTER);

        add(Ilayinfo, labelInfo1, labelInfo2, userNameTextField, passwordField, loginButton, rememberMe);

        setWidth("400px");
        setClassName("login-view");

        setPadding(true);
        setMargin(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

    }
}

