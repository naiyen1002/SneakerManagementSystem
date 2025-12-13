package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Constants.StockConstants;
import asg.StockManagementModule.Constants.StockMenuOption;
import asg.StockManagementModule.Service.StockService;
import asg.StockManagementModule.View.StockView;

/**
 * Stock Management Module Menu Options.
 */
import java.util.Scanner;

public class StockApp {

    private final StockService service;
    private final StockView view;

    public StockApp(Scanner scanner) {
        StockItemController inventory = new StockItemController();
        inventory.initializeDefaultStock();

        StockController controller = new StockController(inventory);

        this.view = new StockView(scanner);
        this.service = new StockService(view, controller);
    }

    public void run() {
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
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new StockApp(scanner).run();
    }

    public static void main() {
        main(new String[0]);
    }
}
