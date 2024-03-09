package org.example.menu;

import org.example.API.AuthorAPI;
import org.example.API.JokeAPI;
import org.example.API.UserAPI;
import org.example.util.Scan;

public class AdminMenu {
    private static String jwt;

    public AdminMenu(String jwt) {
        this.jwt = jwt;
    }

     static void displayMainMenu() {
        while (true) {
            System.out.println("Välkommen till AdminMenyn!");
            System.out.println("Välj ett alternativ:");
            System.out.println("1. Gå till Joke");
            System.out.println("2. Gå till Author");
            System.out.println("3. Gå till User");
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
                case 0:
                    System.out.println("Programmet avslutas.");
                    System.exit(0);
                default:
                    System.out.println("Ogiltigt val. Prova igen.");
            }
        }
    }

    // Hantera skämtMeny
    private static void handleJokeMenu() {
        while (true) {
            System.out.println("Välkommen till JokeMenyn");
            System.out.println("Välj ett alternativ:");
            System.out.println("1. Uppdatera skämt");
            System.out.println("2. Radera skämt");
            System.out.println("0. Tillbaka till huvudmenyn");


        Long choice = Scan.getLong("Ange ditt val: ");
        switch (choice.intValue()) {
            case 1:
                updateJoke();
                break;
            case 2:
                deleteJoke();
                break;
            case 0:
                return;
            default:
                System.out.println("Ogiltigt val. Vänligen prova igen.");
        }
    }
}
    private static void updateJoke() {

        Long jokeId = Scan.getLong("Ange ID för skämtet du vill uppdatera:");
        String updatedJoke = Scan.getString("Ange det uppdaterade skämtet:");
        JokeAPI.updateJoke(jokeId, updatedJoke, jwt);
    }

    private static void deleteJoke() {
        Long jokeId = Scan.getLong("Ange ID för skämtet du vill radera:");
        JokeAPI.deleteJoke(jokeId, jwt);
    }

    // Hantera FörfattarMeny
    private static void handleAuthorMenu() {
        while (true) {
            System.out.println("Välkommen till AuthorMenyn!");
            System.out.println("Välj ett alternativ:");
            System.out.println("1. Uppdatera författare");
            System.out.println("2. Radera författare");
            System.out.println("0. Tillbaka till huvudmenyn");

            Long choice = Scan.getLong("Ange ditt val: ");
            switch (choice.intValue()) {
                case 1:
                    updateAuthor();
                    break;
                case 2:
                    deleteAuthor();
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
    private static void handleUserMenu() {
        while (true) {
            System.out.println("UserMeny");
            System.out.println("välj ett alternativ");
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

    private static void getUserById() {
        Long userId = Scan.getLong("Ange ID för användaren du vill hämta:");
        UserAPI.getUserById(userId, jwt);
    }

    private static void getAllUsers() {
        UserAPI.getAllUsers(jwt);
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

