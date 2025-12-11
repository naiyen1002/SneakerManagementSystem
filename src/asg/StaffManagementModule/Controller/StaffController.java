package asg.StaffManagementModule.Controller;

import asg.StaffManagementModule.Constants.StaffConstants;
import asg.StaffManagementModule.Constants.StaffData;
import asg.StaffManagementModule.Constants.StaffMenuOptions;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.View.StaffView;
import asg.StaffManagementModule.Service.StaffService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffController {

    public final ArrayList<Staff> staffList;
    public final Scanner scanner;
    private final StaffView view;
    private final StaffService staffService;

    public StaffController() {
        this.staffList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Initialise the StaffView for Staff Module UI display
        this.view = new StaffView();
        // Initialise the StaffService for getting the validation functions
        this.staffService = new StaffService(view, scanner, staffList);
    }

    public static void main() {
        StaffController staffSystem = new StaffController();
        staffSystem.retrieveStaff();
        staffSystem.staffMenu();
    }

    /**
     * Staff main menu
     */
    public void staffMenu() {
        StaffMenuOptions option;

        do {
            // Display the menu from enum (StaffMenuOptions)
            displayMenu();
            option = getMenuOption();

            if (option == null) {
                view.displayErrorMessage(StaffConstants.ERROR_INVALID_CHOICE);
                continue;
            }

            // Process user option using enum
            processMenuOption(option);

        } while (option != StaffMenuOptions.EXIT);
    }

    /**
     * Display menu options from enum
     */
    private void displayMenu() {
        view.displayMenuHeader();
        for (StaffMenuOptions opt : StaffMenuOptions.getMainMenuOptions()) {
            System.out.printf("\t\t\t\t\t %d -> %s\n", opt.getCode(), opt.getDescription());
        }
        view.displayMenuEnder();
    }

    /**
     * Get menu option from user input
     */
    private StaffMenuOptions getMenuOption() {
        int choice = getMenuChoice();
        return StaffMenuOptions.fromCode(choice);
    }

    /**
     * Process the selected menu option
     */
    private void processMenuOption(StaffMenuOptions option) {
        switch (option) {
            case DISPLAY_STAFF:
                displayStaff();
                break;
            case ADD_STAFF:
                addStaff();
                break;
            case UPDATE_STAFF:
                modifyStaff();
                break;
            case DELETE_STAFF:
                deleteStaff();
                break;
            case SEARCH_STAFF:
                searchStaff();
                break;
            case SALARY_REPORT:
                departmentSalaryReport();
                break;
            case EXIT:
                closeSystem();
                break;
            default:
                break;
        }
    }

    /**
     * Get the choice input by user
     * 
     * @return choice
     */
    private int getMenuChoice() {
        while (true) {
            view.displayPrompt(StaffConstants.ENTER_CHOICE);
            String input = scanner.nextLine();

            // Check for blank input
            if (input.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_BLANK_INPUT);
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                view.displayErrorMessage(StaffConstants.ERROR_INVALID_CHOICE);
            }
        }
    }

    /**
     * Display all staff
     */
    public void displayStaff() {
        view.displayAllStaff(staffList);
    }

    /**
     * Add a new staff
     */
    public void addStaff() {
        String id = staffService.getValidStaffId();
        String name = staffService.getValidName(false);
        String gender = staffService.getValidGender(false);

        String position = staffService.getValidPosition(false);
        if (position == null) {
            return;
        }

        Double salary = staffService.getValidSalary(false);
        if (salary == null) {
            return;
        }

        String department = staffService.getValidDepartment(false);
        if (department == null) {
            return;
        }

        // If no issue, then can add the staff
        Staff newStaff = new Staff(id, name, gender, position, salary, department);
        staffList.add(newStaff);

        view.displaySuccessMessage(StaffConstants.SUCCESS_STAFF_ADDED);
    }

    /**
     * Modify staff details
     */
    public void modifyStaff() {
        Staff staffToModify = null;

        while (staffToModify == null) {
            view.displayPrompt(StaffConstants.ENTER_ID);
            String staffId = scanner.nextLine();

            if (staffId.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_ID_BLANK);
                continue;
            }

            // Search for staff
            for (Staff staff : staffList) {
                if (staff.getId().equalsIgnoreCase(staffId)) {
                    staffToModify = staff;
                    break;
                }
            }

            if (staffToModify == null) {
                view.displayErrorMessage(String.format(StaffConstants.ERROR_STAFF_NOT_FOUND, staffId));
            }
        }

        view.displayStaffDetails(staffToModify);

        StaffMenuOptions choice;
        boolean modified = false;

        do {
            displayModifyMenu();
            choice = getModifyMenuOption();

            if (choice == null) {
                view.displayErrorMessage(StaffConstants.ERROR_INVALID_CHOICE);
                continue;
            }

            modified = processModifyOption(choice, staffToModify, modified);

        } while (choice != StaffMenuOptions.FINISH_MODIFICATION);
    }

    /**
     * Display modify menu options from enum
     */
    private void displayModifyMenu() {
        view.displayModifyMenuHeader();
        for (StaffMenuOptions opt : StaffMenuOptions.getModifyMenuOptions()) {
            System.out.printf(" %d -> %s\n", opt.getCode(), opt.getDescription());
        }
        view.displayModifyMenuEnder();
    }

    /**
     * Get modify menu user input
     */
    private StaffMenuOptions getModifyMenuOption() {
        int choice = getMenuChoice();
        return StaffMenuOptions.fromModifyCode(choice);
    }

    /**
     * Process the selected modify option
     */
    private boolean processModifyOption(StaffMenuOptions option, Staff staffToModify, boolean modified) {
        switch (option) {
            case MODIFY_NAME:
                String name = staffService.getValidName(true);
                if (!name.isEmpty()) {
                    if (staffService.getConfirmation("Confirm update name to '" + name + "'? (yes/no): ")) {
                        staffToModify.setName(name);
                        view.displaySuccessMessage("Name updated successfully.");
                        modified = true;
                    } else {
                        view.displaySuccessMessage(StaffConstants.ACTION_CANCELLED);
                    }
                }
                break;

            case MODIFY_GENDER:
                String gender = staffService.getValidGender(true);
                if (staffService.getConfirmation("Confirm update gender to '" + gender + "'? (yes/no): ")) {
                    staffToModify.setGender(gender);
                    view.displaySuccessMessage("Gender updated successfully.");
                    modified = true;
                } else {
                    view.displaySuccessMessage(StaffConstants.ACTION_CANCELLED);
                }
                break;

            case MODIFY_POSITION:
                String position = staffService.getValidPosition(true);
                if (position != null && !position.isEmpty()) {
                    if (staffService.getConfirmation("Confirm update position to '" + position + "'? (yes/no): ")) {
                        staffToModify.setPosition(position);
                        view.displaySuccessMessage("Position updated successfully.");
                        modified = true;
                    } else {
                        view.displaySuccessMessage(StaffConstants.ACTION_CANCELLED);
                    }
                }
                break;

            case MODIFY_SALARY:
                Double salary = staffService.getValidSalary(true);
                if (salary != null) {
                    if (staffService.getConfirmation("Confirm update salary to RM" + salary + "? (yes/no): ")) {
                        staffToModify.setSalary(salary);
                        view.displaySuccessMessage("Salary updated successfully.");
                        modified = true;
                    } else {
                        view.displaySuccessMessage(StaffConstants.ACTION_CANCELLED);
                    }
                }
                break;

            case MODIFY_DEPARTMENT:
                String department = staffService.getValidDepartment(true);
                if (department != null && !department.isEmpty()) {
                    if (staffService.getConfirmation("Confirm update department to '" + department + "'? (yes/no): ")) {
                        staffToModify.setDepartment(department);
                        view.displaySuccessMessage("Department updated successfully.");
                        modified = true;
                    } else {
                        view.displaySuccessMessage(StaffConstants.ACTION_CANCELLED);
                    }
                }
                break;

            case FINISH_MODIFICATION:
                if (modified) {
                    view.displaySuccessMessage(StaffConstants.SUCCESS_STAFF_MODIFIED);
                } else {
                    view.displayErrorMessage("No modifications were made.");
                }
                break;

            default:
                view.displayErrorMessage(StaffConstants.ERROR_INVALID_CHOICE);
        }
        return modified;
    }

    /**
     * Delete staff
     */
    public void deleteStaff() {
        Staff staffToDelete = null;

        while (staffToDelete == null) {
            view.displayPrompt(StaffConstants.ID_TO_DELETE);
            String staffId = scanner.nextLine();

            if (staffId.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_ID_BLANK);
                continue;
            }

            // Search for staff
            for (Staff staff : staffList) {
                if (staff.getId().equalsIgnoreCase(staffId)) {
                    staffToDelete = staff;
                    break;
                }
            }

            if (staffToDelete == null) {
                view.displayErrorMessage(String.format(StaffConstants.ERROR_STAFF_NOT_FOUND, staffId));
            }
        }

        view.displayStaffDetails(staffToDelete);

        String confirmPrompt = String.format(StaffConstants.CONFIRM_DELETE, staffToDelete.getId());
        if (staffService.getConfirmation(confirmPrompt)) {
            staffList.remove(staffToDelete);
            view.displaySuccessMessage(String.format(StaffConstants.SUCCESS_STAFF_DELETED, staffToDelete.getId()));
        } else {
            view.displaySuccessMessage(StaffConstants.ACTION_CANCELLED);
        }
    }

    /**
     * Search a staff
     */
    public void searchStaff() {
        String searchQuery;

        while (true) {
            view.displayPrompt(StaffConstants.SEARCH_QUERY);
            searchQuery = scanner.nextLine();

            if (searchQuery.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_SEARCH_BLANK);
                continue;
            }

            if (searchQuery.trim().length() < 2) {
                view.displayErrorMessage(StaffConstants.ERROR_SEARCH_MIN_LENGTH);
                continue;
            }

            if (!searchQuery.matches(StaffConstants.REGEX_STAFFID)
                    && searchQuery.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_SEARCH_NUMERIC);
                continue;
            }

            break;
        }

        boolean found = false;

        view.displaySearchResultsHeader();

        for (Staff staff : staffList) {
            if (staff.getId().equalsIgnoreCase(searchQuery)
                    || staff.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                view.displayStaffDetails(staff);
                found = true;
            }
        }

        if (!found) {
            view.displayErrorMessage(StaffConstants.ERROR_NO_MATCHING_STAFF);
        }
    }

    /**
     * Staff salary report with menu options
     */
    public void departmentSalaryReport() {
        displayReportMenu();
        StaffMenuOptions choice = getReportMenuOption();

        if (choice == null) {
            view.displayErrorMessage(StaffConstants.ERROR_INVALID_CHOICE);
            return;
        }

        switch (choice) {
            case OVERALL_REPORT:
                displayOverallReport();
                break;
            case DEPARTMENT_REPORT:
                displayDepartmentReport();
                break;
            case BACK_TO_MENU:
                break;
            default:
                break;
        }
    }

    /**
     * Display report menu options from enum
     */
    private void displayReportMenu() {
        System.out.println("\n====================================");
        System.out.println("        SELECT REPORT TYPE");
        System.out.println("====================================");
        for (StaffMenuOptions opt : StaffMenuOptions.getReportMenuOptions()) {
            System.out.printf(" %d -> %s%n", opt.getCode(), opt.getDescription());
        }
        System.out.println("====================================");
    }

    /**
     * Get report menu option from user input
     */
    private StaffMenuOptions getReportMenuOption() {
        view.displayPrompt("Enter choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return StaffMenuOptions.fromReportCode(choice);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Display overall staff report
     */
    private void displayOverallReport() {
        if (staffList.isEmpty()) {
            view.displayErrorMessage(StaffConstants.ERROR_NO_STAFF_AVAILABLE);
            return;
        }

        double totalSalary = 0;
        for (Staff staff : staffList) {
            totalSalary += staff.getSalary();
        }

        System.out.println("\n" + StaffConstants.REPORT_SEPARATOR);
        System.out.println(StaffConstants.REPORT_TITLE_OVERALL);
        System.out.println(StaffConstants.REPORT_SEPARATOR);
        System.out.printf(StaffConstants.TABLE_STAFF_HEADER_FORMAT + "%n",
                "No", "Staff ID", "Name", "Gender", "Position", "Salary (RM)", "Department");
        System.out.println(StaffConstants.REPORT_SEPARATOR);

        for (int i = 1; i < staffList.size(); i++) {
            Staff staff = staffList.get(i);
            System.out.printf(StaffConstants.TABLE_STAFF_ROW_FORMAT + "%n",
                    i,
                    staff.getId(),
                    staff.getName(),
                    staff.getGender(),
                    staff.getPosition(),
                    staff.getSalary(),
                    staff.getDepartment());
        }

        System.out.println(StaffConstants.REPORT_SEPARATOR);
        System.out.printf("Total Staff: %d | Total Salary: RM %.2f%n", staffList.size(), totalSalary);
        System.out.println(StaffConstants.REPORT_SEPARATOR + "\n");
    }

    /**
     * Display specific department report
     */
    private void displayDepartmentReport() {

        view.displayExistingDepartments(getExistingDepartments());

        String departmentName;

        while (true) {
            view.displayPrompt(StaffConstants.DEPARTMENT_REPORT);
            departmentName = scanner.nextLine();

            if (departmentName.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_BLANK);
                continue;
            }

            if (departmentName.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_CONTAINS_NUMBERS);
                continue;
            }

            boolean departmentExists = false;
            for (String dept : getExistingDepartments()) {
                if (dept.equalsIgnoreCase(departmentName)) {
                    departmentName = dept;
                    departmentExists = true;
                    break;
                }
            }

            if (!departmentExists) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_NOT_EXIST);
                continue;
            }

            break;
        }

        double totalSalary = 0;
        List<Staff> staffInDepartment = new ArrayList<>();

        for (Staff staff : staffList) {
            if (staff.getDepartment().equalsIgnoreCase(departmentName)) {
                staffInDepartment.add(staff);
                totalSalary += staff.getSalary();
            }
        }

        System.out.println("\n" + StaffConstants.REPORT_SEPARATOR);
        System.out.printf(StaffConstants.REPORT_TITLE_DEPARTMENT + "%n", departmentName);
        System.out.println(StaffConstants.REPORT_SEPARATOR);
        System.out.printf(StaffConstants.TABLE_STAFF_HEADER_FORMAT + "%n",
                "No", "Staff ID", "Name", "Gender", "Position", "Salary (RM)", "Department");
        System.out.println(StaffConstants.REPORT_SEPARATOR);

        for (int i = 0; i < staffInDepartment.size(); i++) {
            Staff staff = staffInDepartment.get(i);
            System.out.printf(StaffConstants.TABLE_STAFF_ROW_FORMAT + "%n",
                    i + 1,
                    staff.getId(),
                    staff.getName(),
                    staff.getGender(),
                    staff.getPosition(),
                    staff.getSalary(),
                    staff.getDepartment());
        }

        System.out.println(StaffConstants.REPORT_SEPARATOR);
        System.out.printf("Total Staff in %s: %d | Total Salary: RM %.2f%n",
                departmentName, staffInDepartment.size(), totalSalary);
        System.out.println(StaffConstants.REPORT_SEPARATOR + "\n");
    }

    /**
     * Get list of existing departments from staff list
     */
    private List<String> getExistingDepartments() {
        List<String> departments = new ArrayList<>();
        for (Staff staff : staffList) {
            if (!departments.contains(staff.getDepartment())) {
                departments.add(staff.getDepartment());
            }
        }
        return departments;
    }

    /**
     * Display single staff details
     * 
     * @param staff
     */
    public void displayStaffDetails(Staff staff) {
        view.displayStaffDetails(staff);
    }

    /**
     * Add the staffby intilise them here through StaffData
     */
    public void retrieveStaff() {
        staffList.addAll(StaffData.getStaffData());
    }

    /**
     * Exit the system
     */
    public void closeSystem() {
        view.displayExitMessage();
    }
}
