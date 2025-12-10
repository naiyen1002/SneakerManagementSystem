package asg;

import java.util.Scanner;

import view.MakeOrder_refactor;

public class SneakerMainSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main() {
        int choice = 0;
        boolean invalidChoice;

        do {
            System.out.printf(
                    "\n\t ______   ______     ______     ______      __         ______     ______     __  __     ______     ______    \n");
            System.out.printf(
                    "\t/\\  ___\\ /\\  __ \\   /\\  __ \\   /\\__  _\\    /\\ \\       /\\  __ \\   /\\  ___\\   /\\ \\/ /    /\\  ___\\   /\\  == \\   \n");
            System.out.printf(
                    "\t\\ \\  __\\ \\ \\ \\/\\ \\  \\ \\ \\/\\ \\  \\/_/\\ \\/    \\ \\ \\____  \\ \\ \\/\\ \\  \\ \\ \\____  \\ \\  _\"-.  \\ \\  __\\   \\ \\  __<   \n");
            System.out.printf(
                    "\t \\ \\_\\    \\ \\_____\\  \\ \\_____\\    \\ \\_\\     \\ \\_____\\  \\ \\_____\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\ \\_\\ \\_\\ \n");
            System.out.printf(
                    "\t  \\/_/     \\/_____/   \\/_____/     \\/_/      \\/_____/   \\/_____/   \\/_____/   \\/_/\\/_/   \\/_____/   \\/_/ /_/ \n");
            System.out.printf("\n\t\t\t\t\t  ** WELCOME TO FOOT LOCKER SYSTEM **\n");
            System.out.printf("\n\t\t\t\t\t    +-----+-----+-----+-----+-----+");
            System.out.printf("\n\t\t\t\t\t    |                             |");
            System.out.printf("\n\t\t\t\t\t    |          MAIN MENU          |");
            System.out.printf("\n\t\t\t\t\t    |                             |");
            System.out.printf("\n\t\t\t\t\t    +-----+-----+-----+-----+-----+");
            System.out.printf("\n\t\t\t\t\t    |                             |");
            System.out.printf("\n\t\t\t\t\t    |     1 --> Staff Module      |");
            System.out.printf("\n\t\t\t\t\t    |     2 --> Member Module     |");
            System.out.printf("\n\t\t\t\t\t    |     3 --> Sales Module      |");
            System.out.printf("\n\t\t\t\t\t    |     4 --> Stock Module      |");
            System.out.printf("\n\t\t\t\t\t    |     5 --> Make Order        |");
            System.out.printf("\n\t\t\t\t\t    |     6 --> Exit Program      |");
            System.out.printf("\n\t\t\t\t\t    |                             |");
            System.out.printf("\n\t\t\t\t\t    +-----+-----+-----+-----+-----+");

            do {
                invalidChoice = false;
                try {
                    if (!invalidChoice) {
                        do {
                            System.out.printf("\n\n\t\t\t\t\t    Choose Your Option : ");
                            choice = scanner.nextInt();
                            if (choice < 0 || choice > 6) {
                                System.out.println("\nYou Can Only Choose From 1 - 6 Only...\n");
                            }
                        } while (choice < 0 || choice > 6);
                    }
                } catch (Exception exc) {
                    System.out.println("\nEnter Digit Only Helloo...\n");
                    invalidChoice = true;
                    scanner.nextLine();
                }
            } while (invalidChoice);

            switch (choice) {
                case 1:
                    StaffManagementSystem.main();
                    break;
                case 2:
                    // OldMemberManagement.main();
                    break;
                case 3:
                    // NewSales.main();
                    break;
                case 4:
                    // Stock.main();
                    break;
                case 5:
                    MakeOrder_refactor.main();
                    break;
                case 6:
                    System.out.println("\nEXITING PROGRAM...\n");
                    break;
                default:
                    System.out.println("\nINVALID CHOICE!!! Please Key In Again.");
            }
        } while (choice != 6);
        System.out.println("THANKS FOR USING FOOT LOCKER SYSTEM. END OF PROCESSING...\n\n");

    }

}
