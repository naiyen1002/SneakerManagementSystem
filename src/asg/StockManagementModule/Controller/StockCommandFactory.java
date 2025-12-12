package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Constants.StockMenuOption;
import asg.StockManagementModule.Service.StockService;

public final class StockCommandFactory {

    private StockCommandFactory() {
    }

    public static StockCommand fromOption(StockMenuOption option, StockService service) {
        if (option == null)
            return null;

        return switch (option) {
            case ADD_ITEM -> new AddItemCommand(service);
            case DISPLAY_ITEMS -> new DisplayItemCommand(service);
            case SEARCH_ITEM -> new SearchItemCommand(service);
            case MODIFY_ITEM -> new ModifyItemCommand(service);
            case DELETE_ITEM -> new DeleteItemCommand(service);
            case EXIT -> new ExitCommand();
        };
    }
}
