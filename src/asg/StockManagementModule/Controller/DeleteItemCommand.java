package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Service.StockService;

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
