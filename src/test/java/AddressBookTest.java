import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        assertEquals(Collections.EMPTY_LIST, testAddressBook.getContactsByAnyMatch("John Doe", false));
    }

    @Test
    @DisplayName("Should return the contact with a given name")
    public void shouldReturnContactWhenSearchByName() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getName()).thenReturn("John Doe");
        when(testContact.getPhoneNumber()).thenReturn("07123456789");
        when(testContact.getEmail()).thenReturn("bar@foo.com");

        when(testContact2.getName()).thenReturn("John Smith");
        when(testContact2.getPhoneNumber()).thenReturn("07987654321");
        when(testContact2.getEmail()).thenReturn("foo@bar.com");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(List.of(testContact), testAddressBook.getContactsByAnyMatch("John Doe", false));
        assertEquals(List.of(testContact2), testAddressBook.getContactsByAnyMatch("John Smith", false));
        assertEquals(Collections.EMPTY_LIST, testAddressBook.getContactsByAnyMatch("John Deere", false));
    }

    @Test
    @DisplayName("Should return all contacts with the same name")
    public void shouldReturnAllContactsWithSameNameWhenSearchByName() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        when(testContact.getName()).thenReturn("John Doe");
        when(testContact.getPhoneNumber()).thenReturn("07123456789");
        when(testContact.getEmail()).thenReturn("bar@foo.com");

        when(testContact2.getName()).thenReturn("John Doe");
        when(testContact2.getPhoneNumber()).thenReturn("07987654321");
        when(testContact2.getEmail()).thenReturn("foo@bar.com");

        when(testContact3.getName()).thenReturn("John Smith");
        when(testContact3.getPhoneNumber()).thenReturn("07987654321");
        when(testContact3.getEmail()).thenReturn("bar@bar.com");

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        assertEquals(List.of(testContact, testContact2), testAddressBook.getContactsByAnyMatch("John Doe", false));
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
                () -> assertThrows(NoSuchElementException.class, () -> testAddressBook.removeContact(testContact2)),
                () -> assertEquals(List.of(testContact), testAddressBook.getContacts())
        );
    }

    @Test
    @DisplayName("Should remove a contact by email address or phone number")
    public void shouldRemoveContactByEmailOrPhoneNumber() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact.getPhoneNumber()).thenReturn("07123456777");
        when(testContact2.getEmail()).thenReturn("bar@foo.com");
        when(testContact2.getPhoneNumber()).thenReturn("07123456789");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertAll(
                () -> assertDoesNotThrow(() -> testAddressBook.removeContact("foo@bar.com")),
                () -> assertDoesNotThrow(() -> testAddressBook.removeContact("07123456789")),
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
        assertThrows(NoSuchElementException.class, () -> testAddressBook.getContactById("07123456789"));
    }

    @Test
    @DisplayName("Should validate the format of the phone number")
    public void shouldValidatePhoneWhenGetByPhone() {
        assertThrows(IllegalArgumentException.class, () -> testAddressBook.getContactById("77123456789"));
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
        assertThrows(NoSuchElementException.class, () -> testAddressBook.getContactById("foo@bar.com"));
    }

    @Test
    @DisplayName("Should validate the format of the email")
    public void shouldValidateEmailWhenGetByEmail() {
        assertThrows(IllegalArgumentException.class, () -> testAddressBook.getContactById("invalid"));
    }

    @Test
    @DisplayName("Should be case-insensitive when searching by id")
    public void shouldBeCaseInsensitiveWhenSearching() {
        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact.getPhoneNumber()).thenReturn("07123456789");

        testAddressBook.addContact(testContact);

        assertEquals(testContact, testAddressBook.getContactById("FOO@BAR.COM"));
    }

    // User story 10
    @Test
    @DisplayName("Should return the contacts in alphabetical order by name when searching by name")
    public void shouldSearchByNameAlphabetical() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getName()).thenReturn("Foo Bar");
        when(testContact2.getName()).thenReturn("Bar Foo");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(testContact2, testAddressBook.getContactsByAnyMatch("Bar", true).get(0));
    }

    @Test
    @DisplayName("Should return the contacts in alphabetical order by name when searching by email")
    public void shouldSearchByEmailAlphabetical() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact.getName()).thenReturn("Foo B");
        when(testContact.getPhoneNumber()).thenReturn("07123456789");

        when(testContact2.getEmail()).thenReturn("bar@foo.com");
        when(testContact2.getName()).thenReturn("B Foo");
        when(testContact2.getPhoneNumber()).thenReturn("07987654321");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(testContact2, testAddressBook.getContactsByAnyMatch("bar", true).get(0));
    }

    @Test
    @DisplayName("Should return the contacts in alphabetical order by name when searching by phone number")
    public void shouldSearchByPhoneAlphabetical() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getPhoneNumber()).thenReturn("07123456789");
        when(testContact.getName()).thenReturn("Foo B");
        when(testContact.getEmail()).thenReturn("foo@bar.com");

        when(testContact2.getPhoneNumber()).thenReturn("07987654321");
        when(testContact2.getName()).thenReturn("B Foo");
        when(testContact2.getEmail()).thenReturn("bar@foo.com");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(testContact2, testAddressBook.getContactsByAnyMatch("07", true).get(0));
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

    // User story 12
    @Test
    @DisplayName("Should sort all contacts in alphabetical order by name")
    public void shouldSortContactsByName() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        when(testContact.getName()).thenReturn("Second");
        when(testContact2.getName()).thenReturn("Third");
        when(testContact3.getName()).thenReturn("First");

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        List<Contact> sorted = testAddressBook.sortContactsByName(Optional.empty());
        assertAll(
                () -> assertEquals("First", sorted.get(0).getName()),
                () -> assertEquals("Second", sorted.get(1).getName()),
                () -> assertEquals("Third", sorted.get(2).getName())
        );
    }

    @Test
    @DisplayName("Should sort all contacts in alphabetical order by email")
    public void shouldSortContactsByEmail() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        when(testContact.getEmail()).thenReturn("bar@foo.com");
        when(testContact2.getEmail()).thenReturn("foo@bar.com");
        when(testContact3.getEmail()).thenReturn("bar@bar.com");

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        List<Contact> sorted = testAddressBook.sortContactsByEmail(Optional.empty());
        assertAll(
                () -> assertEquals("bar@bar.com", sorted.get(0).getEmail()),
                () -> assertEquals("bar@foo.com", sorted.get(1).getEmail()),
                () -> assertEquals("foo@bar.com", sorted.get(2).getEmail())
        );
    }

    @Test
    @DisplayName("Should sort all contacts in alphabetical order by phone number")
    public void shouldSortContactsByPhone() {
        Contact testContact2 = mock(Contact.class);
        Contact testContact3 = mock(Contact.class);

        when(testContact.getPhoneNumber()).thenReturn("07111222333");
        when(testContact2.getPhoneNumber()).thenReturn("07999999999");
        when(testContact3.getPhoneNumber()).thenReturn("07234567890");

        testAddressBook.addContacts(List.of(testContact, testContact2, testContact3));

        List<Contact> sorted = testAddressBook.sortContactsByPhone(Optional.empty());
        assertAll(
                () -> assertEquals("07111222333", sorted.get(0).getPhoneNumber()),
                () -> assertEquals("07234567890", sorted.get(1).getPhoneNumber()),
                () -> assertEquals("07999999999", sorted.get(2).getPhoneNumber())
        );
    }

    @Test
    @DisplayName("Should allow to sort a custom list of contacts by phone")
    public void shouldSortCustomListByPhone() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getPhoneNumber()).thenReturn("07999999999");
        when(testContact2.getPhoneNumber()).thenReturn("07111222333");

        List<Contact> sorted = testAddressBook.sortContactsByPhone(Optional.of(List.of(testContact, testContact2)));
        assertAll(
                () -> assertEquals("07111222333", sorted.get(0).getPhoneNumber()),
                () -> assertEquals("07999999999", sorted.get(1).getPhoneNumber())
        );
    }

    @Test
    @DisplayName("Should allow to sort a custom list of contacts by email")
    public void shouldSortCustomListByEmail() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getEmail()).thenReturn("foo@bar.com");
        when(testContact2.getEmail()).thenReturn("bar@foo.com");

        List<Contact> sorted = testAddressBook.sortContactsByEmail(Optional.of(List.of(testContact, testContact2)));
        assertAll(
                () -> assertEquals("bar@foo.com", sorted.get(0).getEmail()),
                () -> assertEquals("foo@bar.com", sorted.get(1).getEmail())
        );
    }

    @Test
    @DisplayName("Should allow to sort a custom list of contacts by email")
    public void shouldSortCustomListByName() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getEmail()).thenReturn("Second");
        when(testContact2.getEmail()).thenReturn("First");

        List<Contact> sorted = testAddressBook.sortContactsByEmail(Optional.of(List.of(testContact, testContact2)));
        assertAll(
                () -> assertEquals("First", sorted.get(0).getEmail()),
                () -> assertEquals("Second", sorted.get(1).getEmail())
        );
    }

    // User story 13
    @Test
    @DisplayName("Should return the total number of contacts  in list")
    public void shouldReturnContactCount() {
        assertEquals(0, testAddressBook.getContactCount());

        testAddressBook.addContact(testContact);

        assertEquals(1, testAddressBook.getContactCount());
    }

    // User story 14
    @Test
    @DisplayName("Should return partial matches when search by name")
    public void shouldPartialMatchWhenSearchByName() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getName()).thenReturn("Bar Foo");
        when(testContact2.getName()).thenReturn("Foo Bar");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(2, testAddressBook.getContactsByAnyMatch("Foo", false).size());
    }

    @Test
    @DisplayName("Should return partial matches when search by email")
    public void shouldPartialMatchWhenSearchByEmail() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getName()).thenReturn("Bar@bar.com");
        when(testContact2.getName()).thenReturn("Foo@bar.com");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(2, testAddressBook.getContactsByAnyMatch("@bar.com", false).size());
    }

    @Test
    @DisplayName("Should return partial matches when search by phone number")
    public void shouldPartialMatchWhenSearchByPhone() {
        Contact testContact2 = mock(Contact.class);

        when(testContact.getName()).thenReturn("07123456789");
        when(testContact2.getName()).thenReturn("07897654321");

        testAddressBook.addContacts(List.of(testContact, testContact2));

        assertEquals(2, testAddressBook.getContactsByAnyMatch("789", false).size());
    }
}