
import org.junit.jupiter.api.Test;

import au.edu.rmit.sct.Person;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {


    //vaild personID
    @Test
    void validPerson() {
        Person person = new Person(
            "36!@&*XYP",
            "Amisha",
            "Pradhan",
            "124|La Trobe Street|Melbourne|Victoria|Australia",
            "28-02-2005"
            );
        assertTrue(person.addPerson());
    }

    //invalid birthdate format
    @Test
    void invalidBirthday() {
        Person person = new Person(
            "36!@&*XYP",
            "Amisha",
            "Pradhan",
            "124|La Trobe Street|Melbourne|Victoria|Australia",
            "2005-02-28"
            );
        assertFalse(person.addPerson());
    }

    //invalid address state
    @Test
    void invalidAddress() {
        Person person = new Person(
            "36!@&*XYP",
            "Amisha",
            "Pradhan",
            "124|La Trobe Street|Melbourne|Tasmania|Australia",
            "28-02-2005"
            );
        assertFalse(person.addPerson());
    }

    //invalid address format
    @Test
    void invalidAddressFormat() {
        Person person = new Person(
            "36!@&*XYP",
            "Amisha",
            "Pradhan",
            "124|La Trobe Street|Melbourne|Victoria",
            "28-02-2005"
            );
        assertFalse(person.addPerson());
    }

    //invalid no special characters
    @Test
    void invalidNoSpecialChar() {
        Person person = new Person(
            "36lmno12YP",
            "Amisha",
            "Pradhan",
            "124|La Trobe Street|Melbourne|Tasmania|Australia",
            "28-02-2005"
            );
        assertFalse(person.addPerson());
    }
}