package asg.StockManagementModule.Constants;

public final class StockConstants {
    private StockConstants() {
    }

    // UI
    public static final String LINE = "====================================";
    public static final String MENU_TITLE = "STOCK MANAGEMENT MENU";

    // Prompts
    public static final String PROMPT_CHOICE = "Enter your choice: ";
    public static final String PROMPT_CODE = "Enter item code (Format: I###): ";
    public static final String PROMPT_BRAND = "Enter item brand: ";
    public static final String PROMPT_DESC = "Enter item description: ";
    public static final String PROMPT_COLOR = "Enter item color: ";
    public static final String PROMPT_PRICE = "Enter item price: ";
    public static final String PROMPT_QTY = "Enter quantity in stock: ";

    public static final String PROMPT_SEARCH_CODE = "Enter item code to search: ";
    public static final String PROMPT_MODIFY_CODE = "Enter item code to modify: ";
    public static final String PROMPT_DELETE_CODE = "Enter item code to delete: ";
    public static final String PROMPT_CONFIRM_DELETE = "Are you sure you want to delete this item? (yes/no): ";

    // General messages
    public static final String INVALID_CHOICE = "Invalid choice. Please try again.";
    public static final String INVALID_NUMBER = "Invalid input. Please enter a valid number.";
    public static final String GOODBYE = "Thank you for using the stock management system. Goodbye!";

    // Business messages
    public static final String ITEM_ADDED = "New item added successfully.";
    public static final String ITEM_EXISTS = "Item code already exists. Please try a different code.";
    public static final String ITEM_NOT_FOUND = "Item not found.";
    public static final String ITEM_DELETED = "Item deleted successfully.";
    public static final String ITEM_MODIFIED = "Item modified successfully.";
    public static final String STOCK_EMPTY = "The stock is empty.";
    public static final String DELETE_CANCELLED = "Delete action cancelled.";

    // Validation error messages
    public static final String INVALID_CODE = "Invalid item code. Required format: I### (e.g., I001).";
    public static final String INVALID_BRAND = "Item brand cannot be empty or contain digits.";
    public static final String INVALID_COLOR = "Item color cannot be empty or contain digits.";
    public static final String INVALID_PRICE = "Item price cannot be less than zero.";
    public static final String INVALID_QTY = "Quantity in stock cannot be less than zero.";
    public static final String INVALID_DESC = "Item description cannot be empty.";

    public static final String ERROR_YES_NO_ONLY = "Please enter 'yes' or 'no' only.";

    // Validation rule for code
    public static final String ITEM_CODE_REGEX = "I\\d{3}";
}
