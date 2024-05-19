public final class Validation {
    /**
     * Matches names with only letters and spaces
     * @param name
     * @throws IllegalArgumentException if the name contains anything other than letters and spaces
     */
    public static boolean validateName(String name) {
        return name.matches("^[A-Za-z ]+$");
    }

    /**
     * Matches email addresses with the format `example@domain.com`
     * @throws IllegalArgumentException if the email does not match the format
     * @param email
     */
    public static boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9_.-]+@[A-Za-z]+\\.[A-Za-z]+$");
    }

    /**
     * Matches a UK phone number beginning with 07 and followed by 9 digits
     * @param phoneNumber
     * @throws IllegalArgumentException if the phone number does not match the format
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^07[0-9]{9}$");
    }
}
