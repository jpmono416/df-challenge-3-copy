import java.util.ArrayList;
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
        if (!this.contacts.contains(contact))
            throw new IllegalArgumentException("Contact does not exist in the address book");

        this.contacts.remove(contact);
    }

    public void removeContact(String details) {
        // Check this
        this.contacts.removeIf(contact -> contact.getName().equals(details) || contact.getPhoneNumber().equals(details) || contact.getEmail().equals(details));
    }

    /**
     * Updates a contact with the given id (either phone number or email)
     *
     * @param idString      : The phone number or email address of the contact, inputted by the user
     * @param editedContact : The updated contact to be saved
     */
    public void updateContact(String idString, Contact editedContact) {
        // Validate contact exists
        Contact contact = findContactById(idString);
        if (contact == null)
            throw new IllegalArgumentException("Contact does not exist");
        if(idAlreadyExists(editedContact.getEmail(), editedContact.getPhoneNumber()))
            throw new IllegalArgumentException("Contact with the same email or phone number already exists");

        // Update contact
        this.contacts.set(this.contacts.indexOf(contact), editedContact);
    }

    /**
     * Finds a contact by its phone number or email address
     *
     * @param idString : The phone number or email address of the contact
     * @return : The matching contact, or null if not found
     */
    private Contact findContactById(String idString) {
        return contacts.stream()
                .filter(contact -> contact.getPhoneNumber().equals(idString) || contact.getEmail().equals(idString))
                .findFirst()
                .orElse(null);
    }

    private boolean idAlreadyExists(String email, String phoneNumber) {
        return contacts.stream().anyMatch(contact -> contact.getPhoneNumber().equals(phoneNumber) || contact.getEmail().equals(email));
    }

}
