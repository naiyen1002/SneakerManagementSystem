package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Service.StockService;

/**
 * Concrete command: Modify item.
 */
public class ModifyItemCommand implements StockCommand {

    private final StockService service;

    public ModifyItemCommand(StockService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.handleModifyItem();
    }
}
