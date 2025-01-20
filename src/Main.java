import Services.AuthService;
import Views.AuthView;
import Views.SignInView;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] s) {
        EventQueue.invokeLater(() -> {
            try {
                AuthService authService = new AuthService();

                // Check if there are any registered users
                if (authService.isAnyUserRegistered()) {
                    // Load SignInView if a user exists
                    SignInView signInView = new SignInView();
                    signInView.setVisible(true);
                } else {
                    // Load RegisterView (AuthView) if no user exists
                    AuthView authView = new AuthView();
                    authView.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

