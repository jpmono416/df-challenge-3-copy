import java.util.List;

public final class IOConstants {

    // Commands
    public static final List<String> COMMANDS = List.of("exit", "help",  "cancel", "add", "addMultiple", "update", "remove", "removeAll",  "getAll", "getById", "getAny", "getAnySorted", "sortByName", "sortByPhone", "sortByEmail");
    public static final String HELP_BODY =  "These are the commands you can use: " +
            "add - Add a new contact" +
            "  remove - Remove a contact" +
            "  removeAll - Remove all contacts" +
            "  update - Update a contact" +
            "  get - Get a contact by email or phone number" +
            "  getAll - Get all contacts" +
            "  getById - Get a contact by email or phone number" +
            "  getAny - Get a contact by any matching field (partial matches allowed)" +
            "  getAnySorted - Get a contact by email or phone number, sorted by name" +
            "  sortByName - Get all contacts, sorted by name" +
            "  sortByPhone - Get all contacts, sorted by phone number" +
            "  sortByEmail - Get all contacts, sorted by email";

    // Return messages
    public static final String COMMAND_NOT_RECOGNISED = "Command not recognised. Type 'help' for a list of commands";
    public static final String CONTACT_ADDED_SUCCESSFULLY = "Contact added successfully";
    public static final String CONTACT_UPDATED_SUCCESSFULLY = "Contact updated successfully";
    public static final String CONTACT_REMOVED_SUCCESSFULLY = "Contact removed successfully";
    public static final String CONTACT_REMOVED_ALL_SUCCESSFULLY = "All contacts removed successfully";
    public static final String CANCELLED = "Cancelled";
    public static final String CONTACTS = "Contacts:";

    // Error messages
    public static final String ERROR = "Error: ";
    public static final String INVALID_ID = "Invalid ID. Please enter a valid email or phone number.";
    public static final String INVALID_NAME = "Invalid name. Only letters and spaces are allowed";
    public static final String INVALID_EMAIL = "Invalid email address. Format should be name@domain.com";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number. Format should be 07 followed by 9 digits";
    public static final String INVALID_YES_NO = "Invalid input. Type 'yes' to confirm, or 'no' to cancel.";
    public static final String CANCEL_NOT_ALLOWED = "Cancel not allowed here. Only while managing contacts";
    public static final String NO_CONTACTS = "There are no contacts in the address book.";

    // Prompts
    public static final String ENTER_ID = "Enter the phone number or email address of the contact:";
    public static final String ENTER_NAME =  "Enter the contact's Name:";
    public static final String ENTER_EMAIL = "Enter the contact's email address:";
    public static final String ENTER_PHONE = "Enter the contact's phone number:";
    public static final String CONFIRM_DELETE_ALL = "Are you sure you want to delete all contacts? Type 'yes' to confirm, or 'no' to cancel.";

}
