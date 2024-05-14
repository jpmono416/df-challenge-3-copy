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
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    private void setEmail(String email) {
        validateEmail(email);
        this.email = email.toLowerCase();
    }

    private void setPhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);

        this.phoneNumber = phoneNumber;
    }

    private void validateName(String name) {
        if (!name.matches("^[A-Za-z ]+$"))
            throw new IllegalArgumentException("Name cannot be empty");
    }

    /**
     * Matches email addresses with the format `example@domain.com`
     * @throws IllegalArgumentException if the email does not match the format
     * @param email
     */
    private void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9_.-]+@[A-Za-z]+\\.[A-Za-z]+$"))
            throw new IllegalArgumentException("Invalid email format");
    }

    /**
     * Matches a UK phone number beginning with 07 and followed by 9 digits
     * @param phoneNumber
     * @throws IllegalArgumentException if the phone number does not match the format
     */
    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^07[0-9]{9}$"))
            throw new IllegalArgumentException("Invalid phone number format");
    }
}
