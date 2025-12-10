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
     * Field modification menu options
     */
    public static final int MODIFY_NAME = 1;
    public static final int MODIFY_GENDER = 2;
    public static final int MODIFY_POSITION = 3;
    public static final int MODIFY_SALARY = 4;
    public static final int MODIFY_DEPARTMENT = 5;
    public static final int FINISH_MODIFICATION = 0;

    /**
     * Field modification menu
     */
    public static final String MODIFY_MENU_HEADER = "\n====================================";
    public static final String MODIFY_MENU_TITLE = "\n\nSELECT FIELD TO MODIFY";
    public static final String MODIFY_MENU_OPTION_1 = " 1 -> Modify Name";
    public static final String MODIFY_MENU_OPTION_2 = " 2 -> Modify Gender";
    public static final String MODIFY_MENU_OPTION_3 = " 3 -> Modify Position";
    public static final String MODIFY_MENU_OPTION_4 = " 4 -> Modify Salary";
    public static final String MODIFY_MENU_OPTION_5 = " 5 -> Modify Department";
    public static final String MODIFY_MENU_OPTION_0 = " 0 -> Finish Modification";
    public static final String MODIFY_MENU_ENDER = "====================================\n";

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
    public static final String ERROR_SEARCH_BLANK = "Search query cannot be blank. Please try again.";
    public static final String ERROR_SEARCH_MIN_LENGTH = "Search query must be at least 2 characters. Please try again.";
    public static final String ERROR_SEARCH_NUMERIC = "Search query cannot contain invalid numbers. Please enter a name or Staff ID (Exp: ST001).";

    /**
     * Exit message
     */
    public static final String MESSAGE_EXIT_STAFF_MODULE = "\nThank you for using the staff management system. Goodbye!\n";

}
