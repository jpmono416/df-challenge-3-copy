<center>

# Address Book System Software Overview

This overview document provides a high-level description of the Address Book System software, its functionalities, as well as some considerations about the software architecture.

</center>

---

## Overview

The Address Book System software is a console application that allows users to manage a digital address book. It provides functionalities to add, remove, update, and search for contacts. Each contact consists of a name, phone number, and email address.

The software also provides functionalities to print a contact's details, print all contacts, and print various messages to the console. It validates user input and handles exceptions to ensure the integrity of the address book.

The software also includes sorting functionalities, allowing users to sort contacts by name, phone number, or email address. It also supports searching for contacts by partial name, phone number, or email address.

### What problems does it solve?

- The software provides a simple and efficient way to manage contacts in an address book.
- It prevents manual errors by validating user input and handling exceptions.
- It provides a user-friendly console interface for interacting with the address book.
- It allows users to easily find contacts using various search and sort functionalities.

### Benefits

- The software is easy to use and provides a simple interface for managing contacts, offering significant advantages in terms of efficiency and user experience compared to manual contact management.
- It prevents manual errors by validating user input and handling exceptions, ensuring the integrity of the address book.
- It provides a simple way to print a contact's details or all contacts, which can be useful for users to keep track of their contacts.
- It allows users to easily find contacts using various search and sort functionalities, enhancing the user experience.

### Risks

- The software does not provide any security features such as user authentication. Therefore, anyone with access to the software can add, remove, update, and search for contacts without any restrictions.
- The software is a console application and does not provide a graphical user interface, which might not be as user-friendly for some users.
- The software does not support multiple address books at the same time in its current state. It should not be used with that purpose in mind.
- The software does not provide any backup or recovery features for the address book. If the data is lost or corrupted, it cannot be recovered.