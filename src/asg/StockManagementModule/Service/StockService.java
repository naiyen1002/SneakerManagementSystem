package asg.StockManagementModule.Service;

import asg.StockManagementModule.Constants.StockConstants;
import asg.StockManagementModule.Controller.StockController;
import asg.StockManagementModule.Controller.StockItemController;
import asg.StockManagementModule.Model.StockItem;
import asg.StockManagementModule.View.StockView;

import java.util.List;

/**
 * Service layer: input + validation flow (类似 StaffService 的风格)
 */
public class StockService {

    private final StockView view;
    private final StockController controller;
    private final StockItemController inventory;

    public StockService(StockView view, StockController controller) {
        this.view = view;
        this.controller = controller;
        this.inventory = controller.getInventory();
    }

    // =================== Use case: Add ===================

    public void handleAddItem() {
        String code = getValidItemCodeForAdd();
        String brand = getValidBrand(false);
        String desc = getValidDescription(false);
        String colour = getValidColour(false);
        double price = getValidPrice(false);
        int qty = getValidQuantity(false);

        boolean ok = controller.addItem(code, brand, desc, colour, price, qty);
        view.displayInfoMessage(ok ? StockConstants.ITEM_ADDED : StockConstants.INVALID_CHOICE);
    }

    // =================== Use case: Search ===================

    public void handleSearchItem() {
        String code = readLine(StockConstants.PROMPT_SEARCH_CODE);

        inventory.findByCode(code)
                .ifPresentOrElse(
                        view::printItemDetails,
                        () -> view.displayErrorMessage(StockConstants.ITEM_NOT_FOUND));
    }

    // =================== Use case: Modify ===================

    public void handleModifyItem() {
        String code = readLine(StockConstants.PROMPT_MODIFY_CODE);

        var found = inventory.findByCode(code);
        if (found.isEmpty()) {
            view.displayErrorMessage(StockConstants.ITEM_NOT_FOUND);
            return;
        }

        StockItem existing = found.get();
        view.displayInfoMessage("Existing item:");
        view.printItemDetails(existing);

        String brand = getValidBrand(true);
        String desc = getValidDescription(true);
        String colour = getValidColour(true);
        double price = getValidPrice(true);
        int qty = getValidQuantity(true);

        boolean ok = controller.updateItem(code, brand, desc, colour, price, qty);
        view.displayInfoMessage(ok ? StockConstants.ITEM_MODIFIED : StockConstants.INVALID_CHOICE);
    }

    // =================== Use case: Display ===================

    public void handleDisplayItems() {
        List<StockItem> items = inventory.getAllItems();
        view.printItemTable(items);
    }

    // =================== Use case: Delete ===================

    public void handleDeleteItem() {
        String code = readLine(StockConstants.PROMPT_DELETE_CODE);

        if (!inventory.exists(code)) {
            view.displayErrorMessage(StockConstants.ITEM_NOT_FOUND);
            return;
        }

        view.displayInfoMessage("Item to be deleted:");
        inventory.findByCode(code).ifPresent(view::printItemDetails);

        boolean confirm = getConfirmation(StockConstants.PROMPT_CONFIRM_DELETE);
        if (!confirm) {
            view.displayInfoMessage(StockConstants.DELETE_CANCELLED);
            return;
        }

        boolean ok = controller.deleteItem(code);
        view.displayInfoMessage(ok ? StockConstants.ITEM_DELETED : StockConstants.INVALID_CHOICE);
    }

    // =================== 输入 + 验证 helper ===================

    public String getValidItemCodeForAdd() {
        String code;

        String suggestion = getNextAvailableCode();
        view.displayInfoMessage("\n[ Suggested next available code: " + suggestion + " ]");

        while (true) {
            code = readLine(StockConstants.PROMPT_CODE);

            if (!controller.isValidItemCode(code)) {
                view.displayErrorMessage(StockConstants.INVALID_CODE);
                continue;
            }

            if (inventory.exists(code)) {
                view.displayErrorMessage(StockConstants.ITEM_EXISTS);
                continue;
            }

            return code.toUpperCase();
        }
    }

    public String getValidBrand(boolean isModify) {
        while (true) {
            String prompt = isModify ? "Enter new item brand: " : StockConstants.PROMPT_BRAND;
            String brand = readLine(prompt);

            if (!controller.isValidBrand(brand)) {
                view.displayErrorMessage(StockConstants.INVALID_BRAND);
                continue;
            }

            return toTitleCase(brand.trim());
        }
    }

    public String getValidDescription(boolean isModify) {
        String prompt = isModify ? "Enter new item description: " : StockConstants.PROMPT_DESC;
        while (true) {
            String desc = readLine(prompt);
            if (!controller.isValidDescription(desc)) {
                view.displayErrorMessage(StockConstants.INVALID_DESC);
                continue;
            }
            return desc.trim();
        }
    }

    public String getValidColour(boolean isModify) {
        while (true) {
            String prompt = isModify ? "Enter new item color: " : StockConstants.PROMPT_COLOR;
            String colour = readLine(prompt);

            if (!controller.isValidColour(colour)) {
                view.displayErrorMessage(StockConstants.INVALID_COLOR);
                continue;
            }
            return toTitleCase(colour.trim());
        }
    }

    public double getValidPrice(boolean isModify) {
        String prompt = isModify ? "Enter new item price: " : StockConstants.PROMPT_PRICE;
        while (true) {
            double price = view.readDouble(prompt);
            if (!controller.isValidPrice(price)) {
                view.displayErrorMessage(StockConstants.INVALID_PRICE);
                continue;
            }
            return price;
        }
    }

    public int getValidQuantity(boolean isModify) {
        String prompt = isModify ? "Enter new quantity in stock: " : StockConstants.PROMPT_QTY;
        while (true) {
            int qty = view.readInt(prompt);
            if (!controller.isValidQuantity(qty)) {
                view.displayErrorMessage(StockConstants.INVALID_QTY);
                continue;
            }
            return qty;
        }
    }

    // ==== 供 Service 内部使用的小 helper ====

    public String readLine(String prompt) {
        return view.readLine(prompt);
    }

    public boolean getConfirmation(String prompt) {
        while (true) {
            String input = readLine(prompt).toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                view.displayErrorMessage(StockConstants.ERROR_YES_NO_ONLY);
            }
        }
    }

    private String getNextAvailableCode() {
        List<StockItem> list = inventory.getAllItems();
        int max = 0;
        for (StockItem item : list) {
            String id = item.getItemCode();
            if (id != null && id.startsWith("I") && id.length() == 4) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("I%03d", max + 1);
    }

    private String toTitleCase(String input) {
        if (input == null || input.isEmpty())
            return input;
        String[] words = input.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0)
                sb.append(" ");
            String w = words[i];
            sb.append(Character.toUpperCase(w.charAt(0)));
            if (w.length() > 1) {
                sb.append(w.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
}
