package com.example.springessentials;

import com.example.springessentials.model.Contact;
import com.example.springessentials.repository.ContactRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Valid
    public void createShouldPersistData() throws ParseException {
        Contact contact = new Contact("Gustavo", "gustavo@gustavo.com", "86891256046", sdf.parse("1990-09-30"));
        contactRepository.save(contact);
        assertThat(contact.getId()).isNotNull();
        assertThat(contact.getName()).isEqualTo("Gustavo");
        assertThat(contact.getEmail()).isEqualTo("gustavo@gustavo.com");
    }

    @Test
    @Valid
    public void updateShouldPersistData() throws ParseException {
        Contact contact = new Contact("Gustavo", "gustavo@gustavo.com", "86891256046", sdf.parse("1990-09-30"));
        contactRepository.save(contact);
        contact.setName("Gustavo de Almeida Santos");
        contact = contactRepository.save(contact);
        assertThat(contact.getName()).isEqualTo("Gustavo de Almeida Santos");
    }

    @Test
    public void createWithWrongCPFShouldFail() throws ParseException {
        Contact contact = new Contact("Gustavo", "gustavo@gustavo.com", "123456", sdf.parse("1990-09-30"));
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        assertThat(violations.size() > 0).isTrue();
    }

    @Test
    public void deleteShouldRemoveData() throws ParseException {
        Contact contact = new Contact("Gustavo", "gustavo@gustavo.com", "86891256046", sdf.parse("1990-09-30"));
        contactRepository.save(contact);
        contactRepository.delete(contact);
        assertThat(contactRepository.findById(contact.getId()).isPresent()).isFalse();
    }


}
