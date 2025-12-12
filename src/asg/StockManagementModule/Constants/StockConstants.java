package asg.StockManagementModule.Constants;

public final class StockConstants {

    private StockConstants() {
    }

    // 共用缩进，让文字大致在中间
    public static final String INDENT = "\n\t\t\t\t\t";

    // UI
    public static final String LINE = INDENT + "====================================";
    public static final String MENU_TITLE = INDENT + "\tSTOCK MANAGEMENT MENU";

    // Prompts
    public static final String PROMPT_CHOICE = INDENT + "Enter your choice: ";
    public static final String PROMPT_CODE = INDENT + "Enter item code (Format: I###): ";
    public static final String PROMPT_BRAND = INDENT + "Enter item brand: ";
    public static final String PROMPT_DESC = INDENT + "Enter item description: ";
    public static final String PROMPT_COLOR = INDENT + "Enter item color: ";
    public static final String PROMPT_PRICE = INDENT + "Enter item price: ";
    public static final String PROMPT_QTY = INDENT + "Enter quantity in stock: ";

    public static final String PROMPT_SEARCH_CODE = INDENT + "Enter item code to search: ";
    public static final String PROMPT_MODIFY_CODE = INDENT + "Enter item code to modify: ";
    public static final String PROMPT_DELETE_CODE = INDENT + "Enter item code to delete: ";
    public static final String PROMPT_CONFIRM_DELETE = INDENT + "Are you sure you want to delete this item? (yes/no): ";

    // General messages
    public static final String INVALID_CHOICE = INDENT + "Invalid choice. Please try again.";
    public static final String INVALID_NUMBER = INDENT + "Invalid input. Please enter a valid number.";
    public static final String GOODBYE = INDENT + "Thank you for using the stock management system. Goodbye!";

    // Business messages
    public static final String ITEM_ADDED = INDENT + "New item added successfully.";
    public static final String ITEM_EXISTS = INDENT + "Item code already exists. Please try a different code.";
    public static final String ITEM_NOT_FOUND = INDENT + "Item not found.";
    public static final String ITEM_DELETED = INDENT + "Item deleted successfully.";
    public static final String ITEM_MODIFIED = INDENT + "Item modified successfully.";
    public static final String STOCK_EMPTY = INDENT + "The stock is empty.";
    public static final String DELETE_CANCELLED = INDENT + "Delete action cancelled.";

    // Validation error messages
    public static final String INVALID_CODE = INDENT + "Invalid item code. Required format: I### (e.g., I001).";
    public static final String INVALID_BRAND = INDENT + "Item brand cannot be empty or contain digits.";
    public static final String INVALID_COLOR = INDENT + "Item color cannot be empty or contain digits.";
    public static final String INVALID_PRICE = INDENT + "Item price cannot be less than zero.";
    public static final String INVALID_QTY = INDENT + "Quantity in stock cannot be less than zero.";
    public static final String INVALID_DESC = INDENT + "Item description cannot be empty.";

    public static final String ERROR_YES_NO_ONLY = INDENT + "Please enter 'yes' or 'no' only.";

    // Validation rule for code (regex 本身不要缩进)
    public static final String ITEM_CODE_REGEX = "I\\d{3}";
}
