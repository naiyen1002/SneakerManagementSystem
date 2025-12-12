package asg.SalesManagementModule.Constants;

/**
 * Enum representing menu options for the Sales Management module.
 * Provides type-safe menu choices with associated codes and descriptions.
 */
public enum SalesMenuOption {

    DISPLAY_SALES(1, "Display Sales Information"),
    ADD_SALES(2, "Add New Sales Information"),
    DELETE_SALES(3, "Delete Sales Information"),
    MODIFY_SALES(4, "Modify Sales Information"),
    SEARCH_SALES(5, "Search Sales Information"),
    REPORT_SALES(6, "Sales Report"),
    EXIT(0, "Exit");

    private final int code;
    private final String description;

    /**
     * Constructor for SalesMenuOption enum.
     * 
     * @param code        The numeric code for the menu option
     * @param description The display description for the menu option
     */
    SalesMenuOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Gets the numeric code for this menu option.
     * 
     * @return The menu option code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the description for this menu option.
     * 
     * @return The menu option description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Finds a SalesMenuOption by its numeric code.
     * 
     * @param code The numeric code to search for
     * @return The corresponding SalesMenuOption
     * @throws IllegalArgumentException if the code is invalid
     */
    public static SalesMenuOption fromCode(int code) {
        for (SalesMenuOption option : values()) {
            if (option.getCode() == code) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid menu option code: " + code);
    }

    /**
     * Checks if a given code is valid.
     * 
     * @param code The code to validate
     * @return true if the code is valid, false otherwise
     */
    public static boolean isValidCode(int code) {
        for (SalesMenuOption option : values()) {
            if (option.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return code + " -> " + description;
    }
}
