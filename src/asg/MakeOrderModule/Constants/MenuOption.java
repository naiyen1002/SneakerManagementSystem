package asg.MakeOrderModule.Constants;

/**
 * Enum representing menu options for the Make Order module.
 * Provides type-safe menu choices with associated codes and descriptions.
 */
public enum MenuOption {

    DISPLAY_ALL_PRODUCTS(1, "Display All Product Details"),
    MAKE_NEW_ORDER(2, "Make New Order"),
    SEARCH_PRODUCT(3, "Search Product Details"),
    DELETE_ORDER(4, "Delete Order"),
    CHECKOUT(5, "Check Out/Make Payment"),
    EXIT(6, "Exit/Back To Menu");

    private final int code;
    private final String description;

    /**
     * Constructor for MenuOption enum.
     * 
     * @param code The numeric code for the menu option
     * @param description The display description for the menu option
     */
    MenuOption(int code, String description) {
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
     * Finds a MenuOption by its numeric code.
     * 
     * @param code The numeric code to search for
     * @return The corresponding MenuOption
     * @throws IllegalArgumentException if the code is invalid
     */
    public static MenuOption fromCode(int code) {
        for (MenuOption option : values()) {
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
        for (MenuOption option : values()) {
            if (option.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return code + ". " + description;
    }
}
