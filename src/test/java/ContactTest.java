import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactTest {


    // User story 1
    @Test
    @DisplayName("Should save email as lowercase")
    public void shouldSaveEmailAsLowerCase() {
        Contact contact = new Contact("John Doe", "07123456789", "Foo@Gmail.com");

        assertEquals("foo@gmail.com", contact.getEmail());
    }
}
