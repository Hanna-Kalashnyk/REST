package com.rest.VaadinUI;//package dev.protsenko.vaadin.web;

import com.rest.Entity.Person;
import com.rest.Repository.PersonRepository;
import com.rest.Security.SecuredByEmail;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Optional;
//@ActiveProfiles("release")
@Route(value = ManagePerson.ROUTE)
//@Secured("ROLE_USER")
@SecuredByEmail

//@Route("manageContact")
public class ManagePerson extends AppLayout implements HasUrlParameter<Long> {
    public static final String ROUTE = "managePerson";

    Anchor logout;
    Long id;
    FormLayout contactForm;
    TextField firstName;
    TextField secondName;
    TextField numberPhone;
    TextField email;
    DatePicker birthDay;
    Checkbox isHuman;
    Button saveContact;

    @Autowired
      PersonRepository personRepository;
    @Autowired
    public ManagePerson() {
        //Создаем объекты для формы
        contactForm = new FormLayout();
        firstName = new TextField("Имя");
        secondName = new TextField("Фамилия");
        numberPhone = new TextField("Номер телефона");
        email = new TextField("Электронная почта");
        birthDay = new DatePicker("Birth Day",LocalDate.now());
        isHuman = new Checkbox("is Human", true);
        saveContact = new Button("Сохранить");
        logout = new Anchor("/logout", "Log out");
        //Добавим все элементы на форму
        contactForm.add(firstName, secondName, numberPhone, email, birthDay, isHuman, saveContact, logout);
        setContent(contactForm);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long contactId) {
        id = contactId;
        if (!id.equals(0L)) {
            addToNavbar(new H3("Редактирование контакта"));
        } else {
            addToNavbar(new H3("Создание контакта"));
        }
        fillForm();
    }


    public void fillForm() {

        if (!id.equals(0L)) {
            Optional<Person> contact = personRepository.findById(id);
            contact.ifPresent(x -> {
                firstName.setValue(x.getFirstName());
                secondName.setValue(x.getSecondName());
                numberPhone.setValue(x.getNumberPhone());
                email.setValue(x.getEmail());
                birthDay.setValue(x.getBirthDay());
                isHuman.setValue(x.isHuman());
            });
        }

        saveContact.addClickListener(clickEvent -> {
            //Создадим объект контакта получив значения с формы
            Person contact = new Person();
            if (!id.equals(0L)) {
                contact.setId(id);
            }
            contact.setFirstName(firstName.getValue());
            contact.setSecondName(secondName.getValue());
            contact.setEmail(email.getValue());
            contact.setNumberPhone(numberPhone.getValue());
            contact.setBirthDay(birthDay.getValue());
            contact.setHuman(isHuman.getValue());
            personRepository.save(contact);

            //Выведем уведомление пользователю и переведем его к списку контактов
            Notification notification = new Notification(id.equals(0L) ? "Контакт успешно создан" : "Контакт был изменен", 500);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.addDetachListener(detachEvent -> UI.getCurrent().navigate(MainView.class));
            contactForm.setEnabled(false);
            notification.open();
        });
    }
}
