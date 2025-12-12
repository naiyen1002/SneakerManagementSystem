package asg.MakeOrderModule.Strategy;

import java.util.Scanner;

/**
 * Strategy interface for all input validations.
 */
public interface ValidationStrategy<T> {
    T validate(Scanner scanner, String prompt);
}
