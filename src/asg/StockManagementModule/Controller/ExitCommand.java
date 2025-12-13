package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Constants.StockConstants;

/**
 * Concrete command: Exit.
 */
public class ExitCommand implements StockCommand {

    @Override
    public void execute() {
        System.out.println(StockConstants.GOODBYE);
    }
}
