
/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file is licensed under the BSD-3-Clause
 * 
 * You may use any part of the file as long as you give credit in your source
 * code.
 * 
 *****************************************************************************/

// Modified from Original

import java.util.ArrayList;

class ThunderbirdContact extends HttpRequest implements Runnable {
    private String preferredName, firstName, lastName;
    private int seatLocation;
    private boolean isSeat = true;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public boolean hasPreferredName() {
        if (preferredName.equals(""))
            return false;
        return true;
    }

    public int getSeat() {
        return (seatLocation);
    }

    ThunderbirdContact(String urlIn) {
        super(urlIn);

        firstName = "";
        lastName = "";
        preferredName = "";
        seatLocation = 0;

        // Todo: Add additional fields.
        // NW - Implemented 'preferredName' field.
    }

    public Boolean load() {
        Boolean returnValue = false;
        System.out.println("loading: " + requestURL);
        if (super.readURL()) {
            parse();
            returnValue = true;
        }

        return returnValue;
    }

    public void parse() {
        for (String s : urlContent) {
            String[] subString = s.split("\"");

            // Todo: parse for additional fields.
            // NW - Implemented 'preferredName' field.
            if (subString.length > 3) {
                if (subString[1].equals("firstName")) {
                    firstName = subString[3];

                } else if (subString[1].equals("lastName")) {
                    lastName = subString[3];

                } else if (subString[1].equals("preferredName")) {
                    preferredName = subString[3];

                } else if (subString[1].equals("seatLocation")) {
                    try {
                        seatLocation = 0;
                        if (!subString[3].equals("")) {
                            seatLocation = Integer.parseInt(subString[3]);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception: " + e);
                    }
                }
            }
        }
    }

    public void validate(ArrayList<String[]> urlContentsList) {
        System.out.print("Validating: " + requestURL);

        if (urlContent.size() < 1) {
            System.out.println("\n    **Failed**: No content loaded\n");

        } else if (firstName.length() == 0) {
            System.out.println("\n    **Failed**: First Name (\"firstName\") required but not found\n\n");
            System.out.println(this);

        } else if (lastName.length() == 0) {
            System.out.println("\n    **Failed**: Last Name (\"lastName\") required but not found\n\n");
            System.out.println(this);

        } else {
            System.out.println("... Passed!");
            return; // Returning from the middle of a method is controversial.
        }
        
        // Todo: Add author's name and email address to failed messages.
        // NW - Fully implemented.
        for (String[] s : urlContentsList) {
            if (s[11].equals(requestURL)) {
                System.out.println("Author: " + s[3]);
                System.out.println("Email: " + s[7] + "\n");
            }
        }
    }

    public String toString() {
        // Todo: Add additional fields to returnString.
        // NW - Implemented 'preferredName' field.
        String returnString = "";

        if (!preferredName.equals("")) {
            if (preferredName.equals(firstName))
                returnString = returnString + "Preferred/First Name: " + preferredName + "\n";

            else {
                returnString = returnString + "Preferred Name: " + preferredName + "\n";
                returnString = returnString + "First Name: " + firstName + "\n";
            }
        } else if (!firstName.equals(""))
            returnString = returnString + "First Name: " + firstName + "\n";

        if (!lastName.equals(""))
            returnString = returnString + "Last Name: " + lastName + "\n";

        if (seatLocation != 0)
            if (isSeat)
                returnString = returnString + "Seat Number: " + seatLocation + "\n\n";
            else
                returnString = returnString + "Aisle Number: " + seatLocation + "\n\n";

        returnString = returnString + super.toString() + "\n";

        return returnString;
    }

    public String toString(boolean isSeat) {
        this.isSeat = isSeat;
        return toString();
    }

    public void run() {
        load();
    }
}