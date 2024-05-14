import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

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
                () -> assertEquals( 1, testAddressBook.getContacts().size()),
                () -> assertEquals(testContact, testAddressBook.getContacts().get(0))
        );
    }

    @Test
    @DisplayName("Should return a succesful message after adding a contact")
    public void  addContactShouldReturnSuccesfulMessage() {
        assertEquals(OutputValues.CONTACT_ADDED_SUCCESSFULLY, testAddressBook.addContact(testContact));
    }

}
