package asg.MemberManagementModule.Constants;

/**
 * MemberConstants - Centralized constant definitions
 * Follows DRY principle: Single source of truth for all string literals
 * Makes maintenance and localization easier
 */
public final class MemberConstants {

    // ==================== GENDER CONSTANTS ====================
    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";
    public static final String GENDER_OTHER = "Other";

    // ==================== MEMBERSHIP TIER CONSTANTS ====================
    public static final String TIER_GOLDEN = "Golden Membership";
    public static final String TIER_SILVER = "Silver Membership";
    public static final String TIER_BRONZE = "Bronze Membership";
    public static final String TIER_BASIC = "Basic Membership";

    // ==================== VALIDATION ERROR MESSAGES ====================
    public static final String ERROR_EMPTY_ID = "Member ID cannot be empty";
    public static final String ERROR_EMPTY_NAME = "Name cannot be empty";
    public static final String ERROR_NAME_TOO_SHORT = "Name must be at least 2 characters long";
    public static final String ERROR_EMPTY_GENDER = "Gender cannot be empty";
    public static final String ERROR_EMPTY_IC = "IC Number cannot be empty";
    public static final String ERROR_EMPTY_CONTACT = "Contact number cannot be empty";
    public static final String ERROR_INVALID_IC = "IC Number must be in format: xxxxxx-xx-xxxx";
    public static final String ERROR_INVALID_CONTACT = "Contact number must be 9-11 digits";
    public static final String ERROR_INVALID_GENDER = "Invalid Gender... Please enter Male, Female, or Other";
    public static final String ERROR_INVALID_DATE_FORMAT = "Invalid date format!  Please enter format: DD/MM/YYYY";
    public static final String ERROR_MEMBER_NOT_FOUND = "Member not found with ID: ";
    public static final String ERROR_DUPLICATE_ID = "Member ID already exists: ";
    public static final String ERROR_NEGATIVE_SPENDING = "Spending amount cannot be negative";
    public static final String ERROR_EMPTY_JOIN_DATE = "Join date cannot be empty";
    public static final String ERROR_FUTURE_DATE = "Join date cannot be in the future";
    public static final String ERROR_INVALID_SEARCH_OPTION = "Invalid search option";
    public static final String ERROR_INVALID_UPDATE_OPTION = "Invalid update option";

    public static final String ERROR_CATCH_INVALID = "Invalid input: ";
    public static final String ERROR_CATCH_UPDATE = "Update error: ";
    public static final String ERROR_CATCH_DELETE = "Failed to delete member";

    // ==================== SUCCESS MESSAGES ====================
    public static final String SUCCESS_ADD = "Member added successfully! ";
    public static final String SUCCESS_UPDATE = "Member updated successfully! ";
    public static final String SUCCESS_DELETE = "Member deleted successfully! ";
    public static final String SUCCESS_SEARCH = "Member found successfully!";

    // ==================== MESSAGE FORMATTING ====================
    public static final String MSG_FORMAT_SUCCESS = "\nSUCCESS: %s";
    public static final String MSG_FORMAT_ERROR = "\nERROR: %s";
    public static final String MSG_FORMAT_CANCEL = "\nCANCELLED: %s";
    public static final String MSG_NOT_FOUND = "Member's Information NOT FOUND!";

    // ==================== OPERATION MESSAGES ====================
    public static final String MSG_CONFIRM_ADD = "Add";
    public static final String MSG_CONFIRM_UPDATE = "Update";
    public static final String MSG_CONFIRM_DELETE = "Delete";
    public static final String MSG_CANCEL_ADD = "ADD CANCELLED";
    public static final String MSG_CANCEL_UPDATE = "UPDATE CANCELLED";
    public static final String MSG_CANCEL_DELETE = "DELETE CANCELLED";
    public static final String MSG_CONTINUE_ADD = "Add Another Record";
    public static final String MSG_CONTINUE_UPDATE = "Update Another Record";
    public static final String MSG_CONTINUE_DELETE = "Delete Another Record";
    public static final String MSG_CONTINUE_SEARCH = "Search Another Member";

    // ==================== MENU HEADERS ====================
    public static final String HEADER_MAIN_MENU = "\n--------------------------------\n\tMember Main Menu\n--------------------------------\n";
    public static final String HEADER_ADD_MEMBER = "\n--------------------------------\n   Add New Member Records\n--------------------------------";
    public static final String HEADER_SEARCH_MEMBER = "\n--------------------------------\n   Search Member Information\n--------------------------------\n";
    public static final String HEADER_UPDATE_MEMBER = "\n--------------------------------\n   Update Member Information\n--------------------------------";
    public static final String HEADER_DELETE_MEMBER = "\n--------------------------------\n   Delete Member Records\n--------------------------------";
    public static final String HEADER_DISPLAY_ALL = "\n--------------------------------\n     All Member Information\n--------------------------------\n";
    public static final String HEADER_MEMBERSHIP_REPORT = "\n\n--------------------------------------------\n          Monthly Report Of Member\n--------------------------------------------\n";

    // ==================== MENU OPTIONS ====================
    public static final String MENU_OPTION_1 = "1. Display All Members Details";
    public static final String MENU_OPTION_2 = "2. Add New Records";
    public static final String MENU_OPTION_3 = "3. Search For Member Records";
    public static final String MENU_OPTION_4 = "4. Modify Member's Records";
    public static final String MENU_OPTION_5 = "5. Delete Member's Records";
    public static final String MENU_OPTION_6 = "6. Show Membership Report";
    public static final String MENU_OPTION_7 = "7. Exit/Back To Menu\n";

