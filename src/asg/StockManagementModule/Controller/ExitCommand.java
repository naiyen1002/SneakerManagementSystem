package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Constants.StockConstants;

public class ExitCommand implements StockCommand {

    @Override
    public void execute() {
        // 这里只负责印出 goodbye message 就好
        System.out.println(StockConstants.GOODBYE);
    }
}
