public class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        setPhoneNumber(phoneNumber);
        setEmail(email);
    }

    public String getName() {
        return this.name;
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

    private void validateEmail(String email) {
        // Regex matches alphanumeric & some special chars, followed by @,
        // followed by alphanumeric, followed by a dot, followed by alphanumeric
        if (!email.matches("^[A-Za-z0-9_.-]+@[A-Za-z]+\\.[A-Za-z]+$"))
            throw new IllegalArgumentException("Invalid email format");
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^07[0-9]{9}$"))
            throw new IllegalArgumentException("Invalid phone number format");
    }
}
