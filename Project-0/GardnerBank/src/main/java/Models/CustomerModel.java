package Models;

import InputOutputFunctions.InputValidation;
import UI.DataStore;

//Class used for storing information in/retrieving information from customers table
public class CustomerModel {
    //Static class variables to store allowed length of Strings when stored as VAR CHAR in SQL
    private static final int givenNameMinLength = 1;
    private static final int givenNameMaxLength = 128;
    private static final int middleNameMinLength = 0;
    private static final int middleNameMaxLength = 128;
    private static final int surnameMinLength = 1;
    private static final int surnameMaxLength = 128;
    private static final int usernameMinLength = 1;
    private static final int usernameMaxLength = 32;
    private static final int passwordMinLength = 1;
    private static final int passwordMaxLength = 32;

    //CustomerModel private member variables to be accessed/changed through getter/setter methods
    private Integer id;
    private String givenName;
    private String middleName;
    private String surname;
    private String username;
    private String password;

    //No arg constructor
    public CustomerModel() {

    }

    //Constructor with all parameters
    public CustomerModel(Integer id, String givenName, String middleName, String surname, String username, String password) {
        this.id = id;
        this.givenName = givenName;
        this.middleName = middleName;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    //Getter/setter methods to access and change private member variables
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public String getGivenName() {
        return givenName;
    }

    public boolean setGivenName(String givenName) {
        //Clear DataStore's potential error message
        DataStore.clearPotentialErrorMessage();

        //Validate given string against name content & length requirements
        if (InputValidation.isValidName("givenName", givenName, givenNameMinLength, givenNameMaxLength)) {
            this.givenName = givenName;
            return true;
        }
        else {
            System.out.println(DataStore.getPotentialErrorMessage());
            return false;
        }
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean setMiddleName(String middleName) {
        //Clear DataStore's potential error message
        DataStore.clearPotentialErrorMessage();

        //Validate given string against name content & length requirements
        if (InputValidation.isValidName("middleName", middleName, middleNameMinLength, middleNameMaxLength)) {
            this.middleName = middleName;
            return true;
        }
        else {
            System.out.println(DataStore.getPotentialErrorMessage());
            return false;
        }
    }

    public String getSurname() {
        return surname;
    }

    public boolean setSurname(String surname) {
        //Clear DataStore's potential error message
        DataStore.clearPotentialErrorMessage();

        //Validate given string against name content & length requirements
        if (InputValidation.isValidName("surname", surname, surnameMinLength, surnameMaxLength)) {
            this.surname = surname;
            return true;
        }
        else {
            System.out.println(DataStore.getPotentialErrorMessage());
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public boolean setUsername(String username) {
        //Clear DataStore's potential error message
        DataStore.clearPotentialErrorMessage();

        //Validate given string against username content & length requirements
        if (InputValidation.isValidUsername("username", username, usernameMinLength, usernameMaxLength)) {
            this.username = username;
            return true;
        }
        else {
            System.out.println(DataStore.getPotentialErrorMessage());
            return false;
        }
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        //Clear DataStore's potential error message
        DataStore.clearPotentialErrorMessage();

        //Validate given string against password content & length requirements
        if (InputValidation.isValidPassword("password", password, passwordMinLength, passwordMaxLength)) {
            this.password = password;
            return true;
        }
        else {
            System.out.println(DataStore.getPotentialErrorMessage());
            return false;
        }
    }

    //Method to return full name of user, leaving out middle name if blank
    public String getFullName() {
        String middleName = (this.getMiddleName() == null || this.getMiddleName().isEmpty()) ? "" : this.getMiddleName() + " ";
        return this.getGivenName() + " " + middleName + this.getSurname();
    }

    //Override of Object.toString() method to automatically convert CustomerModel object to String
    //with all member variables present
    @Override
    public String toString() {
        return "Customer #" + this.getId() + ": " + this.getFullName() + "\nusername: " + this.getUsername() + " password: " + this.getPassword();
    }
}
