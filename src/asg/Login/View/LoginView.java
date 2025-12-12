package asg.Login.View;

import java.util.Scanner;

public class LoginView {
    private Scanner scanner;

    public LoginView() {
        this.scanner = new Scanner(System.in);
    }

    // Display welcome banner
    public void displayWelcomeBanner() {
        System.out.printf("\n\n\t\t\t██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗");
        System.out.printf("\n\t\t\t██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝");
        System.out.printf("\n\t\t\t██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗  ");
        System.out.printf("\n\t\t\t██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝  ");
        System.out.printf("\n\t\t\t╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗");
        System.out.printf("\n\t\t\t ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝\n");
    }

    // Get username input from user
    public String getUsername() {
        String username;
        while (true) {
            System.out.print("\n\t\t\t< Enter Username > : ");
            username = scanner.nextLine().trim();
            if (!username.isEmpty()) {
                return username;
            }
            System.out.println("\t\t\t[!] Username cannot be blank. Please try again.");
        }
    }

    // Get password input from user
    public String getPassword() {
        String password;
        while (true) {
            System.out.print("\n\t\t\t< Enter Password > : ");
            password = scanner.nextLine().trim();
            if (!password.isEmpty()) {
                return password;
            }
            System.out.println("\t\t\t[!] Password cannot be blank. Please try again.");
        }
    }

    // Display login success message
    public void displayLoginSuccess(String username) {
        System.out.println("\n\t\t\t\t\tLogin SUCCESSFUL !!! Welcome " + username + "\n");
    }

    // Display login failure message with remaining attempts
    public void displayLoginFailure(int attemptsLeft) {
        System.out.println("\n\t\t\tIncorrect username or password. Attempts left: " + attemptsLeft + "\n");
    }

    // Display final failure message and exit
    public void displayFinalFailure() {
        System.out.println("\n\t\t\tLogin FAILED !!! Exiting program...\n");
    }

    // Close scanner resource
    public void close() {
        scanner.close();
    }
}
