package asg.StaffManagementModule.Controller;

import asg.StaffManagementModule.Constants.StaffConstants;
import asg.StaffManagementModule.Constants.StaffData;
import asg.StaffManagementModule.Model.Staff;
import asg.StaffManagementModule.View.StaffView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StaffManagementSystem {

    public final ArrayList<Staff> staffList;
    public final Scanner scanner;
    private final StaffView view;

    public StaffManagementSystem() {
        this.staffList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Initialise the StaffView for Staff Module UI display
        this.view = new StaffView();
    }

    public static void main() {
        StaffManagementSystem staffSystem = new StaffManagementSystem();
        staffSystem.initialiseStaff();
        staffSystem.staffMenu();
    }

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
                case StaffConstants.REPORT_STAFF:
                    reportDepartment();
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

    // Pending changes!!!
    public void displayStaff() {
        if (staffList.isEmpty()) {
            System.out.println("No staff information available.");
        } else {
            System.out.println("Staff Details:");
            for (Staff staff : staffList) {

                System.out.println("ID: " + staff.getId());
                System.out.println("Name: " + staff.getName());
                System.out.println("Gender: " + staff.getGender());
                System.out.println("Position: " + staff.getPosition());
                System.out.println("Salary: " + staff.getSalary());
                System.out.println("Department: " + staff.getDepartment());
                System.out.println("-----------------------------");
            }
        }
    }

    // Pending changes!!!
    public void addStaff() {
        String id;
        while (true) {
            System.out.print("Enter ID: ");
            id = scanner.nextLine();
            if (id.matches("\\d+")) {
                break;
            } else {
                System.out.println("ID must contain numbers only. Please try again.");
            }
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String gender;
        while (true) {
            System.out.print("Enter Gender (male/female): ");
            gender = scanner.nextLine().toLowerCase();
            if (gender.equals("male") || gender.equals("female")) {
                break;
            } else {
                System.out.println("Gender must be 'male' or 'female'. Please try again.");
            }
        }

        System.out.print("Enter Position: ");
        String position = scanner.nextLine();

        if (position.matches(".*\\d+.*")) {
            System.out.println("Position cannot contain numbers. Please try again.");
            return;
        }

        double salary;
        while (true) {
            System.out.print("Enter Salary: ");
            try {
                salary = scanner.nextDouble();
                if (salary > 0) {
                    break;
                } else {
                    System.out.println("Salary cannot less than zero. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        if (department.matches(".*\\d+.*")) {
            System.out.println("Department cannot contain numbers. Please try again.");
            return;
        }

        if (id.isEmpty() || name.isEmpty() || position.isEmpty() || department.isEmpty()) {
            System.out.println("ID, Name, Position, and Department are mandatory fields and cannot be empty.");
            return;
        }

        Staff newStaff = new Staff(id, name, gender, position, salary, department);
        staffList.add(newStaff);
        System.out.println("Staff added successfully.");
    }

    // Pending changes!!!
    public void modifyStaff() {
        System.out.print("Enter ID of staff to modify: ");
        String staffIdToModify = scanner.nextLine();
        boolean found = false;

        for (Staff staff : staffList) {
            if (staff.id.equals(staffIdToModify)) {
                System.out.println("Current Staff Details:");
                displayStaffDetails(staff);

                System.out.print("Enter new Name: ");
                String name = scanner.nextLine();

                String gender;
                while (true) {
                    System.out.print("Enter new Gender (male/female): ");
                    gender = scanner.nextLine().toLowerCase();
                    if (gender.equals("male") || gender.equals("female")) {
                        break;
                    } else {
                        System.out.println("Gender must be 'male' or 'female'. Please try again.");
                    }
                }

                String position;
                while (true) {
                    System.out.print("Enter new Position: ");
                    position = scanner.nextLine();
                    if (!position.matches(".*\\d+.*")) {
                        break;
                    } else {
                        System.out.println("Position cannot contain numbers. Please try again.");
                    }
                }

                double salary;
                while (true) {
                    System.out.print("Enter new Salary: ");
                    try {
                        salary = scanner.nextDouble();
                        if (salary >= 0) {
                            break;
                        } else {
                            System.out.println("Salary cannot be negative. Please try again.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.nextLine();
                    }
                }
                scanner.nextLine();

                String department;
                while (true) {
                    System.out.print("Enter new Department: ");
                    department = scanner.nextLine();
                    if (!department.matches(".*\\d+.*")) {
                        break;
                    } else {
                        System.out.println("Department cannot contain numbers. Please try again.");
                    }
                }

                if (name.isEmpty() || position.isEmpty() || department.isEmpty()) {
                    System.out.println("Name, Position, and Department are mandatory fields and cannot be empty.");
                    return;
                }

                staff.name = name;
                staff.gender = gender;
                staff.position = position;
                staff.salary = salary;
                staff.department = department;

                System.out.println("Staff modified successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Staff with ID " + staffIdToModify + " not found.");
        }
    }

    // Pending changes!!!
    public void deleteStaff() {
        System.out.print("Enter ID of staff to delete: ");
        String staffIdToDelete = scanner.nextLine();
        boolean found = false;

        for (Staff staff : staffList) {
            if (staff.id.equals(staffIdToDelete)) {
                staffList.remove(staff);
                System.out.println("Staff with ID " + staffIdToDelete + " deleted successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Staff with ID " + staffIdToDelete + " not found.");
        }
    }

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

    // Pending changes!!!
    public void reportDepartment() {
        System.out.print("Enter Department to report: ");
        String departmentToReport = scanner.nextLine();
        double totalSalary = 0;

        System.out.println("Staff Report for Department: " + departmentToReport);

        for (Staff staff : staffList) {
            if (staff.department.equalsIgnoreCase(departmentToReport)) {
                displayStaffDetails(staff);
                totalSalary += staff.salary;
                System.out.println("-----------------------------");
            }
        }

        if (totalSalary > 0) {
            System.out.println("Total Salary for Department " + departmentToReport + ": " + totalSalary);
        } else {
            System.out.println("No staff found in Department " + departmentToReport);
        }
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
    public void initialiseStaff() {
        staffList.addAll(StaffData.getInitialStaffData());
    }

    /**
     * Exit the system
     */
    public void closeSystem() {
        view.displayExitMessage();
    }
}
