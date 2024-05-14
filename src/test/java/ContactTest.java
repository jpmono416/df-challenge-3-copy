import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactTest {


    // User story 1
    @Test
    @DisplayName("Should save email as lowercase")
    public void shouldSaveEmailAsLowerCase() {
        Contact contact = new Contact("John Doe", "07123456789", "Foo@Gmail.com");

        assertEquals("foo@gmail.com", contact.getEmail());
    }

    @Test
    @DisplayName("Should validate the email address")
    public void shouldValidateEmailAddress() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("John Doe", "07123456789", "@Gmail.com"));
    }
}