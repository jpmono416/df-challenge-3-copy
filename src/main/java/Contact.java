public class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    /**
     * Creates a new contact with a name, phone number and email.
     * @param name
     * @param phoneNumber
     * @param email
     * @throws IllegalArgumentException if the name, phone number or email are invalid
     */
    public Contact(String name, String phoneNumber, String email) {
        setName(name.toLowerCase());
        setPhoneNumber(phoneNumber);
        setEmail(email.toLowerCase());
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (!Validation.validateName(name))
            throw new IllegalArgumentException("Name is invalid - only letters and spaces are allowed.");

        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    private void setEmail(String email) {
        if(!Validation.validateEmail(email))
            throw new IllegalArgumentException("Email is invalid - must be in the format name@domain.com");

        this.email = email.toLowerCase();
    }

    private void setPhoneNumber(String phoneNumber) {
        if(!Validation.validatePhoneNumber(phoneNumber))
            throw new IllegalArgumentException("Phone number is invalid - must be 11 digits long, starting with 07.");

        this.phoneNumber = phoneNumber;
    }
}
