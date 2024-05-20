import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        IOManager ioManager = new IOManager(new Scanner(System.in));
        AddressBook addressBook = new AddressBook();

        while (true) {
            String command = ioManager.getCommand();

            switch (command) {
                case "add":
                    tryAddContact(ioManager, addressBook);
                    break;
                case "remove":
                    tryRemoveContact(ioManager, addressBook);
                    ioManager.print(IOConstants.CONTACT_REMOVED_SUCCESSFULLY);
                    break;
                case "removeAll":
                    tryRemoveAll(ioManager, addressBook);
                    ioManager.print(IOConstants.CONTACT_REMOVED_ALL_SUCCESSFULLY);
                    break;
                case "update":
                    tryUpdate(ioManager, addressBook);
                    
                    break;
                case "getAll":
                    ioManager.printContacts(addressBook.getContacts());
                    break;
                case "getById":
                    tryGetById(ioManager, addressBook);
                    break;
                case "getAny":
                    tryGetAny(ioManager, addressBook, false);
                    break;
                case "getAnySorted":
                    tryGetAny(ioManager, addressBook, true);
                    break;
                case "sortByName":
                    ioManager.printContacts(addressBook.sortContactsByName(Optional.empty()));
                    break;
                case "sortByPhone":
                    ioManager.printContacts(addressBook.sortContactsByEmail(Optional.empty()));
                    break;
                case "sortByEmail":
                    ioManager.printContacts( addressBook.sortContactsByPhone(Optional.empty()));
                    break;
                case "help":
                    ioManager.printHelp();
                    break;
                case "exit":
                    ioManager.print("Thank you for using the address book. Goodbye!");
                    System.exit(0);
                    break;
                case "cancel":
                    ioManager.print(IOConstants.CANCEL_NOT_ALLOWED);
                    break;
                default:
                    ioManager.printError("Invalid command. Type 'help' to see a list of commands.");
            }
        }
    }

    private static void tryGetAny(IOManager ioManager, AddressBook addressBook, boolean sorted) {
        boolean found = false;
        do {
            String idString = ioManager.getContactID();
            if (isCancelled(idString)) {
                ioManager.print(IOConstants.CANCELLED);
                break;
            }

            try
            {
                ioManager.printContacts(addressBook.getContactsByAnyMatch(idString, sorted));
                found = true;
            } catch (RuntimeException e) {
                ioManager.printError(e.getMessage());
            }
        } while (!found);
    }

    private static void tryGetById(IOManager ioManager, AddressBook addressBook) {
        boolean found = false;
        do {
            String idString = ioManager.getContactID();
            if (isCancelled(idString)) {
                ioManager.print(IOConstants.CANCELLED);
                break;
            }

            try {
                ioManager.printContact(addressBook.getContactById(idString));
                found = true;
            } catch (RuntimeException e) {
                ioManager.printError(e.getMessage());
            }
        } while (!found);
    }

    private static void tryUpdate(IOManager ioManager, AddressBook addressBook) {
        boolean isUpdated = false;
        do {
            String idString = ioManager.getContactID();
            if (isCancelled(idString)) {
                ioManager.print(IOConstants.CANCELLED);
                break;
            }

            Map<String, String> updatedContactDetails = ioManager.getContactDetails();
            try {
                addressBook.updateContact(idString, new Contact(updatedContactDetails.get("name"), updatedContactDetails.get("phoneNumber"), updatedContactDetails.get("email")));
                isUpdated = true;
                ioManager.print(IOConstants.CONTACT_UPDATED_SUCCESSFULLY);
            } catch (RuntimeException e) {
                ioManager.printError(e.getMessage());
            }
        } while (!isUpdated);
    }

    private static void tryRemoveAll(IOManager ioManager, AddressBook addressBook) {
        String confirmation = ioManager.getDeleteConfirmation();
        if (confirmation.equals("yes")) {
            addressBook.removeAllContacts();
            ioManager.print(IOConstants.CONTACT_REMOVED_ALL_SUCCESSFULLY);
        } else {
            ioManager.print(IOConstants.CANCELLED);
        }
    }

    private static void tryRemoveContact(IOManager ioManager, AddressBook addressBook) {
        boolean isRemoved = false;
        do {
            String idString = ioManager.getContactDetails().get("email");
            if (isCancelled(idString)) {
                ioManager.print(IOConstants.CANCELLED);
                break;
            }

            try {
                addressBook.removeContact(idString);
                isRemoved = true;
                ioManager.print(IOConstants.CONTACT_REMOVED_SUCCESSFULLY);
            } catch (RuntimeException e) {
                ioManager.printError(e.getMessage());
            }
        } while (!isRemoved);
    }

    private static void tryAddContact(IOManager ioManager, AddressBook addressBook) {
        boolean isAdded = false;
        do {
            Map<String, String> contactDetails = ioManager.getContactDetails();
            if (isCancelled(contactDetails)) {
                ioManager.print(IOConstants.CANCELLED);
                break;
            }

            try {
                addressBook.addContact(new Contact(contactDetails.get("name"),
                        contactDetails.get("phoneNumber"), contactDetails.get("email")));

                isAdded = true;
                ioManager.print(IOConstants.CONTACT_ADDED_SUCCESSFULLY);
            } catch (IllegalArgumentException e) {
                ioManager.printError(e.getMessage());
            }
        } while (!isAdded);
    }

    private static boolean isCancelled(Map<String, String> contactDetails) {
        return contactDetails.containsValue("cancel");
    }

    private static boolean isCancelled(String command) {
        return command.equals("cancel");
    }
}