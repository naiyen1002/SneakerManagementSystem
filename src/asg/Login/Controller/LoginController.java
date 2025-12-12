package asg.Login.Controller;

import java.util.ArrayList;

import asg.SneakerMainSystem;
import asg.Login.Model.IdLogin;
import asg.Login.View.LoginView;

public class LoginController {
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private ArrayList<IdLogin> userCredentials;
    private LoginView view;

    public LoginController() {
        this.view = new LoginView();
        this.userCredentials = new ArrayList<>();
        initializeCredentials();
    }

    // Initialize user credentials
    private void initializeCredentials() {
        userCredentials.add(new IdLogin("ed", "ed6767"));
        userCredentials.add(new IdLogin("jh", "jh2004"));
        userCredentials.add(new IdLogin("ny", "ny1002"));
        userCredentials.add(new IdLogin("jo", "jo1337"));
        userCredentials.add(new IdLogin("sy", "sy1234"));
    }

    // Validate credentials against stored users
    public boolean validateCredentials(String username, String password) {
        for (IdLogin credentials : userCredentials) {
            if (credentials.getUsername().equals(username)
                    && credentials.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Main login process
    public void startLogin() {
        int loginAttempts = 0;
        boolean isLoggedIn = false;

        view.displayWelcomeBanner();

        while (loginAttempts < MAX_LOGIN_ATTEMPTS && !isLoggedIn) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (validateCredentials(username, password)) {
                isLoggedIn = true;
                view.displayLoginSuccess(username);
                SneakerMainSystem.main();
            } else {
                loginAttempts++;
                view.displayLoginFailure(MAX_LOGIN_ATTEMPTS - loginAttempts);
            }
        }

        if (!isLoggedIn) {
            view.displayFinalFailure();
            System.exit(0);
        }

        view.close();
    }

    // Entry point
    public static void main(String[] args) {
        LoginController controller = new LoginController();
        controller.startLogin();
    }
}
