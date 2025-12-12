package asg.StaffManagementModule.Constants;

public class StaffConstants {

    /**
     * Staff module menu
     */
    public static final String STAFF_MENU_HEADER = "\n\t\t\t\t\t====================================";
    public static final String STAFF_MENU_TITLE = "\n\t\t\t\t\t       STAFF MANAGEMENT MENU        ";
    public static final String STAFF_MENU_ENDER = "\t\t\t\t\t====================================\n\n";

    /**
     * Field modification menu
     */
    public static final String MODIFY_MENU_HEADER = "\n====================================";
    public static final String MODIFY_MENU_TITLE = "\n\nSELECT FIELD TO MODIFY";
    public static final String MODIFY_MENU_ENDER = "====================================\n";

    /**
     * Staff gender
     */
    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";

    /**
     * Added regex patterns for input validation
     */
    public static final String REGEX_STAFFID = "(?i)ST\\d{3}";
    public static final String REGEX_POSITION_DEPT = ".*\\d+.*";

    /**
     * Fixed input prompts
     */
    public static final String ENTER_CHOICE = "Enter your choice: ";
    public static final String ENTER_ID = "Enter ID (Exp: ST001): ";
    public static final String ENTER_NAME = "Enter Name: ";
    public static final String ENTER_GENDER = "Enter Gender (Male/Female): ";
    public static final String ENTER_POSITION = "Enter Position: ";
    public static final String ENTER_SALARY = "Enter Salary: RM ";
    public static final String ENTER_DEPARTMENT = "Enter Department: ";
    public static final String NEW_NAME = "Enter new Name: ";
    public static final String NEW_GENDER = "Enter new Gender (male/female): ";
    public static final String NEW_POSITION = "Enter new Position: ";
    public static final String NEW_SALARY = "Enter new Salary: RM ";
    public static final String NEW_DEPARTMENT = "Enter new Department: ";
    public static final String ID_TO_MODIFY = "Enter ID of staff to modify: ";
    public static final String ID_TO_DELETE = "Enter ID of staff to delete: ";
    public static final String SEARCH_QUERY = "Enter ID or Name of staff to search: ";
    public static final String DEPARTMENT_REPORT = "Enter Department to report: ";

    /**
     * Fixed validation message
     */
    public static final String ERROR_ID_NUMERIC = "ID must start with ST and follow with index number only.  Please try again.";
    public static final String ERROR_ID_EXISTS = "Staff ID %s already exists. Please try again.";
    public static final String ERROR_NAME_CONTAINS_NUMBERS = "Name cannot contain numbers. Please try again.";
    public static final String ERROR_NAME_BLANK = "Name cannot be empty. Please try again.";
    public static final String ERROR_GENDER_INVALID = "Gender must be 'male' or 'female'. Please try again.";
    public static final String ERROR_POSITION_CONTAINS_NUMBERS = "Position cannot contain numbers. Please try again.";
    public static final String ERROR_POSITION_BLANK = "Position cannot be empty. Please try again.";
    public static final String ERROR_DEPARTMENT_CONTAINS_NUMBERS = "Department cannot contain numbers. Please try again.";
    public static final String ERROR_DEPARTMENT_BLANK = "Department cannot be blank. Please try again.";
    public static final String ERROR_DEPARTMENT_NOT_EXIST = "Department does not exist. Please enter an existing department.";
    public static final String ERROR_SALARY_ZERO = "Salary cannot less than zero. Please try again.";
    public static final String ERROR_SALARY_NEGATIVE = "Salary cannot be negative. Please try again.";
    public static final String ERROR_SALARY_BLANK = "Salary cannot be empty. Please try again.";
    public static final String ERROR_SALARY_NUMERIC = "Salary must be a numeric value. Please try again.";
    public static final String ERROR_INVALID_INPUT = "Invalid input. Please enter a valid number.";
    public static final String ERROR_MANDATORY_FIELDS_MODIFY = "Name, Position, and Department are mandatory fields and cannot be empty.";
    public static final String ERROR_INVALID_CHOICE = "\nInvalid choice.. .\n";
    public static final String ERROR_BLANK_INPUT = "\nChoice cannot be blank. Please enter a valid option.\n";
    public static final String ERROR_ID_BLANK = "Staff ID cannot be blank. Please try again.";
    public static final String ERROR_ID_ZERO = "Staff ID cannot be ST000. Please use a valid ID (ST001-ST999).";
    public static final String ERROR_INVALID_CHOICE_RETRY = "Invalid choice. Please enter again.. .\n";

    /**
     * Display labels for search case
     */
    public static final String LABEL_STAFF_DETAILS = "\nStaff Details:\n";
    public static final String LABEL_CURRENT_STAFF = "\nCurrent Staff Details:";
    public static final String LABEL_SEARCH_RESULTS = "\nSearch Results:";
    public static final String LABEL_SEPARATOR = "-------------------------------";
    public static final String LABEL_ID = "ID: ";
    public static final String LABEL_NAME = "Name: ";
    public static final String LABEL_GENDER = "Gender: ";
    public static final String LABEL_POSITION = "Position: ";
    public static final String LABEL_SALARY = "Salary: RM ";
    public static final String LABEL_DEPARTMENT = "Department: ";
    // Labels for Staff report
    public static final String LABEL_DEPARTMENT_REPORT = "\nStaff Report for Department: %s";
    public static final String LABEL_TOTAL_SALARY = "Total Salary for Department %s: RM %.2f";
    public static final String LABEL_EXISTING_DEPARTMENTS = "\nExisting Departments:";

