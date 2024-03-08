package org.example.menu;

import org.apache.hc.core5.http.ParseException;
import org.example.dto.LoginResponseDTO;
import org.example.dto.RegistrationDTO;
import org.example.dto.Role;
import org.example.dto.User;
import org.example.util.Scan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Set;

public class Menu { // huvud meny för att registrering och inloggning

   private static String MENU =
           "> GÖR VAL:\n"
                   + "> REGISTER:    [1]\n"
                   + "> LOG IN:      [2]\n"
                   + "> EXIT:        [3]\n";

    private final String AUTH_API_BASE_URL = "http://localhost:5000/api/v1/auth";
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
                System.out.println("Thank you for using our application!");
                System.exit(0);
            default:
                System.out.println("Invalid selection. Please try again.");
                displayMenu();
                break;
        }
    }



    private void register() throws IOException, ParseException {
        String username = Scan.getString("Enter username:");
        String password = Scan.getString("Enter password");

        String registerUrl = AUTH_API_BASE_URL + "/register";
        RegistrationDTO registrationDTO = new RegistrationDTO(username, password);
        ResponseEntity<LoginResponseDTO> responseEntity = restTemplate.postForEntity(registerUrl, registrationDTO, LoginResponseDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("User registered!");
            login();
        } else {
            System.out.println("Registration failed!");
            displayMenu();
        }
    }

    private void login() throws IOException, ParseException {
        String username = Scan.getString("Enter username:");
        String password = Scan.getString("Enter password:");
        String loginUrl = AUTH_API_BASE_URL + "/login";
        RegistrationDTO loginDTO = new RegistrationDTO(username, password);
        ResponseEntity<LoginResponseDTO> responseEntity = restTemplate.postForEntity(loginUrl, loginDTO, LoginResponseDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            LoginResponseDTO loginResponseDTO = responseEntity.getBody();
            this.jwtToken = loginResponseDTO.getJwt();
            System.out.println("Login successful!");
            User user = loginResponseDTO.getUser();
            navigateToMenu(user.getAuthorities());
        } else {
            System.out.println("Login failed.");
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
