
import org.junit.jupiter.api.Test;

import au.edu.rmit.sct.Person;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {


    //vaild personID
    @Test
    void validPerson() {
        Person person = new Person(
            "36!@&*XYP",
            "Emily",
            "Smith",
            "124|La Trobe Street|Melbourne|Victoria|Australia",
            "05-05-2005"
            );
        assertTrue(person.addPerson());
    }

    //invalid birthdate format
    @Test
    void invalidBirthday() {
        Person person = new Person(
            "36!@&*XYP",
            "Emily",
            "Smith",
            "124|La Trobe Street|Melbourne|Victoria|Australia",
            "2005-05-05"
            );
        assertFalse(person.addPerson());
    }

    //invalid address state
    @Test
    void invalidAddress() {
        Person person = new Person(
            "36!@&*XYP",
            "Emily",
            "Smith",
            "124|La Trobe Street|Melbourne|Tasmania|Australia",
            "05-05-2005"
            );
        assertFalse(person.addPerson());
    }

    //invalid address format
    @Test
    void invalidAddressFormat() {
        Person person = new Person(
            "36!@&*XYP",
            "Emily",
            "Smith",
            "124|La Trobe Street|Melbourne|Victoria",
            "05-05-2005"
            );
        assertFalse(person.addPerson());
    }

    //invalid no special characters
    @Test
    void invalidNoSpecialChar() {
        Person person = new Person(
            "36lmno12YP",
            "Emily",
            "Smith",
            "124|La Trobe Street|Melbourne|Tasmania|Australia",
            "05-05-2005"
            );
        assertFalse(person.addPerson());
    }
}