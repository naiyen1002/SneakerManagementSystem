package asg.SalesManagementModule.Constants;

/**
 * Constants for the Sales Management Module.
 * Contains UI strings, error messages, validation messages, and table formats.
 * Follows Single Responsibility Principle - all UI-related constants in one
 * place.
 */
public class SalesConstants {

    // ==================== Menu Headers ====================
    public static final String MENU_HEADER = "\n\t\t\t\t\t======================================";
    public static final String MENU_TITLE = "\n\t\t\t\t\t       SALES MANAGEMENT MENU      ";
    public static final String MENU_FOOTER = "\t\t\t\t\t======================================\n";

    public static final String SEARCH_MENU_HEADER = "\n\t\t\t\t\t--------------------------------------";
    public static final String SEARCH_MENU_TITLE = "\n\t\t\t\t\t              SEARCH MENU             ";

    // ==================== Input Prompts ====================
    public static final String ENTER_CHOICE = "Enter your choice: ";
    public static final String ENTER_SALES_ID = "Enter Sales ID (FOUR characters): ";
    public static final String ENTER_MEMBER_ID = "Enter Member ID (FOUR characters): ";
    public static final String ENTER_ITEM_CODE = "Enter Item Code (FOUR characters): ";
    public static final String ENTER_BRAND = "Enter Brand: ";
    public static final String ENTER_DESCRIPTION = "Enter Item Description: ";
    public static final String ENTER_COLOUR = "Enter Colour: ";
    public static final String ENTER_PRICE = "Enter Item Price: RM ";
    public static final String ENTER_QUANTITY = "Enter Item Quantity Sales: ";

    public static final String ENTER_NEW_SALES_ID = "Enter new Sales ID (FOUR characters): ";
    public static final String ENTER_NEW_MEMBER_ID = "Enter new Member ID (FOUR characters): ";
    public static final String ENTER_NEW_ITEM_CODE = "Enter new Item Code (FOUR characters): ";

    public static final String ENTER_SALES_ID_TO_DELETE = "Enter Sales ID to delete: ";
    public static final String ENTER_SALES_ID_TO_MODIFY = "Enter Sales ID to modify: ";
    public static final String ENTER_SALES_ID_TO_SEARCH = "Enter Sales ID to search: ";

    // ==================== Validation Error Messages ====================
    public static final String ERROR_INVALID_LENGTH = "Invalid length. Please enter exactly 4 characters.";
    public static final String ERROR_SALES_ID_EXISTS = "Sales ID already exists. Please enter a unique Sales ID.";
    public static final String ERROR_MEMBER_ID_EXISTS = "Member ID already exists. Please enter a unique Member ID.";
    public static final String ERROR_ITEM_CODE_EXISTS = "Item Code already exists. Please enter a unique Item Code.";
    public static final String ERROR_BRAND_TOO_LONG = "Brand name is too long. Please enter below 9 characters.";
    public static final String ERROR_BRAND_EMPTY = "Please enter brand name!";
    public static final String ERROR_DESCRIPTION_TOO_LONG = "Item description is too long. Please enter below 30 characters.";
    public static final String ERROR_DESCRIPTION_EMPTY = "Please enter item description!";
    public static final String ERROR_COLOUR_TOO_LONG = "Colour name is too long. Please enter below 11 characters.";
    public static final String ERROR_COLOUR_EMPTY = "Please enter colour name!";
    public static final String ERROR_PRICE_EXCEEDS_MAX = "Item price exceeds the maximum allowed amount of RM999999999999.99.";
    public static final String ERROR_INVALID_CHOICE = "Invalid choice. Please try again.";
    public static final String ERROR_INVALID_INPUT = "Invalid input. Please enter a valid number.";
    public static final String ERROR_YES_NO_ONLY = "Enter Y, y, N, or n only!";

    // ==================== Success Messages ====================
    public static final String SUCCESS_SALES_ADDED = "Sales record added successfully.";
    public static final String SUCCESS_SALES_DELETED = "Sales record with ID %s deleted successfully.";
    public static final String SUCCESS_SALES_MODIFIED = "Sales record with ID %s modified successfully.";

