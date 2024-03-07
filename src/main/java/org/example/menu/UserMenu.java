package org.example.menu;

import org.example.util.Scan;

public class UserMenu {
    private final String jwt;
    private static String MENU =
             "> GÖR VAL:\n"
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

    public static void displayMenu() {
        switchUserChoise(getUserChoiseFromMenu(MENU));
    }

    // Om det enbart är en USER kan man låta menu vara tomt (""), annars lägg till admin meny
    public static Long getUserChoiseFromMenu(String menu) {
        return Scan.getLong(MENU + menu);
    }
    public static Object[] getCreateJokePayload() {
        Object[] payload = new String[2];
        payload[0] = Scan.getString(CREATE_JOKE);
        payload[1] = Scan.getLong(AUTHOR_ID);
        return payload;
    }
    private static String getAuthorName() {
        return Scan.getString(CREATE_AUTHOR);
    }
    static void switchUserChoise(Long choise) {
        switch (choise.intValue()) {
            case 1: {
                // Hämta ett skämt
                System.out.println(JokeAPI.getRandomJoke(jwt).getJoke());
                //System.console().wait(2000);
                displayMenu();
            }
            case 2: {
                // Hämta alla skämt
                List<Joke> jokes = JokeAPI.getAllJokes(jwt);
                String haha = "haha";
                for (Joke joke : jokes) {
                    System.out.println(joke.getJoke());
                    System.out.println(haha += "haha");
                }
                displayMenu();
            }
            case 3: {
                // Skapa ett skämt
                JokeAPI.createJoke(getCreateJokePayload(), jwt);
                displayMenu();
            }
            case 4: {
                // Hämta en författare
                try {
                    Long authorId = Scan.getLong(AUTHOR_ID);
                    System.out.println(AuthorAPI.getAuthor(authorId), jwt);
                    displayMenu();
                }
                catch (RuntimeException e) {
                    System.out.println("> Skriv in ett giltligt ID värde.");
                    switchUserChoise(4L);
                }

            }
            case 5: {
                // Hämta alla författare
                List<Author> authors = AuthorAPI.getAllAuthors(jwt);
                int count = 1;
                for (Author author : authors) {
                    System.out.println(count + ". ");
                    count ++;
                }
                displayMenu();
            }
            case 6: {
                // Skapa en ny författare
                AuthorAPI.createAuthor(Scan.getString(CREATE_AUTHOR), jwt);
                displayMenu();
            }
            case 7: {
                System.exit(66);
            }
            default: {
                System.out.println("Gör ett bättre val...");
                displayMenu();
            }

        }

    }

}
