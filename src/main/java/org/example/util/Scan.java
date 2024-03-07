package org.example.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Scan {

    private static Scanner scan;

    static {
        scan = new Scanner(System.in);
    }


    public static String getString(String print) {
        System.out.println(print);
        return scan.nextLine();
    }
    // funkar
    public static Long getLong(String print) {
        System.out.println(print);
        try {
            Long id = scan.nextLong();
            return id;
        } catch (InputMismatchException e) {
            throw new RuntimeException(); // Kasta fel vid fel inmatning
        }
    }
}
