package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Service.StockService;

/**
 * Concrete command: Search item.
 */
public class SearchItemCommand implements StockCommand {

    private final StockService service;

    public SearchItemCommand(StockService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.handleSearchItem();
    }
}
