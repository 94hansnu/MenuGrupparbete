package org.example.util;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.util.Validator.isNumeric;

public class Scan {

    private static Scanner scan;

    static {
        scan = new Scanner(System.in);
    }


    public static String getString(String print) {
        Scanner scan = new Scanner(System.in);
        System.out.println(print);
        return scan.nextLine();
    }
    // funkar
    public static Long getLong(String print) {
        String userInput = getString(print);
        while (!isNumeric(userInput)) {
            userInput = getString("> Skriv in ett giltligt nummervärde.");
            try {
                Long id = Long.valueOf(userInput);
                return id;
            } catch (NumberFormatException e) {
                System.out.println("> Försök igen.");
            }
        }
        return  Long.valueOf(userInput);
    }
}
