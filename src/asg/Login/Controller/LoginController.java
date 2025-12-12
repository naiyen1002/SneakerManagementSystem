package asg.Login.Controller;

import java.util.ArrayList;

import asg.SneakerMainSystem;
import asg.Login.Model.IdLogin;
import asg.Login.View.LoginView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        // SHA-256
        userCredentials.add(new IdLogin("ed", "76f4b696ce3e0ba0bab88a94245fa64eb090252075fb700660157f564a9738a9")); 
        userCredentials.add(new IdLogin("jh", "1dc63c28fbdf59f8c4d9661927a02cf4435bbf225728132701ac6fd77848dd3e")); 
        userCredentials.add(new IdLogin("ny", "e0b91a1333983d788c57e78209cf55e19370de2313f9db4a8b3c824fdc8b28ec")); 
        userCredentials.add(new IdLogin("jo", "e8691cc90bb1b82afd63c44c9269aa51f3e2bdeca2992d17bb0a09d905c7781f")); 
        userCredentials.add(new IdLogin("sy", "6e59398cf4113b7b5fae45849e137f3b5d499cb477853e37cc35d5d94388278f")); 
    }

    // Validate credentials against stored users
    public boolean validateCredentials(String username, String password) {
        for (IdLogin credentials : userCredentials) {
            if (credentials.getUsername().equals(username)
                    && credentials.getPassword().equals(hashPassword(password))) {
                return true;
            }
        }
        return false;
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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
