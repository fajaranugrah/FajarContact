package com.example.fajarcontact;

import com.example.fajarcontact.Model.Contact;
import com.example.fajarcontact.ViewModel.ContactViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ContactViewModelTest {

    private static final String TRUE_CONTACT = "Contact Saved";
    private static final String WRONG_CONTACT = "Contact Not Saved";

    //email
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(ContactViewModel.isValidEmail("name@email.com"));
    }
    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(ContactViewModel.isValidEmail("name@email.co.uk"));
    }
    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(ContactViewModel.isValidEmail("name@email"));
    }
    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(ContactViewModel.isValidEmail("name@email..com"));
    }
    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(ContactViewModel.isValidEmail("@email.com"));
    }
    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(ContactViewModel.isValidEmail(""));
    }
    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(ContactViewModel.isValidEmail(null));
    }

    //name
    @Test
    public void nameValidator_CorrectNameSimple_ReturnsTrue() {
        assertTrue(ContactViewModel.isValidEmpty("fajar"));
    }
    @Test
    public void nameValidator_CorrectNameNumberSimple_ReturnsTrue() {
        assertTrue(ContactViewModel.isValidEmpty("fajar123"));
    }
    @Test
    public void nameValidator_EmptyString_ReturnsFalse() {
        assertFalse(ContactViewModel.isValidEmpty(""));
    }
    @Test
    public void nameValidator_NullName_ReturnsFalse() {
        assertFalse(ContactViewModel.isValidEmpty(null));
    }

    //setup the contact
    Contact contact;

    public Contact setUp(){
        contact = new Contact("fajar", "0857946587965", "fajar@gmail.com", "Jalan Klang Lama, No. 2, Jalan 1/13a, 58000 Kuala Lumpur");
        return contact;
    }

    public Contact setUpWrong(){
        contact = new Contact("fajar12@", "0857946587965", "fajar@gmail...com", "Jalan Klang Lama, No. 2, Jalan 1/13a, 58000 Kuala Lumpur");
        return contact;
    }

    //add contact
    @Test
    public void addContact() {
        String result = ContactViewModel.insertSucced(setUp());
        //then the result should be the expected one.
        assertThat(result, is(TRUE_CONTACT));
    }

    @Test
    public void wrongAddContact() {
        String result = ContactViewModel.insertSucced(setUpWrong());
        //then the result should be the expected one.
        assertThat(result, is(WRONG_CONTACT));
    }
}
