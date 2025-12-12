package asg.MakeOrderModule.Strategy;

import java.util.Scanner;

public class YesNoValidator implements ValidationStrategy<Character> {

    @Override
    public Character validate(Scanner scanner, String prompt) {
        String input;

        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.println("\nNo Input Provided...");
                continue;
            }

            char response = Character.toUpperCase(input.charAt(0));

            if (response == 'Y' || response == 'N') {
                return response;
            }

            System.out.println("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No");
        }
    }
}
