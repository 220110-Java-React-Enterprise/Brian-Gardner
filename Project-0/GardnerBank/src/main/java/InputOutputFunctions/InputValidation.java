package InputOutputFunctions;

import UI.DataStore;

//Class to hold static functions that validate user input before storing into objects/tables
public class InputValidation {
    //Function to check whether a character is an uppercase alphabetic letter
    public static boolean isAlphaUpper(char chr) {
        return !(chr < 65 || chr > 90);
    }

    //Function to check whether a character is a lowercase alphabetic letter
    public static boolean isAlphaLower(char chr) {
        return !(chr < 97 || chr > 122);
    }

    //Function to check whether a character is alphabetic, uppercase or lowercase
    public static boolean isAlpha(char chr) {
        return (isAlphaUpper(chr) || isAlphaLower(chr));
    }

    //Function to check whether a character is numeric
    public static boolean isNumeric(char chr) {
        return !(chr < 45 || chr > 57);
    }

    //Function to check whether a character is alphabetic or numeric
    public static boolean isAlphaNumeric(char chr) {
        return (isAlpha(chr) || isNumeric(chr));
    }

    //Function to check whether a character is an empty space
    public static boolean isEmptySpace(char chr) { return (chr == 32); }

    //Function to check whether a character is alphabetic or an empty space
    public static boolean isAlphaSpace(char chr) { return (isAlpha(chr) || isEmptySpace(chr)); }

    //Function to check whether all characters in a string are uppercase alphabetic letters
    public static boolean isAlphaUpper(String str) {
        for (int index = 0; index < str.length(); index++) {
            if (!isAlphaUpper(str.charAt(index)))
                return false;
        }
        return true;
    }

    //Function to check whether all characters in a string are lowercase alphabetic letters
    public static boolean isAlphaLower(String str) {
        for (int index = 0; index < str.length(); index++) {
            if (!isAlphaLower(str.charAt(index)))
                return false;
        }
        return true;
    }

    //Function to check whether all characters in a string are alphabetic, uppercase or lowercase
    public static boolean isAlpha(String str) {
        for (int index = 0; index < str.length(); index++) {
            if (!isAlpha(str.charAt(index)))
                return false;
        }
        return true;
    }

    //Function to check whether all characters in a string are numeric
    public static boolean isNumeric(String str) {
        for (int index = 0; index < str.length(); index++) {
            if (!isNumeric(str.charAt(index)))
                return false;
        }
        return true;
    }

    //Function to check whether all characters in a string are alphabetic or numeric characters
    public static boolean isAlphaNumeric(String str) {
        for (int index = 0; index < str.length(); index++) {
            if (!isAlphaNumeric(str.charAt(index)))
                return false;
        }
        return true;
    }

    //Function to check whether all characters in a string are alphabetic or empty spaces
    public static boolean isAlphaSpace(String str) {
        for (int index = 0; index < str.length(); index++) {
            if (!isAlphaSpace(str.charAt(index)))
                return false;
        }
        return true;
    }

    //Method to test whether a string is within the required length parameters
    //to be used by other String validation functions
    public static boolean isValidString(String fieldName, String str, int minSize, int maxSize) {
        //Set str to empty if null
        str = str == null ? "" : str;

        //Set return value to true if String length is within given parameters
        boolean answer = !(str.length() < minSize || str.length() > maxSize);

        //Add error message to DataStore
        if (!answer) DataStore.addToPotentialErrorMessage(invalidStringErrorMessage(fieldName, minSize, maxSize));

        return answer;
    }

    //Method to test whether a string is a valid name
    //checking whether the string is within given required length parameters and
    //elements are alphabetic characters
    public static boolean isValidName(String fieldName, String name, int minSize, int maxSize) {
        boolean answer = isAlpha(name);
        if (!answer) DataStore.addToPotentialErrorMessage(invalidNameErrorMessage(fieldName));
        answer = isValidString(fieldName, name, minSize, maxSize) && answer;

        return answer;
    }

    //Method to test whether a string is a valid potential username
    //Checks whether the string is within given required length parameters and
    //elements are alphabetic or numeric characters
    public static boolean isValidUsername(String fieldName, String username, int minSize, int maxSize) {
        boolean answer = isAlphaNumeric(username);
        if (!answer) DataStore.addToPotentialErrorMessage(invalidUsernameErrorMessage(fieldName));
        answer = isValidString(fieldName, username, minSize, maxSize) && answer;

        return answer;
    }

    //Method to test whether a string is a valid potential username
    //Checks whether the string is within given required length parameters and
    //elements are alphabetic or numeric characters
    public static boolean isValidPassword(String fieldName, String password, int minSize, int maxSize) {
        boolean answer = isAlphaNumeric(password);
        if (!answer) DataStore.addToPotentialErrorMessage(invalidPasswordErrorMessage(fieldName));
        answer = isValidString(fieldName, password, minSize, maxSize) && answer;

        return answer;
    }

    //Method to test whether a string is a valid potential description
    //Checks whether the string is within given required length parameters and
    //elements are alphabetic or empty spaces
    public static boolean isValidDescription(String fieldName, String description, int minSize, int maxSize) {
        boolean answer = isAlphaSpace(description);
        if (!answer) DataStore.addToPotentialErrorMessage(invalidDescriptionErrorMessage(fieldName));
        answer = isValidString(fieldName, description, minSize, maxSize) && answer;

        return answer;
    }

    //Method that creates and returns an error message as string based on given field name, min and max string size
    public static String invalidStringErrorMessage(String fieldName, int minSize, int maxSize) {
        return "Error: " + fieldName + " must be " + minSize + " to " + maxSize + " characters in length.\n";
    }

    //Method that creates and returns an error message as string based on given field name
    public static String invalidNameErrorMessage(String fieldName) {
        return "Error: " + fieldName + " must contain only alphabetical characters.\n";
    }

    //Method that creates and returns an error message as string based on given field name
    public static String invalidUsernameErrorMessage(String fieldName) {
        return "Error: " + fieldName + " must contain only alphabetical and numeric characters.\n";
    }

    //Method that creates and returns an error message as string based on given field name
    public static String invalidPasswordErrorMessage(String fieldName) {
        return "Error: " + fieldName + " must contain only alphabetical and numeric characters.\n";
    }

    //Method that creates and returns an error message as string based on given field name
    public static String invalidDescriptionErrorMessage(String fieldName) {
        return "Error: " + fieldName + " must contain only alphabetical and empty space characters.\n";
    }
}
