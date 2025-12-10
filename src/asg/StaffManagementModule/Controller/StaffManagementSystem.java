package asg.StaffManagementModule.Controller;

import asg.StaffManagementModule.Constants.StaffConstants;
import asg.StaffManagementModule.Constants.StaffData;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.View.StaffView;
// import asg.StaffManagementModule.Service.StaffService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffManagementSystem {

    public final ArrayList<Staff> staffList;
    public final Scanner scanner;
    private final StaffView view;
    // private final StaffService staffService;

    public StaffManagementSystem() {
        this.staffList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Initialise the StaffView for Staff Module UI display
        this.view = new StaffView();
        // this.staffService = new StaffService(view);
    }

    public static void main() {
        StaffManagementSystem staffSystem = new StaffManagementSystem();
        staffSystem.retrieveStaff();
        staffSystem.staffMenu();
    }

    /**
     * Staff main menu
     */
    public void staffMenu() {
        int option;

        do {
            // Display the menu
            view.displayMenu();
            option = getMenuChoice();

            // Use constants instead of original magic numbers (For the option, may need to
            // change follow like practical 2)
            switch (option) {
                case StaffConstants.DISPLAY_STAFF:
                    displayStaff();
                    break;
                case StaffConstants.ADD_STAFF:
                    addStaff();
                    break;
                case StaffConstants.UPDATE_STAFF:
                    modifyStaff();
                    break;
                case StaffConstants.DELETE_STAFF:
                    deleteStaff();
                    break;
                case StaffConstants.SEARCH_STAFF:
                    searchStaff();
                    break;
                case StaffConstants.SALARY_REPORT:
                    departmentSalaryReport();
                    break;
                case StaffConstants.EXIT_STAFF_MODULE:
                    closeSystem();
                    break;
                default:
                    view.displayErrorMessage(StaffConstants.ERROR_INVALID_CHOICE);
            }
        } while (option != StaffConstants.EXIT_STAFF_MODULE);
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
        String id = getValidStaffId();
        String name = getValidName(false);
        String gender = getValidGender(false);

        String position = getValidPosition(false);
        if (position == null) {
            return;
        }

        Double salary = getValidSalary(false);
        if (salary == null) {
            return;
        }

        String department = getValidDepartment(false);
        if (department == null) {
            return;
        }

        // If no issue, then can add the staff
        Staff newStaff = new Staff(id, name, gender, position, salary, department);
        staffList.add(newStaff);

        view.displaySuccessMessage(StaffConstants.SUCCESS_STAFF_ADDED);
    }

    // Validation for staff id
    private String getValidStaffId() {
        String id;
        while (true) {
            view.displayPrompt(StaffConstants.ENTER_ID);
            id = scanner.nextLine();
            if (!id.matches(StaffConstants.REGEX_STAFFID)) {
                view.displayErrorMessage(StaffConstants.ERROR_ID_NUMERIC);
                continue;
            }
            // Check if ID already exists
            if (isStaffIdExists(id)) {
                view.displayErrorMessage(String.format(StaffConstants.ERROR_ID_EXISTS, id));
                continue;
            }
            break;
        }
        return id;
    }

    // Check if staff ID already exists in the system
    private boolean isStaffIdExists(String id) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Validate staff name
    private String getValidName(boolean isModify) {
        String name;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_NAME : StaffConstants.ENTER_NAME);
            name = scanner.nextLine();

            if (name.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_NAME_BLANK);
                continue;
            }

            if (name.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_NAME_CONTAINS_NUMBERS);
                continue;
            }

            return name;
        }
    }

    // Validate gender
    private String getValidGender(boolean isModify) {
        String gender;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_GENDER : StaffConstants.ENTER_GENDER);
            gender = scanner.nextLine().toLowerCase();
            if (gender.equals(StaffConstants.GENDER_MALE) || gender.equals(StaffConstants.GENDER_FEMALE)) {
                return gender.substring(0, 1).toUpperCase() + gender.substring(1);
            } else {
                view.displayErrorMessage(StaffConstants.ERROR_GENDER_INVALID);
            }
        }
    }

    // Validate position
    private String getValidPosition(boolean isModify) {
        String position;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_POSITION : StaffConstants.ENTER_POSITION);
            position = scanner.nextLine();

            if (position.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_POSITION_BLANK);
                continue;
            }

            if (position.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_POSITION_CONTAINS_NUMBERS);
                continue;
            }

            return position;
        }
    }

    // Validate salary
    private Double getValidSalary(boolean isModify) {
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_SALARY : StaffConstants.ENTER_SALARY);
            String input = scanner.nextLine();

            if (input.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_SALARY_BLANK);
                continue;
            }

            try {
                double salary = Double.parseDouble(input);
                if (salary >= StaffData.MIN_SALARY) {
                    return salary;
                } else {
                    view.displayErrorMessage(StaffConstants.ERROR_SALARY_ZERO);
                }
            } catch (NumberFormatException e) {
                view.displayErrorMessage(StaffConstants.ERROR_SALARY_NUMERIC);
            }
        }
    }

    // Validate department
    private String getValidDepartment(boolean isModify) {
        String department;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_DEPARTMENT : StaffConstants.ENTER_DEPARTMENT);
            department = scanner.nextLine();

            if (department.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_BLANK);
                continue;
            }

            if (department.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_CONTAINS_NUMBERS);
                continue;
            }

            return department;
        }
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
                if (staff.getId().equals(staffId)) {
                    staffToModify = staff;
                    break;
                }
            }

            if (staffToModify == null) {
                view.displayErrorMessage(String.format(StaffConstants.ERROR_STAFF_NOT_FOUND, staffId));
            }
        }

        // Staff found, proceed modification
        view.displayCurrentStaffHeader();
        displayStaffDetails(staffToModify);

        int choice;
        boolean modified = false;

        do {
            view.displayModifyMenu();
            choice = getMenuChoice();

            // May require switch to enum
            switch (choice) {
                case StaffConstants.MODIFY_NAME:
                    String name = getValidName(true);
                    if (!name.isEmpty()) {
                        staffToModify.setName(name);
                        view.displaySuccessMessage("Name updated successfully.");
                        modified = true;
                    } else {
                        view.displayErrorMessage("Name cannot be empty.");
                    }
                    break;

                case StaffConstants.MODIFY_GENDER:
                    String gender = getValidGender(true);
                    staffToModify.setGender(gender);
                    view.displaySuccessMessage("Gender updated successfully.");
                    modified = true;
                    break;

                case StaffConstants.MODIFY_POSITION:
                    String position = getValidPosition(true);
                    if (position != null && !position.isEmpty()) {
                        staffToModify.setPosition(position);
                        view.displaySuccessMessage("Position updated successfully.");
                        modified = true;
                    } else {
                        view.displayErrorMessage("Position cannot be empty.");
                    }
                    break;

                case StaffConstants.MODIFY_SALARY:
                    Double salary = getValidSalary(true);
                    if (salary != null) {
                        staffToModify.setSalary(salary);
                        view.displaySuccessMessage("Salary updated successfully.");
                        modified = true;
                    }
                    break;

                case StaffConstants.MODIFY_DEPARTMENT:
                    String department = getValidDepartment(true);
                    if (department != null && !department.isEmpty()) {
                        staffToModify.setDepartment(department);
                        view.displaySuccessMessage("Department updated successfully.");
                        modified = true;
                    } else {
                        view.displayErrorMessage("Department cannot be empty.");
                    }
                    break;

                case StaffConstants.FINISH_MODIFICATION:
                    if (modified) {
                        view.displaySuccessMessage(StaffConstants.SUCCESS_STAFF_MODIFIED);
                    } else {
                        view.displayErrorMessage("No modifications were made.");
                    }
                    break;

                default:
                    view.displayErrorMessage(StaffConstants.ERROR_INVALID_CHOICE);
            }

        } while (choice != StaffConstants.FINISH_MODIFICATION);
    }

    /**
     * Delete staff
     */
    public void deleteStaff() {
        String staffToDelete;

        while (true) {
            view.displayPrompt(StaffConstants.ID_TO_DELETE);
            staffToDelete = scanner.nextLine();
            if (staffToDelete.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_ID_BLANK);
            } else {
                break;
            }
        }

        boolean found = false;

        for (Staff staff : staffList) {
            if (staff.getId().equals(staffToDelete)) {
                staffList.remove(staff);
                view.displaySuccessMessage(String.format(StaffConstants.SUCCESS_STAFF_DELETED, staffToDelete));
                found = true;
                break;
            }
        }

        if (!found) {
            view.displayErrorMessage(String.format(StaffConstants.ERROR_STAFF_NOT_FOUND, staffToDelete));
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
     * Make salary report of the whole department
     */
    public void departmentSalaryReport() {
        // Display existing departments
        view.displayExistingDepartments(getExistingDepartments());

        String departmentSalaryReport;

        while (true) {
            view.displayPrompt(StaffConstants.DEPARTMENT_REPORT);
            departmentSalaryReport = scanner.nextLine();

            if (departmentSalaryReport.trim().isEmpty()) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_BLANK);
                continue;
            }

            if (departmentSalaryReport.matches(StaffConstants.REGEX_POSITION_DEPT)) {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_CONTAINS_NUMBERS);
                continue;
            }

            boolean departmentExists = false;
            for (String dept : getExistingDepartments()) {
                if (dept.equalsIgnoreCase(departmentSalaryReport)) {
                    departmentSalaryReport = dept; // Use the exact case from system
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
            if (staff.getDepartment().equalsIgnoreCase(departmentSalaryReport)) {
                staffInDepartment.add(staff);
                totalSalary += staff.getSalary();
            }
        }

        view.displayDepartmentReport(departmentSalaryReport, staffInDepartment, totalSalary);
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
