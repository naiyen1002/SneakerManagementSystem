package asg.MemberManagementModule.Constants;

/**
 * MemberOption - Enum for all menu options in Member Management Module
 * This enum provides type-safe constants for menu choices, replacing magic
 * numbers
 * and improving code maintainability and readability.
 * 
 * Clean Code Principle: Replace Magic Numbers with Named Constants
 * Design Pattern: Type-Safe Enum Pattern
 */
public enum MemberOptions {

    // ==================== MAIN MENU OPTIONS ====================

    /**
     * Main Menu Option 1: Display all members
     */
    DISPLAY_ALL_MEMBERS(1, "Display All Members Details", MenuType.MAIN),

    /**
     * Main Menu Option 2: Add new member record
     */
    ADD_MEMBER(2, "Add New Records", MenuType.MAIN),

    /**
     * Main Menu Option 3: Search for member records
     */
    SEARCH_MEMBER(3, "Search For Member Records", MenuType.MAIN),

    /**
     * Main Menu Option 4: Modify member's records
     */
    UPDATE_MEMBER(4, "Modify Member's Records", MenuType.MAIN),

    /**
     * Main Menu Option 5: Delete member's records
     */
    DELETE_MEMBER(5, "Delete Member's Records", MenuType.MAIN),

    /**
     * Main Menu Option 6: Show membership tier report
     */
    SHOW_MEMBERSHIP_REPORT(6, "Show All Type Of Member", MenuType.MAIN),

    /**
     * Main Menu Option 7: Exit/Back to menu
     */
    EXIT(7, "Exit/Back To Menu", MenuType.MAIN),

    // ==================== SEARCH MENU OPTIONS ====================

    /**
     * Search Option 1: Search by Member ID
     */
    SEARCH_BY_ID(1, "Member ID", MenuType.SEARCH),

    /**
     * Search Option 2: Search by Name
     */
    SEARCH_BY_NAME(2, "Name", MenuType.SEARCH),

    /**
     * Search Option 3: Search by Gender
     */
    SEARCH_BY_GENDER(3, "Gender Male / Female / Others", MenuType.SEARCH),

    /**
     * Search Option 4: Search by IC Number
     */
    SEARCH_BY_IC(4, "IC Number (xxxxxx-xx-xxxx)", MenuType.SEARCH),

    /**
     * Search Option 5: Search by Contact Number
     */
    SEARCH_BY_CONTACT(5, "Contact Number (+60)", MenuType.SEARCH),

    /**
     * Search Option 6: Search by Join Date
     */
    SEARCH_BY_DATE(6, "Date Become A Member (dd/mm/yyyy)", MenuType.SEARCH),

    // ==================== UPDATE MENU OPTIONS ====================

    /**
     * Update Option 1: Update Name
     */
    UPDATE_NAME(1, "Update Name", MenuType.UPDATE),

    /**
     * Update Option 2: Update Gender
     */
    UPDATE_GENDER(2, "Update Gender", MenuType.UPDATE),

    /**
     * Update Option 3: Update IC Number
     */
    UPDATE_IC(3, "Update IC Number", MenuType.UPDATE),

    /**
     * Update Option 4: Update Contact Number
     */
    UPDATE_CONTACT(4, "Update Contact Number", MenuType.UPDATE),

    /**
     * Update Option 5: Finish updating
     */
    UPDATE_FINISH(5, "Finish", MenuType.UPDATE);

    // ==================== ENUM FIELDS ====================

    private final int value;
    private final String description;
    private final MenuType menuType;

    // ==================== CONSTRUCTOR ====================

    /**
     * Constructor for MemberOption enum
     * 
     * @param value       The numeric value of the option
     * @param description Human-readable description of the option
     * @param menuType    The type of menu this option belongs to
     */
    MemberOptions(int value, String description, MenuType menuType) {
        this.value = value;
        this.description = description;
        this.menuType = menuType;
    }

    // ==================== GETTERS ====================

    /**
     * Get the numeric value of the option
     * 
     * @return The option value
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the description of the option
     * 
     * @return The option description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the menu type this option belongs to
     * 
     * @return The menu type
     */
    public MenuType getMenuType() {
        return menuType;
    }

    // ==================== STATIC FACTORY METHODS ====================

    /**
     * Get MemberOption from value and menu type
     * 
     * @param value    The numeric value
     * @param menuType The menu type
     * @return The corresponding MemberOption
     * @throws IllegalArgumentException if no matching option is found
     */
    public static MemberOptions fromValue(int value, MenuType menuType) {
        for (MemberOptions option : MemberOptions.values()) {
            if (option.value == value && option.menuType == menuType) {
                return option;
            }
        }
        throw new IllegalArgumentException(
                String.format("Invalid option value %d for menu type %s", value, menuType));
    }

    /**
     * Get main menu option from value
     * 
     * @param value The numeric value (1-7)
     * @return The corresponding main menu option
     * @throws IllegalArgumentException if value is invalid
     */
    public static MemberOptions fromMainMenuValue(int value) {
        return fromValue(value, MenuType.MAIN);
    }

    /**
     * Get search menu option from value
     * 
     * @param value The numeric value (1-6)
     * @return The corresponding search menu option
     * @throws IllegalArgumentException if value is invalid
     */
    public static MemberOptions fromSearchMenuValue(int value) {
        return fromValue(value, MenuType.SEARCH);
    }

    /**
     * Get update menu option from value
     * 
     * @param value The numeric value (1-5)
     * @return The corresponding update menu option
     * @throws IllegalArgumentException if value is invalid
     */
    public static MemberOptions fromUpdateMenuValue(int value) {
        return fromValue(value, MenuType.UPDATE);
    }

    /**
     * Check if a value is valid for a given menu type
     * 
     * @param value    The numeric value to check
     * @param menuType The menu type
     * @return true if valid, false otherwise
     */
    public static boolean isValidOption(int value, MenuType menuType) {
        try {
            fromValue(value, menuType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // ==================== NESTED ENUM ====================

    /**
     * MenuType - Enum to categorize different types of menus
     */
    public enum MenuType {
        MAIN("Main Menu"),
        SEARCH("Search Menu"),
        UPDATE("Update Menu");

        private final String displayName;

        MenuType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}
