package org.example.menu;

import org.example.dto.LoginResponseDTO;
import org.example.dto.RegistrationDTO;
import org.example.dto.Role;
import org.example.util.Scan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class Menu { // huvud meny för att registrering och inloggning

    private final String AUTH_API_BASE_URL = "http://localhost:5000/api/v1/auth";
    private final RestTemplate restTemplate;
    private String jwtToken;

    public Menu(){
        this.restTemplate = new RestTemplate();
        this.jwtToken = null;
    }

    public void displayMenu() {
        System.out.println("Welcome!");
        System.out.println("1. Register");
        System.out.println("2. Log in");
        System.out.println("3. Exit");
        System.out.println("Choose one of the options:");
        int choice = Integer.parseInt(Scan.getString(""));

        switch (choice) {
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
        }
    }

    private void register() {
        String username = Scan.getString("Enter username:");
        String password = Scan.getString("Enter password");

        String registerUrl = AUTH_API_BASE_URL + "/register";
        RegistrationDTO registrationDTO = new RegistrationDTO(username, password);
        ResponseEntity<LoginResponseDTO> responseEntity = restTemplate.postForEntity(registerUrl,registrationDTO, LoginResponseDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("User registered!");
        }else {
            System.out.println("Registration failed!");
        }

    }

    private void login(){
        String username = Scan.getString("Enter username:");
        String password = Scan.getString("Enter password:");

        String loginUrl = AUTH_API_BASE_URL + "/login";
        RegistrationDTO loginDTO = new RegistrationDTO(username, password);
        ResponseEntity<LoginResponseDTO> responseEntity = restTemplate.postForEntity(loginUrl,loginDTO, LoginResponseDTO.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            LoginResponseDTO loginResponseDTO = responseEntity.getBody();
            this.jwtToken = loginResponseDTO.getJwt();
            System.out.println("Login successful!");
            navigateToMenu(LoginResponseDTO.getUser().getAuthorities());
        } else {
            System.out.println("Login failed.");
        }
    }

    private void navigateToMenu(Set<Role> roles) {
        if (roles.stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
            AdminMenu adminMenu = new AdminMenu(this.jwtToken);
            adminMenu.displayMenu();
        } else {
            UserMenu userMenu = new UserMenu(this.jwtToken);
            userMenu.displayMenu();
        }
    }

}


