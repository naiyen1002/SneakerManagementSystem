package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Service.StockService;

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
