package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Service.StockService;

/**
 * Concrete command: Diplay item.
 */
public class DisplayItemCommand implements StockCommand {

    private final StockService service;

    public DisplayItemCommand(StockService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.handleDisplayItems();
    }
}
