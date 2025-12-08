package asg.StaffManagementModule.Constants;

public class StaffConstants {

    /**
     * Staff menu options
     */
    // Later change to StaffOption.java (Practical 2)
    public static final int DISPLAY_STAFF = 1;
    public static final int ADD_STAFF = 2;
    public static final int UPDATE_STAFF = 3;
    public static final int DELETE_STAFF = 4;
    public static final int SEARCH_STAFF = 5;
    public static final int SALARY_REPORT = 6;
    public static final int EXIT_STAFF_MODULE = 0;

    /**
     * Staff module menu
     */
    public static final String STAFF_MENU_HEADER = "\n\t\t\t\t\t====================================";
    public static final String STAFF_MENU_TITLE = "\n\t\t\t\t\t       STAFF MANAGEMENT MENU        ";
    public static final String STAFF_MENU_OPTION_1 = "\n\t\t\t\t\t 1 -> Display Staff Details";
    public static final String STAFF_MENU_OPTION_2 = "\n\t\t\t\t\t 2 -> Add New Staff";
    public static final String STAFF_MENU_OPTION_3 = "\n\t\t\t\t\t 3 -> Modify Current Staff Information";
    public static final String STAFF_MENU_OPTION_4 = "\n\t\t\t\t\t 4 -> Delete Staff";
    public static final String STAFF_MENU_OPTION_5 = "\n\t\t\t\t\t 5 -> Search Staff";
    public static final String STAFF_MENU_OPTION_6 = "\n\t\t\t\t\t 6 -> Report of Staff Overview";
    public static final String STAFF_MENU_OPTION_0 = "\n\t\t\t\t\t 0 -> Exit Staff Module\n";
    public static final String STAFF_MENU_ENDER = "\t\t\t\t\t====================================\n\n";

    /**
     * Staff gender
     */
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";

    /**
     * Added regex patterns for input validation
     */
    public static final String REGEX_STAFFID = "ST\\d+";
    public static final String REGEX_POSITION_DEPT = ".*\\d+.*";

    /**
     * Fixed input prompts
     */
    public static final String ENTER_CHOICE = "Enter your choice: ";
    public static final String ENTER_ID = "Enter ID (Exp: ST001): ";
    public static final String ENTER_NAME = "Enter Name: ";
    public static final String ENTER_GENDER = "Enter Gender (Male/Female): ";
    public static final String ENTER_POSITION = "Enter Position: ";
    public static final String ENTER_SALARY = "Enter Salary: ";
    public static final String ENTER_DEPARTMENT = "Enter Department: ";
    public static final String NEW_NAME = "Enter new Name: ";
    public static final String NEW_GENDER = "Enter new Gender (male/female): ";
    public static final String NEW_POSITION = "Enter new Position: ";
    public static final String NEW_SALARY = "Enter new Salary: ";
    public static final String NEW_DEPARTMENT = "Enter new Department: ";
    public static final String ID_TO_MODIFY = "Enter ID of staff to modify: ";
    public static final String ID_TO_DELETE = "Enter ID of staff to delete: ";
    public static final String SEARCH_QUERY = "Enter ID or Name of staff to search: ";
    public static final String DEPARTMENT_REPORT = "Enter Department to report: ";

    /**
     * Fixed validation message
     */
    public static final String ERROR_ID_NUMERIC = "ID must start with ST and follow with index number only.  Please try again.";
    public static final String ERROR_GENDER_INVALID = "Gender must be 'male' or 'female'. Please try again.";
    public static final String ERROR_POSITION_CONTAINS_NUMBERS = "Position cannot contain numbers. Please try again.";
    public static final String ERROR_DEPARTMENT_CONTAINS_NUMBERS = "Department cannot contain numbers. Please try again.";
    public static final String ERROR_SALARY_ZERO = "Salary cannot less than zero. Please try again.";
    public static final String ERROR_SALARY_NEGATIVE = "Salary cannot be negative. Please try again.";
    public static final String ERROR_INVALID_INPUT = "Invalid input. Please enter a valid number.";
    public static final String ERROR_MANDATORY_FIELDS = "ID, Name, Position, and Department are mandatory fields and cannot be empty.";
    public static final String ERROR_MANDATORY_FIELDS_MODIFY = "Name, Position, and Department are mandatory fields and cannot be empty.";
    public static final String ERROR_INVALID_CHOICE = "\nInvalid choice.. .\n";
    public static final String ERROR_INVALID_CHOICE_RETRY = "Invalid choice. Please enter again.. .\n";

    /**
     * Display labels for search case
     */
    public static final String LABEL_STAFF_DETAILS = "Staff Details:";
    public static final String LABEL_CURRENT_STAFF = "Current Staff Details:";
    public static final String LABEL_SEARCH_RESULTS = "Search Results:";
    public static final String LABEL_SEPARATOR = "-----------------------------";
    public static final String LABEL_ID = "ID: ";
    public static final String LABEL_NAME = "Name: ";
    public static final String LABEL_GENDER = "Gender: ";
    public static final String LABEL_POSITION = "Position: ";
    public static final String LABEL_SALARY = "Salary: ";
    public static final String LABEL_DEPARTMENT = "Department: ";
    // Labels for Staff report
    public static final String LABEL_DEPARTMENT_REPORT = "Staff Report for Department: %s";
    public static final String LABEL_TOTAL_SALARY = "Total Salary for Department %s: %.2f";

    /**
     * Success messages
     */
    public static final String SUCCESS_STAFF_ADDED = "Staff added successfully.";
    public static final String SUCCESS_STAFF_MODIFIED = "Staff modified successfully.";
    public static final String SUCCESS_STAFF_DELETED = "Staff with ID %s deleted successfully.";

    /**
     * Error messages - Not found messages
     */
    public static final String ERROR_NO_STAFF_AVAILABLE = "No staff information available. ";
    public static final String ERROR_STAFF_NOT_FOUND = "Staff with ID %s not found.";
    public static final String ERROR_NO_MATCHING_STAFF = "No matching staff found.";
    public static final String ERROR_NO_STAFF_IN_DEPARTMENT = "No staff found in Department %s";

    /**
     * Exit message
     */
    public static final String MESSAGE_EXIT_STAFF_MODULE = "\nThank you for using the staff management system. Goodbye!\n";

}
