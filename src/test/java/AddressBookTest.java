import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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

    // TODO - Write the code to pass this later
    //@Test
    //@DisplayName("Should remove a contact by email address or phone number")
    public void shouldRemoveContactByEmailOrPhoneNumber() {
        Contact testContact2 = mock(Contact.class);
        String testEmail = "foo@gmail.com";
        String testPhoneNumber = "07123456789";

        when(testContact.getEmail()).thenReturn(testEmail);
        when(testContact2.getPhoneNumber()).thenReturn(testPhoneNumber);

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertAll(
                () -> assertDoesNotThrow(() -> testAddressBook.removeContact(testEmail)),
                () -> assertDoesNotThrow(() -> testAddressBook.removeContact(testPhoneNumber)),
                () -> assertEquals(Collections.EMPTY_LIST, testAddressBook.getContacts())
        );
    }

    // User story 4
    @Test
    @DisplayName("Should edit a contact in the address book")
    public void shouldUpdateContact() {
        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact.getPhoneNumber()).thenReturn("07123456789"); // Used in findContactById
        Contact editedContact = mock(Contact.class);

        testAddressBook.addContact(testContact);
        testAddressBook.updateContact(testContact.getEmail(), editedContact);

        assertAll(
                () -> assertEquals(1, testAddressBook.getContacts().size()),
                () -> assertEquals(editedContact, testAddressBook.getContacts().get(0))
        );
    }

    @Test
    @DisplayName("Should not update a contact that does not exist")
    public void shouldNotUpdateContactThatDoesNotExist() {
        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact.getPhoneNumber()).thenReturn("07123456789"); // Used in findContactById
        Contact editedContact = mock(Contact.class);

        testAddressBook.addContact(testContact);

        assertThrows(IllegalArgumentException.class, () -> testAddressBook.updateContact("invalid", editedContact));
    }

    // User story 5
    @Test
    @DisplayName("Should not update a contact with an id that already exists (email or phone number)")
    public void shouldNotUpdateContactWithExistingEmail() {
        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact.getPhoneNumber()).thenReturn("07123456789");

        // Same email
        Contact testContact2 = mock(Contact.class);
        when(testContact2.getEmail()).thenReturn("foo@bar.com");
        when(testContact2.getPhoneNumber()).thenReturn("07987654321");

        // Same phone number
        Contact testContact3 = mock(Contact.class);
        when(testContact3.getEmail()).thenReturn("bar@foo.com");
        when(testContact3.getPhoneNumber()).thenReturn("07123456789");

        testAddressBook.addContact(testContact);

        // Invalid email
        assertThrows(IllegalArgumentException.class, () -> testAddressBook.updateContact(testContact.getEmail(), testContact2));

        // Invalid phone
        assertThrows(IllegalArgumentException.class, () -> testAddressBook.updateContact(testContact.getPhoneNumber(), testContact3));
    }

    @Test
    @DisplayName("Should not add a contact with an email or phone number that already exists")
    public void shouldNotAddContactWithExistingEmailOrPhoneNumber() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);
        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact.getPhoneNumber()).thenReturn("07123456789");

        when(testContact2.getEmail()).thenReturn("foo@bar.com");
        when(testContact3.getPhoneNumber()).thenReturn("07123456789");

        testAddressBook.addContact(testContact);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> testAddressBook.addContact(testContact2)),
                () -> assertThrows(IllegalArgumentException.class, () -> testAddressBook.addContact(testContact3))
        );
    }

    // User story 6
    @Test
    @DisplayName("Should return all contacts in the address book")
    public void shouldReturnAllContacts() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        assertEquals(List.of(testContact, testContact2, testContact3), testAddressBook.getContacts());
    }

    @Test
    @DisplayName("Should return an empty list if no contacts are found")
    public void shouldReturnEmptyListWhenNoContactsFound() {
        assertEquals(Collections.EMPTY_LIST, testAddressBook.getContacts());
    }

    // User story 8
    @Test
    @DisplayName("Should return the contact with the given phone number")
    public void shouldReturnContactWhenSearchByPhoneNumber() {
        when(testContact.getPhoneNumber()).thenReturn("07123456789");

        testAddressBook.addContact(testContact);

        assertEquals(testContact, testAddressBook.getContactById("07123456789"));
    }

    @Test
    @DisplayName("Should return an exception if the contact does not exist")
    public void shouldReturnExceptionWhenContactDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> testAddressBook.getContactById("07123456789"));
    }

    // User story 9
    @Test
    @DisplayName("Should return the contact with the given email")
    public void shouldReturnContactWhenSearchByEmail() {
        String testEmail = "bar@foo.com";
        when(testContact.getEmail()).thenReturn(testEmail);
        when(testContact.getPhoneNumber()).thenReturn("07123456789");

        testAddressBook.addContact(testContact);

        assertEquals(testContact, testAddressBook.getContactById(testEmail));
    }

    @Test
    @DisplayName("Should return an exception if the contact does not exist")
    public void shouldReturnExceptionWhenContactDoesNotExistByEmail() {
        assertThrows(IllegalArgumentException.class, () -> testAddressBook.getContactById("foo@bar.com"));
    }

    // User story 11
    @Test
    @DisplayName("Should delete all contacts in the address book")
    public void shouldDeleteAllContacts() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        testAddressBook.deleteAllContacts();
        assertEquals(0, testAddressBook.getContacts().size());
    }

    @Test
    @DisplayName("Should not do anything if there are no contacts")
    public void shouldNotDoAnythingWithEmptyList() {
        assertThrows(NoSuchElementException.class, () -> testAddressBook.deleteAllContacts());
    }
}