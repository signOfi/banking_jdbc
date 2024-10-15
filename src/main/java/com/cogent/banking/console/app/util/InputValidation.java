package com.cogent.banking.console.app.util;

import java.util.Locale;
import java.util.Set;

public class InputValidation {

    public static boolean isAnInteger(String userInput) {
        /* Validate not a string */
        try {
            Integer.valueOf(userInput);
            return true;
        } catch (Exception e) {
            System.out.println("Must enter numeric input.");
            return false;
        }
    }

    public static boolean validateAmount(String userInput) {

        userInput = userInput.trim();

        /* Validate not a string */
        try {
            Integer.valueOf(userInput);
        } catch (Exception e) {
            System.out.println("Must enter numeric input for the amount. Try Again");
            return false;
        }

        /* Validate must not be zero, Validate not a negative */
        return userInput.charAt(0) != '-' && userInput.charAt(0) != '0';
    }

    public static boolean validateUserOption(String userInput, Set<Integer> options) {

        userInput = userInput.trim();

        try {
            int option = Integer.parseInt(userInput);
            if (options.contains(option))
                return true;
            else {
                System.out.println("Please enter a option number within the correct range");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a numerical option");
            return false;
        }

    }

    public static boolean validateEmployeeOrUser(String s) {
        s = s.toLowerCase();
        return s.equals("e") || s.equals("c");
    }


}
