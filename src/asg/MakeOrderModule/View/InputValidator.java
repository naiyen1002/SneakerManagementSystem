package asg.MakeOrderModule.View;

import java.util.Scanner;

/**
 * Utility class for handling and validating user input.
 * Separates input validation logic from the main View class.
 */
public class InputValidator {
    

    public static int getMenuChoice(Scanner scanner, int min, int max) {
        int choice = -1;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.printf("Choose From %d to %d : ", min, max);
            
            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    validChoice = true;
                } else {
                    System.out.printf("\nInvalid Number! Please choose between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEnter Digit Only Please...\n");
            }
        }

        return choice;
    }

    public static char getYesNoResponse(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            
            while (input.isEmpty()) {
                System.out.println("\nNo Input Provided...");
                System.out.print(prompt);
                input = scanner.nextLine();
            }
            
            char response = Character.toUpperCase(input.charAt(0));
            if (response == 'Y' || response == 'N') {
                return response;
            }
            System.out.println("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No");
        } while (true);
    }

    public static String getNonEmptyString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nNo Input Provided...");
            }
        } while (input.isEmpty());
        return input;
    }
}
