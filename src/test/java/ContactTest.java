import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    private static final String defaultName = "John Doe";
    public static final String defaultPhoneNumber = "07123456789";
    public static final String defaultEmail = "foo@gmail.com";

    // User story 1
    @Test
    @DisplayName("Should save email as lowercase")
    public void shouldSaveEmailAsLowerCase() {
        Contact contact = new Contact(defaultName, defaultPhoneNumber, defaultEmail);

        assertEquals(defaultEmail, contact.getEmail());
    }

    @Test
    @DisplayName("Should validate the email address")
    public void shouldValidateEmailAddress() {
        assertAll(
                () -> assertDoesNotThrow(() -> new Contact(defaultName, defaultPhoneNumber, defaultEmail)), // valid contact
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, defaultPhoneNumber, "@gmail.com")), // missing name
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, defaultPhoneNumber, "foo.gmail.com")), // missing @
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, defaultPhoneNumber, "foo@.com")), // missing domain
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, defaultPhoneNumber, "foo@gmailcom")), // missing .
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, defaultPhoneNumber, "foo@gmail.")), // missing com
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, defaultPhoneNumber, "foo@gmail")) // missing .com
        );
    }

    @Test
    @DisplayName("Should validate the phone number")
    public void shouldValidatePhoneNumber() {
        assertAll(
                () -> assertDoesNotThrow(() -> new Contact(defaultName, defaultPhoneNumber, defaultEmail)), // valid contact
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, "0712345678", defaultEmail)), // wrong amount of digits
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, "071234567890", defaultEmail)), // wrong amount of digits
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, "071234567a", defaultEmail)), // contains a letter
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Contact(defaultName, "12345678901", defaultEmail)) // does not start with 07
        );
    }


}