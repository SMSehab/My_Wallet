package Controllers;

import Services.AuthService;
import Models.User; 

public class AuthController {

    private AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    public boolean authenticate(String username, String password) {
        return authService.authenticate(new User(username, password));
    }

    public boolean register(String username, String password) throws Exception {
        return authService.register(new User(username, password));
    }
}

