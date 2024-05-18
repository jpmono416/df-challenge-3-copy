import java.util.*;
import java.util.stream.Collectors;

public class AddressBook {

    final private List<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        if (idAlreadyExists(contact.getEmail(), contact.getPhoneNumber()))
            throw new IllegalArgumentException("Contact with the same email or phone number already exists");

        this.contacts.add(contact);
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

    /**
     * Since there is a double primary key on email & phone number, this method is in charge of checking
     * against both fields that make up the ID as to avoid duplicated logic and improving complexity.
     * @apiNote  This function is a wrapper around the findContactById function, which is private, so that if logic changes in the future
     * it can be easily updated here without modifying the behaviour of the other public methods that use it.
     *
     * @param idString : The phone number or email address of the contact, inputted by the user
     * @return : The contact with the given id or null if not found
     */
    public Contact getContactById(String idString) {
        Contact result = findContactById(idString);

        if (result == null)
            throw new IllegalArgumentException("Contact does not exist");

        return result;
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
        if (idAlreadyExists(editedContact.getEmail(), editedContact.getPhoneNumber()))
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

    public void deleteAllContacts() {
        if(this.contacts.isEmpty())
            throw new NoSuchElementException("There are no contacts to delete in the address book.");

        this.contacts.clear();
    }

    public List<Contact> sortContactsByName(Optional<List<Contact>> optContactsToSort) {
        List<Contact> toSort = optContactsToSort.orElse(this.contacts);

        return toSort.stream()
                .sorted(Comparator.comparing(Contact::getName))
                .collect(Collectors.toList());

    }

    public List<Contact> sortContactsByEmail(Optional<List<Contact>> optContactsToSort) {
        List<Contact> toSort = optContactsToSort.orElse(this.contacts);

        return toSort.stream()
                .sorted(Comparator.comparing(Contact::getEmail))
                .collect(Collectors.toList());
    }

    public List<Contact> sortContactsByPhone(Optional<List<Contact>> optContactsToSort) {
        List<Contact> toSort = optContactsToSort.orElse(this.contacts);

        return toSort.stream()
                .sorted(Comparator.comparing(Contact::getPhoneNumber))
                .collect(Collectors.toList());
    }

    public int getContactCount() {
        return this.contacts.size();
    }
}
