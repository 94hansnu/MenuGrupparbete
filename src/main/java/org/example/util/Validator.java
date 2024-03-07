package org.example.util;

public class Validator {
    public static Boolean isNumeric(String input) {
        try { // Se om input är av numerisk form
            Long number = Long.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
