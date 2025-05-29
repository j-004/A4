package au.edu.rmit.sct;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;

    private HashMap<Date, Integer> demeritPoints;
    private boolean isSuspended;

    public Person (String personID, String firstName, String lastName, String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
    }
    public Person() {
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
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


public String addDemeritPoints(String offenseDateStr, int points) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    sdf.setLenient(false);

    try {
        // 1) parse & validate offense date format
        Date offenseDate = sdf.parse(offenseDateStr);

        // 2) validate points range
        if (points < 1 || points > 6) {
            return "Failed";
        }

        // 3) calculate age at time of offense
        Date birthDate = sdf.parse(this.birthdate);
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthDate);
        Calendar offCal = Calendar.getInstance();
        offCal.setTime(offenseDate);
        int age = offCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
        if (offCal.get(Calendar.DAY_OF_YEAR) < birthCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        // 4) record the new offense
        demeritPoints.put(offenseDate, points);

        // 5) sum points in rolling 2-year window
        Calendar twoYearsAgo = (Calendar) offCal.clone();
        twoYearsAgo.add(Calendar.YEAR, -2);
        int total = demeritPoints.entrySet().stream()
            .filter(e -> !e.getKey().before(twoYearsAgo.getTime()))
            .mapToInt(Map.Entry::getValue)
            .sum();

        // 6) update suspension flag
        if ((age < 21 && total > 6) || (age >= 21 && total > 12)) {
            isSuspended = true;
        }

        // 7) append record to TXT file
        String line = personID
                    + "|" + offenseDateStr
                    + "|" + points
                    + "|" + isSuspended
                    + System.lineSeparator();
        try (FileWriter fw = new FileWriter("demerits.txt", true)) {
            fw.write(line);
        }

        return "Success";
    } catch (Exception e) {
        return "Failed";
    }
}

// accessors
public void setPersonID(String personID) {
    this.personID = personID;
}

public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
}

public boolean isSuspended() {
    return isSuspended;
}
}