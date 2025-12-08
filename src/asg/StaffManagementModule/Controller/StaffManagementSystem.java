package asg.StaffManagementModule.Controller;

import asg.StaffManagementModule.Constants.StaffConstants;
import asg.StaffManagementModule.Constants.StaffData;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.View.StaffView;
// import asg.StaffManagementModule.Service.StaffService;

import java.util.ArrayList;
import java.util.InputMismatchException;
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
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println(StaffConstants.ERROR_INVALID_CHOICE);
                scanner.nextLine();
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

        // Name
        view.displayPrompt(StaffConstants.ENTER_NAME);
        String name = scanner.nextLine();

        // Gender
        String gender = getValidGender(false);

        // Position
        String position = getValidPosition(false);
        if (position == null) {
            return;
        }

        // Salary
        Double salary = getValidSalary(false);
        if (salary == null) {
            return;
        }

        // Department
        String department = getValidDepartment(false);
        if (department == null) {
            return;
        }

        // Checking required fields
        if (id.isEmpty() || name.isEmpty() || position.isEmpty() || department.isEmpty()) {
            view.displayErrorMessage(StaffConstants.ERROR_MANDATORY_FIELDS);
            return;
        }

        // If no issue, then can add the staff
        Staff newStaff = new Staff(id, name, gender, position, salary, department);
        staffList.add(newStaff);

        view.displaySuccessMessage(StaffConstants.SUCCESS_STAFF_ADDED);
    }

    // Move to services file later - Not sure yet, still thinking
    private String getValidStaffId() {
        String id;
        while (true) {
            view.displayPrompt(StaffConstants.ENTER_ID);
            id = scanner.nextLine();
            if (id.matches(StaffConstants.REGEX_NUMERIC)) {
                break;
            } else {
                view.displayErrorMessage(StaffConstants.ERROR_ID_NUMERIC);
            }
        }
        return id;
    }

    // Move to services file later - Not sure yet, still thinking
    private String getValidGender(boolean isModify) {
        String gender;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_GENDER : StaffConstants.ENTER_GENDER);
            gender = scanner.nextLine().toLowerCase();
            if (gender.equals(StaffConstants.GENDER_MALE) || gender.equals(StaffConstants.GENDER_FEMALE)) {
                return gender;
            } else {
                view.displayErrorMessage(StaffConstants.ERROR_GENDER_INVALID);
            }
        }
    }

    // Move to services file later - Not sure yet, still thinking
    private String getValidPosition(boolean isModify) {
        String position;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_POSITION : StaffConstants.ENTER_POSITION);
            position = scanner.nextLine();
            if (!position.matches(StaffConstants.REGEX_CONTAINS_NUMBERS)) {
                return position;
            } else {
                view.displayErrorMessage(StaffConstants.ERROR_POSITION_CONTAINS_NUMBERS);
            }
        }
    }

    // Move to services file later - Not sure yet, still thinking
    private Double getValidSalary(boolean isNewStaff) {
        while (true) {
            view.displayPrompt(isNewStaff ? StaffConstants.NEW_SALARY : StaffConstants.ENTER_SALARY);
            try {
                double salary = scanner.nextDouble();
                scanner.nextLine();
                if (isNewStaff && salary >= StaffData.MIN_SALARY) {
                    return salary;
                } else if (!isNewStaff && salary >= StaffData.MIN_SALARY) {
                    return salary;
                } else {
                    view.displayErrorMessage(
                            isNewStaff ? StaffConstants.ERROR_SALARY_ZERO : StaffConstants.ERROR_SALARY_NEGATIVE);
                }
            } catch (InputMismatchException e) {
                view.displayErrorMessage(StaffConstants.ERROR_INVALID_INPUT);
                scanner.nextLine();
            }
        }
    }

    // Move to services file later - Not sure yet, still thinking
    private String getValidDepartment(boolean isModify) {
        String department;
        while (true) {
            view.displayPrompt(isModify ? StaffConstants.NEW_DEPARTMENT : StaffConstants.ENTER_DEPARTMENT);
            department = scanner.nextLine();
            if (!department.matches(StaffConstants.REGEX_CONTAINS_NUMBERS)) {
                return department;
            } else {
                view.displayErrorMessage(StaffConstants.ERROR_DEPARTMENT_CONTAINS_NUMBERS);
            }
        }
    }

    /**
     * Modify staff details
     */
    public void modifyStaff() {
        view.displayPrompt(StaffConstants.ENTER_ID);
        String staffToModify = scanner.nextLine();
        boolean found = false;

        for (Staff staff : staffList) {

            // If the staff is being founded, then execute the modification
            if (staff.getId().equals(staffToModify)) {
                view.displayCurrentStaffHeader();
                displayStaffDetails(staff);

                // Name
                view.displayPrompt(StaffConstants.NEW_NAME);
                String name = scanner.nextLine();

                String gender = getValidGender(true);
                String position = getValidPosition(true);

                Double salary = getValidSalary(true);
                if (salary == null) {
                    return;
                }

                String department = getValidDepartment(true);

                // Checking required fields
                if (name.isEmpty() || position.isEmpty() || department.isEmpty()) {
                    view.displayErrorMessage(StaffConstants.ERROR_MANDATORY_FIELDS_MODIFY);
                    return;
                }

                // If everything is fine, then modify the staff details
                staff.setName(name);
                staff.setGender(gender);
                staff.setPosition(position);
                staff.setSalary(salary);
                staff.setDepartment(department);

                view.displaySuccessMessage(StaffConstants.SUCCESS_STAFF_MODIFIED);
                found = true;
                break;
            }
        }

        // If the input staff is not exist
        if (!found) {
            view.displayErrorMessage(String.format(StaffConstants.ERROR_STAFF_NOT_FOUND, staffToModify));
        }
    }

    /**
     * Delete staff
     */
    public void deleteStaff() {
        view.displayPrompt(StaffConstants.ID_TO_DELETE);
        String staffToDelete = scanner.nextLine();
        boolean found = false;

        for (Staff staff : staffList) {
            if (staff.getId().equals(staffToDelete)) {
                staffList.remove(staff);
                view.displaySuccessMessage(String.format(StaffConstants.SUCCESS_STAFF_DELETED, staffToDelete));
                found = true;
                break;
            }

            if (!found) {
                view.displayErrorMessage(String.format(StaffConstants.ERROR_STAFF_NOT_FOUND, staffToDelete));
            }
        }
    }

    /**
     * Search a staff
     */
    public void searchStaff() {
        // Display the search prompt
        view.displayPrompt(StaffConstants.SEARCH_QUERY);
        String searchQuery = scanner.nextLine();
        boolean found = false;

        // Search header display
        view.displaySearchResultsHeader();

        for (Staff staff : staffList) {
            // Modified id and name to getId() and getName() because of encapsulation
            // changes made in Staff.java
            if (staff.getId().equalsIgnoreCase(searchQuery) || staff.getName().contains(searchQuery)) {
                view.displayStaffDetails(staff);
                found = true;
            }
        }

        if (!found) {
            // Display no matching staff found message
            view.displayErrorMessage(StaffConstants.ERROR_NO_MATCHING_STAFF);
        }
    }

    /**
     * Make salary report of the whole department
     */
    public void departmentSalaryReport() {

        view.displayPrompt(StaffConstants.DEPARTMENT_REPORT);
        String departmentSalaryReport = scanner.nextLine();
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
