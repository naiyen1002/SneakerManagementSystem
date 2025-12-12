package asg.MakeOrderModule.Strategy;

import java.util.Scanner;

/**
 * Context class to execute the chosen validation strategy.
 */
public class ValidationContext<T> {

    private ValidationStrategy<T> strategy;

    public void setStrategy(ValidationStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public T execute(Scanner scanner, String prompt) {
        return strategy.validate(scanner, prompt);
    }
}
