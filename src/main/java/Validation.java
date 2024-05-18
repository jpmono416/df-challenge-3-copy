public final class Validation {
    /**
     * Matches names with only letters and spaces
     * @param name
     * @throws IllegalArgumentException if the name contains anything other than letters and spaces
     */
    public static void validateName(String name) {
        if (!name.matches("^[A-Za-z ]+$"))
            throw new IllegalArgumentException("Name cannot be empty");
    }

    /**
     * Matches email addresses with the format `example@domain.com`
     * @throws IllegalArgumentException if the email does not match the format
     * @param email
     */
    public static void validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9_.-]+@[A-Za-z]+\\.[A-Za-z]+$"))
            throw new IllegalArgumentException("Invalid email format");
    }

    /**
     * Matches a UK phone number beginning with 07 and followed by 9 digits
     * @param phoneNumber
     * @throws IllegalArgumentException if the phone number does not match the format
     */
    public static void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^07[0-9]{9}$"))
            throw new IllegalArgumentException("Invalid phone number format");
    }
}
