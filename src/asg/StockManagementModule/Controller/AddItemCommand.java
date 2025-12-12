package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Service.StockService;

/**
 * Concrete command: Add item.
 */
public class AddItemCommand implements StockCommand {

    private final StockService service;

    public AddItemCommand(StockService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.handleAddItem();
    }
}
