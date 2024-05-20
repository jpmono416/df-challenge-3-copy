import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IOManager {
    private final Scanner scanner;

    public IOManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getCommand() {
        print("Enter a command:");
        return validateCommand(read());
    }

    public Map<String, String> getContactDetails() {
        print(IOConstants.ENTER_NAME);
        String name = validateName(read());

        print(IOConstants.ENTER_EMAIL);
        String email = validateEmail(read());

        print(IOConstants.ENTER_PHONE);
        String phoneNumber = validatePhoneNumber(read());

        return Map.of("name", name, "phoneNumber", phoneNumber, "email", email);
    }

    public String getDeleteConfirmation() {
        print(IOConstants.CONFIRM_DELETE_ALL);
        String confirmation = read();
        while (!confirmation.equals("yes") && !confirmation.equals("no")) {
            printError(IOConstants.INVALID_YES_NO);
            confirmation = read();
        }
        return confirmation;
    }

    public String getContactID() {
        print(IOConstants.ENTER_ID);
        String idString = validateIDString(read());
        return idString;
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.err.println(IOConstants.ERROR + message);
    }

    public void printContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            print(IOConstants.NO_CONTACTS);
            return;
        }

        print(IOConstants.CONTACTS);
        contacts.forEach(this::printContact);
    }

    public void printContact(Contact contact) {
        print("Name: " + contact.getName() + " | Phone: " + contact.getPhoneNumber() + " | Email: " + contact.getEmail());
    }

    public void printHelp() {
        print(IOConstants.HELP_BODY);
    }

    private String read() {
        return scanner.nextLine();
    }

    private String validateCommand(String command) {
        while (!IOConstants.COMMANDS.contains(command)) {
            printError(IOConstants.COMMAND_NOT_RECOGNISED);
            command = read();
        }
        return command;
    }

    private String validateIDString(String idString) {
        while(!(Validation.validateEmail(idString) || Validation.validatePhoneNumber(idString))) {
            printError(IOConstants.INVALID_ID);
            idString = read();
        }

        return idString;
    }

    private String validateEmail(String email) {
        while (!Validation.validateEmail(email)) {
            printError(IOConstants.INVALID_EMAIL);
            email = read();
        }
        return email;
    }

    private String validateName(String name) {
        while (!Validation.validateName(name)) {
            printError(IOConstants.INVALID_NAME);
            name = read();
        }
        return name;
    }

    private String validatePhoneNumber(String phoneNumber) {
        while (!Validation.validatePhoneNumber(phoneNumber)) {
            printError(IOConstants.INVALID_PHONE_NUMBER);
            phoneNumber = read();
        }
        return phoneNumber;
    }
}