    /**
     * Staff list display
     */
    public static final String TABLE_SEPARATOR = "===========================================================================================================";
    public static final String TABLE_STAFF_TITLE = "                                           STAFF LIST";
    public static final String TABLE_STAFF_HEADER_FORMAT = "%-4s | %-10s | %-15s | %-8s | %-15s | %-12s | %-15s";
    public static final String TABLE_STAFF_ROW_FORMAT = "%-4d | %-10s | %-15s | %-8s | %-15s | %-12.2f | %-15s";

    /**
     * Success messages
     */
    public static final String SUCCESS_STAFF_ADDED = "Staff added successfully.";
    public static final String SUCCESS_STAFF_MODIFIED = "Staff modified successfully.";
    public static final String SUCCESS_STAFF_DELETED = "Staff with ID %s deleted successfully.";
    public static final String ACTION_CANCELLED = "Action cancelled.";

    /**
     * Confirmation prompts
     */
    public static final String CONFIRM_MODIFY = "Are you sure you want to modify this staff? (yes/no): ";
    public static final String CONFIRM_DELETE = "Are you sure you want to delete staff %s? (yes/no): ";
    public static final String ERROR_YES_NO_ONLY = "Invalid input. Please enter 'yes' or 'no' only.";

    /**
     * Error messages - Not found messages
     */
    public static final String ERROR_NO_STAFF_AVAILABLE = "No staff information available. ";
    public static final String ERROR_STAFF_NOT_FOUND = "Staff with ID %s not found.";
    public static final String ERROR_NO_MATCHING_STAFF = "No matching staff found.";
    public static final String ERROR_NO_STAFF_IN_DEPARTMENT = "No staff found in Department %s";
    public static final String ERROR_SEARCH_BLANK = "Search query cannot be blank. Please try again.";
    public static final String ERROR_SEARCH_MIN_LENGTH = "Search query must be at least 2 characters. Please try again.";
    public static final String ERROR_SEARCH_NUMERIC = "Search query cannot contain invalid numbers. Please enter a name or Staff ID (Exp: ST001).";

    /**
     * Staff details display (single staff)
     */
    public static final String STAFF_DETAILS_SEPARATOR = "========================================";
    public static final String STAFF_DETAILS_TITLE = "           STAFF DETAILS";

    /**
     * Report display
     */
    public static final String REPORT_SEPARATOR = "===========================================================================================================";
    public static final String REPORT_TITLE_OVERALL = "                                    OVERALL STAFF REPORT";
    public static final String REPORT_TITLE_DEPARTMENT = "                               DEPARTMENT STAFF REPORT: %s";

    /**
     * Exit message
     */
    public static final String MESSAGE_EXIT_STAFF_MODULE = "\n\nThank you for using the staff management system. Goodbye!\n";

    /**
     * Report menu
     */
    public static final String REPORT_MENU_HEADER = "\n====================================";
    public static final String REPORT_MENU_TITLE = "        SELECT REPORT TYPE";
    public static final String REPORT_MENU_ENDER = "====================================";

    /**
     * Menu option formats
     */
    public static final String MENU_OPTION_FORMAT = "\t\t\t\t\t %d -> %s%n";
    public static final String MENU_OPTION_FORMAT_SIMPLE = " %d -> %s%n";

    /**
     * Report summary labels
     */
    public static final String LABEL_TOTAL_STAFF_SALARY = "Total Staff: %d | Total Salary: RM %.2f";
    public static final String LABEL_DEPT_TOTAL_STAFF_SALARY = "Total Staff in %s: %d | Total Salary: RM %.2f";

    /**
     * Service suggestions
     */
    public static final String LABEL_SUGGESTED_ID = "\n[ Suggested next available ID: %s ]";

    /**
     * Modify confirmation messages
     */
    public static final String CONFIRM_UPDATE_NAME = "Confirm update name to '%s'? (yes/no): ";
    public static final String CONFIRM_UPDATE_GENDER = "Confirm update gender to '%s'? (yes/no): ";
    public static final String CONFIRM_UPDATE_POSITION = "Confirm update position to '%s'? (yes/no): ";
    public static final String CONFIRM_UPDATE_SALARY = "Confirm update salary to RM%s? (yes/no): ";
    public static final String CONFIRM_UPDATE_DEPARTMENT = "Confirm update department to '%s'? (yes/no): ";

    /**
     * Success messages for individual field updates
     */
    public static final String SUCCESS_NAME_UPDATED = "Name updated successfully.";
    public static final String SUCCESS_GENDER_UPDATED = "Gender updated successfully.";
    public static final String SUCCESS_POSITION_UPDATED = "Position updated successfully.";
    public static final String SUCCESS_SALARY_UPDATED = "Salary updated successfully.";
    public static final String SUCCESS_DEPARTMENT_UPDATED = "Department updated successfully.";
    public static final String ERROR_NO_MODIFICATIONS = "No modifications were made.";

}
