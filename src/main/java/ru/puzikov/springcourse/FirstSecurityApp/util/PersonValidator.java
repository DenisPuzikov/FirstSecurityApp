package ru.puzikov.springcourse.FirstSecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.puzikov.springcourse.FirstSecurityApp.Models.Person;
import ru.puzikov.springcourse.FirstSecurityApp.services.PersonDetailsService;

@Component
public class PersonValidator implements Validator {

    private PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored){
            return;
        }
        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }
}
