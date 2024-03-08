package org.example;

import org.apache.hc.core5.http.ParseException;
import org.example.menu.Menu;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        Menu menu = new Menu();
        menu.displayMenu();
    }
}