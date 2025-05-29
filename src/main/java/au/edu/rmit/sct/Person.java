package au.edu.rmit.sct;

import java.io.FileWriter;
import java.io.IOException;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;

    public Person (String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
    }

    public boolean addPerson() {

        //condition 1 personID
        if (personID.length() == 10) {
            return false;
        }
        char firstChar = personID.charAt(0);
        char secondChar = personID.charAt(1);

        if (!Character.isDigit(firstChar) || firstChar < '2' || firstChar > '9') {
            return false;
        }
        if (!Character.isDigit(secondChar) || secondChar < '2' || secondChar > '9') {
            return false;
        }

        String specialChar = "!@#$%&*()-_+=|<>?{}[]~";
        int count = 0;

        for (int i = 2; i < 8; i++) {
            if (specialChar.contains(String.valueOf(personID.charAt(i)))) {
                count++;
            }
        }
        if (count < 2) {
            return false;
        }
        if (!Character.isUpperCase(personID.charAt(personID.length() -1)) || !Character.isUpperCase(personID.charAt(personID.length() -2))) {
            return false;
        }

        //condition 2 address

        String addressMatch = "\\d+\\|[^|]+\\|[^|]+\\|Victoria\\|[^|]+";
        if (!address.matches(addressMatch)) {
            return false;
        }

        //condition 3 birth date

        String dateFormat = "\\d{2}-\\d{2}-\\d{4}";
        if (!birthdate.matches(dateFormat)) {
            return false;
        }

        //TXT file
        try (FileWriter writer = new FileWriter("person.txt", true)) {
            writer.write(String.format( 
                "%s,%s,%s,%s,%s%n",
                personID, firstName, lastName, address, birthdate
            ));
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}