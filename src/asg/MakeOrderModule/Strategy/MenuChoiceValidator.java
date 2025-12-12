package asg.MakeOrderModule.Strategy;

import java.util.Scanner;

public class MenuChoiceValidator implements ValidationStrategy<Integer> {

    private final int min;
    private final int max;

    public MenuChoiceValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer validate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("\nInvalid Number! Please choose between %d and %d.\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("\nEnter Digit Only Please...\n");
            }
        }
    }
}