    // ==================== Info Messages ====================
    public static final String INFO_RECORDS_DISPLAYED = "%d records have been displayed.";
    public static final String INFO_RECORDS_ADDED = "%d records have been added.";
    public static final String INFO_RECORDS_DELETED = "%d records have been deleted.";
    public static final String INFO_RECORDS_MODIFIED = "%d records have been modified.";
    public static final String INFO_ITEM_FOUND = "Item Found:";
    public static final String INFO_ITEM_NOT_FOUND = "Sales ID %s not found.";
    public static final String INFO_NO_MATCHING_RECORDS = "No matching records found.";

    // ==================== Confirmation Prompts ====================
    public static final String CONFIRM_ADD = "Do you want to add this new sales information? (Y/N): ";
    public static final String CONFIRM_DELETE = "Are you sure you want to delete this record? (Y/N): ";
    public static final String CONFIRM_MODIFY = "Are you sure you want to modify this record? (Y/N): ";
    public static final String CONFIRM_CONTINUE_ADD = "Do you want to continue entering new items? (Y/N): ";
    public static final String CONFIRM_CONTINUE_DELETE = "Do you want to continue deleting items? (Y/N): ";
    public static final String CONFIRM_CONTINUE_MODIFY = "Do you want to continue modifying items? (Y/N): ";
    public static final String CONFIRM_CONTINUE_SEARCH = "Do you want to continue searching? (Y/N): ";

    public static final String ACTION_CANCELLED = "Action cancelled.";

    // ==================== Table Format ====================
    public static final String TABLE_HEADER_SEPARATOR = "+========+=========+=========+=========+=============================+==========+==============+=========+";
    public static final String TABLE_ROW_SEPARATOR = "+--------+---------+---------+---------+-----------------------------+----------+--------------+---------+";
    public static final String TABLE_HEADER = "|SALES ID|MEMBER ID|ITEM CODE|  BRAND  |       ITEM DESCRIPTION      |  COLOUR  |ITEM PRICE(RM)|QTY SALES|";
    public static final String TABLE_ROW_FORMAT = "|  %-6s|  %-7s|  %-7s|%-9s|%-29s|%-10s|%14.2f|%9d|";

    // ==================== Display Labels ====================
    public static final String LABEL_SALES_ID = "SALES ID        : ";
    public static final String LABEL_MEMBER_ID = "MEMBER ID       : ";
    public static final String LABEL_ITEM_CODE = "ITEM CODE       : ";
    public static final String LABEL_BRAND = "BRAND           : ";
    public static final String LABEL_DESCRIPTION = "ITEM DESCRIPTION: ";
    public static final String LABEL_COLOUR = "COLOUR          : ";
    public static final String LABEL_PRICE = "ITEM PRICE      : RM";
    public static final String LABEL_QUANTITY = "QUANTITY SALES  : ";

    // ==================== Report Labels ====================
    public static final String REPORT_LIST_SALES_ITEM = "List Sales Item      = ";
    public static final String REPORT_TOTAL_QUANTITY = "Total Quantity Sales = ";
    public static final String REPORT_SUBTOTAL = "SubTotal Sales       = RM";

    // ==================== Navigation Messages ====================
    public static final String PRESS_ENTER_CONTINUE = "Press Enter to continue...";
    public static final String PRESS_ENTER_BACK = "Press Enter to go back...";
    public static final String EXITING_MENU = "Exiting Sales Management Menu...";
    public static final String EXITING_SEARCH_MENU = "Exiting Search Menu...";

    // ==================== Brand Options ====================
    public static final String[] BRAND_OPTIONS = { "NIKE", "PUMA", "ADIDAS", "Other" };

    // ==================== Colour Options ====================
    public static final String[] COLOUR_OPTIONS = { "Black", "White", "Grey", "Red", "Other" };

    // ==================== Validation Constants ====================
    public static final int ID_LENGTH = 4;
    public static final int MAX_BRAND_LENGTH = 9;
    public static final int MAX_DESCRIPTION_LENGTH = 29;
    public static final int MAX_COLOUR_LENGTH = 10;
    public static final double MAX_PRICE = 999999999999.99;
}