    // ==================== INPUT PROMPTS ====================
    public static final String PROMPT_MEMBER_ID = "\nMember ID: ";
    public static final String PROMPT_NAME = "\nName: ";
    public static final String PROMPT_GENDER = "\nGender (Male/Female/Other): ";
    public static final String PROMPT_IC_NUMBER = "\nIC Number (xxxxxx-xx-xxxx): ";
    public static final String PROMPT_CONTACT = "\nContact Number (+60): ";
    public static final String PROMPT_JOIN_DATE = "\nDate Become A Member (dd/mm/yyyy): ";
    public static final String PROMPT_MENU_CHOICE = "Choose From (1-7): ";
    public static final String PROMPT_SEARCH_CHOICE = "\nChoose From (1-6) to Search Member's Information: ";
    public static final String PROMPT_UPDATE_CHOICE = "\nChoose Field to Update (1-5): ";
    public static final String PROMPT_Y_STRING = "Y";
    public static final String PROMPT_N_STRING = "N";

    // ==================== VALIDATION PATTERNS ====================
    public static final String PATTERN_IC_NUMBER = "\\d{6}-\\d{2}-\\d{4}";
    public static final String PATTERN_CONTACT = "\\d{9,11}";

    // ==================== DATE FORMAT ====================
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // ==================== DISPLAY FORMATS ====================
    public static final String FORMAT_TABLE_HEADER = "%-11s %-20s\t%-10s\t%-16s\t%-22s\t%-25s\t%-20s\n";
    public static final String FORMAT_TABLE_ROW = "%-11s %-20s\t%-10s\t%-16s\t%-22s\t%-25s\t%-20s\n";
    public static final String FORMAT_TABLE_SEPARATOR = "=========   ====================\t==========\t================\t======================\t=========================\t====================";

    // ==================== MEMBER DETAILS DISPLAY ====================
    public static final String DETAIL_SEPARATOR = "\n===============================================";
    public static final String DETAIL_MEMBER_ID = "Member ID            : %s\n";
    public static final String DETAIL_NAME = "Name                 : %s\n";
    public static final String DETAIL_GENDER = "Gender               : %s\n";
    public static final String DETAIL_IC_NUMBER = "IC Number            : %s\n";
    public static final String DETAIL_CONTACT = "Contact Number (+60) : %s\n";
    public static final String DETAIL_JOIN_DATE = "Date Become Member   : %s\n";
    public static final String DETAIL_MEMBERSHIP_TIER = "Membership Tier      : %s\n";
    public static final String DETAIL_TOTAL_SPENDING = "Total Spending       : RM %.2f\n";

    // ==================== MEMBERSHIP REPORT DISPLAY ====================
    public static final String REPORT_TABLE_HEADER = "%-11s %-20s\t%-25s\t%-20s\n";
    public static final String REPORT_TABLE_ROW = "%-11s %-20s\t%-25s\tRM %.2f\n";
    public static final String REPORT_TABLE_SEPARATOR = "=========   ====================\t=========================\t====================";
    public static final String REPORT_TIER_SEPARATOR = "\n--------------------------------------------";
    public static final String REPORT_TIER_SEPARATOR_END = "--------------------------------------------\n";
    public static final String REPORT_TOTAL_GOLDEN = "Total Golden Member  : %d\n";
    public static final String REPORT_TOTAL_SILVER = "Total Silver Member  : %d\n";
    public static final String REPORT_TOTAL_BRONZE = "Total Bronze Member  : %d\n";
    public static final String REPORT_TOTAL_BASIC = "Total Basic Member   : %d\n";
    public static final String REPORT_HEADER_MEMBER_ID = "Member ID";
    public static final String REPORT_HEADER_NAME = "Name";
    public static final String REPORT_HEADER_TYPE = "Type Of Member";
    public static final String REPORT_HEADER_SPENDING = "Total Spending (RM)";

    // ==================== SEARCH MENU OPTIONS ====================
    public static final String SEARCH_BY_ID = "1. Member ID";
    public static final String SEARCH_BY_NAME = "2. Name";
    public static final String SEARCH_BY_GENDER = "3. Gender";
    public static final String SEARCH_BY_IC = "4. IC Number";
    public static final String SEARCH_BY_CONTACT = "5. Contact Number";
    public static final String SEARCH_BY_DATE = "6. Join Date";

    // ==================== UPDATE MENU OPTIONS ====================
    public static final String UPDATE_NAME = "1. Name";
    public static final String UPDATE_GENDER = "2. Gender";
    public static final String UPDATE_IC = "3. IC Number";
    public static final String UPDATE_CONTACT = "4. Contact Number";
    public static final String UPDATE_FINISH = "5. Finish Update";

    // ==================== MISCELLANEOUS ====================
    public static final String EXIT_MESSAGE = "\nEXITING MENU.. .\n";
    public static final String END_PROCESSING = "END OF PROCESSING.. .\n";
    public static final String NO_MEMBERS_FOUND = "\nNo members found in the system.\n";
    public static final String TOTAL_MEMBERS_FORMAT = "\n<<< Total %d Member(s) >>>\n";
    public static final String INVALID_MENU_RANGE = "\nYou Can Only Choose From %d - %d Only.. .\n";
    public static final String INVALID_INPUT_DIGIT = "\nEnter Digit Only...  Please enter a number between %d and %d.\n";
    public static final String INVALID_RESPONSE = "\nINVALID RESPONSE!  Please Enter y=Yes or n=No";
    public static final String NO_INPUT_PROVIDED = "\nNo input provided.. .";
    public static final String CONFIRM_ACTION_FORMAT = "\nAre You Sure Want To %s Record(s) (y=Yes/n=No)? :";
    public static final String ASK_CONTINUE_FORMAT = "\n%s (y=Yes/n=No): ";

    // Private constructor to prevent instantiation
    private MemberConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
