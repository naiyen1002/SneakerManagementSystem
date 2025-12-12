/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package asg;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int loginAttempts = 0;
        boolean isLoggedIn = false;
        ArrayList<IdLogin> userCredentials = new ArrayList<>();

        userCredentials.add(new IdLogin("ed", "ed6767"));
        userCredentials.add(new IdLogin("jh", "jh2004"));
        userCredentials.add(new IdLogin("ny", "ny1002"));
        userCredentials.add(new IdLogin("jo", "jo1337"));
        userCredentials.add(new IdLogin("sy", "sy1234"));


        System.out.printf("\n\n\t\t\t██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗");
        System.out.printf("\n\t\t\t██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝");
        System.out.printf("\n\t\t\t██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗  ");
        System.out.printf("\n\t\t\t██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝  ");
        System.out.printf("\n\t\t\t╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗");
        System.out.printf("\n\t\t\t ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝\n");


        while (loginAttempts < 3 && !isLoggedIn) {
            System.out.print("\n\t\t\t< Enter Username > : ");
            String username = scanner.nextLine();
            System.out.print("\n\t\t\t< Enter Password > : ");
            String password = scanner.nextLine();

            for (IdLogin credentials : userCredentials) {
                if (credentials.getUsername().equals(username) && credentials.getPassword().equals(password)) {
                    isLoggedIn = true;
                    System.out.println("\n\t\t\t\t\tLogin SUCCESSFUL !!! Welcome " + username+"\n");
                    SneakerMainSystem.main();
                    break;
                }
            }

            if (!isLoggedIn) {
                loginAttempts++;
                System.out.println("\n\t\t\tIncorrect username or password. Attempts left: " + (3 - loginAttempts)+"\n");
            }
        }

        if (!isLoggedIn) {
            System.out.println("\n\t\t\tLogin FAILED !!! Exiting program...\n");
            System.exit(0);
        }

        scanner.close();
    }
    
}

                                                              

