package com.rest.VaadinUI;

import com.rest.Controller.PersonController;
import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import com.rest.Security.SecuredByRole;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

//@ActiveProfiles("release")
@Route(value = MainView.ROUTE)
//@SecuredByRole("ROLE_ADMIN")
@SecuredByRole("ROLE_ADMIN")
@PWA(name = "CRUD Vaadin Flow Project with Spring", shortName = "Vaadin CRUD",
        enableInstallPrompt = false)

public class MainView extends VerticalLayout implements RouterLayout {

    public static final String ROUTE = "list";

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonController personController;

    @Autowired
    PersonEditor editor;
    TextField filter;
    TextField filter1;
    Button addNewPerson;

    Icon human;
    Icon notHuman;
    Icon trash;

    private Grid<Person> grid;

    public MainView(PersonRepository repo, PersonEditor editor) {

        this.editor = editor;
        this.repository = repo;
        this.filter = new TextField();
        this.filter1 = new TextField();
        this.addNewPerson = new Button("New person", VaadinIcon.PLUS.create());

        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout actions = new HorizontalLayout(filter1, filter, addNewPerson, logout);
        actions.expand(addNewPerson);
        actions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.grid = new Grid<>(Person.class);
        grid.setColumns("id", "firstName", "secondName", "numberPhone", "email");

        grid.addColumn(new LocalDateRenderer<>(Person::getBirthDay,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                .setHeader("Birth Day");

        grid.addColumn(new ComponentRenderer<>(person -> {
            if (person.isHuman()) {
                human = new Icon(VaadinIcon.HANDS_UP);
                human.setSize("20px");
                human.setColor("green");
                return human;
            } else {
                notHuman = new Icon(VaadinIcon.BUG_O);
                notHuman.setSize("20px");
                notHuman.setColor("red");
                return notHuman;
            }
        })).setHeader("is Human");

        //Добавим кнопку удаления
        grid.addColumn(new ComponentRenderer<>(person -> {
            trash = new Icon(VaadinIcon.TRASH);
            trash.addClickListener(clickEvent -> deleteDialog(person));
            return trash;
        }));

        grid.setSizeFull();
        add(actions, grid, editor);


        grid.setHeight("350px");
        grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);
        filter.setPlaceholder("Filter by last name");
        filter1.setPlaceholder("Filter by first name");
        filter.setClearButtonVisible(true);
        filter1.setClearButtonVisible(true);

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        filter1.setValueChangeMode(ValueChangeMode.EAGER);
        filter1.addValueChangeListener(e -> listCustomers1(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> editor.editCustomer(e.getValue()));

        // Instantiate and edit new Customer the new button is clicked
        addNewPerson.addClickListener(e -> {
            editor.editCustomer(new Person("", ""));
            editor.isHuman.setValue(true);
        });

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        listCustomers(null);
    }

    void deleteDialog(Person person) {

        Dialog dialog = new Dialog();
        Button confirm = new Button("Delete");
        confirm.getElement().getThemeList().add("error");
        Button cancel = new Button("Cancel");
        cancel.getElement().getThemeList().add("primary");
        dialog.add("Are you sure to remove contact?");
        dialog.add(confirm);
        dialog.add(cancel);

        confirm.addClickListener(clickEvent -> {
            // personController.cleanPeopleById(person.getId());
            repository.delete((person));
            dialog.close();
            Notification notification = new Notification("Person info is removed.", 500);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();

            listCustomers(null);
        });

        cancel.addClickListener(clickEvent -> dialog.close());

        dialog.open();
    }

    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            List<Person> personList = repository.findAll();
            grid.setItems(personList);
        } else {
            List<Person> personList = repository.findBySecondNameStartsWithIgnoreCase(filterText);
            grid.setItems(personList);
        }
    }

    void listCustomers1(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            //without this two line method trick app gives
            //org.springframework.beans.factory.NoSuchBeanDefinitionException or
            //org.springframework.dao.InvalidDataAccessResourceUsageException
            List<Person> personList = repository.findAll();
            grid.setItems(personList);
        } else {
            List<Person> personList = repository.findByFirstNameStartsWithIgnoreCase(filterText);
            grid.setItems(personList);

        }

    }

}
