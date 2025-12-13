package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Service.StockService;

/**
 * Concrete command: Delete item.
 */
public class DeleteItemCommand implements StockCommand {

    private final StockService service;

    public DeleteItemCommand(StockService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.handleDeleteItem();
    }
}
