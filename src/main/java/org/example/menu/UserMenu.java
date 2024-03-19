package org.example.menu;

import org.apache.hc.core5.http.ParseException;
import org.example.util.Scan;
import org.example.data.*;
import org.example.API.*;

import java.io.IOException;
import java.util.List;

public class UserMenu {

    private final String jwt;
    private static String MENU =
             "> GÖR VAL:\n\n"
            +"> HÄMTA ETT SKÄMT:         [1]\n"
            +"> HÄMTA ALLA SKÄMT:        [2]\n"
            +"> SKAPA ETT SKÄMT:         [3]\n"
            +"> HÄMTA EN FÖRFATTARE:     [4]\n"
            +"> HÄMTA ALLA FÖRFATTARE:   [5]\n"
            +"> SKAPA EN NY FÖRFATTARE:  [6]\n"
            +"> AVSLUTA:                 [7]\n";

    private static String CREATE_JOKE = "> ANGE SKÄMT: ";
    private static String AUTHOR_ID = "> ANGE FÖRFATTAR ID: ";
    private static String CREATE_AUTHOR = "> ANGE FÖRFATTARENS NAMN: ";


    public UserMenu(String jwt) {
        this.jwt = jwt;
    }

    public void displayMenu() throws IOException, ParseException {
        switchUserChoise(getUserChoiseFromMenu());
    }


    public static Long getUserChoiseFromMenu() {
        return Scan.getLong(MENU );
    }
    public static CreateJokeDTO getCreateJokePayload() {
        CreateJokeDTO payload = new CreateJokeDTO(
                Scan.getString(CREATE_JOKE),
                Scan.getLong(AUTHOR_ID));
        return payload;
    }
    private static String getAuthorName() {
        return Scan.getString(CREATE_AUTHOR);
    }
     void switchUserChoise(Long choise) throws IOException, ParseException {

        switch (choise.intValue()) {
            case 1: {
                // Hämta ett skämt
                System.out.println(JokeAPI.getRandomJoke(jwt).joke());
                //System.console().wait(2000);
                displayMenu();
                break;
            }
            case 2: {
                // Hämta alla skämt
                List<Joke> jokes = JokeAPI.getAllJokes(jwt);
                String haha = "haha";
                int count = 0;
                for (Joke joke : jokes) {
                    System.out.println("> Författare: " + joke.author().name());
                    System.out.println(joke.joke());

                    if (count % 2 == 0) {
                        System.out.println(haha.toUpperCase() + "\n\n");
                    }
                    else {
                        System.out.println(haha + "\n\n");
                    }
                    haha += "haha";
                    count ++;
                }
                displayMenu();
                break;
            }
            case 3: {
                // Skapa ett skämt
                JokeAPI.createJoke(getCreateJokePayload(), jwt);
                displayMenu();
                break;
            }
            case 4: {
                // Hämta en författare

                    Long authorId = Scan.getLong(AUTHOR_ID);
                    Author author = AuthorAPI.getAuthor(authorId, jwt);
                    if (author  == null) {
                        System.out.println("> Författare med detta ID finns inte.");
                    }
                    else {
                        System.out.println("> Namn: " + author.name());
                    }
                    displayMenu();

                break;

            }
            case 5: {
                // Hämta alla författare
                List<Author> authors = AuthorAPI.getAllAuthors(jwt);
                int count = 1;
                for (Author author : authors) {
                    System.out.println(count + ". " + author.name());
                    count ++;
                }
                System.out.println();
                displayMenu();
                break;
            }
            case 6: {
                // Skapa en ny författare
                CreateAuthorDTO author = new CreateAuthorDTO(null, Scan.getString(CREATE_AUTHOR));
                AuthorAPI.createAuthor(author, jwt);
                displayMenu();
                break;
            }
            case 7: {
                Menu menu = new Menu();
                menu.displayMenu();
                break;
            }
            default: {
                System.out.println("Gör ett bättre val...");
                displayMenu();
                break;
            }

        }

    }

}
