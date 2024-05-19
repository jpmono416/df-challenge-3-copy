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
        Validation.validateName(name);
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    private void setEmail(String email) {
        Validation.validateEmail(email);
        this.email = email.toLowerCase();
    }

    private void setPhoneNumber(String phoneNumber) {
        Validation.validatePhoneNumber(phoneNumber);

        this.phoneNumber = phoneNumber;
    }


}
