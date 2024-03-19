package org.example.menu;

import org.apache.hc.core5.http.ParseException;
import org.example.API.AuthorAPI;
import org.example.API.JokeAPI;
import org.example.API.UserAPI;
import org.example.data.Author;
import org.example.data.CreateAuthorDTO;
import org.example.data.Joke;
import org.example.data.UpdateJokeDTO;
import org.example.dto.User;
import org.example.util.Scan;

import java.io.IOException;
import java.util.List;

import static org.example.API.JokeAPI.*;

import static org.example.menu.UserMenu.getCreateJokePayload;

public class AdminMenu {
    private static String jwt;

    public AdminMenu(String jwt) {
        this.jwt = jwt;
    }
    private static String CREATE_JOKE = "> ANGE SKÄMT: ";
    private static String AUTHOR_ID = "> ANGE FÖRFATTAR ID: ";
    private static String CREATE_AUTHOR = "> ANGE FÖRFATTARENS NAMN: ";

     static void displayMainMenu() throws IOException, ParseException {
        while (true) {
            System.out.println("[ADMINMENY]");
            System.out.println("Välj ett alternativ:");
            System.out.println("1. Gå till Joke");
            System.out.println("2. Gå till Author");
            System.out.println("3. Gå till User");
            System.out.println("4. Logga ut");
            System.out.println("0. Avsluta programmet.");

            Long choice = Scan.getLong("Ange ditt val: ");
            switch (choice.intValue()) {
                case 1:
                    handleJokeMenu();
                    break;
                case 2:
                    handleAuthorMenu();
                    break;
                case 3:
                    handleUserMenu();
                    break;
                case 4:
                    Menu menu = new Menu();
                    menu.displayMenu();
                    break;
                case 0:
                    System.out.println("Programmet avslutas.");
                    System.exit(0);
                default:
                    System.out.println("Ogiltigt val. Prova igen.");
            }
        }
    }

    // Hantera skämtMeny
    private static void handleJokeMenu() throws IOException, ParseException {
        while (true) {
            System.out.println("[JOKEMENY]");
            System.out.println("Välj ett alternativ:");
            System.out.println("1. Uppdatera skämt");
            System.out.println("2. Radera skämt");
            System.out.println("3. Skapa nytt skämt");
            System.out.println("4. Hämta ett slumpmässigt skämt");
            System.out.println("5. Hämta alla skämt");
            System.out.println("0. Tillbaka till huvudmenyn");


        Long choice = Scan.getLong("Ange ditt val: ");
        switch (choice.intValue()) {
            case 1:
                updateJoke();
                break;
            case 2:
                deleteJoke();
                break;
            case 3:
                createJoke(getCreateJokePayload(), jwt);
                break;
            case 4:
                System.out.println(JokeAPI.getRandomJoke(jwt).joke());
                break;
            case 5:
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
                break;
            case 0:
                return;
            default:
                System.out.println("Ogiltigt val. Vänligen prova igen.");
        }
    }
}

    // behöver fixas.
    private static void updateJoke() {

        Long jokeId = Scan.getLong("Ange ID för skämtet du vill uppdatera:");
        String updatedJoke = Scan.getString("Ange det uppdaterade skämtet:");
        UpdateJokeDTO updateJoke = new UpdateJokeDTO(jokeId, updatedJoke);
        JokeAPI.updateJoke(updateJoke, jwt);
    }

    private static void deleteJoke() {
        Long jokeId = Scan.getLong("Ange ID för skämtet du vill radera:");
        JokeAPI.deleteJoke(jokeId, jwt);
    }

