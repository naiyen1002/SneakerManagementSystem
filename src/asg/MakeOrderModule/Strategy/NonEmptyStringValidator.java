package asg.MakeOrderModule.Strategy;

import java.util.Scanner;

public class NonEmptyStringValidator implements ValidationStrategy<String> {

    @Override
    public String validate(Scanner scanner, String prompt) {
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
