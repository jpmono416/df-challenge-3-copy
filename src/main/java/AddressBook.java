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
        return OutputValues.CONTACT_ADDED_SUCCESSFULLY; // TODO: Not needed! Remove later and tests point to App IOManager
    }

    public void addContacts(List<Contact> contacts) {
        this.contacts.addAll(contacts);
    }

    public List<Contact> getContactsByName(String name) {
        List<Contact> matchingContacts = new ArrayList<>();

        this.contacts.stream().filter(contact -> contact.getName().equals(name)).forEach(matchingContacts::add);

        return matchingContacts;
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }

    public void removeContact(Contact contact) {
        if(!this.contacts.contains(contact))
            throw new IllegalArgumentException("Contact does not exist in the address book");

        this.contacts.remove(contact);
    }

    public void removeContact(String details) {
        // Check this
        this.contacts.removeIf(contact -> contact.getName().equals(details) || contact.getPhoneNumber().equals(details) || contact.getEmail().equals(details));
    }


}