    // Hantera FörfattarMeny
    private static void handleAuthorMenu() throws IOException, ParseException {
        while (true) {
            System.out.println("[AUTHORMENY]!");
            System.out.println("Välj ett alternativ:");
            System.out.println("1. Uppdatera författare");
            System.out.println("2. Radera författare");
            System.out.println("3. Skapa en författare");
            System.out.println("4. Hämta en författare");
            System.out.println("5. Hämta alla författare");
            System.out.println("0. Tillbaka till huvudmenyn");

            Long choice = Scan.getLong("Ange ditt val: ");
            switch (choice.intValue()) {
                case 1:
                    updateAuthor();
                    break;
                case 2:
                    deleteAuthor();
                    break;
                case 3:
                    CreateAuthorDTO author = new CreateAuthorDTO(null, Scan.getString(CREATE_AUTHOR));
                    AuthorAPI.createAuthor(author, jwt);
                    break;
                case 4:
                    Long authorId = Scan.getLong(AUTHOR_ID);
                    Author authorr = AuthorAPI.getAuthor(authorId, jwt);
                    if (authorr  == null) {
                        System.out.println("> Författare med detta ID finns inte.");
                    }
                    else {
                        System.out.println("> Namn: " + authorr.name());
                    }
                    break;
                case 5:
                    List<Author> authors = AuthorAPI.getAllAuthors(jwt);
                    int count = 1;
                    for (Author author3 : authors) {
                        System.out.println(count + ". " + author3.name());
                        count ++;
                    }
                    System.out.println();
                    break;
                case 0:

                    return;
                default:
                    System.out.println("Ogiltigt val. Prova igen.");
            }
        }
    }

    private static void updateAuthor() {
        Long authorId = Scan.getLong("Ange ID för författaren du vill uppdatera:");
        String updatedName = Scan.getString("Ange det uppdaterade namnet för författaren:");

        AuthorAPI.updateAuthor(authorId, updatedName, jwt);
    }
    private static void deleteAuthor() {
        Long authorId = Scan.getLong("Ange ID för författaren du vill radera:");
        AuthorAPI.deleteAuthor(authorId, jwt);
    }

    // Hantera UserMenu
    private static void handleUserMenu() throws IOException, ParseException {
        while (true) {
            System.out.println("[USERMENY]");
            System.out.println("Välj ett alternativ");
            System.out.println("1. Hämta användare med specifikt ID");
            System.out.println("2. Hämta alla användare");
            System.out.println("3. Uppdatera en användare");
            System.out.println("4. Radera en användare");
            System.out.println("5. Uppgradera en användare");
            System.out.println("6. Nedgradera en användare");
            System.out.println("0. Tillbaka till huvudmenyn");

            Long choice = Scan.getLong("Ange ditt val:");
            switch (choice.intValue()) {
                case 1:
                    getUserById();
                    break;
                case 2:
                    getAllUsers();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    upgradeUser();
                    break;
                case 6:
                    downgradeUser();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ogiltigt val, prova igen.");
            }
        }
    }

    private static void getUserById() throws IOException, ParseException {
        Long userId = Scan.getLong("Ange ID för användaren du vill hämta:");
        User user = UserAPI.getUserById(userId, jwt);
        if (user != null && user != null) {
            System.out.println("Användarinformation:");
            System.out.println(UserAPI.getUserById(userId, jwt));
            System.out.println("NAMN: " + user.getUsername());
            System.out.println("LÖSENORD: " + user.getPassword());
            System.out.println("AUTHORITIES: " + user.getAuthorities());
        } else {
            System.out.println("Användaren med ID " + userId + " hittades inte.");
        }
    }

    private static void getAllUsers() throws IOException {
        try {
            List<User> users = UserAPI.getAllUsers(jwt);
            System.out.println("Alla användare:");
            for (User user : users) {
                System.out.println(user.getUsername());
            }
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }

    private static void updateUser() {
        Long userId = Scan.getLong("Ange ID för användaren du vill uppdatera:");
        String updatedUsername = Scan.getString("Ange det uppdaterade användarnamnet:");
        String updatedPassword  = Scan.getString("Ange det uppdaterade lösenordet:");

        UserAPI.updateUser(userId, updatedUsername, updatedPassword, jwt);
    }

    private static void deleteUser() {
        Long userId = Scan.getLong("Ange ID för användaren du vill radera:");

        UserAPI.deleteUser(userId, jwt);
    }

    private static void upgradeUser() {
        Long userId = Scan.getLong("Ange ID för avändaren du vill uppgradera till Admin:");

        UserAPI.upgradeUser(userId, jwt);
    }

    private static void downgradeUser() {
        Long userId = Scan.getLong("Ange ID för användaren du vill nedgradera till vanlig användare:");

        UserAPI.downgradeUser(userId, jwt);
    }
}

