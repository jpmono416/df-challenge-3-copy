import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressBookTest {

    AddressBook testAddressBook;
    Contact testContact;

    @BeforeEach
    public void setUp() {
        testAddressBook = new AddressBook();
        testContact = mock(Contact.class);
    }

    @AfterEach
    public void unset() {
        testAddressBook = null;
        testContact = null;
    }

    // User Story 1
    @Test
    @DisplayName("Should add a contact to the address book")
    public void shouldAddContact() {
        testAddressBook.addContact(testContact);

        assertAll(
                () -> assertEquals(1, testAddressBook.getContacts().size()),
                () -> assertEquals(testContact, testAddressBook.getContacts().get(0))
        );
    }

    @Test
    @DisplayName("Should add multiple contacts to the address book")
    public void shouldAddMultipleContacts() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        assertAll(
                () -> assertEquals(3, testAddressBook.getContacts().size()),
                () -> assertEquals(testContact, testAddressBook.getContacts().get(0)),
                () -> assertEquals(testContact2, testAddressBook.getContacts().get(1)),
                () -> assertEquals(testContact3, testAddressBook.getContacts().get(2))
        );
    }

    @Test
    @DisplayName("Should return a succesful message after adding a contact")
    public void addContactShouldReturnSuccesfulMessage() {
        assertEquals(OutputValues.CONTACT_ADDED_SUCCESSFULLY, testAddressBook.addContact(testContact));
    }

    // User Story 2
    @Test
    @DisplayName("Should return an empty list if no contact is found")
    public void shouldReturnEmptyListWhenNoContactFound() {
        assertEquals(Collections.EMPTY_LIST, testAddressBook.getContactsByName("John Doe"));
    }

    @Test
    @DisplayName("Should return the contact with a given name")
    public void shouldReturnContactWhenSearchByName() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getName()).thenReturn("John Doe");
        when(testContact2.getName()).thenReturn("John Smith");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(List.of(testContact), testAddressBook.getContactsByName("John Doe"));
        assertEquals(List.of(testContact2), testAddressBook.getContactsByName("John Smith"));
        assertEquals(Collections.EMPTY_LIST, testAddressBook.getContactsByName("John Deere"));
    }

    @Test
    @DisplayName("Should return all contacts with the same name")
    public void shouldReturnAllContactsWithSameNameWhenSearchByName() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        when(testContact.getName()).thenReturn("John Doe");
        when(testContact2.getName()).thenReturn("John Doe");
        when(testContact3.getName()).thenReturn("John Smith");

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        assertEquals(List.of(testContact, testContact2), testAddressBook.getContactsByName("John Doe"));
    }

    // User Story 3
    @Test
    @DisplayName("Should remove a contact from the address book")
    public void shouldRemoveContact() {
        testAddressBook.addContact(testContact);

        testAddressBook.removeContact(testContact);

        assertEquals(Collections.EMPTY_LIST, testAddressBook.getContacts());
    }

    @Test
    @DisplayName("Should not remove a contact that does not exist")
    public void shouldNotRemoveContactThatDoesNotExist() {
        testAddressBook.addContact(testContact);

        Contact testContact2 = mock(Contact.class);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> testAddressBook.removeContact(testContact2)),
                () -> assertEquals(List.of(testContact), testAddressBook.getContacts())
        );
    }


}
