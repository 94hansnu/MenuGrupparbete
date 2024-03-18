package org.example.menu;

import org.apache.hc.core5.http.ParseException;
import org.example.dto.LoginResponseDTO;
import org.example.dto.RegistrationDTO;
import org.example.dto.Role;
import org.example.dto.User;
import org.example.util.Scan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Set;

public class Menu { // huvud meny för registrering och inloggning

   private static String MENU =
           "> GÖR VAL:\n"
                   + "> REGISTRERA DIG:    [1]\n"
                   + "> LOGGA IN:          [2]\n"
                   + "> AVSLUTA:           [3]\n";

    private final String AUTH_API_BASE_URL = "http://ec2-13-53-167-112.eu-north-1.compute.amazonaws.com/api/v1/auth";
    private final RestTemplate restTemplate;
    private String jwtToken;

    public Menu() {
        this.restTemplate = new RestTemplate();
        this.jwtToken = null;
    }

    public void displayMenu() throws IOException, ParseException {
        switchUserChoice(getUserChoiceFromMenu());
    }

    public Long getUserChoiceFromMenu() {
        return Scan.getLong(MENU);
    }

    private void switchUserChoice(Long choice) throws IOException, ParseException {
        switch (choice.intValue()) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Tack för att du använder vår applikation!");
                System.exit(0);
            default:
                System.out.println("Ogiltigt val. Försök igen.");
                displayMenu();
                break;
        }
    }



    private void register() throws IOException, ParseException {
        String username = Scan.getString("Ange användarnamn:");
        String password = "";


        while (true) {
            password = Scan.getString("Ange lösenord: (minst 8 tecken med minst en siffra):");
            if (password.length() >= 8 && password.matches(".*\\d.*")) {
                break;
            } else {
                System.out.println("Lösenordet måste vara minst 8 tecken långt och innehålla minst en siffra. Försök igen.");
            }
        }

        String registerUrl = AUTH_API_BASE_URL + "/register";
        RegistrationDTO registrationDTO = new RegistrationDTO(username, password);
        try {
            ResponseEntity<LoginResponseDTO> responseEntity = restTemplate.postForEntity(registerUrl, registrationDTO, LoginResponseDTO.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                System.out.println("Användare registrerad!");
                login();
            } else {
                System.out.println("Registreringen misslyckades!");
                displayMenu();
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                System.out.println("Användarnamnet är redan registrerat. Vänligen välj ett annat användarnamn.");
            } else {
                System.out.println("Registreringen misslyckades. Ett fel inträffade.");
            }
            displayMenu();
        }
    }

    private void login() throws IOException, ParseException {
        String username = Scan.getString("Ange användarnamn:");
        String password = Scan.getString("Ange lösenord:");
        String loginUrl = AUTH_API_BASE_URL + "/login";
        RegistrationDTO loginDTO = new RegistrationDTO(username, password);
        try {
            ResponseEntity<LoginResponseDTO> responseEntity = restTemplate.postForEntity(loginUrl, loginDTO, LoginResponseDTO.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                LoginResponseDTO loginResponseDTO = responseEntity.getBody();
                this.jwtToken = loginResponseDTO.getJwt();
                System.out.println("Inloggningen lyckades!");
                User user = loginResponseDTO.getUser();
                navigateToMenu(user.getAuthorities());
            } else {
                System.out.println("Inloggningen misslyckades. Kontrollera dina inloggningsuppgifter och försök igen.");
                displayMenu();
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                System.out.println("Ogiltigt användarnamn eller lösenord. Försök igen.");
            } else {
                System.out.println("Inloggningen misslyckades på grund av klientfel. Försök igen senare.");
            }
            displayMenu();
        }
    }

    private void navigateToMenu(Set<Role> roles) throws IOException, ParseException {
        if (roles.stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
          AdminMenu adminMenu = new AdminMenu(this.jwtToken);
          adminMenu.displayMainMenu();
        } else {
            UserMenu userMenu = new UserMenu(this.jwtToken);
            userMenu.displayMenu();
        }
    }

}
