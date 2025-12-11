package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Constants.StockConstants;
import asg.StockManagementModule.Constants.StockMenuOption;
import asg.StockManagementModule.Service.StockService;
import asg.StockManagementModule.View.StockView;

import java.util.Scanner;

/**
 * Main menu / Invoker for Command pattern.
 */
public class StockApp {

    public static void main(String[] args) {
        // Inventory & controller
        StockItemController inventory = new StockItemController();
        inventory.initializeDefaultStock();
        StockController controller = new StockController(inventory);

        // IO
        Scanner scanner = new Scanner(System.in);
        StockView view = new StockView(scanner);

        // Service (Receiver)
        StockService service = new StockService(view, controller);

        boolean exit = false;
        while (!exit) {
            view.displayMenu();
            int choice = view.readInt(StockConstants.PROMPT_CHOICE);
            StockMenuOption option = StockMenuOption.fromCode(choice);

            StockCommand cmd = StockCommandFactory.fromOption(option, service);
            if (cmd == null) {
                view.displayErrorMessage(StockConstants.INVALID_CHOICE);
                continue;
            }

            cmd.execute();

            if (option == StockMenuOption.EXIT) {
                exit = true;
            }
        }

        scanner.close();
    }
}
