package com.rest.VaadinUI;

import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

//@ActiveProfiles("release")
@SpringComponent
@UIScope
public class PersonEditor extends VerticalLayout implements KeyNotifier {

    PersonRepository repository;
    private Person person = new Person();

    TextField firstName = new TextField("First Name");
    TextField secondName = new TextField("Second Name");
    TextField phoneNumber = new TextField("Phone Number");

    EmailField emailField = new EmailField("Email");
    DatePicker birthDay = new DatePicker("Birth Day");

    Checkbox isHuman = new Checkbox("is Human", true);

    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel changes");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());


    public Binder<Person> binder = new Binder<>(Person.class);
    private ChangeHandler changeHandler;


    @Autowired
    public PersonEditor(PersonRepository repository) {

        this.repository = repository;
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage("Please enter a valid email address");

        HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
        bindingFields();

        VerticalLayout vLayout1 = new VerticalLayout();
        VerticalLayout vLayout2 = new VerticalLayout();

        vLayout1.add(firstName, secondName, phoneNumber);
        vLayout2.add(emailField, birthDay, isHuman);
        isHuman.setValue(true);
        HorizontalLayout hLayout = new HorizontalLayout(vLayout1, vLayout2);

        add(hLayout, actions);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> {
            repository.save(person);
            changeHandler.onChange();
        });

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> {
            repository.save(person);
            changeHandler.onChange();
        });

        delete.addClickListener(e -> sureDeleteDialog(person));
        // delete.addClickListener(e -> {repository.delete(person);changeHandler.onChange();});
        cancel.addClickListener(e -> editCustomer(person));
        setVisible(false);
    }

    void sureDeleteDialog(Person person) {

        Dialog dialog = new Dialog();
        Button confirm = new Button(" Sure delete ?");
        confirm.getElement().getThemeList().add("error");
        Button cancel = new Button("Cancel");
        cancel.getElement().getThemeList().add("primary");
        dialog.add(confirm);
        dialog.add(cancel);

        confirm.addClickListener(clickEvent -> {
            // personController.cleanPeopleById(person.getId());
            repository.delete((person));
            dialog.close();
            Notification notification = new Notification("Person info is removed", 900);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();

            changeHandler.onChange();
        });

        cancel.addClickListener(clickEvent -> dialog.close());

        dialog.open();
    }

    private void bindingFields() {
        binder.forField(firstName)
                .withValidator(
                        firstName -> firstName.length() >= 2,
                        "Name must contain at least two characters")
                .bind(Person::getFirstName, Person::setFirstName);

        binder.forField(secondName)
                .withValidator(
                        secondName -> secondName.length() >= 2,
                        "Name must contain at least two characters")
                .bind(Person::getSecondName, Person::setSecondName);


        binder.forField(phoneNumber)
                .withValidator(
                        phoneNumber -> phoneNumber.length() >= 10,
                        "Number must contain at least ten signs")
                .withValidator(this::isNumericArray, (
                        "Must enter a number"))
                .bind(Person::getNumberPhone, Person::setNumberPhone);

        binder.forField(emailField)
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .bind(Person::getEmail, Person::setEmail);

        binder.forField(isHuman)
                .bind(Person::isHuman, Person::setHuman);

        binder.bindInstanceFields(this);
    }

    public final void editCustomer(Person customerUnderEdit) {
        //    Person person = binder.getBean();
        if (customerUnderEdit == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = (customerUnderEdit.getId() != null);

        if (persisted) {
            // Find fresh entity for editing
            person = repository.findById(customerUnderEdit.getId()).get();
        } else {
            person = customerUnderEdit;
        }

        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(person);

        setVisible(true);

        firstName.focus();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        // ChangeHandler is notified when either save or delete is clicked
        this.changeHandler = changeHandler;
    }

    public boolean isNumericArray(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray())
            if (c < '0' || c > '9')
                return false;
        return true;
    }


}
