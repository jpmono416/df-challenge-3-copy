import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AddressBook {

    final private List<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }
    public String addContact(Contact contact) {
        this.contacts.add(contact);
        return OutputValues.CONTACT_ADDED_SUCCESSFULLY;
    }

    public void addContacts(List<Contact> contacts) {
        this.contacts.addAll(contacts);
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }
}
